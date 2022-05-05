package Algos;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.util.*;

/**
 * Dijkstra's: shortest path from source to all vertices
 * -> explore the shortest paths via minheap and update the count of visited nodes
 * https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
 * Space complexity:
 * - adjacency list O(V + E), O(V^2) if vertices are all connected
 * - adjacency matrix O(V^2)
 * Time complexity: O(V^2) or O(E log V) with a binary heap
 */
class Dijkstra {
    void dijkstra(int graph[][], int src) {
        int nodes = graph.length;
        int[] dist = new int[nodes];
        boolean[] visited = new boolean[nodes];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int count = 0; count < nodes - 1; count++) {
            int u = vertexWithMinDistance(dist, visited);
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
    int vertexWithMinDistance(int[] dist, boolean[] visited) {
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
     * 743. Network Delay Time
     *
     */
    public int networkDelayTime(int[][] times, int n, int k) {
        HashMap<Integer, List<int[]>> adj = new HashMap<>();
        for (int[] v : times) {
            int from = v[0], to = v[1], time = v[2];
            adj.computeIfAbsent(from, a -> new ArrayList<>()).add(new int[]{to, time});
        }

        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[1])); // [to, time]
        heap.add(new int[]{k, 0});

        int max = -1;
        boolean[] visited = new boolean[n + 1];
        while (n > 0) {
            if (heap.isEmpty()) return -1;
            int[] currTime = heap.poll();
            int curr = currTime[0], time = currTime[1];
            if (visited[curr]) continue;
            visited[curr] = true;
            n--;
            max = Math.max(max, time);
            if (!adj.containsKey(curr)) continue;
            for (int[] next : adj.get(curr)) {
                heap.add(new int[]{next[0], next[1] + time});
            }
        }
        return max;
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
        Arrays.fill(cost, -1);
        Queue<long[]> queue = new PriorityQueue<>(Comparator.comparingLong(v -> v[1]));
        countPaths[0] = 1;
        cost[0] = 0;
        queue.offer(new long[]{0, 0});

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
                    cost[nextNode[0]] = newCost;
                    queue.offer(new long[]{nextNode[0], newCost});
                }
            }
        }
        return -1;
    }
}