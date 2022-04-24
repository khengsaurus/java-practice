import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class DPQns {
    /**
     * 123. Best Time to Buy and Sell Stock III
     * Context: consider the cost price
     */
    public int maxProfit4(int[] prices) {
        int days = prices.length;
        if (days <= 1) return 0;
        int[][] profits = new int[3][days];

        for (int txn = 1; txn < 3; txn++) {
            int maxAmt = -prices[0]; // cost price
            for (int day = 1; day < days; day++) {
                /* maxAmt on txn,day = maxProfit of txn ending on prevDay
                   AND minimum (cost) price on any prevDay before day */
                maxAmt = Math.max(maxAmt, profits[txn - 1][day - 1] - prices[day - 1]);
                profits[txn][day] = Math.max(profits[txn][day - 1], maxAmt + prices[day]);
            }
        }
        return profits[2][days - 1];
    }

    public int maxProfit4AmazingSln(int[] prices) {
        int buy1 = Integer.MAX_VALUE, buy2 = Integer.MAX_VALUE;
        int profit1 = 0, profit2 = 0;
        for (int p : prices) {
            buy1 = Math.min(buy1, p);
            profit1 = Math.max(profit1, p - buy1); // can buy with the lowest price, and sell with the highest price
            buy2 = Math.min(buy2, p - profit1); // ^, and we sink a new cost price
            profit2 = Math.max(profit2, p - buy2);
        }
        return profit2;
    }

    /**
     * 1014 - Best scoring scenic pair
     * score(i,j) = values[i] + i  +  values[j] - j
     */
    public int maxScoreSightseeingPair(int[] values) {
        int max = 0, maxL = values[0];
        for (int i = 1; i < values.length; i++) {
            max = Math.max(max, maxL + values[i] - i);
            maxL = Math.max(maxL, values[i] + i);
        }
        return max;
    }

    //    139. Word Break
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> words = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int r = 1; r < s.length() + 1; r++) {
            for (int l = 0; l < r; l++) { // l = 0, r = 3;
                if (words.contains(s.substring(l, r)) && dp[l]) {
                    dp[r] = true;
                }
            }
        }
        return dp[s.length()];
    }

    //    1143. Longest Common Subsequence
    public int longestCommonSubsequenceBottomUp(String text1, String text2) {
        int cols = text1.length(), rows = text2.length();
        int[][] dp = new int[cols + 1][rows + 1];

        for (int i = cols - 1; i >= 0; i--) {
            for (int j = rows - 1; j >= 0; j--) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i][j] = 1 + dp[i + 1][j + 1];
                } else {
                    dp[i][j] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }
        return dp[0][0];
    }

    public int longestCommonSubsequenceTopDown(String text1, String text2) {
        int rows = text1.length() + 1, cols = text2.length() + 1;
        int[][] arr = new int[rows][cols];
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                arr[i][j] = Math.max(
                        Math.max(arr[i][j - 1], arr[i - 1][j]),
                        arr[i - 1][j - 1] + (text1.charAt(i - 1) == text2.charAt(j - 1) ? 1 : 0)
                );
            }
        }
        return arr[rows - 1][cols - 1];
    }


    /**
     * 518. Coin Change 2
     * Unbounded knapsack
     */
    private Integer[][] dp518;

    public int changeKnapsack(int amount, int[] coins) {
        if (amount == 0) return 1;
        if (coins.length == 0) return 0;
        dp518 = new Integer[coins.length][amount + 1];
        return changeHelper(amount, coins, 0);
    }

    private int changeHelper(int amount, int[] coins, int take) {
        if (amount == 0) return 1;
        if (amount < 0 || take >= coins.length) return 0;
        if (dp518[take][amount] != null) return dp518[take][amount];

        int w0 = changeHelper(amount, coins, take + 1);
        int w1 = changeHelper(amount - coins[take], coins, take);
        int sum = w0 + w1;
        dp518[take][amount] = sum;
        return sum;
    }

    public static boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;
        for (int i : bills) {
            switch (i) {
                case 20: {
                    if (five <= 0) return false;
                    if (ten <= 0) {
                        if (five < 3) return false;
                        five -= 3;
                    } else {
                        ten--;
                        five--;
                    }
                    break;
                }
                case 10: {
                    if (five == 0) return false;
                    five--;
                    ten++;
                    break;
                }
                case 5:
                    five++;
                    break;
            }
        }
        return true;
    }

    public static int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        for (int j = 1; j <= coins.length; j++) {
            dp[0] = 1;
            for (int i = 1; i < amount + 1; i++) {
                if (i - coins[j - 1] >= 0) {
                    dp[i] += dp[i - coins[j - 1]];
                }
            }
        }
        return dp[amount];
    }

    //    322. Coin Change
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int amt = 0; amt <= amount; amt++) {
            for (int coin : coins) {
                if (amt >= coin) {
                    dp[amt] = Math.min(dp[amt], 1 + dp[amt - coin]);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    public int coinChange2(int[] coins, int amount) {
        if (amount == 0) return 0;
        if (coins.length == 0) return -1;
        return dp322(coins, amount, new int[amount + 1]);
    }

    private int dp322(int[] coins, int amt, int[] memo) {
        if (amt == 0) return 0;
        if (memo[amt] != 0) return memo[amt];
        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int ways = coin > amt ? -1 : dp322(coins, amt - coin, memo);
            if (ways >= 0) min = Math.min(min, 1 + ways);
        }
        memo[amt] = (min == Integer.MAX_VALUE) ? -1 : min;
        return memo[amt];
    }

    // 42 - Trapping Rain Water
    // https://www.youtube.com/watch?v=ZI2z5pq0TqA
    public int trap(int[] height) {
        if (height.length <= 2) return 0;
        int l = 0, r = height.length - 1, total = 0;
        int maxLeft = height[l], maxRight = height[r];
        while (l < r) {
            if (maxLeft < maxRight) {
                l++;
                maxLeft = Math.max(maxLeft, height[l]); // smart. avoid another check
                total += maxLeft - height[l];
            } else {
                r--;
                maxRight = Math.max(maxRight, height[r]);
                total += maxRight - height[r];
            }
        }
        return total;
    }

    /**
     * 309 - Best Time to Buy and Sell Stock with Cooling Period
     * https://www.youtube.com/watch?v=I7j0F7AHpb8
     * State based decision:
     * canBuy -> buy (then canSell next day) or noAction (then canBuy next day)
     * canSell -> sell (then canBuy the day after) or noAction (then canSell next day)
     */
    static int[] buy, sell;

    public static int maxProfit(int[] prices) {
        buy = new int[prices.length];
        sell = new int[prices.length];
        return dfs309(prices, 0, true);
    }

    public static int dfs309(int[] prices, int day, boolean canBuy) {
        if (day >= prices.length) return 0;
        if (canBuy) {
            if (buy[day] != 0) return buy[day];
        } else if (sell[day] != 0) {
            return sell[day];
        }
        int noAction = dfs309(prices, day + 1, canBuy);
        if (canBuy) {
            buy[day] = Math.max(noAction, dfs309(prices, day + 1, false) - prices[day]);
            return buy[day];
        } else {
            sell[day] = Math.max(noAction, dfs309(prices, day + 2, true) + prices[day]);
            return sell[day];
        }
    }

    //    120
    public int minimumTotal(List<List<Integer>> triangle) {
        int rows = triangle.size();
        int first = triangle.get(0).get(0);
        if (rows == 1) return first;
        int[][] dp = new int[rows][rows];
        dp[0][0] = first;
        for (int row = 1; row < rows; row++) {
            for (int col = 0; col <= row; col++) {
                int currVal = triangle.get(row).get(col);
                if (col == 0) {
                    dp[row][col] = currVal + dp[row - 1][0];
                } else if (col == row) {
                    dp[row][col] = currVal + dp[row - 1][col - 1];
                } else {
                    dp[row][col] = currVal + Math.min(dp[row - 1][col - 1], dp[row - 1][col]);
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i : dp[rows - 1]) {
            min = Math.min(min, i);
        }
        return min;
    }

    /**
     * 1567
     * vals: 1  1 -1  1 -1  1  1  0  1 -1 -1
     * pos:  1  2  0  1  5  6  7  0  1  0  3
     * neg:  0  0  3  4  2  3  4  0  0  2  1
     */
    public static int getMaxLen(int[] nums) {
        int pos = 0, neg = 0, max = 0;
        for (int i : nums) {
            if (i == 0) {
                pos = 0;
                neg = 0;
            } else if (i < 0) {
                int t = neg == 0 ? 0 : neg + 1;
                neg = pos + 1;
                pos = t;
            } else {
                pos++;
                neg = neg == 0 ? 0 : neg + 1;
            }
            max = Math.max(max, pos);
        }
        return max;
    }

    // 918 - Kadane
    public static int maxSubarraySumCircular(int[] nums) {
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return Math.max(nums[0], nums[1]);
        int total = 0, maxSum = Integer.MIN_VALUE, minSum = 0;
        int currMax = 0, currMin = 0;
        for (int i = 0; i < nums.length; i++) {
            total += nums[i];
            currMax = Math.max(nums[i] + currMax, nums[i]);
            currMin = Math.min(nums[i] + currMin, nums[i]);
            maxSum = Math.max(currMax, maxSum);
            minSum = Math.min(currMin, minSum);
        }

        return total == minSum ? maxSum : Math.max(maxSum, total - minSum);
    }

    //    45. Jump Game II
    public static int jumpBetter(int[] nums) {
        // Idea: you don't have to `jump` at that specific value, rather just log the jump and reach the max
        int end = 0, max = 0, jumps = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            max = Math.max(max, i + nums[i]);
            if (i == end) {
                end = max;
                jumps++;
            }
        }
        return jumps;
    }

    public static int jump(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < len; i++) {
            int amtCanJump = nums[i];
            if (amtCanJump > 0 && (i == 0 || dp[i] < Integer.MAX_VALUE)) {
                for (int j = 1; j <= amtCanJump; j++) {
                    if (j + i < len) {
                        dp[j + i] = Math.min(dp[j + i], dp[i] + 1);
                    }
                }
            }
        }
        return dp[len - 1];
    }

    //    740 - N log N with Arrays.sort for memo much better than N with Priority Queue (log N) + HashMap for memo
    public static int deleteAndEarn(int[] nums) {
        Arrays.sort(nums);
        int[] sumVals = new int[nums[nums.length - 1] + 1];
        /**
         * sumVals:  1, 2, 3, 4, 5, 6
         * didTake: 1, 2, 4, 6 <- prev not take + val
         * notTake: 0, 1, 2, 4 <- max(prev take, prev not take)
         */
        for (int i : nums) sumVals[i]++;
        int didTake = 0, notTake = 0;
        for (int i = 1; i < sumVals.length; i++) {
            int t = Math.max(didTake, notTake);
            didTake = notTake + i * sumVals[i];
            notTake = t;
        }
        return Math.max(didTake, notTake);
    }

    @Test
    public void deleteAndEarnWorks() {
        assertTrue(deleteAndEarn(new int[]{3, 4, 2}) == 6);
        assertTrue(deleteAndEarn(new int[]{2, 2, 3, 3, 3, 4}) == 9);
    }

    // 746
    public static int minCostClimbingStairs(int[] cost) {
        int dp1 = 0, dp2 = 0;
        for (int i = 2; i <= cost.length; i++) {
            int t = dp1;
            dp1 = Math.min(dp1 + cost[i - 1], dp2 + cost[i - 2]);
            dp2 = t;
        }
        return dp1;
    }

    // 70
    public static int climbStairs(int n) {
        int[] dp = new int[n + 1];
        return climbHelper(n, dp);
    }

    public static int climbHelper(int n, int[] dp) {
        if (n <= 3) return n;
        if (dp[n] > 0) return dp[n];
        int steps = climbHelper(n - 1, dp) + climbHelper(n - 2, dp);
        dp[n] = steps;
        return steps;
    }

    // 118
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> dp = new ArrayList<>() {
        };
        for (int i = 0; i < numRows; i++) {
            dp.add(new ArrayList<>(Arrays.asList(1)));
        }
        for (int i = 1; i < numRows; i++) {
            List<Integer> prevRow = dp.get(i - 1);
            List<Integer> currRow = dp.get(i);
            for (int j = 1; j <= i; j++) {
                if (j == i) {
                    currRow.add(1);
                } else {
                    currRow.add(prevRow.get(j - 1) + prevRow.get(j));
                }
            }
        }
        return dp;
    }

    //    566
    public static int[][] matrixReshape(int[][] mat, int r, int c) {
        if (r * c != mat.length * mat[0].length) return mat;
        int currRow = 0, currCol = 0;
        int[][] reshaped = new int[r][c];

        for (int row = 0; row < mat.length; row++) {
            for (int col = 0; col < mat[0].length; col++) {
                reshaped[currRow][currCol] = mat[row][col];
                if (currCol + 1 >= c) {
                    currCol = 0;
                    currRow++;
                } else {
                    currCol++;
                }
            }
        }

        return reshaped;
    }

    //    221 - https://www.youtube.com/watch?v=6X7Ha2PrDmM
    public static int maximalSquare(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows + 1][cols + 1];
        int max = 0;
//        fill bottom up dp
        for (int row = rows - 1; row >= 0; row--) {
            for (int col = cols - 1; col >= 0; col--) {
                if (matrix[row][col] == '1') {
                    int t = Math.min(dp[row + 1][col], dp[row][col + 1]);
                    int n = 1 + Math.min(dp[row + 1][col + 1], t);
                    dp[row][col] = n;
                    if (n > max) max = n;
                } else {
                    dp[row][col] = 0;
                }
            }
        }
        return max * max;
    }

    public static String simplifyPath(String path) {
        Stack<String> stack = new Stack<>();
        String[] paths = path.split("/");
        for (String s : paths) {
            if (s.equals("..")) {
                if (!stack.isEmpty()) stack.pop();
            } else if (!s.equals("") && !s.equals(".")) {
                stack.add(s);
            }
        }
        StringBuilder fullPath = new StringBuilder();
        if (stack.isEmpty()) {
            return "/";
        }
        while (!stack.isEmpty()) {
            fullPath.insert(0, stack.pop());
            fullPath.insert(0, "/");
        }
        return fullPath.toString();
    }

    public static int knapsack(int[] values, int[] weights, int maxWeight) {
        int[][] dp = new int[weights.length + 1][maxWeight + 1];
        for (int w = 1; w <= weights.length; w++) { // rows
            for (int max = 1; max <= maxWeight; max++) { // cols
                dp[w][max] = Math.max(Math.max(dp[w - 1][max], dp[w][max - 1]), (weights[w - 1] > max ? 0 : dp[w - 1][max - weights[w - 1]] + values[w - 1])

                );
            }
        }
        for (int[] w : dp) {
            System.out.println(Arrays.toString(w));
        }
        return dp[weights.length][maxWeight];

    }

    public static char firstNonRepeatingChar(String s) {
        int[] ref = new int[26];
        for (char c : s.toCharArray()) {
            ref[c - 'a'] += 1;
        }
        for (char c : s.toCharArray()) {
            if (ref[c - 'a'] == 1) {
                return c;
            }
            ;
        }
        return '_';
    }

    public static boolean canJump(int[] nums) {
        int reach = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > reach) return false;
