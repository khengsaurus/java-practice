import java.util.*;

public class DPQns {
    public static void main(String[] args) {
        System.out.println(simplifyPath("/a//b/"));
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
        if(stack.isEmpty()){
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

    public static int climbStairs(int n) {
//        Map<Integer, Integer> map = new HashMap<>();
//        map.put(1, 1);
//        map.put(2, 2);
//        if (n < 3) {
//            return map.get(n);
//        }
//        for (int step = 3; step <= n; step++) {
//            map.put(step, map.get(step - 2) + map.get(step - 1));
//        }
//        return map.get(n);
//        2 -> 2
//        3 -> 3
//        4 -> 5
//        5 -> 8
        if (n < 2) {
            return 1;
        }
        int pre = 1;
        int pre2 = 1;
        for (int step = 2; step <= n; step++) {
            int t = pre + pre2;
            pre2 = pre;
            pre = t;
        }
        return pre;
    }
}
