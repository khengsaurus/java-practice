import java.util.*;

class GraphQns {
    public static void main(String[] args) {
//        int[] r2 = new int[]{1, 0, 0, 0, 1};
//        int res = maxDistance(new int[][]{r0, r1});
//        int res = shortestPathBinaryMatrix(new int[][]{{0, 0, 0}, {1, 1, 0}, {1, 1, 0}});
        int[][] res = updateMatrix(new int[][]{{0, 0, 0}, {0, 1, 0}, {1, 1, 1}});
        for (int[] r : res) {
            System.out.println(Arrays.toString(r));
        }
    }

    //    542
    public static int[][] updateMatrix(int[][] mat) {
        int rows = mat.length, cols = mat[0].length, d = 1;
        int[][] dist = new int[rows][cols], dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
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
                for (int[] dir : dirs) {
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
        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};

        int path = 1; // *1 - shit this is smart
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) { // *1
                int[] curr = queue.poll();
                int currI = curr[0], currJ = curr[1];
                if (currI == rows - 1 && currJ == cols - 1) return path;
                for (int[] dir : dirs) {
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
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
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
            for (int[] dir : directions) {
                int nextI = row + dir[0], nextJ = col + dir[1];
                if (nextI < 0 || nextJ < 0 || nextI >= rows || nextJ >= cols || visited[nextI][nextJ]) {
                    continue;
                } else {
                    visited[nextI][nextJ] = true;
                    grid[nextI][nextJ] = grid[row][col] + 1;
                    result = Math.max(result, grid[nextI][nextJ]);
                    queue.offer(new int[]{nextI, nextJ});
//                    for (int[] r : grid) System.out.println(Arrays.toString(r));
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

    public static boolean canStudyDfs(int course, Map<Integer, List<Integer>> courseAndPreReqs, Set<Integer> visited) {
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
