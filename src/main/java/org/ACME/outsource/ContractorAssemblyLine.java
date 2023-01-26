package org.ACME.outsource;

import org.ACME.common.AssemblyLine;

public class ContractorAssemblyLine extends AssemblyLine {
    private int sabotageCounter;

    public ContractorAssemblyLine(String name, int productionRate) {
        super(name, productionRate);
        sabotageCounter = 0;
    }

    public void setSabotageCounter(int sabotageCounter) {
        this.sabotageCounter = sabotageCounter;
    }
}
