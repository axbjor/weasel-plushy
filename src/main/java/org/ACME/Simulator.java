package org.ACME;

import org.ACME.insource.HouseFactory;
import org.ACME.outsource.ContractorAssemblyLine;
import org.ACME.outsource.ContractorFactory;
import org.ACME.outsource.LoadingDock;

import java.util.HashMap;
import java.util.LinkedList;

/** Master class for simulating production */
public class Simulator {
    private final LinkedList<HouseFactory> houseFactories;
    private final LinkedList<ContractorFactory> contractorFactories;
    private final HashMap<String, Integer> efficiencyCounter;
    private final Calendar calendar;
    private String sabotageTarget;
    private int sabotageDuration;
    private int sabotagePeriod;
    private int alterMonth;
    private float timeMultiplier;
    private float lossChance;

    public Simulator() {
        this.houseFactories = new LinkedList<>();
        this.contractorFactories = new LinkedList<>();
        this.efficiencyCounter = new HashMap<>();
        this.calendar = new Calendar();
    }

    protected void setup() {
        // Identify parts needed for products
        HashMap<String, Integer> weaselPlushieRecipe = new HashMap<>();
        weaselPlushieRecipe.put("Fur", 1);
        weaselPlushieRecipe.put("Filling", 1);
        weaselPlushieRecipe.put("Snout", 1);
        weaselPlushieRecipe.put("Eye button", 2);

        // Build house facilities
        houseFactories.add(new HouseFactory());
        houseFactories.getLast().createAssemblyLine("Weasel plushie", 500, 1000000, weaselPlushieRecipe);
        houseFactories.getLast().createWarehouse();

        // Prepare efficiency rating
        for (HouseFactory houseFactory : houseFactories) {
            for (String assemblyLine : houseFactory.getProductionActivity().keySet()) {
                efficiencyCounter.put(assemblyLine, 0);
            }
        }

        // Universal map of product packaging sizes
        HashMap<String, Integer> packagingSizeReference = new HashMap<>();
        packagingSizeReference.put("Fur", 200);
        packagingSizeReference.put("Filling", 10);
        packagingSizeReference.put("Snout", 100);
        packagingSizeReference.put("Eye button", 300);

        // Build contractor facilities
        contractorFactories.add(new ContractorFactory(houseFactories.getLast()));
        contractorFactories.getLast().createAssemblyLine("Fur", 40);
        contractorFactories.getLast().createLoadingDock(packagingSizeReference, 10);
        contractorFactories.getLast().createWarehouse();

        contractorFactories.add(new ContractorFactory(houseFactories.getLast()));
        contractorFactories.getLast().createAssemblyLine("Filling", 45);
        contractorFactories.getLast().createLoadingDock(packagingSizeReference, 12);
        contractorFactories.getLast().createWarehouse();

        contractorFactories.add(new ContractorFactory(houseFactories.getLast()));
        contractorFactories.getLast().createAssemblyLine("Snout", 60);
        contractorFactories.getLast().createLoadingDock(packagingSizeReference, 8);
        contractorFactories.getLast().createWarehouse();

        contractorFactories.add(new ContractorFactory(houseFactories.getLast()));
        contractorFactories.getLast().createAssemblyLine("Eye button", 90);
        contractorFactories.getLast().createLoadingDock(packagingSizeReference, 14);
        contractorFactories.getLast().createWarehouse();

        // Note production start date
        int year = 2023;
        int month = 4;
        int day = 1;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        calendar.setDate(year, month, day, hours, minutes, seconds);

        // Sabotage
        this.sabotageTarget = "Snout";
        this.sabotageDuration = 12; // In hours
        int sabotageAttemptPeriod = 168; // In hours | 7 days = 7 * 24h = 168h
        float sabotageSuccessChance = 0.1f;
        this.sabotagePeriod = (int) (sabotageAttemptPeriod / sabotageSuccessChance);

        // Different circumstance month
        this.alterMonth = 11; // December
        // Delivery speed multiplier
        this.timeMultiplier = 0.5f;
        // Delivery loss rate
        this.lossChance = 0.2f;
    }

    protected void simulateProduction() {
        System.out.printf("Production started on:%n%s%n", calendar.getDate());
        HashMap<String, Integer> reachedGoals = new HashMap<>();
        int counter = 0;
        boolean run = true;
        while (run) {
            for (ContractorFactory contractorFactory : contractorFactories) {
                // Sabotage
                if (counter % sabotagePeriod == 0) {
                    sabotage(contractorFactory);
                }
                // Weather
                LoadingDock loadingDock = contractorFactory.getLoadingDock();
                if (calendar.getMonth() == alterMonth) {
                    loadingDock.setLossRate(lossChance);
                    loadingDock.setTimeMultiplier(timeMultiplier);
                }
                else {
                    loadingDock.setLossRate(0);
                    loadingDock.setTimeMultiplier(1);
                }
                // Work
                contractorFactory.work();
            }
            for (HouseFactory houseFactory : houseFactories) {
                // Work
                houseFactory.work();
                // Note efficiency
                incrementEfficiencyCounter(houseFactory);
                // Check for reached goals
                reachedGoals = houseFactory.getReachedGoals();
                if (!reachedGoals.isEmpty()) { // Stop simulation on first production goal
                    run = false;
                    break;
                }
            }
            if (run) {
                counter++;
                calendar.addHour();
            }
        }
        // Simulation result
        for (String product : reachedGoals.keySet()) {
            float efficiency = (float) efficiencyCounter.get(product) * 100 / counter;
            System.out.printf("%nProduction goal of %d %ss with efficiency rate %.2f%% reached on:%n%s%n", reachedGoals.get(product), product, efficiency, calendar.getDate());
        }
    }

    private void sabotage(ContractorFactory contractorFactory) {
        for (ContractorAssemblyLine contractorAssemblyLine : contractorFactory.getAssemblyLines()) {
            if (contractorAssemblyLine.getProductName().equals(sabotageTarget)) {
                contractorAssemblyLine.setSabotageTimer(sabotageDuration);
            }
        }
    }

    private void incrementEfficiencyCounter(HouseFactory ourFactory) {
        HashMap<String, Boolean> activity = ourFactory.getProductionActivity();
        for (String product : activity.keySet()) {
            if (activity.get(product)) {
                efficiencyCounter.replace(product, efficiencyCounter.get(product) + 1);
            }
        }
    }
}
