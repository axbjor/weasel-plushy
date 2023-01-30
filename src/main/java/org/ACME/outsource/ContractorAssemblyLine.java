package org.ACME.outsource;

import org.ACME.common.AssemblyLine;

import java.util.HashMap;

/** Subcontractor assembly line that produces indefinitely and can be sabotaged */
public class ContractorAssemblyLine extends AssemblyLine {
    private int sabotageTimer;

    public ContractorAssemblyLine(String name, int productionRate) {
        super(name, productionRate);
        sabotageTimer = 0;
    }

    @Override
    public HashMap<String, Integer> produce() {
        HashMap<String, Integer> products = new HashMap<>();
        if (sabotageTimer == 0) {
            products.put(productName, productionRate);
        }
        else {
            sabotageTimer--;
        }
        return products;
    }

    public void setSabotageTimer(int sabotageTimer) {
        this.sabotageTimer = sabotageTimer;
    }
}
