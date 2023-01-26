package org.ACME.common;

import java.util.HashMap;

public class Warehouse {
    HashMap<String, Integer> inventory;

    public Warehouse() {
        inventory = new HashMap<>();
    }

    public void store(HashMap<String, Integer> products) {
        for (String productName : products.keySet()) {
            if (inventory.containsKey(productName)) {
                inventory.replace(productName, inventory.get(productName) + products.get(productName));
            }
            else {
                inventory.put(productName, products.get(productName));
            }
        }
    }

    private boolean isInStore(HashMap<String, Integer> items) {
        for (String item : items.keySet()) {
            if (!(inventory.containsKey(item) && inventory.get(item) >= items.get(item))) {
                return false;
            }
        }
        return true;
    }

    public boolean retrieve(HashMap<String, Integer> items) {
        if (isInStore(items)) {
            for (String item : items.keySet()) {
                if (inventory.get(item) - items.get(item) <= 0) {
                    inventory.remove(item);
                }
                else {
                    inventory.replace(item, inventory.get(item) - items.get(item));
                }
            }
            return true;
        }
        return false;
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }
}
