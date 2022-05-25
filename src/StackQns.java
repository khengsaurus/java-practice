import java.util.*;

/*
Decreasing mono-stack: popping the completed stack consecutively will return increasing elements.
 */
public class StackQns {
    //    32. Longest Valid Parentheses
    public int longestValidParentheses(String s) {
        int len = s.length();
        int[] dp = new int[len];
        int open = 0, max = 0;

        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == '(') open++;
            else {
                if (open > 0) {
                    dp[i] = 2 + dp[i - 1];
                    int prev = i - dp[i];
                    if (prev > 0) dp[i] += dp[prev];
                    open--;
                }
            }
            if (dp[i] > max) max = dp[i];
        }
        return max;
    }

    //    1209. Remove All Adjacent Duplicates in String II
    public String removeDuplicates(String s, int k) {
        ArrayDeque<Adjacent> stack = new ArrayDeque<>(s.length());
        if (s.length() < k) return s;
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && stack.peekLast()._char == c) {
                stack.peekLast()._count++;
            } else {
                stack.addLast(new Adjacent(c, 1));
            }
            if (!stack.isEmpty() && stack.peekLast()._count == k) stack.removeLast();
        }
        StringBuilder sb = new StringBuilder();
        for (Adjacent a : stack) {
            sb.append(String.valueOf(a._char).repeat(a._count));
        }
        return sb.toString();
    }

    static class Adjacent {
        public char _char;
        public int _count;

        public Adjacent(char c, int count) {
            this._char = c;
            this._count = count;
        }
    }

    //    456. 132 Pattern TODO
    public boolean find132pattern(int[] nums) {
        int len = nums.length;
        if (len < 3) return false;
        Deque<Integer> stack = new ArrayDeque<>();
        int k = -1;
        for (int i = len - 1; i >= 0; i--) {
            if (k > -1 && nums[i] < nums[k]) return true;
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) k = stack.pop();
            stack.push(i);
        }
        return false;
    }

    //    907. Sum of Subarray Minimums TODO
    public static int sumSubarrayMins(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        int[] dp = new int[arr.length + 1];
        stack.push(-1);
        int result = 0, M = (int) 1e9 + 7;
        for (int i = 0; i < arr.length; i++) {
            while (stack.peek() != -1 && arr[i] <= arr[stack.peek()]) stack.pop();
            dp[i + 1] = (dp[stack.peek() + 1] + (i - stack.peek()) * arr[i]) % M;
            stack.push(i);
            result += dp[i + 1];
            result %= M;
        }
        return result;
    }

    //    1130. Minimum Cost Tree From Leaf Values
    public int mctFromLeafValues(int[] arr) {
        int res = 0;
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(Integer.MAX_VALUE);
        for (int a : arr) {
            while (stack.peekLast() <= a) {
                int prev = stack.pollLast();
                res += prev * Math.min(stack.peekLast(), a);
            }
            stack.offerLast(a);
        }
        while (stack.size() > 2) {
            res += stack.pollLast() * stack.peekLast();
        }
        return res;
    }

    //    503. Next Greater Element II
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n * 2; i++) {
            int _i = i % n;
            while (!stack.isEmpty() && nums[stack.peek()] < nums[_i]) {
                res[stack.pop()] = nums[_i];
            }
            stack.push(_i);
        }
        return res;
    }

    //    496. Next Greater Element I
    public static int[] nextGreaterElementStack(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        for (int num : nums2) {
            while (!stack.isEmpty() && stack.peek() < num) {
                map.put(stack.pop(), num);
            }
            stack.push(num);
        }
        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = map.getOrDefault(nums1[i], -1);
        }
        return nums1;
    }

    //    Without mono stack: O(N1 * N2)
    public static int[] nextGreaterElementArray(int[] nums1, int[] nums2) {
        int[] res = new int[nums1.length];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums2.length; i++) map.put(nums2[i], i);

        int j;
        for (int i = 0; i < nums1.length; i++) {
            if (!map.containsKey(nums1[i])) continue;
            for (j = map.get(nums1[i]) + 1; j < nums2.length; j++) {
                if (nums1[i] < nums2[j]) {
                    res[i] = nums2[j];
                    break;
                }
            }
            if (j == nums2.length) res[i] = -1;
        }
        System.out.println(Arrays.toString(res));
        return res;
    }

    //    402. Remove K Digits
    public String removeKdigits(String num, int k) {
        if (k == num.length()) return "0";
        ArrayDeque<Character> stack = new ArrayDeque<>();
        int i = 0;
        for (char digit : num.toCharArray()) {
            /* When we find a digit smaller than the previous, discard the previous if k > 0.
               The digit at the end will be removed if k > 0 */
            while (stack.size() > 0 && k > 0 && stack.peekLast() > digit) {
                stack.pollLast();
                k--;
            }
            stack.offerLast(digit);
        }

        while (k-- > 0) stack.pollLast();

        StringBuilder str = new StringBuilder();
        while (!stack.isEmpty()) str.append(stack.pollFirst());
        while (str.length() > 1 && str.charAt(0) == '0') str.deleteCharAt(0);
        return str.length() == 0 ? "0" : str.toString();
    }
}
