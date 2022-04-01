import java.util.*;

class GraphQns {
    public static void main(String[] args) {
//        List<List<Integer>> res = allPathsSourceTarget(new int[][]{{1, 2}, {3}, {3}, {}});
//        List<List<Integer>> res = allPathsSourceTarget(new int[][]{{4, 3, 1}, {3, 2, 4}, {3}, {4}, {}});
//        System.out.println(res);
        int res = findCircleNum(new int[][]{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 1}, {0, 0, 1, 1}});
        System.out.println(res);
    }

    final static int[][] fourDirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    final static int[][] eightDirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};

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
            while (size > 0) {
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
                size--;
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
            while (size > 0) {
                int[] curr = toVisit.poll();
                for (int[] dir : fourDirs) {
                    int newI = curr[0] + dir[0];
                    int newJ = curr[1] + dir[1];
                    if (newI < 0 || newI >= rows || newJ < 0 || newJ >= cols || visited[newI][newJ]) continue;
                    visited[newI][newJ] = true;
                    dist[newI][newJ] = d;
                    toVisit.add(new int[]{newI, newJ});
                }
                size--;
            }
            d++;
        }
        return dist;
    }

    //    1091
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


    // 417
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

        boolean[][] pacVisited = bfs417(heights, pacQueue);
        boolean[][] atlVisited = bfs417(heights, atlQueue);

        List<List<Integer>> common = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (pacVisited[i][j] && atlVisited[i][j])
                    common.add(Arrays.asList(i, j));
            }
        }
        return common;
    }

    public List<List<Integer>> pacificAtlanticDFS(int[][] heights) {
        /**
         * 3 ms, faster than 99.66%
         * 43.5 MB, less than 87.64%
         */
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

    //    1162
    public static int maxDistance(int[][] grid) {
        if (grid == null || grid.length == 0)
            return -1;
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int res = bfs1162(grid, visited, rows, cols);
        return res;
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
                if (nextI < 0 || nextJ < 0 || nextI >= rows || nextJ >= cols || visited[nextI][nextJ]) {
                    continue;
                } else {
                    visited[nextI][nextJ] = true;
                    grid[nextI][nextJ] = grid[row][col] + 1;
                    result = Math.max(result, grid[nextI][nextJ]);
                    queue.offer(new int[]{nextI, nextJ});
                }
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
        reduceGrid2(grid1, grid2, row + 1, col);
        reduceGrid2(grid1, grid2, row - 1, col);
        reduceGrid2(grid1, grid2, row, col + 1);
        reduceGrid2(grid1, grid2, row, col - 1);
    }

    //    1020
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
        convertToSea(grid, row + 1, col);
        convertToSea(grid, row - 1, col);
        convertToSea(grid, row, col + 1);
        convertToSea(grid, row, col - 1);
    }

    //    207
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
//        Create adjacency-list representation
        Map<Integer, List<Integer>> courseAndPreReqs = new HashMap<>();
        for (int[] c : prerequisites) {
            if (!courseAndPreReqs.containsKey(c[0])) {
                courseAndPreReqs.put(c[0], new ArrayList<>(Arrays.asList(c[1])));
            } else {
                courseAndPreReqs.get(c[0]).add(c[1]);
            }
        }
        if (courseAndPreReqs.size() > numCourses) return false;

//        Call DFS
        Set<Integer> visited = new HashSet<>();
        for (int course : courseAndPreReqs.keySet()) {
            if (!canStudyDfs(course, courseAndPreReqs, visited)) return false;
            courseAndPreReqs.get(course).clear();
        }
        return true;
    }

    public static boolean canStudyDfs(int course, Map<
            Integer, List<Integer>> courseAndPreReqs, Set<Integer> visited) {
        if (visited.contains(course)) return false;
        List<Integer> preReqs = courseAndPreReqs.get(course);
        if (preReqs == null || preReqs.size() == 0) return true;
        visited.add(course);
        for (int preReq : preReqs) {
            if (!canStudyDfs(preReq, courseAndPreReqs, visited)) return false;
        }
        visited.remove(course);
        return true;
    }

    ;

    //    130
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
        clearFromSides(board, row + 1, col);
        clearFromSides(board, row - 1, col);
        clearFromSides(board, row, col + 1);
        clearFromSides(board, row, col - 1);
    }

    //    200
//    Manipulate the problem - reduce all '1' to '0' except for the seed island, possible as islands are not connected
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
        clearLand(grid, row + 1, col);
        clearLand(grid, row - 1, col);
        clearLand(grid, row, col + 1);
        clearLand(grid, row, col - 1);
    }

    //    773
    public static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int oldColor = image[sr][sc];
        if (oldColor == newColor) return image;
        fill(image, sr, sc, oldColor, newColor, image.length, image[0].length);
        return image;
    }

    public static void fill(int[][] image, int sr, int sc, int oldColor, int newColor, int m, int n) {
        if (sr < 0 || sc < 0 || sr >= m || sc >= n) return;
        if (image[sr][sc] == oldColor) {
            image[sr][sc] = newColor;
            fill(image, sr - 1, sc, oldColor, newColor, m, n);
            fill(image, sr + 1, sc, oldColor, newColor, m, n);
            fill(image, sr, sc - 1, oldColor, newColor, m, n);
            fill(image, sr, sc + 1, oldColor, newColor, m, n);
        }
    }

    public static boolean isValidBST(TreeNode root) {
        if ((root.left != null && root.left.val >= root.val) || (root.right != null && root.right.val <= root.val)) {
            return false;
        }
        return (root.left == null || isValidBST(root.left)) && (root.right == null || isValidBST(root.right));
    }

    public static List<Integer> traversal(TreeNode root) {
        List<Integer> rv = new ArrayList<>();
        if (root != null) dfs(root, rv);
        return rv;
    }

    private static void dfs(TreeNode node, List<Integer> rv) {
//        rv.add(node.val); // <-- preOrder
        if (node.left != null) {
            dfs(node.left, rv);
        }
        rv.add(node.val); // <-- inOrder
        if (node.right != null) {
            dfs(node.right, rv);
        }
//        rv.add(node.val); // <-- postOrder
    }

    public GraphNode cloneGraph(GraphNode node) {
        if (node == null) return null;
        GraphNode copy = new GraphNode(node.val);
        GraphNode[] visited = new GraphNode[101];
        dfs(node, copy, visited);
        return copy;
    }

    public void dfs(GraphNode node, GraphNode copy, GraphNode[] visited) {
        visited[copy.val] = copy;
        for (GraphNode n : node.neighbors) {
            if (visited[n.val] == null) {
                GraphNode newNode = new GraphNode(n.val);
                copy.neighbors.add(newNode);
                dfs(n, newNode, visited);
            } else {
                copy.neighbors.add(visited[n.val]);
            }
        }
    }
}
