package org.ACME.common;

import java.util.HashMap;
import java.util.LinkedList;

/** Named longer and more awkward "Manufactory" to not confuse with the "Factory" naming convention */
public abstract class Manufactory {
    protected LinkedList<AssemblyLine> assemblyLines;
    protected Warehouse warehouse;

    public Manufactory() {
    }

    public void createAssemblyLine(String productName, int productionRate) {
        assemblyLines.add(new AssemblyLine(productName, productionRate));
    }

    public void createWarehouse() {
        warehouse = new Warehouse();
    }

    public HashMap<String, Integer> runAssemblyLines() {
        HashMap<String, Integer> products = new HashMap<>();
        for (AssemblyLine assemblyLine : assemblyLines) {
            products.put(assemblyLine.getProductName(), assemblyLine.produce());
        }
        return products;
    }

    public abstract void work();
}
