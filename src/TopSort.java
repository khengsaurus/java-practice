import java.util.*;

public class TopSort {
    public static void main(String[] args) {
        List<Integer> roots = findMinHeightTrees(7, new int[][]{{3, 0}, {3, 1}, {3, 2}, {3, 4}, {4, 5}, {5, 6}});
        System.out.println(roots);
    }

    /**
     * 310. Minimum Height Trees
     * Idea is to cut off all leaves (degree == 1 since this is a bi-direction edge)
     * The roots will eventually have degree <= 1 too BUT catch them with the while(n > 2)
     * This works because there will be 1 root for odd-numbered vertices, 2 roots for even-numbered
     */
    public static List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) return List.of(0);

        List<Set<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) adj.add(new HashSet<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        List<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; ++i)
            if (adj.get(i).size() == 1) leaves.add(i);

        while (n > 2) {
            n -= leaves.size();
            List<Integer> newLeaves = new ArrayList<>();
            for (int leaf : leaves) {
                int neighbor = adj.get(leaf).iterator().next();
                Set<Integer> neighborsNeighbors = adj.get(neighbor);
                neighborsNeighbors.remove(leaf);
                if (neighborsNeighbors.size() == 1) newLeaves.add(neighbor);
            }
            leaves = newLeaves;
        }
        return leaves;
    }

    public int[] findOrder2(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> adjList = new HashMap<Integer, List<Integer>>();
        int[] degree = new int[numCourses];
        int[] topologicalOrder = new int[numCourses];

        for (int[] prerequisite : prerequisites) {
            int parent = prerequisite[1], child = prerequisite[0];
            List<Integer> children = adjList.getOrDefault(parent, new ArrayList<Integer>());
            children.add(child);
            adjList.put(parent, children);
            degree[child] += 1;
        }

        Queue<Integer> q = new LinkedList<Integer>();
        for (int i = 0; i < numCourses; i++) {
            if (degree[i] == 0) {
                q.add(i);
            }
        }

        int i = 0;
        while (!q.isEmpty()) {
            int parent = q.remove();
            topologicalOrder[i++] = parent;
            if (adjList.containsKey(parent)) {
                for (Integer child : adjList.get(parent)) {
                    if (--degree[child] == 0) q.add(child);
                }
            }
        }
        return i == numCourses ? topologicalOrder : new int[0];
    }

    //    210. Course Schedule II
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if (numCourses <= 0) return new int[0];

        //1. Init Map
        HashMap<Integer, Integer> degree = new HashMap<>();
        HashMap<Integer, List<Integer>> topoMap = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            degree.put(i, 0);
            topoMap.put(i, new ArrayList<Integer>());
        }

        //2. Build Map
        for (int[] pair : prerequisites) {
            int child = pair[0], parent = pair[1]; // [0, 1] - [child <- parent]
            topoMap.get(parent).add(child);  // put the child into it's parent's list
            degree.put(child, degree.get(child) + 1); // increase child's degree by 1
        }

        //3. find course with 0 degree, minus one to its children's degrees, until all degree = 0
        int[] res = new int[numCourses];
        int index = 0;
        while (!degree.isEmpty()) {
            boolean childWithDegree0 = false;
            for (int c : degree.keySet().stream().toList()) {
                if (degree.get(c) == 0) {
                    childWithDegree0 = true;
                    res[index++] = c;
                    List<Integer> parents = topoMap.get(c);  // get the node's parents, and decrease their degrees
                    for (int parent : parents) degree.put(parent, degree.get(parent) - 1);
                    degree.remove(c);      // remove the current node with 0 degree and start over
                    break;
                }
            }
            if (!childWithDegree0) return new int[0];
        }
        return res;
    }

}
