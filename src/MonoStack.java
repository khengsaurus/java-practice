import java.util.*;

/*
Decreasing mono-stack: popping the completed stack consecutively will return increasing elements.
 */
public class MonoStack {
    public static void main(String[] args) {
        int res = sumSubarrayMins(new int[]{4, 2, 3, 5, 1});
        System.out.println(res);
    }

    /**
     * 907. Sum of Subarray Minimums TODO: help idgi
     * [4, 2, 3, 5, 1] <-- array
     * [1, 2, 1, 1, 5] <-- count of subarrays ending with [i] and [i] is the min element
     * [4, 4, 3, 5, 5] <-- contributions to res
     * [4, 4, 7, 12, 5] <-- dp: contribution of [i] and prev min
     * <p>
     * Equivalent to:
     * [1, 4, 2, 1, 5] <-- count of subarrays where [i] is the min element
     * [4, 8, 6, 5, 5] <-- contribution of [i]
     */
    public static int sumSubarrayMins(int[] arr) {
        Stack<Integer> stack = new Stack<>(); // mono increasing stack
        int[] dp = new int[arr.length + 1];
        int[] prevMinCont = new int[arr.length + 1];
        int[] subarrayCount = new int[arr.length + 1];
        int[] cont = new int[arr.length + 1];
        stack.push(-1);
        int res = 0, MOD = (int) 1e9 + 7;
        for (int i = 0; i < arr.length; i++) {
            /* Find the previous smallest element. Pop larger elements - increasing stack  */
            while (stack.peek() != -1 && arr[stack.peek()] >= arr[i]) stack.pop();
            /* Contribution of an element being the frequency it is the smallest element
            in surrounding sub arrays, i.e. [2,1,3] -> value of 1 = 4 */
            int PLECont = dp[stack.peek() + 1];
            prevMinCont[i + 1] = PLECont;
            /* Number of elements to PLE, including self.
            0 -> the smallest element so far
            1 -> previous element was smaller */
            int rightMinusLeft = i - stack.peek();
            subarrayCount[i + 1] = rightMinusLeft;
            int currCont = rightMinusLeft * arr[i];
            cont[i + 1] = currCont;
            dp[i + 1] = PLECont + currCont;
            res = (res + dp[i + 1]) % MOD;
            stack.push(i);
        }
//        System.out.println("   " + Arrays.toString(arr));
//        System.out.println(Arrays.toString(subarrayCount) + "<- subarrayCount");
//        System.out.println(Arrays.toString(cont) + "<- cont");
//        System.out.println(Arrays.toString(prevMinCont) + "<- prevMinCont");
//        System.out.println(Arrays.toString(dp) + "<- dp");
        return res;
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
