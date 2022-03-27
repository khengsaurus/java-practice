package LeetcodePlans;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DS1 {
    // 946 - genius sln which isnt mine
    public static boolean validateStackSequences(int[] pushed, int[] popped) {
        int i = 0, j = 0;
        for (int val : pushed) {
            pushed[i++] = val; // req because i-- later;
            while (j < popped.length && i > 0 && pushed[i - 1] == popped[j]) {
                i--;
                j++;
            }
            System.out.println(i + ", " + j);
            System.out.println(Arrays.toString(pushed));
        }
        return i == 0;
    }

    // 74
    public static boolean searchMatrix(int[][] matrix, int target) {
        int row = 0;
        boolean finished = false;
        while (row < matrix.length - 1 && !finished) {
            if (matrix[row + 1][0] <= target) {
                row++;
            } else {
                finished = true;
            }
        }
        return binSearchRow(matrix[row], 0, matrix[row].length - 1, target) >= 0;
    }

    public static int binSearchRow(int[] row, int left, int right, int target) {
        if (left > right) return -1;
        int mid = left + (right - left) / 2;
        int midValue = row[mid];
        if (target == midValue) return mid;
        if (target < midValue) return binSearchRow(row, left, mid - 1, target);
        return binSearchRow(row, mid + 1, right, target);
    }

    public static int binSearchMatrixRows(int[][] matrix, int top, int bot, int target) {
        if (top > bot) return -1;
        if (top == bot) return top;
        int mid = top + (bot - top) / 2;
        int midVal = matrix[mid][0];
        if (midVal > target) {
            return binSearchMatrixRows(matrix, top, mid - 1, target);
        }
        if (midVal < target) {
            if (mid < matrix.length - 1 && matrix[mid + 1][0] > target) {
                return mid;
            } else {
                return binSearchMatrixRows(matrix, mid + 1, bot, target);
            }
        }
        return mid;
    }

    @Test
    public void searchMatrixWorks() {
        int[] r1 = new int[]{1, 3, 5, 7};
        int[] r2 = new int[]{10, 11, 16, 20};
        int[] r3 = new int[]{23, 30, 34, 60};
        int[][] matrix = new int[][]{r1, r2, r3};
        assertTrue(searchMatrix(matrix, 3));
        assertTrue(searchMatrix(matrix, 60));
        assertFalse(searchMatrix(matrix, 4));
        assertFalse(searchMatrix(matrix, 61));
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
