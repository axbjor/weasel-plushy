package org.ACME;

import org.ACME.insource.InHouseManufactory;
import org.ACME.outsource.ContractorManufactory;

import java.util.HashMap;
import java.util.LinkedList;

public class Simulator {
    Calendar calendar;
    HashMap<String, Integer> efficiencyCounter;
    LinkedList<InHouseManufactory> inHouseFactories;
    LinkedList<ContractorManufactory> contractorFactories;

    protected void setup() {
        // Identify part requirements for products
        HashMap<String, Integer> weaselPlushyRequirements = new HashMap<>();
        weaselPlushyRequirements.put("Fur", 1);
        weaselPlushyRequirements.put("Filling", 1);
        weaselPlushyRequirements.put("Snout", 1);
        weaselPlushyRequirements.put("Eye button", 2);

        // Build our facilities
        inHouseFactories = new LinkedList<>();

        inHouseFactories.add(new InHouseManufactory());
        inHouseFactories.getLast().createAssemblyLine("Weasel plushy", 500, 1000000, weaselPlushyRequirements);
        inHouseFactories.getLast().createWarehouse();

        // Prepare efficiency counting
        efficiencyCounter = new HashMap<>();
        for (InHouseManufactory ourFactory : inHouseFactories) {
            for (String assemblyLine : ourFactory.getProductionActivity().keySet()) {
                efficiencyCounter.put(assemblyLine, 0);
            }
        }

        // Universal map of how much of a product fit into a delivery
        HashMap<String, Integer> universalCargoSizeReference = new HashMap<>();
        universalCargoSizeReference.put("Fur", 200);
        universalCargoSizeReference.put("Filling", 10);
        universalCargoSizeReference.put("Snout", 100);
        universalCargoSizeReference.put("Eye button", 300);

        // Build subcontractor facilities
        contractorFactories = new LinkedList<>();

        contractorFactories.add(new ContractorManufactory(inHouseFactories.getLast()));
        contractorFactories.getLast().createAssemblyLine("Fur", 40, Integer.MAX_VALUE, null);
        contractorFactories.getLast().createLoadingDock(universalCargoSizeReference, 10);
        contractorFactories.getLast().createWarehouse();

        contractorFactories.add(new ContractorManufactory(inHouseFactories.getLast()));
        contractorFactories.getLast().createAssemblyLine("Filling", 45, Integer.MAX_VALUE, null);
        contractorFactories.getLast().createLoadingDock(universalCargoSizeReference, 12);
        contractorFactories.getLast().createWarehouse();

        contractorFactories.add(new ContractorManufactory(inHouseFactories.getLast()));
        contractorFactories.getLast().createAssemblyLine("Snout", 60, Integer.MAX_VALUE, null);
        contractorFactories.getLast().createLoadingDock(universalCargoSizeReference, 8);
        contractorFactories.getLast().createWarehouse();

        contractorFactories.add(new ContractorManufactory(inHouseFactories.getLast()));
        contractorFactories.getLast().createAssemblyLine("Eye button", 90, Integer.MAX_VALUE, null);
        contractorFactories.getLast().createLoadingDock(universalCargoSizeReference, 14);
        contractorFactories.getLast().createWarehouse();

        // Note production start date
        int year = 2023;
        int month = 4;
        int day = 1;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        calendar = new Calendar();
        calendar.setDate(year, month, day, hours, minutes, seconds);
    }

    protected void simulateProduction() {
        HashMap<String, Integer> reachedProductionGoals = new HashMap<>();
        int counter = 0;
        boolean runSimulation = true;
        while (runSimulation) {
            for (ContractorManufactory contractor : contractorFactories) {
                contractor.work();
            }
            for (InHouseManufactory ourFactory : inHouseFactories) {
                ourFactory.work();
                updateEfficiencyCounter(ourFactory);
                reachedProductionGoals = ourFactory.CheckForReachedProductionGoals();
                System.out.println("COUNTER "+counter);
                if (!reachedProductionGoals.isEmpty()) {
                    runSimulation = false;
                    break;
                }
            }
            if (runSimulation) {
                calendar.addHour();
                counter++;
            }
        }

        for (String product : reachedProductionGoals.keySet()) {
            float efficiency = (float) efficiencyCounter.get(product) * 100 / counter;
            System.out.printf("Production goal of %d %ss reached on %s with efficiency rate: %.1f%%!%n", reachedProductionGoals.get(product), product, calendar.getDate(), efficiency);
        }
    }

    private void updateEfficiencyCounter(InHouseManufactory ourFactory) {
        HashMap<String, Boolean> activity = ourFactory.getProductionActivity();
        for (String product : activity.keySet()) {
            if (activity.get(product)) {
                efficiencyCounter.replace(product, efficiencyCounter.get(product) + 1);
            }
        }
    }
}
