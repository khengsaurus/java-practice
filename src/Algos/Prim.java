package Algos;

import java.util.*;

/**
 * Minimum spanning tree
 * 1584. Min Cost to Connect All Points
 * Prim's: https://www.youtube.com/watch?v=cplfcGZmX7I
 *
 * O:
 * Adjacency matrix -> O(V^2)
 * Binary heap, adjacency list -> O(VlgV + ElgV)
 */
public class Prim {
    public static void main(String[] args) {
//        int res = minCostConnectPoints(new int[][]{{0, 0}, {2, 2}, {3, 10}, {5, 2}, {7, 0}});
//        System.out.println(res);
    }

    public static int minCostConnectPointsEg(int[][] points) {
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0])); // [cost, point]
        int numConnected = 1, n = points.length, idx = 0, res = 0;
        boolean[] visited = new boolean[n];
        while (numConnected < n) {
            visited[idx] = true;
            for (int i = 0; i < n; i++) {
                if (visited[i]) continue;
                minHeap.offer(new int[]{getManhatDist(points[idx], points[i]), i});
            }
            while (!minHeap.isEmpty() && visited[minHeap.peek()[1]]) minHeap.poll();
            int[] cur = minHeap.poll();
            if (cur != null) {
                idx = cur[1];
                res += cur[0];
                numConnected++;
            }
        }
        return res;
    }

    public int minCostConnectPoints(int[][] points) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]); // [cost, point]
        int connected = 0, n = points.length, res = 0;
        boolean[] visited = new boolean[n];
        heap.add(new int[]{0, 0});
        while (connected < n && !heap.isEmpty()) {
            int[] costPoint = heap.poll();
            if (visited[costPoint[1]]) continue;
            visited[costPoint[1]] = true;
            connected++;
            res += costPoint[0];
            for (int next = 0; next < n; next++) {
                if (visited[next]) continue; // visited on a shorter path before, skip
                heap.offer(new int[]{getManhatDist(points[costPoint[1]], points[next]), next});
            }
        }
        return res;
    }

    public static int getManhatDist(int[] x, int[] y) {
        return Math.abs(x[0] - y[0]) + Math.abs(x[1] - y[1]);
    }

    public static int minCostConnectPointsAdjList(int[][] points) {
        int V = points.length;
        if (V == 1) return 0;
        Map<Integer, List<int[]>> adjList = new HashMap<>();
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                int dist = getManhatDist(points[i], points[j]);
                adjList.computeIfAbsent(i, k -> new ArrayList<>());
                adjList.computeIfAbsent(j, k -> new ArrayList<>());
                adjList.get(i).add(new int[]{dist, j});
                adjList.get(j).add(new int[]{dist, i});
            }
        }

        int totalCost = 0;
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        heap.add(new int[]{0, 0});
        while (visited.size() < V) {
            int[] costPoint = heap.remove();
            int point = costPoint[1], cost = costPoint[0];
            if (visited.contains(point)) continue;
            totalCost += cost;
            visited.add(point);
            for (int[] neighbor : adjList.get(point)) {
                if (!visited.contains(neighbor[1])) heap.add(neighbor);
            }
        }

        return totalCost;
    }
}
