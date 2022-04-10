package Algos;

import java.util.*;

/**
 * Top sort
 * https://www.geeksforgeeks.org/topological-sorting-indegree-based-solution/
 */
class Kahn {
    public static void main(String[] args) {
        Kahn k = new Kahn(6, new int[][]{{5, 2}, {5, 0}, {4, 0}, {4, 1}, {2, 3}, {3, 1}});
        System.out.println(k.topologicalSort());
    }

    int V;
    List<Integer>[] adjList;

    public Kahn(int V, int[][] E) {
        this.V = V;
        adjList = new ArrayList[V];
        for (int i = 0; i < V; i++) adjList[i] = new ArrayList<Integer>();
        for (int[] edge : E) {
            addEdge(edge[0], edge[1]);
        }
    }

    public void addEdge(int u, int v) {
        adjList[u].add(v);
    }

    // Return a Topological Sort of the complete graph
    public List<Integer> topologicalSort() {
        int[] inDegree = new int[V];

        // Traverse adjacency lists to fill inDegrees of vertices. O(V+E)
        for (int i = 0; i < V; i++) {
            ArrayList<Integer> neighbors = (ArrayList<Integer>) adjList[i];
            for (int node : neighbors) inDegree[node]++;
        }

        // Create a queue and enqueue all vertices with inDegree 0 - i.e. leaves
        Queue<Integer> q = new LinkedList<Integer>();
        for (int i = 0; i < V; i++) if (inDegree[i] == 0) q.add(i);

        int visitedCount = 0;

        // Create a vector to store result - a topological ordering of all vertices
        List<Integer> topOrder = new ArrayList<>();
        while (!q.isEmpty()) {
            // Extract front of queue (or perform dequeue) and add it to topological order
            int u = q.poll();
            topOrder.add(u);

            // Iterate through all its neighbouring nodes of dequeued node u and decrease their in-degree by 1
            // If is now a leaf, add to topOrder
            for (int node : adjList[u]) if (--inDegree[node] == 0) q.add(node);
            visitedCount++;
        }

        if (visitedCount != V) {
            System.out.println("There exists a cycle in the graph");
            return null;
        }

        // Print topological order
        return topOrder;
    }
}