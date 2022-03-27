import org.junit.Test;

public class BinarySearch {

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
