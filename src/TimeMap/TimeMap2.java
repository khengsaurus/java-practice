package TimeMap;

import java.util.*;

class TimeMap2 {
    Map<String, TreeMap<Integer, String>> map;

    public TimeMap2() {
        map = new HashMap<String, TreeMap<Integer, String>>();
    }

    public void set(String key, String value, int timestamp) {
        if (!map.containsKey(key)) map.put(key, new TreeMap<>());
        map.get(key).put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        if (map.containsKey(key)) {
            TreeMap<Integer, String> treeMap = map.get(key);
            if (treeMap.containsKey(timestamp)) return treeMap.get(timestamp);

            // find next lowest entry || null
            Map.Entry<Integer, String> entry = treeMap.lowerEntry(timestamp);
            if (entry == null) return "";
            return entry.getValue();
        }
        return "";
    }
}