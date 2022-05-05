import java.util.*;

class GraphQns implements HasDirs {
    /**
     * 399. Evaluate Division
     * Equation & value: a,b & 2 -> a/b = 2, b,c & 3 -> b/c = 3
     * I.e. edge a -> b weight(2), b -> a weight(0.5)
     * Query a/c ->a/b * b/c -> 2 * 3
     */
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = buildGraph(equations, values);
        double[] result = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            List<String> q = queries.get(i);
            result[i] = getPathWeight(q.get(0), q.get(1), new HashSet<>(), graph);
        }
        return result;
    }

    private Map<String, Map<String, Double>> buildGraph(List<List<String>> equations, double[] values) {
        Map<String, Map<String, Double>> graph = new HashMap<>();

        for (int i = 0; i < equations.size(); i++) {
            List<String> eqn = equations.get(i);
            String u = eqn.get(0), v = eqn.get(1);
            graph.computeIfAbsent(u, k -> new HashMap<>()).put(v, values[i]);
            graph.get(u).put(u, 1.0);
            graph.computeIfAbsent(v, k -> new HashMap<>()).put(u, 1 / values[i]);
            graph.get(v).put(v, 1.0);
        }
        return graph;
    }

    private double getPathWeight(String start, String end, Set<String> visited, Map<String, Map<String, Double>> graph) {
        if (!graph.containsKey(start)) return -1.0;
        if (start.equals(end)) return 1;
        if (graph.get(start).containsKey(end)) return graph.get(start).get(end);

        visited.add(start);
        Map<String, Double> neighbors = graph.get(start);
        for (Map.Entry<String, Double> neighbor : neighbors.entrySet()) {
            String n = neighbor.getKey();
            if (!visited.contains(n)) {
                double weight = getPathWeight(n, end, visited, graph);
                if (weight != -1.0) return neighbor.getValue() * weight;
            }
        }
        return -1.0;
    }

    /**
     * 1631. Path With Minimum Effort
     * Use dijkstra's with an int[][] dist to memoize the greatest dist required to get to any cell.
     * If a cell can be reached with dist < dist[r][c] update dist[r][c] and offer from that cell.
     */
    public int minimumEffortPath(int[][] heights) {
        int rows = heights.length, cols = heights[0].length;
        int[][] distances = new int[rows][cols];
        for (int i = 0; i < rows; i++) Arrays.fill(distances[i], Integer.MAX_VALUE);

        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        minHeap.offer(new int[]{0, 0, 0}); // distance, row, col
        while (!minHeap.isEmpty()) {
            int[] curr = minHeap.poll();
            int dist = curr[0], row = curr[1], col = curr[2];
            if (row == rows - 1 && col == cols - 1) break;
            if (dist > distances[row][col]) continue;
            for (int i = 0; i < 4; i++) {
                int nextRow = row + dirs[i], nextCol = col + dirs[i + 1];
                if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols) continue;
                int newDist = Math.max(dist, Math.abs(heights[nextRow][nextCol] - heights[row][col]));
                if (distances[nextRow][nextCol] > newDist) {
                    distances[nextRow][nextCol] = newDist;
                    minHeap.offer(new int[]{newDist, nextRow, nextCol});
                }
            }
        }
        return distances[rows - 1][cols - 1];
    }

    /**
     * 329. Longest Increasing Path in a Matrix
     * Can be done via top sort/bfs too
     */
    public static int longestIncreasingPath(int[][] matrix) {
        Dfs329 dfs = new Dfs329(matrix);
        return dfs.getCount();
    }

    static class Dfs329 {
        private int rows, cols, count;

        public Dfs329(int[][] matrix) {
            this.rows = matrix.length;
            this.cols = matrix[0].length;
            if (this.rows == 0) {
                this.count = 0;
            } else {
                int[][] cache = new int[rows][cols];
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        this.count = Math.max(this.count, dfs329(matrix, row, col, cache));
                    }
                }
            }
        }

        private int dfs329(int[][] matrix, int row, int col, int[][] cache) {
            if (cache[row][col] != 0) return cache[row][col];
            for (int[] dir : fourDirs) {
                int newX = row + dir[0], newY = col + dir[1];
                if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) continue;
                if (matrix[newX][newY] > matrix[row][col]) {
                    cache[row][col] = Math.max(cache[row][col], dfs329(matrix, newX, newY, cache));
                }
            }
            return ++cache[row][col];
        }

        public int getCount() {
            return this.count;
        }
    }

    /**
     * 289. Game of Life
     * Any live cell with fewer than two live neighbors dies as if caused by under-population.
     * Any live cell with two or three live neighbors lives on to the next generation.
     * Any live cell with more than three live neighbors dies, as if by over-population.
     * Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
     */
    public void gameOfLife(int[][] board) {
        int rows = board.length, cols = board[0].length;
        if (rows == 1 && cols == 1) {
            board[0][0] = 0;
            return;
        }
        int[][] memo = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int n = countLiveNeighbors(board, r, c);
                if (board[r][c] == 0) {
                    if (n == 3) memo[r][c] = 1;
                } else {
                    if (n < 2 || n > 3) {
                        memo[r][c] = -1;
                    }
                }
            }
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] += memo[r][c];
            }
        }
    }

    public int countLiveNeighbors(int[][] board, int row, int col) {
        int count = 0;
        for (int[] dir : eightDirs) {
            int newX = row + dir[0], newY = col + dir[1];
            if (newX < 0 || newX >= board.length || newY < 0 || newY >= board[0].length) continue;
            count += board[newX][newY];
        }
        return count;
    }

    //    207. Course Schedule
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> courseReqs = new HashMap<>();
        for (int[] courseReq : prerequisites) {
            List<Integer> reqs = courseReqs.getOrDefault(courseReq[0], new ArrayList<>());
            reqs.add(courseReq[1]);
            courseReqs.put(courseReq[0], reqs);
        }
        if (courseReqs.size() > numCourses) return false;
        for (int course : courseReqs.keySet().stream().toList()) {
            if (!dfs207(course, courseReqs, new HashSet<Integer>())) return false;
            courseReqs.remove(course, null);
        }
        return true;
    }

    public boolean dfs207(int course, Map<Integer, List<Integer>> courseReqs, Set<Integer> visited) {
        if (visited.contains(course)) return false;
        List<Integer> preReqs = courseReqs.get(course);
        if (preReqs == null || preReqs.isEmpty()) return true;
        visited.add(course);
        for (int prereq : preReqs) {
            if (!dfs207(prereq, courseReqs, visited)) return false;
            courseReqs.put(prereq, null);
        }
        visited.remove(course);
        return true;
    }

    //    79
    public boolean exist(char[][] board, String word) {
        int rows = board.length, cols = board[0].length;
        char startingChar = word.charAt(0);
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == startingChar) {
                    boolean[][] visited = new boolean[rows][cols];
                    if (dfs79(board, visited, row, col, word, 0)) return true;
                }
            }
        }
        return false;
    }

    public boolean dfs79(char[][] board, boolean[][] visited, int row, int col, String word, int curr) {
        if (curr == word.length() - 1) return true;
        visited[row][col] = true;
        char nextChar = word.charAt(curr + 1);
        for (int[] dir : fourDirs) {
            int nextRow = row + dir[0], nextCol = col + dir[1];
            if (nextRow < 0 || nextCol < 0 || nextRow >= board.length || nextCol >= board[0].length || visited[nextRow][nextCol])
                continue;
            if (board[nextRow][nextCol] == nextChar && dfs79(board, visited, nextRow, nextCol, word, curr + 1)) {
                return true;
            }
        }
        visited[row][col] = false;
        return false;
    }

    // 1466
    public int minReorder(int n, int[][] connections) {
        Map<Integer, List<Integer>> roadsTo = new HashMap<>();
        Map<Integer, List<Integer>> roadsFrom = new HashMap<>();
        for (int[] road : connections) {
            int roadFrom = road[0], roadTo = road[1];
            roadsFrom.computeIfAbsent(roadFrom, k -> new ArrayList<>());
            roadsFrom.get(roadFrom).add(roadTo);
            roadsTo.computeIfAbsent(roadTo, k -> new ArrayList<>());
            roadsTo.get(roadTo).add(roadFrom);
        }
        boolean[] parentZero = new boolean[n];
        parentZero[0] = true;
        return explore(roadsTo, roadsFrom, parentZero, 0, 0, 0);
    }

    public int explore(
            Map<Integer, List<Integer>> roadsTo,
            Map<Integer, List<Integer>> roadsFrom,
            boolean[] parentZero,
            int city, int from, int count) {
        boolean add = false;
        for (int c : roadsTo.get(city)) {
            if (c != from || !parentZero[c]) add = true;

        }
        return count;
    }

    //    1129
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        List<Integer>[] reds = new ArrayList[n], blues = new ArrayList[n];
        for (int[] e : redEdges) {
            if (reds[e[0]] == null) reds[e[0]] = new ArrayList<>();
            reds[e[0]].add(e[1]);
        }
        for (int[] e : blueEdges) {
            if (blues[e[0]] == null) blues[e[0]] = new ArrayList<>();
            blues[e[0]].add(e[1]);
        }

        Queue<int[]> q = new LinkedList<>();
        int[] answer = new int[n];
        Arrays.fill(answer, -1);
        q.add(new int[]{0, 0});
        int moves = 0;
        Set<String> seen = new HashSet<>();

        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int[] curr = q.remove();
                String key = curr[0] + " " + curr[1];
                if (seen.contains(key)) continue;
                seen.add(key);
                if (answer[curr[0]] == -1) answer[curr[0]] = moves;

                if (curr[1] != 1) { // marked red visited
                    if (reds[curr[0]] != null) {
                        for (int child : reds[curr[0]]) q.add(new int[]{child, 1});
                    }
                }
                if (curr[1] != 2) { // marked blue visited
                    if (blues[curr[0]] != null) {
                        for (int child : blues[curr[0]]) q.add(new int[]{child, 2});
                    }
                }
            }
            ++moves;
        }
        return answer;
    }

    /**
     * 1376 - Time Needed to Inform All Employees
     * Shit this solution is smartttt (not mine)
     * If i's manager != -1, compute & update the inform time of i
     * -> i.e. the inform time of i's manager + i's inform time
     * Set i's manager to be -1 (caching) and return ^
     */
    public static int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        int res = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, dfs1376(i, manager, informTime));
        }
        return res;
    }

    public static int dfs1376(int i, int[] manager, int[] informTime) {
        if (manager[i] != -1) {
            informTime[i] += dfs1376(manager[i], manager, informTime);
            manager[i] = -1;
        }
        return informTime[i];
    }

    /**
     * 1319 - Number of Operations to Make Network Connected
     * Union find
     */
    public int makeConnected(int n, int[][] connections) {
        int[] parents = new int[n];
        for (int i = 0; i < n; i++) parents[i] = i;
        int m = connections.length, islands = 0, extraEdge = 0;
        for (int i = 0; i < m; i++) {
            int p1 = findParent1319(parents, connections[i][0]);
            int p2 = findParent1319(parents, connections[i][1]);
            if (p1 == p2) extraEdge++; // they are already indirectly connected, hence edge is not required
            else parents[p1] = p2;
        }
        for (int i = 0; i < n; i++) if (parents[i] == i) islands++;
        return (extraEdge >= islands - 1) ? islands - 1 : -1;
    }

    public int findParent1319(int[] par, int i) {
        if (par[i] != i) {
            par[i] = findParent1319(par, par[i]);
        }
        return par[i];
    }

    //    547
    public static int findCircleNum(int[][] isConnected) {
        int count = 0;
        boolean[] isVisited = new boolean[isConnected.length];
        for (int i = 0; i < isConnected.length; i++) {
            if (!isVisited[i]) {
                count++;
                dfs547(isConnected, i, isVisited);
            }
        }
        return count;
    }

    public static void dfs547(int[][] isConnected, int n, boolean[] isVisited) {
        if (!isVisited[n]) {
            isVisited[n] = true;
            int[] toVisit = isConnected[n];
            for (int i = 0; i < toVisit.length; i++) {
                if (toVisit[i] == 1 && i != n) dfs547(isConnected, i, isVisited);
            }
        }
    }

    // 841
    public static boolean canVisitAllRooms(List<List<Integer>> rooms) {
        Queue<Integer> toVisit = new LinkedList<>();
        boolean[] visited = new boolean[rooms.size()];
        List<Integer> currRoom = rooms.get(0);
        visited[0] = true;
        for (int i = 0; i < currRoom.size(); i++) toVisit.add(currRoom.get(i));
        while (!toVisit.isEmpty()) {
            int r = toVisit.poll();
            if (!visited[r]) {
                visited[r] = true;
                currRoom = rooms.get(r);
                for (int i = 0; i < currRoom.size(); i++) toVisit.add(currRoom.get(i));
            }
        }
        for (boolean b : visited) {
            if (!b) return false;
        }
        return true;
    }

    //    797
    public static List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> paths = new ArrayList<>();
        for (int init : graph[0]) {
            List<Integer> newRoute = new ArrayList<>(Arrays.asList(0, init));
            dfs797(graph, paths, newRoute);
        }
        return paths;
    }

    public static void dfs797(int[][] graph, List<List<Integer>> paths, List<Integer> currRoute) {
        int latestPos = currRoute.get(currRoute.size() - 1);
        System.out.println(latestPos);
        if (latestPos == graph.length - 1) {
            paths.add(currRoute);
        } else {
            int[] next = graph[latestPos];
            for (int n : next) {
                List<Integer> continueRoute = new ArrayList<>(currRoute);
                continueRoute.add(n);
                dfs797(graph, paths, continueRoute);
            }
        }
    }

    // 934
    public static int shortestBridge(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        Queue<int[]> toVisit = new LinkedList<>();
        boolean firstIslandFound = false;
        for (int row = 0; row < rows && !firstIslandFound; row++) {
            for (int col = 0; col < cols && !firstIslandFound; col++) {
                if (grid[row][col] == 1) {
                    dfs934(grid, toVisit, row, col);
                    firstIslandFound = true;
                }
            }
        }

        int count = 0;
        while (!toVisit.isEmpty()) {
            int size = toVisit.size();
            while (size-- > 0) {
                int[] curr = toVisit.poll();
                int row = curr[0], col = curr[1], val = grid[row][col];
                if (val == 3) continue;
                if (val == 1) return count;
                grid[row][col] = 3;
                for (int[] dir : fourDirs) {
                    if (isValidCell(grid, row + dir[0], col + dir[1])) {
                        toVisit.add(new int[]{row + dir[0], col + dir[1]});
                    }
                }
            }
            count++;
        }
        return count;
    }

    public static boolean isValidCell(int[][] grid, int row, int col) {
        return (row >= 0 && col >= 0 && row < grid.length && col < grid[0].length);
    }

    public static boolean isValidCell(int[][] grid, int[] curr) {
        int row = curr[0], col = curr[1];
        return (row >= 0 && col >= 0 && row < grid.length && col < grid[0].length);
    }

    public static void dfs934(int[][] grid, Queue<int[]> toVisit, int i, int j) {
        if (!isValidCell(grid, i, j) || grid[i][j] != 1) return;
        grid[i][j] = 3;
        for (int[] dir : fourDirs) {
            int newI = i + dir[0], newJ = j + dir[1];
            if (isValidCell(grid, newI, newJ)) {
                int val = grid[newI][newJ];
                if (val == 1) {
                    dfs934(grid, toVisit, newI, newJ);
                } else if (val == 0) {
                    toVisit.add(new int[]{newI, newJ});
                }
            }
        }
    }

    // 1926 - can this be done w dfs ??
    public static int nearestExit(char[][] maze, int[] entrance) {
        int rows = maze.length, cols = maze[0].length, entI = entrance[0], entJ = entrance[1];
        maze[entI][entJ] = '+';
        Queue<int[]> toExplore = new LinkedList<>();
        for (int[] dir : fourDirs) {
            int newI = entI + dir[0], newJ = entJ + dir[1];
            if (newI >= 0 && newJ >= 0 && newI < rows && newJ < cols && maze[newI][newJ] == '.') {
                toExplore.add(new int[]{newI, newJ});
            }
        }
        return bfs1926(maze, toExplore);
    }

    private static int bfs1926(char[][] maze, Queue<int[]> toExplore) {
        int steps = 1, rows = maze.length, cols = maze[0].length;
        while (!toExplore.isEmpty()) {
            int size = toExplore.size(); // Fk la bro this is impt... u have to take this out or it will recompute everytime zz
            for (int i = 0; i < size; i++) {
                int[] curr = toExplore.poll();
                int row = curr[0], col = curr[1];
                if (maze[row][col] == '.') {
                    if (row == 0 || col == 0 || row == rows - 1 || col == cols - 1) return steps;
                    maze[row][col] = '+';
                    for (int[] dir : fourDirs) {
                        int newI = row + dir[0], newJ = col + dir[1];
                        if (newI >= 0 && newJ >= 0 && newI < rows && newJ < cols && maze[newI][newJ] == '.') {
                            toExplore.add(new int[]{newI, newJ});
                        }
                    }
                }
            }
            steps++;
        }
        return -1;
    }

    // 795
    public static int maxAreaOfIsland(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int max = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 1) {
                    max = Math.max(max, bfs795(grid, row, col));
                }
            }
        }
        return max;
    }

    private static int bfs795(int[][] grid, int row, int col) {
        int rows = grid.length, cols = grid[0].length;
        Queue<int[]> toVisit = new LinkedList<>();
        toVisit.add(new int[]{row, col});
        int count = 0;
        while (!toVisit.isEmpty()) {
            int[] curr = toVisit.poll();
            int i = curr[0], j = curr[1];
            if (grid[i][j] == 1) {
                count++;
                grid[i][j] = 0;
                for (int[] dir : fourDirs) {
                    int newI = i + dir[0], newJ = j + dir[1];
                    if (newI < rows && newJ < cols && newI >= 0 && newJ >= 0 && grid[newI][newJ] == 1)
                        toVisit.add(new int[]{newI, newJ});
                }
            }
        }

        return count;
    }

    //    542
    public static int[][] updateMatrix(int[][] mat) {
        int rows = mat.length, cols = mat[0].length, d = 1;
        int[][] dist = new int[rows][cols];
        Queue<int[]> toVisit = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (mat[row][col] == 0) {
                    toVisit.offer(new int[]{row, col});
                    visited[row][col] = true;
                }
            }
        }

        while (!toVisit.isEmpty()) {
            int size = toVisit.size();
            while (size-- > 0) {
                int[] curr = toVisit.poll();
                for (int[] dir : fourDirs) {
                    int newI = curr[0] + dir[0];
                    int newJ = curr[1] + dir[1];
                    if (newI < 0 || newI >= rows || newJ < 0 || newJ >= cols || visited[newI][newJ]) continue;
                    visited[newI][newJ] = true;
                    dist[newI][newJ] = d;
                    toVisit.add(new int[]{newI, newJ});
                }
            }
            d++;
        }
        return dist;
    }

    //    1091. Shortest Path in Binary Matrix
    public static int shortestPathBinaryMatrix(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;
        Queue<int[]> queue = new LinkedList();
        int rows = grid.length, cols = grid[0].length;
        if (grid[0][0] != 0 || grid[rows - 1][cols - 1] != 0) return -1;

        queue.add(new int[]{0, 0});

        int path = 1; // *1 - shit this is smart
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) { // *1
                int[] curr = queue.poll();
                int currI = curr[0], currJ = curr[1];
                if (currI == rows - 1 && currJ == cols - 1) return path;
                for (int[] dir : eightDirs) {
                    int next_i = currI + dir[0], next_j = currJ + dir[1];
                    if (next_i >= 0 && next_i < rows && next_j >= 0 && next_j < rows && grid[next_i][next_j] == 0) {
                        queue.add(new int[]{next_i, next_j});
                        grid[next_i][next_j] = -1; // mark the grid to prevent recursive loop
                    }
                }
            }
            path++; // *1
        }

        return -1;
    }


    //    417. Pacific Atlantic Water Flow
    public List<List<Integer>> pacificAtlanticBFS(int[][] heights) {
        /**
         * 19 ms, faster than 28.00%
         * 55.5 MB, less than 25.19%
         */
        int rows = heights.length, cols = heights[0].length;
        Queue<int[]> pacQueue = new LinkedList<>(), atlQueue = new LinkedList<>();

//        left - pacific, right - atlantic
        for (int i = 0; i < rows; i++) {
            pacQueue.add(new int[]{i, 0});
            atlQueue.add(new int[]{i, cols - 1});
        }

//        top - pacific, bottom - atlantic
        for (int i = 0; i < cols; i++) {
            pacQueue.add(new int[]{0, i});
            atlQueue.add(new int[]{rows - 1, i});
        }

        boolean[][] pacVisited = bfs417(heights, pacQueue), atlVisited = bfs417(heights, atlQueue);

        List<List<Integer>> common = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (pacVisited[i][j] && atlVisited[i][j]) {
                    common.add(Arrays.asList(i, j));
                }
            }
        }
        return common;
    }

    public List<List<Integer>> pacificAtlanticDFS(int[][] heights) {
        int rows = heights.length, cols = heights[0].length;
        boolean[][] pacVisited = new boolean[rows][cols], atlVisited = new boolean[rows][cols];

//        left - pacific, right - atlantic
        for (int i = 0; i < rows; i++) {
            dfs417(heights, i, 0, pacVisited);
            dfs417(heights, i, cols - 1, atlVisited);
        }

//        top - pacific, bottom - atlantic
        for (int i = 0; i < cols; i++) {
            dfs417(heights, 0, i, pacVisited);
            dfs417(heights, rows - 1, i, atlVisited);
        }

        List<List<Integer>> common = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (pacVisited[i][j] && atlVisited[i][j])
                    common.add(Arrays.asList(i, j));
            }
        }
        return common;
    }

    private boolean[][] bfs417(int[][] heights, Queue<int[]> queue) {
        int rows = heights.length, cols = heights[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int[] dirs = {0, 1, 0, -1, 0};

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            visited[curr[0]][curr[1]] = true;
            int currHeight = heights[curr[0]][curr[1]];

            for (int k = 0; k < 4; k++) {
                int nextI = curr[0] + dirs[k];
                int nextJ = curr[1] + dirs[k + 1];
                if (nextI >= 0 && nextI < rows && nextJ >= 0 && nextJ < cols && !visited[nextI][nextJ] &&
                        currHeight <= heights[nextI][nextJ]) {
                    queue.add(new int[]{nextI, nextJ});
                }
            }
        }

        return visited;
    }

    public void dfs417(int[][] heights, int i, int j, boolean[][] visited) {
        if (i < 0 || j < 0 || i >= heights.length || j >= heights[0].length || visited[i][j]) return;
        visited[i][j] = true;
        int currHeight = heights[i][j];
        if (i + 1 < heights.length && heights[i + 1][j] >= currHeight) dfs417(heights, i + 1, j, visited);
        if (i - 1 >= 0 && heights[i - 1][j] >= currHeight) dfs417(heights, i - 1, j, visited);
        if (j + 1 < heights[0].length && heights[i][j + 1] >= currHeight) dfs417(heights, i, j + 1, visited);
        if (j - 1 >= 0 && heights[i][j - 1] >= currHeight) dfs417(heights, i, j - 1, visited);
    }

    //    1162. As Far from Land as Possible
    public static int maxDistance(int[][] grid) {
        if (grid == null || grid.length == 0) return -1;
        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        return bfs1162(grid, visited, rows, cols);
    }

    public static int bfs1162(int[][] grid, boolean[][] visited, int rows, int cols) {
        Queue<int[]> queue = new LinkedList<>();
        int result = -1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    visited[i][j] = true;
                    queue.offer(new int[]{i, j});
                }
            }
        }
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0], col = current[1];
            for (int[] dir : fourDirs) {
                int nextI = row + dir[0], nextJ = col + dir[1];
                if (nextI < 0 || nextJ < 0 || nextI >= rows || nextJ >= cols || visited[nextI][nextJ]) continue;
                visited[nextI][nextJ] = true;
                grid[nextI][nextJ] = grid[row][col] + 1;
                result = Math.max(result, grid[nextI][nextJ]);
                queue.offer(new int[]{nextI, nextJ});
            }
        }

        return result < 0 ? -1 : result - 1;
    }

    //    1905
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        for (int row = 0; row < grid1.length; row++) {
            for (int col = 0; col < grid1[0].length; col++) {
                if (grid1[row][col] == 0) reduceGrid2(grid1, grid2, row, col);
            }
        }

        int islands = 0;
        for (int row = 0; row < grid1.length; row++) {
            for (int col = 0; col < grid1[0].length; col++) {
                if (grid2[row][col] == 1) {
                    islands += 1;
                    reduceGrid2(grid1, grid2, row, col);
                }
                ;
            }
        }

        return islands;
    }

    public void reduceGrid2(int[][] grid1, int[][] grid2, int row, int col) {
        if (row < 0 || col < 0 || row >= grid1.length || col >= grid1[0].length || grid2[row][col] == 0) return;
        grid2[row][col] = 0;
        for (int i = 0; i < 4; i++) {
            reduceGrid2(grid1, grid2, row + dirs[i], col + dirs[i + 1]);
        }
    }

    //    1020. Number of Enclaves
    public int numEnclaves(int[][] grid) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        // clear left and right borders
        for (int row = 0; row < rows; row++) {
            convertToSea(grid, row, 0);
            convertToSea(grid, row, cols - 1);
        }
        // clear top and bottom borders
        for (int col = 0; col < cols; col++) {
            convertToSea(grid, 0, col);
            convertToSea(grid, rows - 1, col);
        }

        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {
                count += grid[row][col];
            }
        }
        return count;
    }

    public void convertToSea(int[][] grid, int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length || grid[row][col] == 0) return;
        grid[row][col] = 0;
        for (int i = 0; i < 4; i++) {
            convertToSea(grid, row + dirs[i], col + dirs[i + 1]);
        }
    }

    //    130. Surrounded Regions
    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') clearFromSides(board, i, 0);
            if (board[i][n - 1] == 'O') clearFromSides(board, i, n - 1);
        }
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') clearFromSides(board, 0, j);
            if (board[m - 1][j] == 'O') clearFromSides(board, m - 1, j);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                if (board[i][j] == '#') board[i][j] = 'O';
            }
        }
    }

    public void clearFromSides(char[][] board, int row, int col) {
        if (row < 0 || col < 0 || row >= board.length || col >= board[0].length || board[row][col] != 'O') return;
        board[row][col] = '#';
        for (int i = 0; i < 4; i++) {
            clearFromSides(board, row + dirs[i], col + dirs[i + 1]);
        }
    }

    /**
     * 200. Number of Islands
     * Manipulate the problem - reduce all '1' to '0' except for the seed island, possible as islands are not connected
     */
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == '1') count++;
                clearLand(grid, row, col);
            }
        }
        return count;
    }

    public void clearLand(char[][] grid, int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length || grid[row][col] == '0') return;
        grid[row][col] = '0';
        for (int i = 0; i < 4; i++) {
            clearLand(grid, row + dirs[i], col + dirs[i + 1]);
        }
    }

    //    733. Flood Fill
    public static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int oldColor = image[sr][sc];
        if (oldColor == newColor) return image;
        fill(image, sr, sc, oldColor, newColor, image.length, image[0].length);
        return image;
    }

    public static void fill(int[][] image, int row, int col, int oldColor, int newColor, int m, int n) {
        if (row < 0 || col < 0 || row >= m || col >= n) return;
        if (image[row][col] == oldColor) {
            image[row][col] = newColor;
            for (int i = 0; i < 4; i++) {
                fill(image, row + dirs[i], col + dirs[i + 1], oldColor, newColor, m, n);
            }
        }
    }
}
