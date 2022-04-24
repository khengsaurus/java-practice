import java.util.*;

public class BSQns {
    public static void main(String[] args) {
    }

    public BSQns() {
    }

    //    1855. Maximum Distance Between a Pair of Values, Binary search
    public int maxDistance(int[] nums1, int[] nums2) {
        int dist = 0;
        for (int i = 0; i < nums1.length; i++) {
            if (i == nums2.length) break;
            int l = i, r = nums2.length - 1;
            while (l <= r) {
                int m = (l + r) / 2;
                if (nums1[i] > nums2[m]) {
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            }
            dist = Math.max(dist, r - i);
        }
        return dist;
    }

    /**
     * 99. Recover Binary Search Tree
     * In-order traversal TODO!!!
     * Think of it as a in-order validation - an in-order BST array will be sorted ascending!!!
     */
    TreeNode prev = null, first = null, second = null;

    public void recoverTree(TreeNode root) {
        inOrder99(root);
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }

    private void inOrder99(TreeNode curr) {
        if (curr == null) return;
        inOrder99(curr.left);
        if (prev != null && prev.val > curr.val) {
            if (first == null) first = prev;
            second = curr;
        }
        prev = curr;
        inOrder99(curr.right);
    }

    //    33. Search in Rotated Sorted Array
    public static int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int m = (l + r) / 2;
            if (nums[m] == target) return m;
            if (nums[l] <= nums[m]) {
                // pivot on right of m
                if (target >= nums[l] && target < nums[m]) {
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            } else {
                // pivot on left of m
                if (target > nums[m] && target <= nums[r]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
        }
        return nums[l] == target ? l : -1;
    }

    //    34. Find First and Last Position of Element in Sorted Array
    public static int[] searchRange(int[] nums, int target) {
        return new int[]{findFirst(nums, target), findLast(nums, target)};
    }

    public static int findFirst(int[] nums, int val) {
        int index = -1, l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] >= val) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
            if (nums[mid] == val) index = mid;
        }
        return index;
    }

    public static int findLast(int[] nums, int val) {
        int index = -1, l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] <= val) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
            if (nums[mid] == val) index = mid;
        }

        return index;
    }

    public static int binarySearch(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (nums[m] == target) return m;
            if (nums[m] > target) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return -1;
    }

    //    1346. Check If N and Its Double Exist
    public static boolean checkIfExist(int[] arr) {
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            int d = 2 * arr[i];
            if (arr[i] >= 0) {
                if (d > arr[arr.length - 1]) return false;
                if (binarySearchLR(arr, d, i + 1, arr.length - 1) > -1) return true;
            }
            if (d < arr[0]) continue;
            if (binarySearchLR(arr, d, 0, i - 1) > -1) return true;
        }
        return false;
    }

    public static int binarySearchLR(int[] sortedArray, int target, int l, int r) {
        while (l <= r) {
            int m = (l + r) / 2;
            if (sortedArray[m] == target) return m;
            if (sortedArray[m] > target) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return -1;
    }

    //    1337. The K Weakest Rows in a Matrix
    public static int[] kWeakestRows(int[][] mat, int k) {
        Map<Integer, Queue<Integer>> map = new HashMap<>();
        int rows = mat.length, cols = mat[0].length;

        for (int row = 0; row < rows; row++) {
            int count = bs1337(mat[row]);
            if (!map.containsKey(count)) {
                Queue<Integer> newQueue = new LinkedList<>();
                newQueue.add(row);
                map.put(count, newQueue);
            } else {
                map.get(count).add(row);
            }
        }

        int[] rv = new int[k];
        int count = 0;
        for (int i = 0; i < k; i++) {
            while (!map.containsKey(count) || map.get(count).isEmpty()) count++;
            rv[i] = map.get(count).remove();
        }

        return rv;
    }

    public static int bs1337(int[] row) {
        int l = 0, r = row.length - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (row[m] == 1) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return l;
    }

    //    1351. Count Negative Numbers in a Sorted Matrix
    public static int countNegatives(int[][] grid) {
        int rows = grid.length, count = 0;
        for (int row = rows - 1; row >= 0; row--) {
            int negativeNumsInRow = bs1351(grid[row]);
            if (negativeNumsInRow == 0) break;
            count += negativeNumsInRow;
        }
        return count;
    }

    public static int bs1351(int[] row) {
        int l = 0, r = row.length - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (row[m] >= 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return row.length - l;
    }

    //    74. Search a 2D Matrix
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
        return bs74(matrix[row], 0, matrix[row].length - 1, target) >= 0;
    }

    public static int bs74(int[] row, int left, int right, int target) {
        if (left > right) return -1;
        int mid = left + (right - left) / 2;
        int midValue = row[mid];
        if (target == midValue) return mid;
        if (target < midValue) return bs74(row, left, mid - 1, target);
        return bs74(row, mid + 1, right, target);
    }

    //    1608. Special Array With X Elements Greater Than or Equal X
    public static int specialArray(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i <= nums.length; i++) {
            if (nums.length - leftMostIndex(nums, i) == i) return i;
        }
        return -1;
    }

    public static int leftMostIndex(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] >= target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    //    538. Convert BST to Greater Tree
    public TreeNode convertBST(TreeNode root) {
        bs538(root, 0);
        return root;
    }

    public int bs538(TreeNode root, int val) {
        if (root == null) return val;
        int right = bs538(root.right, val);
        int left = bs538(root.left, root.val + right);
        root.val += right;
        return left;
    }

    //    669. Trim a Binary Search Tree
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) return null;
        if (root.val > high) return trimBST(root.left, low, high);
        if (root.val < low) return trimBST(root.right, low, high);

        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);

        return root;
    }

    /**
     * 1539. Kth Missing Positive Number
     * Strictly increasing order -> index 1, no 0
     * |
     * If no numbers are missing, correctVal at index m = m+1+k
     * [1,2,3,4,5,6] & k=0
     * At index i, correctVal = i+1
     * |
     * [1,2,3,4,6,7] & k=1
     * At index 4, correctVal = 6 (midVal = 6)
     * |
     * If midValue >= correctVal, there is a value missing -> shift left
     * Else no missing value -> shift right
     */
    public static int findKthPositive(int[] arr, int k) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (arr[mid] >= mid + 1 + k) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return l + k;
    }

    //    744. Find Smallest Letter Greater Than Target
    public static char nextGreatestLetter(char[] letters, char target) {
        if ((int) target >= (int) letters[letters.length - 1]) return letters[0];
        int l = 0, r = letters.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            char m = letters[mid];
            if ((int) target >= (int) m) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return letters[l];
    }

    /**
     * 69. Sqrt(x)
     * Use div instead of multiplication to avoid overflow
     */
    public int mySqrt(int x) {
        if (x <= 1) return x;
        int l = 1, r = x / 2;
        while (l <= r) {
            int mid = l + (r - l) / 2, div = x / mid;
            if (div == mid) return mid;
            if (div > mid) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        return l - 1;
    }

    //    104. Maximum Depth of Binary Tree
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * 110. Balanced Binary Tree
     */
    public boolean isBalanced(TreeNode root) {
        Bst110 b = new Bst110(root);
        return b.getRes();
    }

    private class Bst110 {
        private boolean res = true;

        public Bst110(TreeNode root) {
            balanced(root);
        }

        public boolean getRes() {
            return res;
        }

        private int balanced(TreeNode root) {
            if (root == null) return 0;
            int left = balanced(root.left);
            int right = balanced(root.right);
            if (Math.abs(left - right) > 1) res = false;
            return 1 + Math.max(left, right);
        }
    }


    //    1385. Find the Distance Value Between Two Arrays
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        int dist = 0;
        for (int i : arr1) {
            if (!bst1385(i, arr2, d)) dist++;
        }
        return dist;
    }

    public boolean bst1385(int i, int[] arr, int d) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int mid = l + ((r - l) / 2), midVal = arr[mid];
            if (Math.abs(i - midVal) <= d) {
                return true;
            }
            if (i > midVal + d) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return false;
    }

    //    367. Valid Perfect Square
    public boolean isPerfectSquare(int num) {
        long l = 1, r = num;
        while (l <= r) {
            long mid = l + (r - l) / 2, p = mid * mid;
            if (p > num) {
                r = mid - 1;
            } else if (p < num) {
                l = mid + 1;
            } else {
                return true;
            }
        }
        return false;
    }

    //    374. Guess Number Higher or Lower
    public int guess(int n, int guess) {
        if (n == guess) return 0;
        if (n > guess) return -1;
        return 1;
    }

    public int guessNumber(int n) {
        int min = 1, max = n, mid = min + (max - min) / 2;
        while (min <= max) {
            mid = min + (max - min) / 2;
            switch (guess(mid, 2)) {
                case 0:
                    return mid;
                case 1:
                    min = mid + 1;
                    break;
                case -1:
                    max = mid - 1;
                    break;
            }
        }
        return mid;
    }
}
