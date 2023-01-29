package org.ACME.common;

/** Abstract superclass for ACME and subcontractor factories */
public abstract class Factory {
    protected Warehouse warehouse;

    public Factory() {}

    public void createWarehouse() {
        warehouse = new Warehouse();
    }

    public void work() {}
}
