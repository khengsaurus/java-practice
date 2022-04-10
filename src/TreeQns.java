import java.util.*;

public class TreeQns {

    //    124
    int globalMax;

    public int maxPathSum(TreeNode root) {
        globalMax = Integer.MIN_VALUE;
        maxPath(root);
        return globalMax;
    }

    public int maxPath(TreeNode root) {
        if (root == null) return 0;
        int maxLeft = Math.max(0, maxPath(root.left));
        int maxRight = Math.max(0, maxRight = maxPath(root.right));
        globalMax = Math.max(globalMax, root.val + maxLeft + maxRight);
        return root.val + Math.max(maxLeft, maxRight);
    }

    //    143
    public static void reorderList(ListNode head) {
        Map<Integer, ListNode> map = new HashMap<>();
        int count = -1;
        while (head != null) {
            map.put(++count, head);
            head = head.next;
        }
        head = null;
        for (int i = 0; i <= count / 2; i++) {
            if (head == null) {
                head = map.get(i);
            } else {
                head.next = map.get(i);
                head = head.next;
            }
            head.next = map.get(count - i);
            head = head.next;
        }
        head.next = null;
    }

    //    133
    public Node cloneGraph(Node node) {
        return dfs133(node, new HashMap<>());
    }

    public Node dfs133(Node node, HashMap<Integer, Node> map) {
        if (node == null) return null;
        if (map.containsKey(node.val)) return map.get(node.val);
        Node newNode = new Node(node.val);
        map.put(node.val, newNode);
        List<Node> neighbors = new ArrayList<>();
        for (Node n : node.neighbors) {
            neighbors.add(dfs133(n, map));
        }
        newNode.neighbors = neighbors;
        return newNode;
    }

    /**
     * Brilliant outside-of-the-box solution
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while ((root.val - p.val) * (root.val - q.val) > 0)
            root = p.val < root.val ? root.left : root.right;
        return root;
    }

    public boolean containsChild(TreeNode root, int val) {
        if (root == null) return false;
        if (root.val == val) return true;
        return (root.val > val)
                ? containsChild(root.left, val)
                : containsChild(root.right, val);
    }

    //    653
    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();
        return bs653(root, k, set);
    }

    public boolean bs653(TreeNode root, int k, Set<Integer> set) {
        if (root == null) return false;
        if (set.contains(k - root.val)) return true;
        set.add(root.val);
        return bs653(root.left, k, set) || bs653(root.right, k, set);
    }

    //    98
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode root, long minVal, long maxVal) {
        if (root == null) return true;
        if (root.val >= maxVal || root.val <= minVal) return false;
        return isValidBST(root.left, minVal, root.val) && isValidBST(root.right, root.val, maxVal);
    }

    // 802 - Find Eventual Safe States
    // 86% & 60% but I just bashed this out without thinking, don't try to understand it @future me
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int[] safe = new int[graph.length];
        List<Integer> s = new ArrayList<>();
        for (int n = 0; n < graph.length; n++) {
            if (isSafeNode(graph, safe, n)) s.add(n);
        }
        return s;
    }

    public boolean isSafeNode(int[][] graph, int[] safe, int node) {
        if (graph[node].length == 0) return true;
        if (safe[node] != 0) return safe[node] == 1;
        safe[node] = 4; // visited
        boolean isSafe = true;
        for (int next : graph[node]) {
            if (safe[next] == 4) {
                isSafe = false;
                break;
            }
            isSafe = isSafe && isSafeNode(graph, safe, next);
            if (!isSafe) break;
        }
        safe[node] = isSafe ? 1 : 2;
        return isSafe;
    }

    public static List<Integer> traversal(TreeNode root) {
        List<Integer> rv = new ArrayList<>();
        if (root != null) dfs(root, rv);
        return rv;
    }

    private static void dfs(TreeNode node, List<Integer> rv) {
//        rv.add(node.val); // <-- preOrder
        if (node.left != null) {
            dfs(node.left, rv);
        }
        rv.add(node.val); // <-- inOrder
        if (node.right != null) {
            dfs(node.right, rv);
        }
//        rv.add(node.val); // <-- postOrder
    }
}
