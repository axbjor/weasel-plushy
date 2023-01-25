package org.ACME.insource;

import org.ACME.common.AssemblyLine;

public class InHouseAssemblyLine extends AssemblyLine {
    private int maxProductionRate;

    public InHouseAssemblyLine(String name, int maxProductionRate) {
        super(name, maxProductionRate);
        this.maxProductionRate = maxProductionRate;
    }

    @Override
    public int produce() {
        // TODO
        return productionRate;
    }
}
