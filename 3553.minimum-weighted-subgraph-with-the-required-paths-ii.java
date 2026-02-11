#
# @lc app=leetcode id=3553 lang=java
#
# [3553] Minimum Weighted Subgraph With the Required Paths II
#

# @lc code=start
class Solution {
    public int[] minimumWeight(int[][] edges, int[][] queries) {
        int n = edges.length + 1;
        @SuppressWarnings("unchecked")
        java.util.List<int[]>[] adj = new java.util.List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new java.util.ArrayList<int[]>();
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            adj[u].add(new int[]{v, w});
            adj[v].add(new int[]{u, w});
        }
        long[] dep = new long[n];
        int[] lev = new int[n];
        int[][] par = new int[n][18];
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 18; j++) {
                par[i][j] = -1;
            }
        }
        java.util.Queue<Integer> q = new java.util.LinkedList<>();
        q.offer(0);
        visited[0] = true;
        dep[0] = 0;
        lev[0] = 0;
        par[0][0] = -1;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int[] nei : adj[u]) {
                int v = nei[0];
                int w = nei[1];
                if (!visited[v]) {
                    visited[v] = true;
                    dep[v] = dep[u] + w;
                    lev[v] = lev[u] + 1;
                    par[v][0] = u;
                    q.offer(v);
                }
            }
        }
        for (int j = 1; j < 18; j++) {
            for (int i = 0; i < n; i++) {
                int p = par[i][j - 1];
                if (p != -1) {
                    par[i][j] = par[p][j - 1];
                }
            }
        }
        class DistHelper {
            long dist(int u, int v) {
                int x = u, y = v;
                if (lev[x] > lev[y]) {
                    int tmp = x;
                    x = y;
                    y = tmp;
                }
                int delta = lev[y] - lev[x];
                for (int j = 0; j < 18; j++) {
                    if ((delta & (1 << j)) != 0) {
                        y = par[y][j];
                    }
                }
                if (x == y) {
                    return dep[u] + dep[v] - 2L * dep[x];
                }
                for (int j = 17; j >= 0; j--) {
                    if (par[x][j] != -1 && par[y][j] != -1 && par[x][j] != par[y][j]) {
                        x = par[x][j];
                        y = par[y][j];
                    }
                }
                int lca = par[x][0];
                return dep[u] + dep[v] - 2L * dep[lca];
            }
        }
        DistHelper dh = new DistHelper();
        int m = queries.length;
        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            int src1 = queries[i][0];
            int src2 = queries[i][1];
            int dest = queries[i][2];
            long d12 = dh.dist(src1, src2);
            long d1d = dh.dist(src1, dest);
            long d2d = dh.dist(src2, dest);
            ans[i] = (int) ((d12 + d1d + d2d) / 2);
        }
        return ans;
    }
}
# @lc code=end