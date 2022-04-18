import java.util.*;

public class ArrayQns {
    public static void main(String[] args) {
        System.out.println(judgeSquareSum(11));
        System.out.println(judgeSquareSum(12));
        System.out.println(judgeSquareSum(4));
        System.out.println(judgeSquareSum(5));
        System.out.println(judgeSquareSum(9));
    }

    /**
     * 633. Sum of Square Numbers
     * Two pointers
     */
    public static boolean judgeSquareSum(int c) {
        long left = 0, right = (long)Math.sqrt(c);
        while (left <= right) {
            long cur = left * left + right * right;
            if (cur < c) {
                left++;
            } else if (cur > c) {
                right--;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 15. 3Sum
     * Two pointers
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                int l = i + 1, r = nums.length - 1, sum = -nums[i];
                while (l < r) {
                    if (nums[l] + nums[r] == sum) {
                        res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                        while (l < r && nums[l] == nums[l + 1]) l++;
                        while (l < r && nums[r] == nums[r - 1]) r--;
                        l++;
                        r--;
                    } else if (nums[l] + nums[r] < sum) {
                        l++;
                    } else {
                        r--;
                    }
                }
            }
        }
        return res;
    }

    //    167. Two Sum II - Input Array Is Sorted
    public static int[] twoSum(int[] numbers, int target) {
        int l = 0, r = numbers.length - 1;
        while (l < r) {
            int sum = numbers[l] + numbers[r];
            if (sum == target) return new int[]{l + 1, r + 1};
            if (sum < target) {
                l++;
            } else {
                r--;
            }
        }
        return new int[]{-1, -1};
    }

    //    39. Combination Sum
    public List<List<Integer>> combinationSum(int[] nums, int target) {
        List<List<Integer>> combos = new ArrayList<>();
        backtrack39(nums, target, 0, 0, combos, new LinkedList<>());
        return combos;
    }

    public void backtrack39(int[] nums, int target, int start, int curSum, List<List<Integer>> combos, Deque<Integer> combo) {
        if (curSum == target) combos.add(new ArrayList(combo));
        while (start < nums.length) {
            if (nums[start] <= target - curSum) {
                combo.offerLast(nums[start]);
                backtrack39(nums, target, start, curSum + nums[start], combos, combo);
                combo.pollLast();
            }
            start++;
        }
    }

    /**
     * 409. Longest Palindrome
     * a a b b a a
     * l i r
     * __l i   r
     * ____l i r
     */
    public static int longestPalindrome(String s) {
        Set<Character> set = new HashSet<>();
        int count = 0;
        for (char c : s.toCharArray()) {
            if (set.remove(c)) {
                count++;
            } else {
                set.add(c);
            }
        }
        return 2 * count + (set.isEmpty() ? 0 : 1);
    }

    /**
     * 150. Evaluate Reverse Polish Notation
     * Think data structures man...
     */
    public static int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        int cur = 0;
        for (String s : tokens) {
            if (isOperand(s)) {
                int second = stack.pop();
                cur = helperRPN(stack.pop(), second, s);
            } else {
                cur = Integer.valueOf(s);
            }
            stack.add(cur);
        }
        return stack.pop();
    }

    public static boolean isOperand(String o) {
        switch (o) {
            case "+":
            case "/":
            case "-":
            case "*":
                return true;
            default:
                return false;
        }
    }

    public static int helperRPN(int l, int r, String o) {
        switch (o) {
            case "+":
                return l + r;
            case "/":
                return l / r;
            case "-":
                return l - r;
            case "*":
                return l * r;
            default:
                return 0;
        }
    }

    //    189. Rotate Array
    public static void rotate(int[] nums, int k) {
        k = k % nums.length;
        if (k == 0) return;
        int[] holders = new int[k];
        for (int i = 0; i < k; i++) {
            holders[i] = nums[nums.length - k + i];
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            nums[i] = i - k < 0 ? holders[i] : nums[i - k];
        }
    }

    //    973. K Closest Points to Origin
    public static int[][] kClosest(int[][] points, int k) {
        PriorityQueue<double[]> heap = new PriorityQueue<>(Comparator.comparingDouble(a -> a[0]));
        for (int i = 0; i < points.length; i++) {
            int[] point = points[i];
            int x = point[0], y = point[1];
            double dist = Math.sqrt((x * x) + (y * y));
            heap.add(new double[]{dist, i});
        }
        int[][] res = new int[k][2];
        for (int i = 0; i < k && !heap.isEmpty(); i++) {
            double index = heap.poll()[1];
            res[i] = points[(int) index];
        }
        return res;
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

    //    923 TODO: mod to
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
