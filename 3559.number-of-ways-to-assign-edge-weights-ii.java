#
# @lc app=leetcode id=3559 lang=java
#
# [3559] Number of Ways to Assign Edge Weights II
#

# @lc code=start
class Solution {
    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        int n = edges.length + 1;
        @SuppressWarnings("unchecked")
        List<Integer>[] adj = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            adj[u].add(v);
            adj[v].add(u);
        }
        final long MOD = 1000000007L;
        final int LOG = 18;
        int[][] par = new int[LOG][n + 1];
        int[] dep = new int[n + 1];
        boolean[] vis = new boolean[n + 1];
        Queue<Integer> q = new LinkedList<>();
        q.offer(1);
        vis[1] = true;
        dep[1] = 0;
        par[0][1] = -1;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj[u]) {
                if (!vis[v]) {
                    vis[v] = true;
                    dep[v] = dep[u] + 1;
                    par[0][v] = u;
                    q.offer(v);
                }
            }
        }
        for (int k = 1; k < LOG; k++) {
            for (int i = 1; i <= n; i++) {
                if (par[k - 1][i] != -1) {
                    par[k][i] = par[k - 1][par[k - 1][i]];
                } else {
                    par[k][i] = -1;
                }
            }
        }
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            if (u == v) {
                ans[i] = 0;
                continue;
            }
            int uu = u;
            int vv = v;
            if (dep[uu] > dep[vv]) {
                int temp = uu;
                uu = vv;
                vv = temp;
            }
            int diff = dep[vv] - dep[uu];
            for (int k = 0; k < LOG; k++) {
                if ((diff & (1 << k)) != 0) {
                    vv = par[k][vv];
                }
            }
            if (uu == vv) {
                int l = uu;
                int dist = dep[u] + dep[v] - 2 * dep[l];
                ans[i] = (int) modpow(2L, (long)dist - 1L, MOD);
                continue;
            }
            for (int k = LOG - 1; k >= 0; k--) {
                if (par[k][uu] != par[k][vv]) {
                    uu = par[k][uu];
                    vv = par[k][vv];
                }
            }
            int l = par[0][uu];
            int dist = dep[u] + dep[v] - 2 * dep[l];
            ans[i] = (int) modpow(2L, (long)dist - 1L, MOD);
        }
        return ans;
    }

    private static long modpow(long b, long e, long mod) {
        long res = 1;
        b %= mod;
        while (e > 0) {
            if ((e & 1) == 1) {
                res = res * b % mod;
            }
            b = b * b % mod;
            e >>= 1;
        }
        return res;
    }
}
# @lc code=end