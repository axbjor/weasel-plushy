package org.ACME.common;

import java.util.HashMap;

public abstract class Common {
    public static HashMap<String, Integer> addToMap(HashMap<String, Integer> map1, HashMap<String, Integer> map2) {
        for (String key : map2.keySet()) {
            if (map1.containsKey(key)) {
                map1.replace(key, map1.get(key) + map2.get(key));
            }
            else {
                map1.put(key, map2.get(key));
            }
        }
        return map1;
    }
}
