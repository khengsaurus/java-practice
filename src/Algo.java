import java.lang.reflect.Array;
import java.util.*;

public class Algo implements HasDirs {
    public static void main(String[] args) {
//        List<List<Integer>> l = permute(new int[]{1, 2, 3});
//        for (List<Integer> _l : l) {
//            System.out.println(_l);
//        }
        List<String> res = letterCasePermutation("sad");
        System.out.println(res);
    }

    static List<List<Integer>> result;
    static List<String> strRes;

    //    784
    public static List<String> letterCasePermutation(String s) {
        char[] chars = s.toCharArray();
        strRes = new ArrayList<>();
        recurse784(chars, 0);
        return strRes;
    }

    public static void recurse784(char[] chars, int start) {
        if (start == chars.length) {
            strRes.add(new String(chars));
            return;
        }
        char c = chars[start];
        if (Character.isDigit(c)) {
            recurse784(chars, start + 1);
        } else {
            chars[start] = Character.toLowerCase(c);
            recurse784(chars, start + 1);
            chars[start] = Character.toUpperCase(c);
            recurse784(chars, start + 1);
        }
    }

    //    46
    public static List<List<Integer>> permute(int[] nums) {
        result = new LinkedList<>();
        Deque<Integer> combo = new LinkedList<>();
        boolean[] used = new boolean[nums.length];
        backtrack46(nums, combo, used);
        return result;
    }

    public static void backtrack46(int[] nums, Deque<Integer> combo, boolean[] used) {
        if (combo.size() == nums.length) {
            result.add(new ArrayList<>(combo));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            combo.offerLast(nums[i]);
            backtrack46(nums, combo, used);
            used[i] = false;
            combo.pollLast();
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    /**
     * 77 - DECISION TREE, BACKTRACKING
     * Base: n, Height of decision tree: k
     * Upperbound: O(n^k)
     */
    public static List<List<Integer>> combine2(int n, int k) {
        result = new LinkedList<>();
        Deque<Integer> comb = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            comb.offerLast(i);
            backTrack2(i + 1, n, k - 1, comb);
            comb.pollLast();
        }
        return result;
    }

    private static void backTrack2(int start, int n, int remaining, Deque<Integer> currComb) {
        if (remaining == 0) {
            result.add(new LinkedList(currComb));
            return;
        }
        for (int i = start; i <= n - remaining + 1; i++) {
            currComb.offerLast(i);
            backTrack2(i + 1, n, remaining - 1, currComb);
            currComb.pollLast();
        }
    }

    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> combs = new ArrayList<List<Integer>>();
        backtrack(combs, new ArrayList<Integer>(), 1, n, k);
        return combs;
    }

    public static void backtrack(List<List<Integer>> combs, List<Integer> comb, int start, int n, int k) {
        if (comb.size() == k) {
            combs.add(new ArrayList<Integer>(comb)); // create new array here, else this will be a reference
            return;
        }
        for (int i = start; i <= n; i++) { // the decision tree
            comb.add(i); // decision: use i
            backtrack(combs, comb, i + 1, n, k); // explore all options with i
            comb.remove(comb.size() - 1); // remove i
        }
    }

    //    410
    public static int splitArray(int[] nums, int m) {
        int max = 0;
        long sum = 0;
        for (int num : nums) {
            max = Math.max(num, max);
            sum += num;
        }
        if (m == 1) return (int) sum;
        //binary search
        long l = max;
        long r = sum;
        while (l <= r) {
            long mid = (l + r) / 2;
            if (valid(mid, nums, m)) {
                System.out.println("Valid: " + l + ", " + mid + ", " + r);
                r = mid - 1;
            } else {
                System.out.println("Invalid: " + l + ", " + mid + ", " + r);
                l = mid + 1;
            }
        }
        return (int) l;
    }

    public static boolean valid(long target, int[] nums, int m) {
        int count = 1;
        long total = 0;
        for (int num : nums) {
            total += num;
            if (total > target) {
                total = num;
                count++;
                if (count > m) {
                    return false;
                }
            }
        }
        return true;
    }

