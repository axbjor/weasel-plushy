package org.ACME.outsource;

import org.ACME.common.Manufactory;

import java.util.HashMap;
import java.util.LinkedList;

public class ContractorManufactory extends Manufactory {
    private LoadingDock loadingDocks;
    private LinkedList<Delivery> deliveries;

    public ContractorManufactory() {
    }

    public void createLoadingDock(int deliverySize, int deliveryTime) {
        loadingDocks = new LoadingDock(deliverySize ,deliveryTime);
    }

    public void createDelivery(String partName, int cargoSize, int deliveryTimeCounter) {
        deliveries.add(new Delivery(partName, cargoSize, deliveryTimeCounter));
    }

    @Override
    public void work() {
        HashMap<String, Integer> products = runAssemblyLines();
        warehouse.store(products);
        for (:
             ) {
            
        }
        if (warehouse.) {
            
        }
    }
}
