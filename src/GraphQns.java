import java.util.*;

class GraphQns {
    public static void main(String[] args) {
//        int[][] courses = new int[3][2];
//        courses[0] = new int[]{1, 2};
//        courses[1] = new int[]{1, 3};
//        courses[2] = new int[]{2, 3};
//        boolean res = canFinish(3, courses);
        TreeNode n5 = new TreeNode(5);
        TreeNode n4 = new TreeNode(4);
        TreeNode n6 = new TreeNode(6);
        TreeNode n3 = new TreeNode(3);
        TreeNode n7 = new TreeNode(7);
        n5.left = n4;
        n5.right = n6;
        n6.left = n3;
        n6.right = n7;
        Boolean res = isValidBST(n5);
        System.out.println(res);
    }

    // 5,4,6,n,n,3,7

    public static boolean isValidBST(TreeNode root) {
        if ((root.left != null && root.left.val >= root.val) || (root.right != null && root.right.val <= root.val)) {
            return false;
        }
        return (root.left == null || isValidBST(root.left)) && (root.right == null || isValidBST(root.right));
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

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, ArrayList<Integer>> prereqs = new HashMap<Integer, ArrayList<Integer>>();
        if (prerequisites.length > numCourses) {
            return false;
        }
        for (int i = 0; i < prerequisites.length; i++) {
            int course = prerequisites[i][0];
            int prereq = prerequisites[i][1];
            if (prereqs.get(course) == null) {
                prereqs.put(course, new ArrayList<Integer>(Arrays.asList(prereq)));
            } else {
                prereqs.get(course).add(prereq);
            }
        }
        Map<Integer, Boolean> isVisited = new HashMap<>();
        for (int i : prereqs.keySet()) {
            if (!canStudyDfs(i, prereqs, isVisited)) {
                return false;
            }
            ;
        }
        return true;
    }

    public static boolean canStudyDfs(int course, Map<Integer, ArrayList<Integer>> prereqs, Map<Integer, Boolean> isVisited) {
        if (isVisited.get(course) != null && isVisited.get(course)) {
            return false;
        }
        ;
        ArrayList<Integer> toVisit = prereqs.get(course);
        if (toVisit == null || toVisit.size() == 0) {
            return true;
        }
        ;
        isVisited.put(course, true);
        for (int c : toVisit) {
            if (!canStudyDfs(c, prereqs, isVisited)) {
                return false;
            }
            ;
        }
        isVisited.put(course, false); //
        prereqs.get(course).clear();
        return true;
    }

    public GraphNode cloneGraph(GraphNode node) {
        if (node == null) return null;
        GraphNode copy = new GraphNode(node.val);
        GraphNode[] visited = new GraphNode[101];
        dfs(node, copy, visited);
        return copy;
    }

    public void dfs(GraphNode node, GraphNode copy, GraphNode[] visited) {
        visited[copy.val] = copy;
        for (GraphNode n : node.neighbors) {
            if (visited[n.val] == null) {
                GraphNode newNode = new GraphNode(n.val);
                copy.neighbors.add(newNode);
                dfs(n, newNode, visited);
            } else {
                copy.neighbors.add(visited[n.val]);
            }
        }
    }
}
