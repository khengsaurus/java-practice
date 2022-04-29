import java.util.*;

public class UnionFind {
    public static void main(String[] args) {
        List<List<Integer>> l = new ArrayList<>();
        l.add(new ArrayList<Integer>(Arrays.asList(0, 1)));
        l.add(new ArrayList<Integer>(Arrays.asList(1, 3)));
        l.add(new ArrayList<Integer>(Arrays.asList(2, 5)));
        String res = smallestStringWithSwaps("cbadaz", l);
        System.out.println(res);
    }

    /**
     * 1202. Smallest String With Swaps
     * Model the string as a graph, with pairs being vertices between the characters (edges).
     * Group the characters by index via union find, e.g. [[0-1], [1-3]] -> 0,1,3 are the same group, with the same parent 0.
     * Init a heap for each group and add each of that group's characters to it.
     * Iterate through the chars and replace charAt(i) with heap(i).poll()
     */
    static int[] parents;
    static int[] inDegree;

    public static String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        char[] cs = s.toCharArray();
        parents = new int[cs.length];
        inDegree = new int[cs.length];
        for (int i = 0; i < parents.length; i++) parents[i] = i;
        HashMap<Integer, PriorityQueue<Character>> map = new HashMap<>();

        for (List<Integer> pair : pairs) union(pair.get(0), pair.get(1));

        for (int i = 0; i < cs.length; i++) {
            int parent = findParent(i);
            map.computeIfAbsent(parent, k -> new PriorityQueue<>()).offer(cs[i]);
        }
        for (int i = 0; i < cs.length; i++) cs[i] = map.get(findParent(i)).poll();
        return new String(cs);
    }

    private static void union(int a, int b) {
        int parentA = findParent(a), parentB = findParent(b);
        if (parentA != parentB) {
            if (inDegree[parentA] > inDegree[parentB]) {
                parents[parentB] = parentA;
            } else if (inDegree[parentB] > inDegree[parentA]) {
                parents[parentA] = parentB;
            } else {
                parents[parentB] = parentA;
                inDegree[parentA]++;
            }
        }
    }

    private static int findParent(int a) {
        while (parents[a] != a) {
            parents[a] = parents[parents[a]];
            a = parents[a];
        }
        return a;
    }

    /**
     * 684. Redundant Connection
     * Union find works here because the edges represent one tree, hence all nodes have the same parent
     */
    public static int[] findRedundantConnection(int[][] edges) {
        int[] parents = new int[1001];
        for (int i = 0; i < parents.length; i++) parents[i] = i;

        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            int fromRoot = findParent(from, parents), toRoot = findParent(to, parents);
            if (fromRoot == toRoot) return edge;
            // Union
            parents[toRoot] = fromRoot;
        }
        return null;
    }

    private static int findParent(int node, int[] parents) {
        while (parents[node] != node) {
            parents[node] = parents[parents[node]];
            node = parents[node];
        }
        return node;
    }

    //    721. Accounts Merge
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int[] parents = new int[accounts.size()];
        for (int i = 0; i < accounts.size(); i++) parents[i] = i;
        Map<String, Integer> owners = new HashMap<>();

        for (int acc = 0; acc < accounts.size(); acc++) {
            for (int emailI = 1; emailI < accounts.get(acc).size(); emailI++) {
                String email = accounts.get(acc).get(emailI);
                if (!owners.containsKey(email)) {
                    owners.put(email, acc);
                } else {
                    int owner = owners.get(email);
                    int accountOwner = findParent(parents, acc);
                    int emailOwner = findParent(parents, owner);
                    if (accountOwner != emailOwner) parents[emailOwner] = accountOwner;
                }
            }
        }

        Map<Integer, TreeSet<String>> users = new HashMap<>();
        for (int acc = 0; acc < accounts.size(); acc++) {
            int parent = findParent(parents, acc);
            List<String> emails = accounts.get(acc);
            users.computeIfAbsent(parent, p -> new TreeSet<>())
                    .addAll(emails.subList(1, emails.size()));
        }

        List<List<String>> res = new ArrayList<>();
        for (Integer user : users.keySet()) {
            String name = accounts.get(user).get(0);
            ArrayList<String> emails = new ArrayList<>(users.get(user));
            emails.add(0, name);
            res.add(emails);
        }
        return res;
    }

    private int findParent(int[] parents, int parent) {
        while (parents[parent] != parent) {
            parents[parent] = parents[parents[parent]];
            parent = parents[parent];
        }
        return parent;
    }

}
