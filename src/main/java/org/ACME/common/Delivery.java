package org.ACME.common;

import java.util.HashMap;

public class Delivery {
    HashMap<String, Integer> cargo;
    private int deliveryTimeCounter;

    public Delivery(HashMap<String, Integer> cargo, int deliveryTimeCounter) {
        this.cargo = cargo;
        this.deliveryTimeCounter = deliveryTimeCounter;
    }

    public boolean hasArrived() {
        if (deliveryTimeCounter == 0) {
            return true;
        }
        return false;
    }

    public void advance() {
        deliveryTimeCounter--;
    }

    public HashMap<String, Integer> getCargo() {
        return cargo;
    }
}
