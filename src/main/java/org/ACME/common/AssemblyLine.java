package org.ACME.common;

import java.util.HashMap;

/** Abstract superclass for ACME and subcontractor assembly lines */
public abstract class AssemblyLine {
    protected String productName;
    protected int productionRate; // per hour

    public AssemblyLine(String name, int productionRate) {
        this.productName = name;
        this.productionRate = productionRate;
    }

    public HashMap<String, Integer> produce() {
        HashMap<String, Integer> products = new HashMap<>();
        products.put(productName, productionRate);
        return products;
    }

    public String getProductName() {
        return productName;
    }
}

