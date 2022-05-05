import org.junit.Test;

public class BinarySearch {
    public static void main(String[] args) {
//        findMedianSortedArrays(new int[]{1, 3, 6, 8, 12, 14, 19, 22}, new int[]{1, 7, 15, 21, 22, 23, 24, 25, 26, 27, 28});
        findMedianSortedArrays(new int[]{9, 9, 9}, new int[]{2, 3, 4, 5, 6, 7, 8, 9});
    }

    /**
     * 4. Median of Two Sorted Arrays
     * TODO: Binary search across 2 arrays
     */
    public static double findMedianSortedArrays(int[] shorter, int[] longer) {
        if (shorter == null && longer == null) return 0;
        if (shorter == null) {
            int n = longer.length;
            return longer[(n - 1) / 2] * 0.5 + longer[n / 2] * 0.5;
        }
        if (longer == null) {
            int n = shorter.length;
            return shorter[(n - 1) / 2] * 0.5 + shorter[n / 2] * 0.5;
        }
        if (shorter.length > longer.length) return findMedianSortedArrays(longer, shorter);

        int sLen = shorter.length, lLen = longer.length, tLen = sLen + lLen, half = tLen / 2;
        int sLeft = 0, sRight = sLen;

//        IMPORTANT
        while (sLeft < sRight) {
            int sMid = (sLeft + sRight) / 2;
            // If shorter[sMid] < longer[lMid - 1], shift sMid right -> lMid left
            if (shorter[sMid] < longer[half - sMid - 1]) sLeft = sMid + 1;
            else sRight = sMid;
        }

        int sMid = sLeft;
        int lMid = half - sLeft;

        int shorterLeft = sMid == 0 ? Integer.MIN_VALUE : shorter[sMid - 1];
        int shorterRight = sMid == sLen ? Integer.MAX_VALUE : shorter[sMid];
//        System.out.println("Shorter middle: " + shorterLeft + ", " + shorterRight);

        int longerLeft = lMid == 0 ? Integer.MIN_VALUE : longer[lMid - 1];
        int longerRight = lMid == lLen ? Integer.MAX_VALUE : longer[lMid];
//        System.out.println("Longer middle: " + longerLeft + ", " + longerRight);

        return tLen % 2 == 1
                ? Math.min(shorterRight, longerRight)
                : Math.max(shorterLeft, longerLeft) * 0.5 + Math.min(shorterRight, longerRight) * 0.5;
    }

    @Test
    public static void test() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
    }

    public static void invertNode(TreeNode node) {
        if (node.left != null || node.right != null) {
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            if (node.left != null) {
                invertNode(node.left);
            }
            if (node.right != null) {
                invertNode(node.right);
            }
        }
    }

    public int binarySearch(int[] nums, int target) {
        int beg = 0;
        int end = nums.length - 1;
        int pos = Integer.MIN_VALUE;
        while (beg <= end) {
            int mid = (beg + end) / 2;
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] < target) {
                beg = mid + 1;
            } else
                end = mid - 1;
        }
        return -1;
    }

}
