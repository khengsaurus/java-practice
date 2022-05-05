import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class DS {
    // 112
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) return root.val == targetSum;
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    //    101
    public boolean isSymmetric(TreeNode root) {
        return root == null || isSymmetricHelp(root.left, root.right);
    }

    private boolean isSymmetricHelp(TreeNode left, TreeNode right) {
        if (left == null || right == null) return left == right;
        if (left.val != right.val) return false;
        return isSymmetricHelp(left.left, right.right) && isSymmetricHelp(left.right, right.left);
    }

    // 946. Validate Stack Sequences
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        // pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
        int push = 0, pop = 0;
        for (int i : pushed) {
            pushed[push++] = i;
            while (pop < popped.length && push > 0 && popped[pop] == pushed[push - 1]) {
                push--;
                pop++;
            }
        }
        return push == 0;
    }

    // 36
    public static boolean isValidSudoku(char[][] board) {
        boolean[][] memoRow = new boolean[9][10];
        boolean[][] memoCol = new boolean[9][10];
        boolean[][] memoBox = new boolean[9][10];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                int cIndex = Character.getNumericValue(board[row][col]);
                if (cIndex > 0) {
                    int box = (3 * (row / 3)) + (col / 3);
                    if (memoRow[row][cIndex] || memoCol[col][cIndex] || memoBox[box][cIndex]) {
                        return false;
                    } else {
                        memoRow[row][cIndex] = true;
                        memoCol[col][cIndex] = true;
                        memoBox[box][cIndex] = true;
                    }
                }
            }
        }
        return true;
    }

    @Test
    public void isValidSudokuTest() {
        char[] r1 = new char[]{'5', '3', '.', '.', '7', '.', '.', '.', '.'};
        char[] r2 = new char[]{'6', '.', '.', '1', '9', '5', '.', '.', '.'};
        char[] r3 = new char[]{'.', '9', '8', '.', '.', '.', '.', '6', '.'};
        char[] r4 = new char[]{'8', '.', '.', '.', '6', '.', '.', '.', '3'};
        char[] r5 = new char[]{'4', '.', '.', '8', '.', '3', '.', '.', '1'};
        char[] r6 = new char[]{'7', '.', '.', '.', '2', '.', '.', '.', '6'};
        char[] r7 = new char[]{'.', '6', '.', '.', '.', '.', '2', '8', '.'};
        char[] r8 = new char[]{'.', '.', '.', '4', '1', '9', '.', '.', '5'};
        char[] r9 = new char[]{'.', '.', '.', '.', '8', '.', '.', '7', '9'};
        assertTrue(isValidSudoku(new char[][]{r1, r2, r3, r4, r5, r6, r7, r8, r9}));
    }

    //    1
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                return new int[]{i, map.get(nums[i])};
            } else {
                map.put(target - nums[i], i);
            }
        }
        return new int[]{};
    }

    //    88
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int index = nums1.length;
        while (m > 0 && n > 0) {
            if (nums1[m - 1] > nums2[n - 1]) {
                nums1[--index] = nums1[--m];
            } else {
                nums1[--index] = nums2[--n];
            }
        }
        while (n > 0) {
            nums1[--index] = nums2[--n];
        }
    }

    //    53
    public static int maxSubArray(int[] nums) {
        int maxSum = nums[0];
        int currSum = maxSum;
        for (int i = 1; i < nums.length; i++) {
            currSum = Math.max(nums[i] + currSum, nums[i]);
            maxSum = Math.max(currSum, maxSum);
        }
        return maxSum;
    }

    //    217
    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i])) {
                return true;
            }
        }
        return false;
    }
}
