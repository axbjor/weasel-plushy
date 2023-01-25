package org.ACME.common;

import java.util.HashMap;

public class Warehouse {
    HashMap<String, Integer> inventory;

    public Warehouse() {
        inventory = new HashMap<>();
    }

    public void store(HashMap<String, Integer> products) {
        for (String product : products.keySet()) {
            if (inventory.containsKey(product)) {
                inventory.replace(product, inventory.get(product) + products.get(product));
            }
            else {
                inventory.put(product, products.get(product));
            }
        }
    }


}
