package org.ACME.outsource;

import org.ACME.common.Delivery;
import org.ACME.common.Factory;
import org.ACME.insource.HouseFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class ContractorFactory extends Factory {
    private final HouseFactory customer;
    private LoadingDock loadingDock;

    public ContractorFactory(HouseFactory customer) {
        this.customer = customer;
    }

    public void createLoadingDock(HashMap<String, Integer> cargoSizeReference, int deliveryTime) {
        loadingDock = new LoadingDock(cargoSizeReference ,deliveryTime);
    }

    @Override
    public void work() {
        // Produce and store
        warehouse.store(runAssemblyLines());

        // Send deliveries if enough produce
        HashMap<String, Integer> inventory = warehouse.getInventory();
        HashMap<String, Integer> packagingSizeReference = loadingDock.getPackagingSizeReference();
        for (String product : inventory.keySet()) {
            int maxPackageAmount = calculateMaxPackageAmount(product, inventory, packagingSizeReference);
            int total = maxPackageAmount * packagingSizeReference.get(product);
            HashMap<String, Integer> cargo = new HashMap<>();
            cargo.put(product, total);
            if (warehouse.retrieve(cargo)) {
                for (int i = 0; i < maxPackageAmount; i++) {
                    loadingDock.createDelivery(product);
                }
            }
        }

        // Manage deliveries
        ArrayList<Delivery> arrivedDeliveries = loadingDock.checkOnDeliveries();
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

    public LoadingDock getLoadingDock() {
        return loadingDock;
    }
}
