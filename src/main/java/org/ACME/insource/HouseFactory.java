package org.ACME.insource;

import org.ACME.common.Factory;
import org.ACME.common.Delivery;

import java.util.ArrayList;
import java.util.HashMap;

/** ACME factories produce end-products that have production goals */
public class HouseFactory extends Factory {
    protected ArrayList<HouseAssemblyLine> assemblyLines;

    public HouseFactory() {
        this.assemblyLines = new ArrayList<>();
    }

    public void createAssemblyLine(String name, int maxProductionRate, int productionGoal, HashMap<String, Integer> productRecipe) {
        assemblyLines.add(new HouseAssemblyLine(name, maxProductionRate, productionGoal, productRecipe));
    }

    @Override
    public void work() {
        HashMap<String, Integer> inventory = warehouse.getInventory();
        for (HouseAssemblyLine assemblyLine : assemblyLines) {
            // Calculate max production rate
            HashMap<String, Integer> productRecipe = assemblyLine.getProductRecipe();
            int productionRate = calculateMaxProduction(inventory, productRecipe);
            assemblyLine.setProductionRate(productionRate);
            if (productionRate == 0) {
                continue;
            }
            // Retrieve required parts
            HashMap<String, Integer> requiredParts = calculateRequiredParts(productionRate, productRecipe);
            if (!warehouse.retrieve(requiredParts)) {
                continue;
            }
            // Produce and store
            warehouse.store(assemblyLine.produce());
        }
    }

    private int calculateMaxProduction(HashMap<String, Integer> inventory, HashMap<String, Integer> productRecipe) {
        int maxProduction = Integer.MAX_VALUE;
        for (String part : productRecipe.keySet()) {
            if (!(inventory.containsKey(part))) {
                return 0;
            }
            int partSufficiency = Math.floorDiv(inventory.get(part), productRecipe.get(part));
            if (partSufficiency < maxProduction) {
                maxProduction = partSufficiency;
            }
        }
        return maxProduction;
    }

    private HashMap<String, Integer> calculateRequiredParts(int productionRate, HashMap<String, Integer> productRecipe) {
        HashMap<String, Integer> requiredParts = new HashMap<>();
        for (String part : productRecipe.keySet()) {
            requiredParts.put(part, productionRate * productRecipe.get(part));
        }
        return requiredParts;
    }

    public void storeDeliveries(ArrayList<Delivery> arrivedDeliveries) {
        for (Delivery delivery : arrivedDeliveries) {
            warehouse.store(delivery.getCargo());
        }
    }

    public HashMap<String, Boolean> getProductionActivity() {
        HashMap<String, Boolean> assemblyLineActiveness = new HashMap<>();
        for (HouseAssemblyLine assemblyLine : assemblyLines) {
            String product = assemblyLine.getProductName();
            if (assemblyLine.isActive()) {
                assemblyLineActiveness.put(product, true);
            }
            else {
                assemblyLineActiveness.put(product, false);
            }
        }
        return assemblyLineActiveness;
    }

    public HashMap<String, Integer> getReachedGoals() {
        HashMap<String, Integer> reachedGoals = new HashMap<>();
        for (HouseAssemblyLine assemblyLine : assemblyLines) {
            String product = assemblyLine.getProductName();
            int productionGoal = assemblyLine.getProductionGoal();
            HashMap<String, Integer> inventory = warehouse.getInventory();
            if (inventory.containsKey(product) && inventory.get(product) >= productionGoal) {
                reachedGoals.put(product, productionGoal);
            }
        }
        return reachedGoals;
    }
}