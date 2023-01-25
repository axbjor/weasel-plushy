package org.ACME.insource;

import org.ACME.common.Manufactory;

public class InHouseManufactory extends Manufactory {

    @Override
    public void createAssemblyLine(String name, int maxProductionRate) {
        assemblyLines.add(new InHouseAssemblyLine(name, maxProductionRate));
    }
}
