#
# @lc app=leetcode id=3553 lang=java
#
# [3553] Minimum Weighted Subgraph With the Required Paths II
#

# @lc code=start
import java.util.*;
class Solution {
    public int[] minimumWeight(int[][] edges, int[][] queries) {
        int n = edges.length + 1;
        List<int[]>[] adj = new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            adj[u].add(new int[]{v, w});
            adj[v].add(new int[]{u, w});
        }
        final int LOG = 18;
        int[][] par = new int[n][LOG];
        long[] wdepth = new long[n];
        int[] level = new int[n];
        boolean[] vis = new boolean[n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(par[i], -1);
        }
        // BFS preprocess
        Queue<Integer> q = new LinkedList<>();
        int root = 0;
        q.offer(root);
        vis[root] = true;
        par[root][0] = -1;
        level[root] = 0;
        wdepth[root] = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int[] e : adj[u]) {
                int v = e[0];
                if (!vis[v]) {
                    vis[v] = true;
                    par[v][0] = u;
                    level[v] = level[u] + 1;
                    wdepth[v] = wdepth[u] + e[1];
                    q.offer(v);
                }
            }
        }
        // Build jumping table
        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                int p = par[i][k - 1];
                if (p != -1) {
                    par[i][k] = par[p][k - 1];
                } else {
                    par[i][k] = -1;
                }
            }
        }
        int m = queries.length;
        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            int a = queries[i][0];
            int b = queries[i][1];
            int c = queries[i][2];
            long dab = getDist(a, b, par, level, wdepth, LOG);
            long dac = getDist(a, c, par, level, wdepth, LOG);
            long dbc = getDist(b, c, par, level, wdepth, LOG);
            ans[i] = (int) ((dab + dac + dbc) / 2);
        }
        return ans;
    }

    private int getLCA(int u, int v, int[][] par, int[] level, int LOG) {
        if (level[u] > level[v]) {
            int tmp = u;
            u = v;
            v = tmp;
        }
        int diff = level[v] - level[u];
        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0 && par[v][k] != -1) {
                v = par[v][k];
            }
        }
        if (u == v) {
            return u;
        }
        for (int k = LOG - 1; k >= 0; k--) {
            if (par[u][k] != -1 && par[v][k] != -1 && par[u][k] != par[v][k]) {
                u = par[u][k];
                v = par[v][k];
            }
        }
        return par[u][0];
    }

    private long getDist(int u, int v, int[][] par, int[] level, long[] wdepth, int LOG) {
        int l = getLCA(u, v, par, level, LOG);
        return wdepth[u] + wdepth[v] - 2L * wdepth[l];
    }
}
# @lc code=end