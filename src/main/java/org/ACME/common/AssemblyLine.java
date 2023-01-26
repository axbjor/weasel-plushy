package org.ACME.common;

public class AssemblyLine {
    protected String productName;
    protected int productionRate; // per hour

    public AssemblyLine(String name, int productionRate) {
        this.productName = name;
        this.productionRate = productionRate;
    }

    public int produce() {
        return productionRate;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductionRate() {
        return productionRate;
    }
}
