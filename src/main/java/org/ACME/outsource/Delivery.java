package org.ACME.outsource;

public class Delivery {
    private String partName;
    private int cargoSize;
    private int deliveryTimeCounter;

    public Delivery(String partName, int cargoSize, int deliveryTimeCounter) {
        this.partName = partName;
        this.cargoSize = cargoSize;
        this.deliveryTimeCounter = deliveryTimeCounter;
    }

    public boolean hasArrived() {
        deliveryTimeCounter--;
        if (deliveryTimeCounter == 0) {
            return true;
        }
        return false;
    }
}
