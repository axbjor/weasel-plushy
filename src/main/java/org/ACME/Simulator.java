package org.ACME;

import org.ACME.insource.InHouseManufactory;
import org.ACME.outsource.ContractorManufactory;

import java.util.HashMap;
import java.util.LinkedList;

public class Simulator {
    Calendar calendar;
    LinkedList<InHouseManufactory> inHouseFactories;
    LinkedList<ContractorManufactory> contractorFactories;

    protected void setup() {
        // Identify part requirements for products
        HashMap<String, Integer> weaselPlushyRequirements = new HashMap<>();
        weaselPlushyRequirements.put("Fur", 1);
        weaselPlushyRequirements.put("Filling", 1);
        weaselPlushyRequirements.put("Snout", 1);
        weaselPlushyRequirements.put("Eye button", 2);

        // Hire subcontractors and build their facilities
        contractorFactories = new LinkedList<>();

        contractorFactories.add(new ContractorManufactory());
        contractorFactories.getLast().createAssemblyLine("Fur", 40);
        contractorFactories.getLast().createLoadingDock(200, 10);
        contractorFactories.getLast().createWarehouse();

        contractorFactories.add(new ContractorManufactory());
        contractorFactories.getLast().createAssemblyLine("Filling", 45);
        contractorFactories.getLast().createLoadingDock(10, 12);
        contractorFactories.getLast().createWarehouse();

        contractorFactories.add(new ContractorManufactory());
        contractorFactories.getLast().createAssemblyLine("Snout", 60);
        contractorFactories.getLast().createLoadingDock(100, 8);
        contractorFactories.getLast().createWarehouse();

        contractorFactories.add(new ContractorManufactory());
        contractorFactories.getLast().createAssemblyLine("Eye button", 90);
        contractorFactories.getLast().createLoadingDock(300, 14);
        contractorFactories.getLast().createWarehouse();

        // Build our facilities
        inHouseFactories = new LinkedList<>();
        inHouseFactories.add(new InHouseManufactory());
        inHouseFactories.getLast().createAssemblyLine("Weasel plushy", 500);
        inHouseFactories.getLast().createWarehouse();

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
        // while True
        while (true) {

            for (int i = 0; i < contractorFactories.size(); i++) {
                contractorFactories.get(i).runAssemblyLines();
            }
        }

            // for each subcontractor

                // Increase part count

                // If enough for delivery, send

                // for each delivery

                    // If time counter = 0, increase factory part count & delete delivery

                    // decrease time counter

            // Calculate size of maximum product batch (Hard cap 500)

            // Produce batch

            // Increase finished product count

            // Decrease part counts

            // If finished product count >= 1 000 000, break

            // increase time by 1h

        // Record time
    }
}
