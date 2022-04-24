package ClassQuestions;

import java.util.ArrayList;
import java.util.List;

//  705. Design HashSet
class MyHashSet {
    final private int bucketsLen = 1000;
    private List<Integer>[] buckets;

    public MyHashSet() {
        buckets = new List[bucketsLen];
    }

    public void add(int key) {
        int hashKey = key % bucketsLen;
        if (buckets[hashKey] == null) buckets[hashKey] = new ArrayList<>();
        List<Integer> bucket = buckets[hashKey];
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i) == key) return;
        }
        buckets[hashKey].add(key);
    }

    public void remove(int key) {
        int hashKey = key % bucketsLen;
        List<Integer> bucket = buckets[hashKey];
        if (bucket == null) return;
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i) == key) {
                bucket.remove(i);
                return;
            }
        }
    }

    public boolean contains(int key) {
        int hashKey = key % bucketsLen;
        List<Integer> bucket = buckets[hashKey];
        if (bucket == null) return false;
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i) == key) return true;
        }
        return false;
    }
}
