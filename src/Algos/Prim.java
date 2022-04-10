package Algos;

import java.util.*;

/**
 * Minimum spanning tree
 * 1584. Min Cost to Connect All Points
 * https://www.youtube.com/watch?v=f7JOBJIC-NA
 */
public class Prim {
    public static void main(String[] args) {
//        int res = minCostConnectPoints(new int[][]{{0, 0}, {2, 2}, {3, 10}, {5, 2}, {7, 0}});
//        System.out.println(res);
    }

    public static int minCostConnectPoints2(int[][] points) {
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
            idx = cur[1];
            res += cur[0];
            numConnected++;
        }
        return res;
    }

    public static int getManhatDist(int[] x, int[] y) {
        return Math.abs(x[0] - y[0]) + Math.abs(x[1] - y[1]);
    }

    public static int minCostConnectPoints(int[][] points) {
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
        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0])); // [cost, point]
        heap.add(new int[]{0, 0}); // init by adding first point, with cost 0
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
