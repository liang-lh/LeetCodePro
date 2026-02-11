#
# @lc app=leetcode id=3585 lang=java
#
# [3585] Find Weighted Median Node in Tree
#

# @lc code=start
class Solution {
    public int[] findMedian(int n, int[][] edges, int[][] queries) {
        java.util.List<int[]>[] adj = new java.util.ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new java.util.ArrayList<int[]>();
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            adj[u].add(new int[]{v, w});
            adj[v].add(new int[]{u, w});
        }
        final int LOG = 18;
        long[] depth = new long[n];
        int[] level = new int[n];
        int[][] par = new int[n][LOG];
        long[][] updist = new long[n][LOG];
        for (int i = 0; i < n; i++) {
            java.util.Arrays.fill(par[i], -1);
        }
        // BFS for depth, level, par[0], updist[0]
        boolean[] vis = new boolean[n];
        java.util.Queue<Integer> q = new java.util.LinkedList<>();
        vis[0] = true;
        depth[0] = 0;
        level[0] = 0;
        par[0][0] = -1;
        updist[0][0] = 0;
        q.offer(0);
        while (!q.isEmpty()) {
            int node = q.poll();
            for (int[] pr : adj[node]) {
                int v = pr[0];
                int w = pr[1];
                if (!vis[v]) {
                    vis[v] = true;
                    par[v][0] = node;
                    updist[v][0] = w;
                    depth[v] = depth[node] + w;
                    level[v] = level[node] + 1;
                    q.offer(v);
                }
            }
        }
        // Fill lifting tables
        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                int a = par[i][k - 1];
                if (a != -1) {
                    par[i][k] = par[a][k - 1];
                    updist[i][k] = updist[i][k - 1] + updist[a][k - 1];
                } else {
                    par[i][k] = -1;
                    updist[i][k] = 0;
                }
            }
        }
        int[] ans = new int[queries.length];
        for (int j = 0; j < queries.length; j++) {
            int u = queries[j][0];
            int v = queries[j][1];
            if (u == v) {
                ans[j] = u;
                continue;
            }
            // Compute LCA using levels
            int aa = u, bb = v;
            if (level[aa] > level[bb]) {
                int t = aa;
                aa = bb;
                bb = t;
            }
            // Lift bb to level[aa]
            for (int k = LOG - 1; k >= 0; k--) {
                if (par[bb][k] != -1 && level[par[bb][k]] >= level[aa]) {
                    bb = par[bb][k];
                }
            }
            int lca_;
            if (aa == bb) {
                lca_ = aa;
            } else {
                for (int k = LOG - 1; k >= 0; k--) {
                    if (par[aa][k] != -1 && par[bb][k] != -1 && par[aa][k] != par[bb][k]) {
                        aa = par[aa][k];
                        bb = par[bb][k];
                    }
                }
                lca_ = par[aa][0];
            }
            long total = depth[u] + depth[v] - 2L * depth[lca_];
            long targ = (total + 1L) / 2L;
            long distul = depth[u] - depth[lca_];
            int med;
            if (distul >= targ) {
                long maxd = targ - 1L;
                int z = u;
                long remd = maxd;
                for (int k = LOG - 1; k >= 0; k--) {
                    if (par[z][k] != -1 && updist[z][k] <= remd) {
                        remd -= updist[z][k];
                        z = par[z][k];
                    }
                }
                med = par[z][0];
            } else {
                long distlv = depth[v] - depth[lca_];
                long rem = targ - distul;
                long maxupv = distlv - rem;
                int y = v;
                long remd = maxupv;
                for (int k = LOG - 1; k >= 0; k--) {
                    if (par[y][k] != -1 && updist[y][k] <= remd) {
                        remd -= updist[y][k];
                        y = par[y][k];
                    }
                }
                med = y;
            }
            ans[j] = med;
        }
        return ans;
    }
}
# @lc code=end