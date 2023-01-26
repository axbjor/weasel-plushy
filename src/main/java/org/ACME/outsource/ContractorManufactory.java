package org.ACME.outsource;

import org.ACME.common.Delivery;
import org.ACME.common.Manufactory;
import org.ACME.insource.InHouseManufactory;

import java.util.ArrayList;
import java.util.HashMap;

public class ContractorManufactory extends Manufactory {
    private final InHouseManufactory customer;
    private LoadingDock loadingDocks;

    public ContractorManufactory(InHouseManufactory customer) {
        this.customer = customer;
    }

    public void createLoadingDock(HashMap<String, Integer> cargoSizeReference, int deliveryTime) {
        loadingDocks = new LoadingDock(cargoSizeReference ,deliveryTime);
    }

    @Override
    public void work() {
        // Produce and store
        warehouse.store(runAssemblyLines());

        // Send deliveries if enough produce
        HashMap<String, Integer> inventory = warehouse.getInventory();
        HashMap<String, Integer> packagingSizeReference = loadingDocks.getPackagingSizeReference();
        for (String product : inventory.keySet()) {
            int maxPackageAmount = calculateMaxPackageAmount(product, inventory, packagingSizeReference);
            int total = maxPackageAmount * packagingSizeReference.get(product);
            HashMap<String, Integer> cargo = new HashMap<>();
            cargo.put(product, total);
            if (warehouse.retrieve(cargo)) {
                for (int i = 0; i < maxPackageAmount; i++) {
                    loadingDocks.createDelivery(product);
                }
            }
        }

        // Manage deliveries
        ArrayList<Delivery> arrivedDeliveries = loadingDocks.checkOnDeliveries();
        if (!(arrivedDeliveries == null) && !arrivedDeliveries.isEmpty()) {
            reportDeliveryArrivals(arrivedDeliveries);
        }
    }

    private int calculateMaxPackageAmount(String product, HashMap<String, Integer> inventory, HashMap<String, Integer> packagingSizeReference) {
        int amount = inventory.get(product);
        return Math.floorDiv(amount, packagingSizeReference.get(product));
    }

    private void reportDeliveryArrivals(ArrayList<Delivery> arrivedDeliveries) {
        customer.secureDeliveries(arrivedDeliveries);
        System.out.println("DELIVERIES " + arrivedDeliveries.size());
    }
}
