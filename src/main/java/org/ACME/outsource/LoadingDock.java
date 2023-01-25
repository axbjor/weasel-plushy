package org.ACME.outsource;

public class LoadingDock {
    private int deliverySize;
    private int deliveryTime; // in hours

    public LoadingDock(int deliverySize, int deliveryTime) {
        this.deliverySize = deliverySize;
        this.deliveryTime = deliveryTime;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}
