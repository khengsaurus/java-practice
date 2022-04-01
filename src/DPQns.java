import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class DPQns {
    public static void main(String[] args) {
//        int res = jumpBetter(new int[]{2, 5, 1, 0, 0, 0});
//        int res = maxSubarraySumCircular(new int[]{10, -3, -2, -3});
//        int res = getMaxLen(new int[]{1, 1, -1, 1, -1, 1, 1, 0, 1, -1, -1});
//        int res = maxScoreSightseeingPair(new int[]{3, 7, 2, 3});
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
        int total = 0, maxSum = -Integer.MAX_VALUE, minSum = 0;
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

    //    45
    public static int jumpBetter(int[] nums) {
        if (nums.length == 1) return 0;
        int leftBoundary = 0, rightBoundary = nums[0];
        int farthestJump = nums[0];
        int numJumps = 1;
        while (farthestJump < nums.length - 1) {
            for (int j = leftBoundary; j <= rightBoundary; j++) {
                farthestJump = Math.max(farthestJump, j + nums[j]);
            }
            leftBoundary = rightBoundary + 1;
            rightBoundary = farthestJump;
            numJumps++;
        }
        return numJumps;
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
                dp[w][max] = Math.max(
                        Math.max(dp[w - 1][max], dp[w][max - 1]),
                        (weights[w - 1] > max ? 0 : dp[w - 1][max - weights[w - 1]] + values[w - 1])

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
            if (i > reach)
                return false;
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
        return Math.max(
                _rob(Arrays.copyOfRange(nums, 0, nums.length - 1)),
                _rob(Arrays.copyOfRange(nums, 1, nums.length))
        );
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

    //    HELP
    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
//         49 MB - not using StringBuilder.
//         Doesn't make sense?? StringBuilder.toString() -> new String
//         String.subString() -> new String
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
//         43 MB - using StringBuilder
//        StringBuilder str = new StringBuilder();
//        for (int i = 1; i <= s.length(); i++) {
//            for (int j = 0; j < i; j++) {
//                str.setLength(0);
//                str.append(s.substring(j, i));
//                if (dp[j] && dict.contains(str.toString())) {
//                    dp[i] = true;
//                    break;
//                }
//            }
//        }
        return dp[s.length()];
    }

    public static int longestCommonSubsequence(String text1, String text2) {
        // NOTE: in 2d array, iterate rows first, then cols
        int cols = text1.length() + 1;
        int rows = text2.length() + 1;
        int[][] arr = new int[rows][cols];
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                arr[row][col] =
                        Math.max(Math.max(arr[row][col - 1], arr[row - 1][col]),
                                (text1.charAt(col - 1) == text2.charAt(row - 1) ? 1 : 0) + arr[row - 1][col - 1]
                        );
            }
        }
//        for (int row = 0; row < rows; row++) {
//            System.out.println(Arrays.toString(arr[row]));
//        }
        return arr[rows - 1][cols - 1];
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

    public static int coinChange(int[] coins, int amount) {
        if (amount == 0 || coins.length == 0) {
            return -1;
        }
        return numberOfWays(coins, amount, new int[amount + 1]);
    }

    public static int numberOfWays(int[] coins, int amount, int[] map) {
        if (amount == 0) {
            return 0;
        }
        if (map[amount] != 0) {
            return map[amount];
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            int count = coins[i] > amount ? -1 : 1 + numberOfWays(coins, amount - coins[i], map);
            if (count >= 0) {
                min = Math.min(min, count);
            }
        }
        if (min != Integer.MAX_VALUE) {
            map[amount] = map[amount] == 0 ? min : Math.min(map[amount], min);
        }
        return map[amount];
    }
}
