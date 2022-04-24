package ClassQuestions;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class Codec {
    private static final String split = ",";
    private static final String NN = "X";

    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        buildString(root, sb);
        return sb.toString();
    }

    // Build string top down!
    private void buildString(TreeNode node, StringBuilder sb) {
        if (node == null) {
//            System.out.println(NN);
            sb.append(NN).append(split);
        } else {
//            System.out.println(node.val);
            sb.append(node.val).append(split);
            buildString(node.left, sb);
            buildString(node.right, sb);
        }
    }

    public TreeNode deserialize(String data) {
//        System.out.println();
        Deque<String> nodes = new LinkedList<>(Arrays.asList(data.split(split)));
        return buildTree(nodes);
    }

    // Build tree bottom up!
    private TreeNode buildTree(Deque<String> nodes) {
        String val = nodes.remove();
        if (val.equals(NN)) {
//            System.out.println(NN);
            return null;
        } else {
            TreeNode node = new TreeNode(Integer.parseInt(val));
            node.left = buildTree(nodes);
            node.right = buildTree(nodes);
//            System.out.println(val);
            return node;
        }
    }
}