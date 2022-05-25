import java.util.Arrays;

public class BSTQns {
    /**
     * 99. Recover Binary Search Tree
     * Model it as an in-order traversal
     * Think of it as an in-order validation - an in-order BST array will be sorted ascending:
     * Valid in-order: [1,2,3,4,5]
     * Invalid: [1,5,3,4,2]
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

    //    669. Trim a Binary Search Tree
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) return null;
        if (root.val > high) return trimBST(root.left, low, high);
        if (root.val < low) return trimBST(root.right, low, high);

        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);

        return root;
    }

    //    538. Convert BST to Greater Tree
    public TreeNode convertBST(TreeNode root) {
        bst538(root, 0);
        return root;
    }

    public int bst538(TreeNode root, int val) {
        if (root == null) return val;
        int right = bst538(root.right, val);
        int left = bst538(root.left, root.val + right);
        root.val += right;
        return left;
    }

    // 1008. Construct Binary Search Tree from Preorder Traversal
    public TreeNode bstFromPreorder(int[] preorder) {
        int len = preorder.length;
        if (len == 0) return null;
        int rightStart = 0, rootVal = preorder[0];
        TreeNode root = new TreeNode(rootVal);
        if (len == 1) return root;

        for (int i = 0; i < len; i++) {
            if (preorder[i] > rootVal) {
                rightStart = i;
                break;
            }
        }
        if (rightStart == 0) {
            root.left = bstFromPreorder(Arrays.copyOfRange(preorder, 1, len));
        } else {
            root.left = bstFromPreorder(Arrays.copyOfRange(preorder, 1, rightStart));
            root.right = bstFromPreorder(Arrays.copyOfRange(preorder, rightStart, len));
        }
        return root;
    }
}
