package org.ACME.common;

public class AssemblyLine {
    protected String ProductName;
    protected int productionRate; // per hour
    protected int productCount;

    public AssemblyLine(String name, int productionRate) {
        this.ProductName = name;
        this.productionRate = productionRate;
        this.productCount = 0;
    }

    public String getProductName() {
        return ProductName;
    }

    public int produce() {
        return productionRate;
    }
}
