package TimeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//  981. Time Based Sandbox.Sandbox.Key-Value Store
public class TimeMap {
    public static void main(String[] args) {
        int res = binSearchClosestLess(new int[]{10, 20}, 15);
        System.out.println(res);
    }

    public static int binSearchClosestLess(int[] nums, int t) {
        int l = 0, r = nums.length;
        while (l < r) {
            int m = (l + r) / 2;
            if (nums[m] == t) return m;
            if (nums[m] > t) {
                r = m - 1;
            } else {
                l = m;
            }
        }
        return l;
    }

    private Map<Integer, String> keyNum;
    private Map<String, List<int[]>> stringKeyMap;
    private int itemIndex;

    public TimeMap() {
        this.stringKeyMap = new HashMap<>();
        this.keyNum = new HashMap<>();
        this.itemIndex = 0;
    }

    public void set(String key, String value, int timestamp) {
        int[] newEntry = new int[]{timestamp, itemIndex};
        stringKeyMap.computeIfAbsent(key, k -> new ArrayList<>());
        stringKeyMap.get(key).add(newEntry);
        keyNum.put(itemIndex, value);
        itemIndex++;
    }

    public String get(String key, int timestamp) {
        List<int[]> ints = stringKeyMap.get(key);
        if (ints == null) return "";
        int[] item = binSearchTimestamp(ints, timestamp);
        if (item[0] == -1) return "";
        return keyNum.get(item[1]);
    }

    private int[] binSearchTimestamp(List<int[]> list, int timestamp) {
        if (timestamp < list.get(0)[0]) return new int[]{-1, -1};
        if (list.size() == 1) return list.get(0);

//        FIXME why can't I do a binary search here lmao
        int i = list.size() - 1;
        while (i >= 0) {
            if (list.get(i)[0] > timestamp) i--;
            else break;
        }
        return list.get(i);

        //        int l = 0, r = list.size() - 1;
        //        while (l < r) {
        //            int m = (l + r) / 2;
        //            int[] midVals = list.get(m);
        //            if (midVals[0] == timestamp) return midVals;
        //            if (midVals[0] > timestamp) {
        //                r = m - 1;
        //            } else {
        //                l = m + 1;
        //            }
        //        }
        //        return list.get(l);
    }
}