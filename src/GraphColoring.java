import java.util.*;

public class GraphColoring {
    /**
     * 785. Is Graph Bipartite?
     * Nodes of the graph can be colored via DFS and BFS
     * Both time O(V+E), space O(V+E)
     */
    public boolean isBipartiteDFS(int[][] graph) {
        int n = graph.length;
        int[] colors = new int[n];
        for (int i = 0; i < n; i++) {
            if (colors[i] == 0 && !dfs785(graph, colors, i, 1)) return false;
        }
        return true;
    }

    private boolean dfs785(int[][] graph, int[] colors, int curr, int color) {
        colors[curr] = color;
        for (int next : graph[curr]) {
            if (colors[next] == -color) continue;
            if (colors[next] == color || !dfs785(graph, colors, next, -color)) return false;
        }
        return true;
    }

    public boolean isBipartiteBFS(int[][] graph) {
        int n = graph.length;
        int[] colors = new int[n];

        for (int i = 0; i < n; i++) {
            if (colors[i] != 0) continue;
            Queue<Integer> q = new LinkedList<>();
            q.add(i);
            colors[i] = 1;

            while (!q.isEmpty()) {
                int cur = q.poll(), toColorNext = -colors[cur];
                for (int x : graph[cur]) {
                    if (colors[x] == 0) {
                        colors[x] = toColorNext;
                        q.offer(x);
                    } else if (colors[x] != toColorNext) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
