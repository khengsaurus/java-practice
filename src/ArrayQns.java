import java.util.*;

public class ArrayQns {
    public static void main(String[] args) {
        int[] res = productExceptSelf(new int[]{1, 2, 3, 4, 5, 6});
        System.out.println(Arrays.toString(res));
    }

    //    682. Baseball Game
    public int calPoints(String[] ops) {
        List<Integer> memo = new ArrayList<>();
        for (String op : ops) {
            int len = memo.size();
            switch (op) {
                case "C":
                    memo.remove(len - 1);
                    break;
                case "D":
                    memo.add(2 * memo.get(len - 1));
                    break;
                case "+":
                    memo.add(memo.get(len - 1) + memo.get(len - 2));
                    break;
                default:
                    memo.add(Integer.valueOf(op));
                    break;
            }
        }
        int total = 0;
        for (int s : memo) total += s;
        return total;
    }

    /**
     * 347. Top K Frequent Elements
     * TODO: note the use of PriorityQueue<Map.Entry<Integer, Integer>>
     */
    public int[] topKFrequent2(int[] nums, int k) {
        if (k == nums.length) return nums;
        Map<Integer, Integer> counts = new HashMap<>(); // key, count
        for (int num : nums) counts.put(num, counts.getOrDefault(num, 0) + 1);

        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        maxHeap.addAll(counts.entrySet());
        int[] list = new int[k];
        while (k > 0) {
            list[k - 1] = maxHeap.poll().getKey();
            k--;
        }

        return list;
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> counts = new HashMap<>();
        List<Integer>[] buckets = new List[nums.length + 1];

        for (int i : nums) counts.put(i, counts.getOrDefault(i, 0) + 1);

        for (int num : counts.keySet()) {
            int count = counts.get(num);
            if (buckets[count] == null) buckets[count] = new ArrayList<>();
            buckets[count].add(num);
        }

        int[] res = new int[k];
        int curr = 0;
        for (int i = buckets.length - 1; i >= 0 && curr < k; i--) {
            if (buckets[i] != null) {
                for (int num : buckets[i]) {
                    if (curr == k) break;
                    res[curr++] = num;
                }
            }
        }
        return res;
    }

    //    1046
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> heap = new PriorityQueue(Collections.reverseOrder());
        for (int s : stones) heap.add(s);
        while (!heap.isEmpty()) {
            int y = heap.poll();
            if (heap.isEmpty()) return y;
            int x = heap.poll();
            if (y > x) heap.add(y - x);
        }
        return 0;
    }

    //    923 TODO:
    public int threeSumMulti(int[] arr, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        int res = 0;
        int mod = 1000000007;
        for (int i = 0; i < arr.length; i++) {
            res = (res + map.getOrDefault(target - arr[i], 0)) % mod;

            for (int j = 0; j < i; j++) {
                int temp = arr[i] + arr[j];
                map.put(temp, map.getOrDefault(temp, 0) + 1);
            }
        }
        return res;
    }

    //    128
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        Integer max = 0;
        for (int n : nums) set.add(n);
        for (int n : set) {
            if (!set.contains(n - 1)) {
                int m = n + 1;
                while (set.contains(m)) m++;
                max = Math.max(max, m - n);
            }
        }
        return max;
    }

    /**
     * 238
     * 1    2   3   4   5   6
     * 1    1   2   6   24  120
     * 720  360 120 30  6   1
     */
    public static int[] productExceptSelf(int[] nums) {
        int[] prefix = new int[nums.length];

        prefix[0] = nums[0];
        for (int i = 1; i < nums.length - 1; i++) {
            prefix[i] = nums[i] * prefix[i - 1];
        }
        prefix[nums.length - 1] = prefix[nums.length - 2];

        System.out.println(Arrays.toString(prefix));
        int suffix = nums[nums.length - 1];

        for (int i = nums.length - 2; i > 0; i--) {
            prefix[i] = suffix * prefix[i - 1];
            suffix *= nums[i];
        }
        prefix[0] = suffix;

        return prefix;
    }
}