//            System.out.println("At " + i + ", can jump to " + (i + nums[i]));
            reach = Math.max(reach, i + nums[i]);
            if (reach > nums.length - 1) {
                return true;
            }
//            System.out.println("max reach: "+reach);
        }
        return true;
    }

    public static int uniquePaths(int m, int n) {
        if (m == 1 || n == 1) {
            return 1;
        } else if (m == 2) {
            return n;
        } else if (n == 2) {
            return m;
        }
        int dp[][] = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    public static int[] s(int[] nums, int target) {
//        HashMap<Integer, Integer> map = new HashMap<>();
        Set<Integer> s = new HashSet<>();
        for (int n : nums) {
            int less = target - n;
            if (s.contains(less)) {
                return new int[]{less, n};
            } else {
                s.add(n);
            }
        }
        return new int[]{};
    }

    public static int numDecodings(String s) {
        if (s.charAt(0) == '0') return 0;
        int sLen = s.length();
        if (sLen == 1) return 1;
        int[] dp = new int[sLen + 1];
        dp[0] = 1;
        dp[1] = 1; // if here, means the first char can be parsed by itself
        for (int i = 1; i < sLen; i++) {
            int currN = Character.getNumericValue(s.charAt(i));
            int prevN = Character.getNumericValue(s.charAt(i - 1));
            if (currN == 0) {
                if (prevN == 0) return 0; // 00 cannot be parsed
                if (prevN > 2) {
                    dp[i + 1] = 0; // 40 -> no way to parse
                } else {
                    dp[i] = dp[i - 1]; // regress! 12 (2 ways) -> 120 (1 way)
                    dp[i + 1] = dp[i];
                }
            } else {
                dp[i + 1] = dp[i]; // can be parsed multiple ways
                if (prevN > 0 && ((prevN * 10) + currN < 27)) {
                    dp[i + 1] += dp[i - 1];
                }
            }
        }
        return dp[sLen];
    }

    public static int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        return Math.max(_rob(Arrays.copyOfRange(nums, 0, nums.length - 1)), _rob(Arrays.copyOfRange(nums, 1, nums.length)));
    }

    public static int _rob(int[] nums) {
        int notRobLast = 0;
        int didRobLast = 0;
        int t = 0;
        for (int v = 0; v < nums.length; v++) {
            t = notRobLast;
            notRobLast = Math.max(didRobLast, notRobLast);
            didRobLast = t + nums[v];
        }
        return Math.max(didRobLast, notRobLast);
    }

    public static int lengthOfLIS(int[] nums) {
        if (nums.length == 1) {
            return 1;
        }
        List<Integer> sub = new ArrayList<Integer>();
        sub.add(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > sub.get(sub.size() - 1)) {
                sub.add(nums[i]);
            } else {
                int j = 0;
                while (sub.get(j) < nums[i]) {
                    j++;
                }
                sub.set(j, nums[i]);
            }
        }
        return sub.size();
    }
}
