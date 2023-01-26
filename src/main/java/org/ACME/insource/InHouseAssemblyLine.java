package org.ACME.insource;

import org.ACME.common.AssemblyLine;

import java.util.HashMap;

public class InHouseAssemblyLine extends AssemblyLine {
    private int maxProductionRate;
    private int productionGoal;
    private HashMap<String, Integer> productRecipe;
    private boolean isActive;


    public InHouseAssemblyLine(String name, int maxProductionRate, int productionGoal, HashMap<String,  Integer> productRecipe) {
        super(name, maxProductionRate);
        this.maxProductionRate = maxProductionRate;
        this.productionGoal = productionGoal;
        this.productRecipe = productRecipe;
        this.isActive = false;
    }

    public void setProductionRate(int productionRate) {
        if (productionRate > maxProductionRate) {
            productionRate = maxProductionRate;
        }
        this.productionRate = productionRate;
        isActive = productionRate > 0;
    }

    public HashMap<String, Integer> getProductRecipe() {
        return productRecipe;
    }

    public int getProductionGoal() {
        return productionGoal;
    }

    public boolean isActive() {
        return isActive;
    }
}
