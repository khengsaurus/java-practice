package Algos;

import java.util.Arrays;

/**
 * Shortest path from source to all vertices
 * https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
 */
class Dijkstra {
    int minDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < visited.length; v++)
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    void printSolution(int dist[]) {
        System.out.println("Vertex \t Distance from Source");
        for (int i = 0; i < dist.length; i++)
            System.out.println(i + " \t\t " + dist[i]);
    }

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
                // Update dist[v] only if is not in visited, there is an edge from u to v,
                if (!visited[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE) {
                    // And total weight of path from src to v through u is smaller than current value of dist[v]
                    int distToUThenV = dist[u] + graph[u][v];
                    if (distToUThenV < dist[v]) dist[v] = distToUThenV;
                }
        }
        printSolution(dist);
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
}