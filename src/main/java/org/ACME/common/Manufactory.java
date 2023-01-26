package org.ACME.common;

import java.util.HashMap;
import java.util.LinkedList;

/** Named longer and more awkward "Manufactory" to not confuse with the "Factory" naming convention */
public abstract class Manufactory {
    protected LinkedList<AssemblyLine> assemblyLines;
    protected Warehouse warehouse;

    public Manufactory() {
        this.assemblyLines = new LinkedList<>();
    }

    public void createAssemblyLine(String productName, int productionRate, int productionGoal, HashMap<String, Integer> productRecipe) {
        assemblyLines.add(new AssemblyLine(productName, productionRate));
    }

    public void createWarehouse() {
        warehouse = new Warehouse();
    }

    protected HashMap<String, Integer> runAssemblyLines() {
        HashMap<String, Integer> products = new HashMap<>();
        for (AssemblyLine assemblyLine : assemblyLines) {
            if (assemblyLine.getProductionRate() > 0) {
                products.put(assemblyLine.getProductName(), assemblyLine.produce());
            }
        }
        return products;
    }

    public abstract void work();
}
