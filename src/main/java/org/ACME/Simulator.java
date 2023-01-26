package org.ACME;

import org.ACME.insource.HouseFactory;
import org.ACME.outsource.ContractorFactory;

import java.util.HashMap;
import java.util.LinkedList;

/** Master class for simulating production */
public class Simulator {
    private LinkedList<HouseFactory> houseFactories;
    private LinkedList<ContractorFactory> contractorFactories;
    private HashMap<String, Integer> efficiencyCounter;
    private Calendar calendar;
    private String lossRate;

    public Simulator() {
        this.houseFactories = new LinkedList<>();
        this.contractorFactories = new LinkedList<>();
        this.efficiencyCounter = new HashMap<>();
        this.calendar = new Calendar();
    }

    protected void setup() {
        // Identify parts needed for products
        HashMap<String, Integer> weaselPlushieParts = new HashMap<>();
        weaselPlushieParts.put("Fur", 1);
        weaselPlushieParts.put("Filling", 1);
        weaselPlushieParts.put("Snout", 1);
        weaselPlushieParts.put("Eye button", 2);

        // Build house facilities
        houseFactories.add(new HouseFactory());
        houseFactories.getLast().createAssemblyLine("Weasel plushie", 500, 1000000, weaselPlushieParts);
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
        contractorFactories.getLast().createAssemblyLine("Fur", 40, Integer.MAX_VALUE, null); // Contractor factories produce infinitely
        contractorFactories.getLast().createLoadingDock(packagingSizeReference, 10);
        contractorFactories.getLast().createWarehouse();

        contractorFactories.add(new ContractorFactory(houseFactories.getLast()));
        contractorFactories.getLast().createAssemblyLine("Filling", 45, Integer.MAX_VALUE, null);
        contractorFactories.getLast().createLoadingDock(packagingSizeReference, 12);
        contractorFactories.getLast().createWarehouse();

        contractorFactories.add(new ContractorFactory(houseFactories.getLast()));
        contractorFactories.getLast().createAssemblyLine("Snout", 60, Integer.MAX_VALUE, null);
        contractorFactories.getLast().createLoadingDock(packagingSizeReference, 8);
        contractorFactories.getLast().createWarehouse();

        contractorFactories.add(new ContractorFactory(houseFactories.getLast()));
        contractorFactories.getLast().createAssemblyLine("Eye button", 90, Integer.MAX_VALUE, null);
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

        //

        // Delivery loss rate in December
        this.lossRate = "1/5"; // Input as smallest fraction to improve simulation accuracy
    }

    protected void simulateProduction() {
        HashMap<String, Integer> reachedGoals = new HashMap<>();
        int counter = 0;
        boolean run = true;
        while (run) {
            for (ContractorFactory contractorFactory : contractorFactories) {
                if (calendar.getMonth() == 11) { // DECEMBER
                    contractorFactory.getLoadingDock().setLossRate(lossRate);
                }
                else {
                    contractorFactory.getLoadingDock().setLossRate("0/100");
                }
                if (counter % 7 == 0 && counter % 100 == 0) {
                    contractorFactory.getLoadingDock().setLossRate(lossRate);
                }
                contractorFactory.work();
            }
            for (HouseFactory houseFactory : houseFactories) {
                houseFactory.work();
                incrementEfficiencyCounter(houseFactory);
                reachedGoals = houseFactory.getReachedGoals();
                System.out.println("COUNTER "+counter);
                if (!reachedGoals.isEmpty()) {
                    run = false;
                    break;
                }
            }
            if (run) {
                calendar.addHour();
                counter++;
            }
        }

        for (String product : reachedGoals.keySet()) {
            float efficiency = (float) efficiencyCounter.get(product) * 100 / counter;
            System.out.printf("Production goal of %d %ss reached on %s with efficiency rate: %.1f%%!%n", reachedGoals.get(product), product, calendar.getDate(), efficiency);
            System.out.println(houseFactories.getLast().counter);
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
