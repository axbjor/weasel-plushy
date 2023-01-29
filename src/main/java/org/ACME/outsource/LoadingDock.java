package org.ACME.outsource;

import org.ACME.common.Delivery;
import org.ACME.common.Factory;

import java.util.ArrayList;
import java.util.HashMap;

/** Handles deliveries for a subcontractor factory to an ACME factory */
public class LoadingDock {
    private final ArrayList<Delivery> deliveries;
    private HashMap<String, Integer> packageSizeReference; // Map of package size for every product
    private int deliveryTime; // In hours
    private int counter;
    private int loseDeliveryOnCount;
    private float timeMultiplier;

    public LoadingDock(HashMap<String, Integer> packageSizeReference, int deliveryTime) {
        this.packageSizeReference = packageSizeReference;
        this.deliveryTime = deliveryTime;
        this.deliveries = new ArrayList<>();
        this.loseDeliveryOnCount = 0;
        this.counter = 1;
        this.timeMultiplier = 1;
    }

    public void createDelivery(HashMap<String, Integer> cargo) {
        if (counter >= loseDeliveryOnCount) {
            deliveries.add(new Delivery(cargo, deliveryTime));
            if (loseDeliveryOnCount > 0) {
                counter++;
            }
        }
        else {
            counter = 1;
        }
    }

    public ArrayList<Delivery> checkOnDeliveries() {
        if (deliveries.isEmpty()) {
            return null;
        }
        ArrayList<Delivery> arrivedDeliveries = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            if (delivery.hasArrived()) {
                arrivedDeliveries.add(delivery);
            }
            else {
                delivery.advance(timeMultiplier);
            }
        }
        for (Delivery delivery : arrivedDeliveries) {
            deliveries.remove(delivery);
        }
        return arrivedDeliveries;
    }

    public void setTimeMultiplier(float timeMultiplier) {
        this.timeMultiplier = timeMultiplier;
    }

    public void setLossRate(float lossChance) {
        if (lossChance == 0) {
            this.loseDeliveryOnCount = 0;
        }
        else {
            this.loseDeliveryOnCount = (int) (1 / lossChance); // Flooring value here insures worst case simulation. Good to be prepared.
        }
    }

    public HashMap<String, Integer> getPackageSizeReference() {
        return packageSizeReference;
    }
}