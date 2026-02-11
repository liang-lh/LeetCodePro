#
# @lc app=leetcode id=3515 lang=java
#
# [3515] Shortest Path in a Weighted Tree
#

# @lc code=start
class Solution {
    public int[] treeQueries(int n, int[][] edges, int[][] queries) {
        @SuppressWarnings("unchecked")
        java.util.List<int[]>[] adj = new java.util.List[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new java.util.ArrayList<>();
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            adj[u].add(new int[]{v, w});
            adj[v].add(new int[]{u, w});
        }
        int[] parent = new int[n + 1];
        int[] edgeToParW = new int[n + 1];
        int[] inTime = new int[n + 1];
        int[] outTime = new int[n + 1];
        int[] initDist = new int[n + 1];
        int[] tim = {1};
        dfs(1, 0, 0, 0, adj, parent, edgeToParW, inTime, outTime, initDist, tim);
        class FenwickTree {
            int[] tree;
            FenwickTree(int size) {
                tree = new int[size + 2];
            }
            void update(int idx, int val) {
                while (idx < tree.length) {
                    tree[idx] += val;
                    idx += idx & -idx;
                }
            }
            int query(int idx) {
                int sum = 0;
                while (idx > 0) {
                    sum += tree[idx];
                    idx -= idx & -idx;
                }
                return sum;
            }
        }
        FenwickTree ft = new FenwickTree(n + 1);
        java.util.List<java.lang.Integer> answer = new java.util.ArrayList<>();
        for (int[] q : queries) {
            if (q[0] == 1) {
                int u = q[1], v = q[2], newW = q[3];
                int child = (parent[v] == u ? v : u);
                int oldW = edgeToParW[child];
                int delta = newW - oldW;
                edgeToParW[child] = newW;
                int L = inTime[child];
                int R = outTime[child];
                ft.update(L, delta);
                ft.update(R, -delta);
            } else {
                int x = q[1];
                int curDist = initDist[x] + ft.query(inTime[x]);
                answer.add(curDist);
            }
        }
        return answer.stream().mapToInt(java.lang.Integer::intValue).toArray();
    }
    private static void dfs(int u, int p, int dist, int wFromP, java.util.List<int[]>[] adj, int[] parent, int[] edgeToParW, int[] inTime, int[] outTime, int[] initDist, int[] tim) {
        parent[u] = p;
        edgeToParW[u] = wFromP;
        initDist[u] = dist;
        inTime[u] = tim[0]++;
        for (int[] ne : adj[u]) {
            int v = ne[0];
            if (v != p) {
                dfs(v, u, dist + ne[1], ne[1], adj, parent, edgeToParW, inTime, outTime, initDist, tim);
            }
        }
        outTime[u] = tim[0];
    }
}
# @lc code=end