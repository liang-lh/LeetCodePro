#
# @lc app=leetcode id=3559 lang=java
#
# [3559] Number of Ways to Assign Edge Weights II
#

# @lc code=start
import java.util.*;
class Solution {
    private static final long MOD = 1000000007L;
    private static final int LOG = 18;

    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        int n = edges.length + 1;
        List<List<Integer>> adj = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
        int[][] up = new int[LOG][n + 1];
        int[] depth = new int[n + 1];
        Arrays.fill(depth, -1);
        Queue<Integer> q = new LinkedList<>();
        depth[1] = 0;
        up[0][1] = 1;
        q.offer(1);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj.get(u)) {
                if (depth[v] == -1) {
                    depth[v] = depth[u] + 1;
                    up[0][v] = u;
                    q.offer(v);
                }
            }
        }
        for (int j = 1; j < LOG; j++) {
            for (int i = 1; i <= n; i++) {
                int p = up[j - 1][i];
                up[j][i] = up[j - 1][p];
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
            int l = getLCA(u, v, depth, up);
            long k = (long) depth[u] + depth[v] - 2L * depth[l];
            ans[i] = (int) modPow(2L, k - 1, MOD);
        }
        return ans;
    }

    private int getLCA(int u, int v, int[] depth, int[][] up) {
        if (depth[u] > depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        int diff = depth[v] - depth[u];
        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                v = up[j][v];
            }
        }
        if (u == v) {
            return u;
        }
        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }
        return up[0][u];
    }

    private long modPow(long base, long exp, long mod) {
        long res = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                res = res * base % mod;
            }
            base = base * base % mod;
            exp >>= 1;
        }
        return res;
    }
}
# @lc code=end