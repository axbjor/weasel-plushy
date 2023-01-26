package org.ACME.outsource;

import org.ACME.common.Delivery;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadingDock {
    private HashMap<String, Integer> packagingSizeReference; // Map of packaging size for every product
    private int deliveryTime; // In hours
    private ArrayList<Delivery> deliveries;
    private int lossBorder;
    private int lossCounter;
    private int lossCounterLimit;

    public LoadingDock(HashMap<String, Integer> packagingSizeReference, int deliveryTime) {
        this.packagingSizeReference = packagingSizeReference;
        this.deliveryTime = deliveryTime;
        this.deliveries = new ArrayList<>();
        this.lossBorder = 0;
        this.lossCounter = 0;
        this.lossCounterLimit = 100;
    }

    public void createDelivery(String productName) {
        if (lossCounter >= lossBorder) {
            HashMap<String, Integer> cargo = new HashMap<>();
            cargo.put(productName, packagingSizeReference.get(productName));
            deliveries.add(new Delivery(cargo, deliveryTime));
        }
        incrementLossCounter();
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

    private void incrementLossCounter() {
        if (lossBorder > 0) {
            lossCounter++;
            if (lossCounter == lossCounterLimit) {
                lossCounter = 0;
            }
        }
    }

    public void setLossRate(String lossRate) {
        String[] split = lossRate.split("/");
        this.lossBorder = Integer.parseInt(split[0]);
        this.lossCounterLimit = Integer.parseInt(split[1]);
        /*
        if (lossRate.equals("1/5")) {
            this.lossBorder = 1;
            this.lossCounterLimit = 5;
        }
        else {
            this.lossBorder = 0;
            this.lossCounterLimit = 100;
        }
         */
    }

    public HashMap<String, Integer> getPackagingSizeReference() {
        return packagingSizeReference;
    }
}