package org.ACME.common;

import java.util.HashMap;

/** Stores and retrieves products for a factory */
public class Warehouse {
    HashMap<String, Integer> inventory;

    public Warehouse() {
        inventory = new HashMap<>();
    }

    public void store(HashMap<String, Integer> items) {
        Helper.addToMap(inventory, items);
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
        if (!isInStore(items)) {
            return false;
        }
        for (String item : items.keySet()) {
            if (inventory.get(item) - items.get(item) == 0) {
                inventory.remove(item);
            }
            else {
                inventory.replace(item, inventory.get(item) - items.get(item));
            }
        }
        return true;
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }
}
