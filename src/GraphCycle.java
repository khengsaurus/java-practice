import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//    TODO:
public class GraphCycle {
    private final int size;
    private final List<List<Integer>> adjacencyList;

    public GraphCycle(int V) {
        this.size = V;
        adjacencyList = new ArrayList<>(V);

        for (int i = 0; i < V; i++) {
            adjacencyList.add(new LinkedList<>());
        }
    }

    // This function is a variation of DFSUtil() in https://www.geeksforgeeks.org/archives/18212
    private boolean isCyclicUtil(int i, boolean[] visited, boolean[] recStack) {
        // Mark the current node as visited and part of recursion stack
        if (recStack[i])
            return true;
        if (visited[i])
            // PC: it should be here, coz if you place this check while making recursion call,
            // then it won't recurse and won't proceed further to check in recursion array.
            return false;

        visited[i] = true;
        recStack[i] = true;
        List<Integer> children = adjacencyList.get(i);
        for (Integer c : children) {
            if (isCyclicUtil(c, visited, recStack)) return true;
        }
        recStack[i] = false;
        return false;
    }

    private void addEdge(int source, int dest) {
        adjacencyList.get(source).add(dest);
    }

    // Returns true if the graph contains a cycle, else false.
    // This function is a variation of DFS() in https://www.geeksforgeeks.org/archives/18212
    private boolean isCyclic() {
        // Mark all the vertices as not visited and not part of recursion stack
        boolean[] visited = new boolean[size];
        boolean[] recStack = new boolean[size];

        // Call the recursive helper function to detect cycle in different DFS trees
        for (int i = 0; i < size; i++)
            if (isCyclicUtil(i, visited, recStack))
                return true;

        return false;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        GraphCycle g = new GraphCycle(numCourses);
        for (int i = 0; i < prerequisites.length; i++) {
            g.addEdge(prerequisites[i][1], prerequisites[i][0]);
        }
        return !g.isCyclic();
    }

    //    TODO:
    public boolean cycleDFS(int curr, ArrayList<ArrayList<Integer>> adj, boolean[] visit, boolean[] dfsVisit) {
        visit[curr] = true;
        dfsVisit[curr] = true;

        for (Integer v : adj.get(curr)) {
            if (!visit[v]) {
                if (cycleDFS(v, adj, visit, dfsVisit)) return true;
            } else if (dfsVisit[v]) return true;
        }
        dfsVisit[curr] = false;
        return false;
    }

    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        boolean[] visit = new boolean[numCourses];
        boolean[] dfsVisit = new boolean[numCourses];
        ArrayList<ArrayList<Integer>> adj = new ArrayList();

        for (int i = 0; i < numCourses; i++) adj.add(new ArrayList());

        for (int[] e : prerequisites) adj.get(e[0]).add(e[1]);

        for (int i = 0; i < numCourses; i++) {
            if (!visit[i]) {
                if (cycleDFS(i, adj, visit, dfsVisit)) return false;
            }
        }
        return true;
    }
}