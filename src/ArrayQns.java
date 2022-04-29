import java.util.*;

public class ArrayQns {

    //    31. Next Permutation
    public void nextPermutation(int[] nums) {
        if(nums == null || nums.length <= 1) return;
        int i = nums.length - 2;

        while (i >= 0 && nums[i] >= nums[i + 1]) i--;
        if (i < 0) {
            Arrays.sort(nums);
        } else {
            int j = nums.length - 1;
            while (nums[j] <= nums[i]) j--;
            swap(nums, i, j);
            reverse(nums, i+1, nums.length-1);
        }
    }

    public void reverse(int[] nums, int i, int j) {
        while (i < j) swap(nums, i++, j--);
    }

    //    714. Best Time to Buy and Sell Stock with Transaction Fee
    public int maxProfit(int[] prices, int fee) {
        int prevMin = Integer.MAX_VALUE, prevMax = 0;
        int currMax = 0, globalMax = 0;
        for (int price : prices) {
            if (price < prevMax - fee || price <= prevMin) {
                prevMax = price;
                prevMin = price;
                globalMax += Math.max(0, currMax - fee);
                currMax = 0;
            } else {
                prevMax = Math.max(prevMax, price);
                currMax = Math.max(currMax, price - prevMin);
            }
        }
        if (prevMin != prices[prices.length - 1]) {
            globalMax += Math.max(0, currMax - fee);
        }
        return globalMax;
    }

    public int maxProfitBetter(int[] prices, int fee) {
        if (prices.length < 2) return 0;
        int total = 0, minimum = prices[0];
        for (int p : prices) {
            if (p < minimum) minimum = p;
            else if (p > minimum + fee) {
                total += p - fee - minimum;
                minimum = p - fee;
            }
        }
        return total;
    }

    /**
     * 416. Partition Equal Subset Sum
     * Failed attempt using sliding window
     * Appropriate solution for finding a consecutive sub-sequence of a sorted array with value == total/2
     */
    public static boolean canPartitionDP(int[] nums) {
        int max = 0, total = 0;
        for (int n : nums) {
            total += n;
            max = Math.max(max, n);
        }
        if (max * 2 > total) return false;

        int[][] dp = new int[nums.length + 2][nums.length];
        for (int i = 0; i < nums.length; i++) dp[1][i] = nums[i];
        for (int row = 2; row < nums.length + 2; row++) {
            for (int col = 0; col < nums.length; col++) {
                if (row - 2 == col) { // same number, cannot take
                    dp[row][col] = dp[row - 1][col];
                } else {
                    int prevRow = row - 1, toAdd = nums[row - 2], newVal = dp[prevRow][col] + toAdd;
                    while (prevRow > 0 && newVal * 2 > total) {
                        newVal = dp[--prevRow][col] + toAdd;
                    }
                    if (newVal * 2 == total) return true;
                    dp[row][col] = newVal;
                }
            }
        }
        return false;
    }

    public boolean canPartitionFailed(int[] nums) {
        int total = 0, windowSum = 0, l = 0, r = 0;
//        Arrays.sort(nums);
        for (int n : nums) total += n;

        // init window
        while (windowSum * 2 < total) windowSum += nums[r++];

        while (l <= r && r < nums.length && windowSum * 2 != total) {
            if (windowSum * 2 > total) {
                windowSum -= nums[l++];
            } else if (windowSum * 2 < total) {
                windowSum += nums[r++];
            }
            if (r < l) r++;
        }

        return windowSum * 2 == total;
    }

    //    1855. Maximum Distance Between a Pair of Values, Two pointers
    public int maxDistance1(int[] nums1, int[] nums2) {
        int res = 0, i = 0;
        for (int j = 0; j < nums2.length; ++j) {
            while (i < nums1.length && nums1[i] > nums2[j]) i++;
            if (i == nums1.length) break;
            res = Math.max(res, j - i);
        }
        return res;
    }

    public int maxDistance2(int[] A, int[] B) {
        int i = 0, j = 0, res = 0;
        while (i < A.length && j < B.length) {
            if (A[i] > B[j]) i++;
            else res = Math.max(res, j++ - i);
        }
        return res;
    }

    //    75. Sort Colors
    public static void sortColors(int[] nums) {
        int l = 0, r = nums.length - 1, m = 0;
        while (m < r) {
            switch (nums[m]) {
                case 0:
                    swap(nums, l++, m++);
                case 1:
                    m++;
                case 2:
                    swap(nums, r--, m++);
            }
        }
    }

    private static void swap(int[] nums, int l, int r) {
        int t = nums[l];
        nums[l] = nums[r];
        nums[r] = t;
    }

    /**
     * 633. Sum of Square Numbers
     * Two pointers
     */
    public static boolean judgeSquareSum(int c) {
        long left = 0, right = (long) Math.sqrt(c);
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

    //    15. 3Sum
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length <= 2) return res;
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // prevent duplicate at the start
            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum > 0) {
                    r--;
                } else if (sum == 0) {
                    res.add(new ArrayList<>(Arrays.asList(nums[i], nums[l], nums[r])));
                    while (l < r && nums[l] == nums[l + 1]) l++; // prevent duplicates at the middle/end
                    while (l < r && nums[r] == nums[r - 1]) r--;
                    l++;
                    r--;
                    while (l < nums.length - 1 && nums[l] == nums[l + 1]) l++;
                } else {
                    l++;
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
     * Seems faster than using PriorityQueue<Map.Entry<Integer, Integer>>
     */
    public int[] topKFrequent(int[] nums, int k) {
        if (nums.length == 0) return new int[0];
        Map<Integer, Integer> map = new HashMap<>();
        PriorityQueue<int[]> heap = new PriorityQueue<int[]>((a, b) -> b[0] - a[0]);
        int[] res = new int[k];

        for (int n : nums) map.put(n, map.getOrDefault(n, 0) + 1);
        for (Map.Entry<Integer, Integer> keyVal : map.entrySet()) {
            heap.add(new int[]{keyVal.getValue(), keyVal.getKey()});
        }
        for (int i = 0; i < k; i++) res[i] = heap.poll()[1];
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

    //    128. Longest Consecutive Sequence
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int count = 1;
        for (int n : nums) set.add(n);
        for (int n : set) {
            if (!set.contains(n - 1)) {
                int m = n + 1;
                while (set.contains(m)) {
                    m += 1;
                    count = Math.max(count, m - n);
                }
            }
        }
        return count;
    }

    //    238. Product of Array Except Self
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        res[0] = 1;
        for (int i = 1; i < n; i++) res[i] = res[i - 1] * nums[i - 1];
        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (i < n - 1) right *= nums[i + 1];
            res[i] *= right;
        }
        return res;
    }
}
