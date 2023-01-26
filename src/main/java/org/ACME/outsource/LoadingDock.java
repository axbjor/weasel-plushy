package org.ACME.outsource;

import org.ACME.common.Delivery;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadingDock {
    private HashMap<String, Integer> packagingSizeReference; // Map of packaging size for every product
    private int deliveryTime; // In hours
    private ArrayList<Delivery> deliveries;

    public LoadingDock(HashMap<String, Integer> packagingSizeReference, int deliveryTime) {
        this.packagingSizeReference = packagingSizeReference;
        this.deliveryTime = deliveryTime;
        this.deliveries = new ArrayList<>();
    }

    public void createDelivery(String productName) {
        HashMap<String, Integer> cargo = new HashMap<>();
        cargo.put(productName, packagingSizeReference.get(productName));
        deliveries.add(new Delivery(cargo, deliveryTime));
    }

    public ArrayList<Delivery> checkOnDeliveries() {
        if (!deliveries.isEmpty()) {
            ArrayList<Delivery> arrivedDeliveries = new ArrayList<>();
            for (Delivery delivery : deliveries) {
                if (delivery.hasArrived()) {
                    arrivedDeliveries.add(delivery);
                }
                    delivery.advance();
            }
            for (Delivery delivery : arrivedDeliveries) {
                deliveries.remove(delivery);
            }
            return arrivedDeliveries;
        }
        return null;
    }

    public HashMap<String, Integer> getPackagingSizeReference() {
        return packagingSizeReference;
    }
}

// for each delivery

// If time counter = 0, increase factory part count & delete delivery

// decrease time counter
