package org.ACME.outsource;

import org.ACME.common.Delivery;
import org.ACME.common.Factory;
import org.ACME.insource.HouseFactory;

import java.util.ArrayList;
import java.util.HashMap;

/** Subcontractor factory that produces components for ACME factories */
public class ContractorFactory extends Factory {
    private ArrayList<ContractorAssemblyLine> assemblyLines;
    private HouseFactory customer;
    private LoadingDock loadingDock;

    public ContractorFactory(HouseFactory customer) {
        this.customer = customer;
        assemblyLines = new ArrayList<>();
    }

    public void createAssemblyLine(String productName, int productionRate) {
        assemblyLines.add(new ContractorAssemblyLine(productName, productionRate));
    }

    public void createLoadingDock(HashMap<String, Integer> packageSizeReference, int deliveryTime) {
        loadingDock = new LoadingDock(packageSizeReference ,deliveryTime);
    }

    @Override
    public void work() {
        // Produce and store
        for (ContractorAssemblyLine assemblyLine : assemblyLines) {
            warehouse.store(assemblyLine.produce());
        }
        // Deliver
        HashMap<String, Integer> inventory = warehouse.getInventory();
        HashMap<String, Integer> packageSizeReference = loadingDock.getPackageSizeReference();
        for (String product : inventory.keySet()) {
            int packageAmount = calculatePackageAmount(product, inventory, packageSizeReference);
            if (packageAmount == 0) {
                continue;
            }
            HashMap<String, Integer> cargo = new HashMap<>();
            cargo.put(product, packageSizeReference.get(product));
            sendDeliveries(cargo, packageAmount);
        }

        // Manage deliveries
        ArrayList<Delivery> arrivedDeliveries = loadingDock.checkOnDeliveries();
        if (!(arrivedDeliveries == null) && !arrivedDeliveries.isEmpty()) {
            reportDeliveryArrivals(arrivedDeliveries);
        }
    }

    private int calculatePackageAmount(String product, HashMap<String, Integer> inventory, HashMap<String, Integer> packageSizeReference) {
        int inventoryAmount = inventory.get(product);
        int packageSize = packageSizeReference.get(product);
        return Math.floorDiv(inventoryAmount, packageSize);
    }

    private void sendDeliveries(HashMap<String, Integer> cargo, int amount) {
        for (int i = 0; i < amount; i++) {
            if (!warehouse.retrieve(cargo)) {
                break;
            }
            loadingDock.createDelivery(cargo);
        }
    }

    private void reportDeliveryArrivals(ArrayList<Delivery> arrivedDeliveries) {
        customer.storeDeliveries(arrivedDeliveries);
    }

    public ArrayList<ContractorAssemblyLine> getAssemblyLines() {
        return assemblyLines;
    }

    public LoadingDock getLoadingDock() {
        return loadingDock;
    }
}
