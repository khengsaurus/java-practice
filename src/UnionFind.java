import java.util.*;

public class UnionFind {
    public static void main(String[] args) {
        int[] res = findRedundantConnection(new int[][]{{1, 2}, {2, 3}, {1, 3}});
        System.out.println(Arrays.toString(res));
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

    private static void union(int from, int to, int[] parents) {
        int tRoot = findParent(to, parents);
        int fRoot = findParent(from, parents);
        if (tRoot == fRoot) return;
        parents[tRoot] = fRoot;
    }

    //    721. Accounts Merge
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int[] parents = new int[accounts.size()];
        for (int i = 0; i < accounts.size(); i++) {
            parents[i] = i;
        }
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
            users.putIfAbsent(parent, new TreeSet<>());
            users.get(parent).addAll(emails.subList(1, emails.size()));
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
