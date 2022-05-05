import java.util.*;

public class TopSort implements HasDirs {
    public static void main(String[] args) {
        longestIncreasingPath(new int[][]{{9, 9, 4}, {6, 6, 8}, {2, 1, 1}});
    }

    //    802. Find Eventual Safe States - top sort performs much worse than dfs
    public static List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        int[] degree = new int[n];
        boolean isSafe[] = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        HashSet<Integer>[] neighbours = new HashSet[n];
        for (int i = 0; i < n; i++) neighbours[i] = new HashSet<>();

        for (int i = 0; i < n; i++) {
            if (graph[i].length == 0) {
                isSafe[i] = true;
                q.offer(i);
            } else {
                for (int v : graph[i]) neighbours[v].add(i);
                degree[i] = graph[i].length;
            }
        }

        while (!q.isEmpty()) {
            int curr = q.poll();
            isSafe[curr] = true;
            for (int v : neighbours[curr]) {
                degree[v]--;
                if (degree[v] == 0) q.offer(v);
            }
        }

        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) if (isSafe[i]) res.add(i);
        return res;
    }

    public int longestIncreasingPath2(int[][] matrix) {
        int[] DIRS = new int[]{0, 1, 0, -1, 0};
        int rows = matrix.length, cols = matrix[0].length;
        int[][] inDegree = new int[rows][cols];
        LinkedList<int[]> q = new LinkedList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int curr = matrix[i][j];
                for (int k = 0; k < 4; k++) {
                    int ni = i + DIRS[k], nj = j + DIRS[k + 1];
                    if (ni < 0 || nj < 0 || ni >= rows || nj >= cols) continue;
                    if (matrix[ni][nj] < curr) inDegree[i][j]++;
                }
                if (inDegree[i][j] == 0) q.add(new int[]{i, j});
            }
        }

        int count = 0;
//        for (int[] r : inDegree) System.out.println(Arrays.toString(r));
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int[] curr = q.poll();
                int i = curr[0], j = curr[1], val = matrix[i][j];
                for (int k = 0; k < 4; k++) {
                    int ni = i + DIRS[k], nj = j + DIRS[k + 1];
                    if (ni < 0 || nj < 0 || ni >= rows || nj >= cols) continue;
                    if (matrix[ni][nj] > val && --inDegree[ni][nj] == 0) {
                        q.add(new int[]{ni, nj});
                    }
                }
            }
            count++;
        }
        return count;
    }

    /**
     * 207. Course Schedule via Top sort
     * [1,0]: to take course 1 you need to finish course 0
     * 0 is the parent, 1 is the child
     * Top sort: 0 -> 1
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] inDegree = new int[numCourses];
        Queue<Integer> q = new LinkedList<>();
        Map<Integer, Set<Integer>> parentChild = new HashMap<>();

        for (int i = 0; i < prerequisites.length; i++) {
            int parent = prerequisites[i][1];
            int child = prerequisites[i][0];
            inDegree[child]++;

            parentChild.computeIfAbsent(parent, k -> new HashSet<>()).add(child);
        }

        int studied = 0;
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                q.offer(i);
                studied++;
            }
        }

        while (!q.isEmpty()) {
            int parent = q.poll();
            Set<Integer> children = parentChild.get(parent);
            if (children == null) continue;
            for (int child : children) {
                if (--inDegree[child] == 0) {
                    q.offer(child);
                    studied++;
                }
            }
        }
        return studied == numCourses;
    }

    /**
     * 329. Longest Increasing Path in a Matrix
     * Done via bfs too
     */
    public static int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;

        int[][] inDegree = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int[] dir : fourDirs) {
                    int nx = i + dir[0];
                    int ny = j + dir[1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                        if (matrix[nx][ny] > matrix[i][j]) inDegree[nx][ny]++;
                    }
                }
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (inDegree[i][j] == 0) queue.add(new int[]{i, j});
            }
        }

        int length = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];

                for (int[] dir : fourDirs) {
                    int nx = x + dir[0], ny = y + dir[1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                        /**
                         * Note that inDegree[x][y] is zero
                         * Only progress to the next greater node if its value is 0
                         */
                        if (matrix[x][y] < matrix[nx][ny]) {
                            if (--inDegree[nx][ny] == 0) queue.offer(new int[]{nx, ny});
                        }
                    }
                }
            }
            length++;
        }

        return length;
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

    //    1462. Course Schedule IV
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        Map<Integer, Set<Integer>> parentChildren = new HashMap<>(), childParents = new HashMap<>();
        int[] inDegree = new int[numCourses];
        for (int[] childParent : prerequisites) {
            inDegree[childParent[0]]++;
            parentChildren.computeIfAbsent(childParent[1], k -> new HashSet<>()).add(childParent[0]);
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < inDegree.length; i++) if (inDegree[i] == 0) q.add(i);

        while (!q.isEmpty()) {
            int parent = q.remove();
            if (parentChildren.containsKey(parent)) {
                for (int child : parentChildren.get(parent)) {
                    childParents.computeIfAbsent(child, k -> new HashSet<>()).add(parent);
                    childParents.get(child).addAll(childParents.computeIfAbsent(parent, k -> new HashSet<>()));
                    if (--inDegree[child] == 0) q.add(child);
                }
            }
        }

        List<Boolean> res = new ArrayList<>();
        for (int i = 0; i < queries.length; i++) {
            int parent = queries[i][1], child = queries[i][0];
            Set<Integer> parents = childParents.get(child);
            res.add(parents != null && parents.contains(parent));
        }
        return res;
    }

    //    210. Course Schedule II
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, Set<Integer>> parentChildren = new HashMap<>();
        int[] inDegree = new int[numCourses];
        for (int[] childParent : prerequisites) {
            inDegree[childParent[0]]++;
            parentChildren.computeIfAbsent(childParent[1], k -> new HashSet<>()).add(childParent[0]);
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) q.add(i);
        }

        int[] res = new int[numCourses];
        int curr = 0;
        while (!q.isEmpty()) {
            int parent = q.remove();
            res[curr++] = parent;
            if (parentChildren.containsKey(parent)) {
                for (int child : parentChildren.get(parent)) {
                    if (--inDegree[child] == 0) q.add(child);
                }
            }
        }

        return curr == numCourses ? res : new int[0];
    }

}
