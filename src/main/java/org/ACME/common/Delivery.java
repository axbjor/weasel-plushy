package org.ACME.common;

import java.util.HashMap;

/** Represents the packages from loading docks to ACME factories */
public class Delivery {
    HashMap<String, Integer> cargo;
    private float deliveryTimeCounter;

    public Delivery(HashMap<String, Integer> cargo, int deliveryTimeCounter) {
        this.cargo = cargo;
        this.deliveryTimeCounter = deliveryTimeCounter;
    }

    public boolean hasArrived() {
        return deliveryTimeCounter <= 0;
    }

    public void advance(float timeMultiplier) {
        deliveryTimeCounter -= 1 * timeMultiplier;
    }

    public HashMap<String, Integer> getCargo() {
        return cargo;
    }
}
