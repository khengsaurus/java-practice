import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertTrue;

public class BSTTraversal {
    public static boolean isValidPostOrderTraversal(int[] nodes) {
//        [...Es < root, ...Es > root, root]
        if (nodes.length <= 1) return true;
        return isValidPostOrderHelper(nodes, 0, nodes.length - 1);
    }

    public static boolean isValidPostOrderHelper(int[] nodes, int indexStart, int indexEnd) {
        if (indexStart >= indexEnd) return true;
        int root = nodes[indexEnd];
        int index = indexEnd - 1;
        while (index >= indexStart && nodes[index] > root) {
            index--;
        }
        int newRoot = index;
        while (index >= indexStart) {
//            Screen to ensure left tree does not contain node > root
            if (nodes[index] > root) return false;
            index--;
        }
        if (newRoot == indexStart) {
            return isValidPostOrderHelper(nodes, indexStart, indexEnd - 1);
//            Screen left tree like 1, 2, 4, 3 (5... <- the right already checked) if there is no right tree
        } else {
//            Screen left tree and right tree
            return isValidPostOrderHelper(nodes, indexStart, newRoot) &&
                    isValidPostOrderHelper(nodes, newRoot + 1, indexEnd - 1);
        }
    }

    public static boolean isValidPreOrderTraversal(int[] nodes) {
        if (nodes.length <= 1) return true;
        Stack<Integer> parents = new Stack<Integer>();
        int leftLimit = -Integer.MAX_VALUE; // left grand^n parent
        for (int newNode : nodes) {
            if (newNode < leftLimit) return false;
            while (!parents.isEmpty() && newNode > parents.peek()) {
                leftLimit = parents.pop(); // newNode will be right child of this leftLimit
            }
            parents.push(newNode);
        }
        return true;
    }
}
