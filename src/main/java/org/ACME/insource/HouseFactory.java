package org.ACME.insource;

import org.ACME.common.Factory;
import org.ACME.common.Delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**  */
public class HouseFactory extends Factory {
    protected LinkedList<HouseAssemblyLine> assemblyLines; /** <- Awkward 'duplicate' attribute */
    /**
     * I declare this attribute to avoid having to cast the superclass AssemblyLine into the child HouseAssemblyLine
     * every time I need to utilize the child's methods.
     */
    public int counter = 0; //TODO

    public HouseFactory() {
        this.assemblyLines = new LinkedList<>();
    }

    @Override
    public void createAssemblyLine(String name, int maxProductionRate, int productionGoal, HashMap<String, Integer> productRecipe) {
        HouseAssemblyLine assemblyLine = new HouseAssemblyLine(name, maxProductionRate, productionGoal, productRecipe);
        super.assemblyLines.add(assemblyLine); /** Give a reference of the HouseAssemblyLine to both super and child to make it work */
        assemblyLines.add(assemblyLine);
    }

    @Override
    public void work() {
        // Calculate production rates
        HashMap<String, Integer> inventory = warehouse.getInventory();
        for (HouseAssemblyLine assemblyLine : assemblyLines) {
            HashMap<String, Integer> productRecipe = assemblyLine.getProductRecipe();
            assemblyLine.setProductionRate(calculateProduction(inventory, productRecipe));
        }

        // Retrieve required parts
        warehouse.retrieve(calculateRequiredComponents());

        // Produce and store
        HashMap<String, Integer> produced = runAssemblyLines();
        warehouse.store(produced);
    }

    private int calculateProduction(HashMap<String, Integer> inventory, HashMap<String, Integer> productRecipe) {
        // Check that inventory contains at least some of every required component
        Set<String> requiredComponents = productRecipe.keySet();
        for (String component : requiredComponents) {
            if (!(inventory.containsKey(component))) {
                return 0;
            }
        }
        // Calculate max amount of products that can be produced
        int maxProductionAmount = Integer.MAX_VALUE;
        for (String component : requiredComponents) {
            int componentsSufficiency = Math.floorDiv(inventory.get(component), productRecipe.get(component));
            if (componentsSufficiency < maxProductionAmount) {
                maxProductionAmount = componentsSufficiency;
            }
        }
        System.out.println("maxProductionAmount " + maxProductionAmount);
        System.out.println(inventory);
        return maxProductionAmount;
    }

    private HashMap<String, Integer> calculateRequiredComponents() {
        // Build a map of required components for all assembly lines combined
        HashMap<String, Integer> requiredComponents = new HashMap<>();
        HashMap<String, Integer> productRecipe;
        for (HouseAssemblyLine assemblyLine : assemblyLines) {
            int productionRate = assemblyLine.getProductionRate();
            if (productionRate > 0) {
                productRecipe = assemblyLine.getProductRecipe();
                for (String component : productRecipe.keySet()) {
                    int componentTotal = productionRate * productRecipe.get(component);
                    if (requiredComponents.containsKey(component)) {
                        requiredComponents.replace(component, requiredComponents.get(component) + componentTotal);
                    }
                    else {
                        requiredComponents.put(component, componentTotal);
                    }
                }
            }
        }
        return requiredComponents;
    }

    public void secureDeliveries(ArrayList<Delivery> arrivedDeliveries) {
        for (Delivery delivery : arrivedDeliveries) {
            warehouse.store(delivery.getCargo());
            counter++;
        }
    }

    public HashMap<String, Boolean> getProductionActivity() {
        HashMap<String, Boolean> assemblyLineActiveness = new HashMap<>();
        for (HouseAssemblyLine assemblyLine : assemblyLines) {
            if (assemblyLine.isActive()) {
                assemblyLineActiveness.put(assemblyLine.getProductName(), true);
            }
            else {
                assemblyLineActiveness.put(assemblyLine.getProductName(), false);
            }
        }
        return assemblyLineActiveness;
    }

    public HashMap<String, Integer> getReachedGoals() {
        HashMap<String, Integer> reachedProductionGoals = new HashMap<>();
        for (HouseAssemblyLine assemblyLine : assemblyLines) {
            String product = assemblyLine.getProductName();
            int productionGoal = assemblyLine.getProductionGoal();
            HashMap<String, Integer> inventory = warehouse.getInventory();

            // TODO:
            if (inventory.containsKey(product)) {
                System.out.println(warehouse.getInventory().get(product));
                System.out.println(inventory);
            }
            else {
                System.out.println(0);
            }

            if (inventory.containsKey(product) && inventory.get(product) >= productionGoal) {
                reachedProductionGoals.put(product, productionGoal);
            }
        }
        return reachedProductionGoals;
    }
}