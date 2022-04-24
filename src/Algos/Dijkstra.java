package Algos;

import java.util.*;

/**
 * Shortest path from source to all vertices
 * https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
 */
class Dijkstra {
    void dijkstra(int graph[][], int src) {
        int nodes = graph.length;
        int[] dist = new int[nodes];
        boolean[] visited = new boolean[nodes];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int count = 0; count < nodes - 1; count++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < nodes; v++)
                /*
                Update dist[v] only if is not in visited, there is an edge from u to v,
                And total weight of path from src to v through u is smaller than current value of dist[v]
                 */
                if (!visited[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE) {
                    int distToUThenV = dist[u] + graph[u][v];
                    if (distToUThenV < dist[v]) dist[v] = distToUThenV;
                }
        }
        printSolution(dist);
    }

    /*
    A utility function to find the vertex with minimum distance value,
    from the set of vertices not yet included in the shortest path tree
     */
    int minDistance(int[] dist, boolean[] visited) {
        int minCost = Integer.MAX_VALUE, minNode = -1;
        for (int v = 0; v < visited.length; v++) {
            if (!visited[v] && dist[v] <= minCost) {
                minCost = dist[v];
                minNode = v;
            }
        }
        return minNode;
    }

    void printSolution(int dist[]) {
        System.out.println("Vertex \t Distance from Source");
        for (int i = 0; i < dist.length; i++)
            System.out.println(i + " \t\t " + dist[i]);
    }

    // Driver method
    public static void main(String[] args) {
        int graph[][] = new int[][]{
                {0, 4, 0, 0, 0, 0, 0, 8, 0},
                {4, 0, 8, 0, 0, 0, 0, 11, 0},
                {0, 8, 0, 7, 0, 4, 0, 0, 2},
                {0, 0, 7, 0, 9, 14, 0, 0, 0},
                {0, 0, 0, 9, 0, 10, 0, 0, 0},
                {0, 0, 4, 14, 10, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 1, 6},
                {8, 11, 0, 0, 0, 0, 1, 0, 7},
                {0, 0, 2, 0, 0, 0, 6, 7, 0},
        };
        Dijkstra t = new Dijkstra();
        t.dijkstra(graph, 0);
    }

    /**
     * 1976. Number of Ways to Arrive at Destination
     * Modified Dijkstra to track count of minimum cost paths
     * https://leetcode.com/problems/number-of-ways-to-arrive-at-destination/discuss/1424422
     */
    public int countPaths(int n, int[][] roads) {
        final long MODULO = 1000000007;
        List<int[]>[] adj = new List[n];
        Arrays.setAll(adj, x -> new ArrayList<>());
        for (int[] r : roads) { // populate adjacency lists
            adj[r[0]].add(new int[]{r[1], r[2]});
            adj[r[1]].add(new int[]{r[0], r[2]});
        }
        long[] cost = new long[n], countPaths = new long[n];
        Arrays.fill(cost, -1); // -1 -> not visited
        // Queue of [node, cost], explore the cheapest paths first
        Queue<long[]> queue = new PriorityQueue<>((l, r) -> Long.compare(l[1], r[1]));
        queue.offer(new long[]{0, 0});
        countPaths[0] = 1; // Intuitive? To make path accumulation work
        cost[0] = 0;

        while (!queue.isEmpty()) {
            long[] curr = queue.poll();
            int currNode = (int) curr[0];
            if (currNode == n - 1) return (int) (countPaths[currNode] % MODULO);
            for (int[] nextNode : adj[currNode]) {
                long newCost = curr[1] + nextNode[1];
                // Paths intersecting, e.g. 1,2,3 & 1,4,3 - add all countPaths from current node
                if (cost[nextNode[0]] == newCost) {
                    countPaths[nextNode[0]] += countPaths[currNode] % MODULO;
                } else if (cost[nextNode[0]] == -1 || cost[nextNode[0]] > newCost) {
                    // newNode not been reached before || curr path to get there cheaper than previously found paths
                    countPaths[nextNode[0]] = countPaths[currNode];
                    queue.offer(new long[]{nextNode[0], newCost});
                    cost[nextNode[0]] = newCost;
                }
            }
        }
        return -1;
    }
}