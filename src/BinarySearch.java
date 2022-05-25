import org.junit.Test;

public class BinarySearch {
    /**
     * 4. Median of Two Sorted Arrays
     * TODO: Binary search across 2 arrays
     */
    public double findMedianSortedArrays(int[] shorter, int[] longer) {
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

        int sLen = shorter.length, lLen = longer.length, halfTLen = (sLen + lLen) / 2;
        int sLeft = 0, sRight = sLen;

        while (sLeft < sRight) {
            int sMid = (sLeft + sRight) / 2, lMid = halfTLen - sMid;
            // If element after sMid < lMid, shift sMid right -> lMid left
            if (shorter[sMid] < longer[lMid - 1]) sLeft = sMid + 1;
            else sRight = sMid;
        }

        int sMid = sLeft,
                shorterLeft = sMid == 0 ? Integer.MIN_VALUE : shorter[sMid - 1],
                shorterRight = sMid == sLen ? Integer.MAX_VALUE : shorter[sMid];
        int lMid = halfTLen - sLeft,
                longerLeft = lMid == 0 ? Integer.MIN_VALUE : longer[lMid - 1],
                longerRight = lMid == lLen ? Integer.MAX_VALUE : longer[lMid];
        return (sLen + lLen) % 2 == 1
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