    //    994
    public static int orangesRotting(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        Queue<int[]> toVisit = new LinkedList<>();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 2) {
                    for (int[] dir : fourDirs) {
                        int newI = row + dir[0], newJ = col + dir[1];
                        if (newI >= 0 && newJ >= 0 && newI < rows && newJ < cols && grid[newI][newJ] == 1) {
                            toVisit.add(new int[]{row + dir[0], col + dir[1]});
                        }
                    }
                }
            }
        }

        int count = 0;
        while (!toVisit.isEmpty()) {
            boolean didRot = false;
            int size = toVisit.size();
            while (size-- > 0) {
                int[] curr = toVisit.poll();
                int i = curr[0], j = curr[1];
                if (grid[i][j] == 1) {
                    grid[i][j] = 2;
                    didRot = true;
                    for (int[] dir : fourDirs) {
                        int newI = i + dir[0], newJ = j + dir[1];
                        if (newI >= 0 && newJ >= 0 && newI < rows && newJ < cols) {
                            toVisit.add(new int[]{i + dir[0], j + dir[1]});
                        }
                    }
                }
            }
            if (didRot) count++;
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 1) return -1;
            }
        }

        return count;
    }

    //    116
    public Node connect(Node root) {
        dfs116(root);
        return root;
    }

    public void dfs116(Node root) {
        if (root == null) return;
        if (root.left != null && root.right != null) {
            root.left.next = root.right;
        }
        if (root.left != null) dfs116(root.left);
        if (root.right != null) {
            if (root.next != null) root.right.next = root.next.left;
            dfs116(root.right);
        }
    }

    //    617
    public static TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            root1 = root1 == null ? root2 : root1;
        } else {
            root1.val = root1.val + root2.val;
            if (root1.left != null || root2.left != null) {
                root1.left = mergeTrees(root1.left, root2.left);
            }
            if (root1.right != null || root2.right != null) {
                root1.right = mergeTrees(root1.right, root2.right);
            }
        }
        return root1;
    }

    // 567 - Check if one of s1's permutations is in a substring of s2
    public boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length();
        if (s2.length() < s1.length()) return false;
        int[] freq1 = charFrequency(s1);
        int[] freq2 = charFrequency(s2.substring(0, len1));
        if (match(freq1, freq2)) return true;
        for (int i = len1; i < s2.length(); i++) {
            freq2[s2.charAt(i) - 'a']++;
            freq2[s2.charAt(i - len1) - 'a']--;
            if (match(freq1, freq2)) return true;
        }
        return false;
    }

    public int[] charFrequency(String s) {
        int[] cs = new int[26];
        for (int i = 0; i < s.length(); i++) cs[s.charAt(i) - 'a']++;
        return cs;
    }

    public boolean match(int[] freq1, int[] freq2) {
        for (int i = 0; i < freq1.length; i++) {
            if (freq1[i] != freq2[i]) return false;
        }
        return true;
    }

    //    557
    public static void reverse(char[] chars, int start, int end) {
        while (start <= end) {
            char t = chars[start];
            chars[start] = chars[end];
            chars[end] = t;
            start++;
            end--;
        }
    }

    public static String reverseWordsBetter(String s) {
        char[] chars = s.toCharArray();
        int left = 0, right = 0;
        while (right < chars.length) {
            if (chars[right] == ' ') {
                reverse(chars, left, right - 1);
                left = right + 1;
            }
            right++;
        }
        reverse(chars, left, right - 1);
        return new String(chars);
    }

    public static String reverseWords(String s) {
        int len = s.length();
        int left = 0;
        int right = 0;
        char[] chars = new char[len];
        while (right < len) {
            if (s.charAt(right) == ' ') {
                chars[right] = ' ';
                for (int i = 0; i < right - left; i++) {
                    chars[left + i] = s.charAt(right - i - 1);
                }
                left = right + 1;
            }
            right++;
        }
        if (left < right) {
            for (int i = 0; i < right - left; i++) {
                chars[left + i] = s.charAt(right - i - 1);
            }
        }
        return new String(chars);
    }

    // 344
    public static void reverseString(char[] s) {
        int len = s.length;
        for (int i = 0; i < len / 2; i++) {
            char t = s[i];
            s[i] = s[len - i - 1];
            s[len - i - 1] = t;
        }
    }

    //    180
    public static void rotate(int[] nums, int k) {
        int len = nums.length;
        k = k % len;
        if (k != 0) {
            int[] holders = new int[k];
            for (int i = 0; i < k; i++) {
                holders[k - i - 1] = nums[len - 1 - i];
            }
            for (int i = len - 1; i >= 0; i--) {
                if (i < k) {
                    nums[i] = holders[i];
                } else {
                    nums[i] = nums[i - k];
                }
            }
        }
    }

    //    977
    public static int[] sortedSquares(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        int index = end;
        int[] res = new int[nums.length];
        while (start <= end) {
            if (Math.abs(nums[start]) > Math.abs(nums[end])) {
                res[index--] = nums[start] * nums[start];
                start++;
            } else {
                res[index--] = nums[end] * nums[end];
                end--;
            }
        }
        return res;
    }

    //    35
    public static int searchInsert(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) { //
            int mid = l + ((r - l) / 2);
            if (nums[mid] == target) return mid;
            if (target < nums[mid]) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return r + 1;
    }

    //    278
    public static boolean isBadVersion(int n) {
        return n >= 2;
    }

    public static int firstBadVersion(int n) {
        int start = 1;
        int end = n;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (isBadVersion(mid)) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }

    //    704
    public static int binarySearch(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] > target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }

    public static int binarySearch(int[] nums, int start, int end, int target) {
        int mid = start + ((end - start) / 2);
        if (nums[mid] == target) return mid;
        if (end <= start) return -1;
        return target < nums[mid]
                ? binarySearch(nums, start, mid - 1, target)
                : binarySearch(nums, mid + 1, end, target);
    }

    //    TODO: 4 - https://www.youtube.com/watch?v=q6IEA26hvXc
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            int[] t = nums1;
            nums1 = nums2;
            nums2 = t;
        }
        if (nums1.length == 0) {
            int len2 = nums2.length;
            return len2 % 2 == 0
                    ? (double) (nums2[len2 / 2] + nums2[len2 / 2 + 1]) / 2
                    : nums2[len2 / 2];
        }

        int partSmallLen = Math.max(1, nums1.length / 2);
        int partBigLen = (nums1.length + nums2.length) / 2 - partSmallLen;

        int intAfterSmall = partSmallLen == nums1.length ? nums2[partBigLen] : nums1[partSmallLen];
        int intAfterBig = partBigLen == nums2.length ? nums1[partSmallLen] : nums2[partBigLen];
        boolean _canShrinkSmall = partSmallLen != 0 && partBigLen != nums2.length;
        boolean _canShrinkBig = partBigLen != 0 && partSmallLen != nums1.length;

        while (_canShrinkBig) {
            if (nums1[partSmallLen] < nums2[partBigLen]) {
                partSmallLen++;
                partBigLen--;
            } else if (nums2[partBigLen] < nums1[partSmallLen]) {
                partBigLen++;
                partSmallLen--;
            }
            _canShrinkBig = partSmallLen != nums1.length && partBigLen != nums2.length;
        }
        return 0;
    }

    //    122
    public static int maxProfit2(int[] prices) {
        int max = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            int priceToday = prices[i];
            int priceTmr = prices[i + 1];
            if (priceTmr > priceToday) max += (priceTmr - priceToday);
        }
        return max;
    }

    //    121
    public static int maxProfit(int[] prices) {
//        int max = 0, left = 0, right = 1;
//        while (right < prices.length) {
//            int diff = prices[right] - prices[left];
//            if (diff > 0) {
//                max = Math.max(max, diff);
//            } else {
//                left = right;
//            }
//            right++;
//        }
        int max = 0, localMin = Integer.MAX_VALUE, localMax = 0;
        for (int i = 0; i < prices.length; i++) {
            int dayPrice = prices[i];
            if (dayPrice < localMin) {
                localMin = dayPrice;
                localMax = 0;
            } else if (dayPrice > localMax) {
                localMax = dayPrice;
                max = Math.max(max, localMax - localMin);
            }
        }
        return max;
    }

    //    350
    public static int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0, j = 0, k = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                nums1[k++] = nums1[i++];
                j++;
            } else if (nums2[j] > nums1[i]) {
                i++;
            } else {
                j++;
            }
        }

        return Arrays.copyOfRange(nums1, 0, k);
//        return res.stream().mapToInt(Integer::intValue).toArray();

//        int[] result = new int[res.size()];
//        for (int k = 0; k < res.size(); j++) {
//            result[k] = res.get(k);
//        }
//        return result;
    }
}
