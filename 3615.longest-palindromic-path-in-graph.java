#
# @lc app=leetcode id=3615 lang=java
#
# [3615] Longest Palindromic Path in Graph
#

# @lc code=start
class Solution {
    public int maxLen(int n, int[][] edges, String label) {
        final java.util.List<java.lang.Integer>[] adj = new java.util.ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new java.util.ArrayList<java.lang.Integer>();
        }
        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }
        final int N = 1 << n;
        final int[][][] memo = new int[n][n][N];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                java.util.Arrays.fill(memo[i][j], -1);
            }
        }
        class Helper {
            int call(int l, int r, int mask) {
                if (memo[l][r][mask] != -1) {
                    return memo[l][r][mask];
                }
                int ans = Integer.bitCount(mask);
                for (int nl : adj[l]) {
                    if ((mask & (1 << nl)) != 0) continue;
                    for (int nr : adj[r]) {
                        if ((mask & (1 << nr)) != 0) continue;
                        if (nl == nr) continue;
                        if (label.charAt(nl) != label.charAt(nr)) continue;
                        int newmask = mask | (1 << nl) | (1 << nr);
                        ans = Math.max(ans, call(nl, nr, newmask));
                    }
                }
                memo[l][r][mask] = ans;
                return ans;
            }
        }
        Helper helper = new Helper();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, helper.call(i, i, 1 << i));
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            if (label.charAt(u) == label.charAt(v)) {
                int m = (1 << u) | (1 << v);
                ans = Math.max(ans, helper.call(u, v, m));
                ans = Math.max(ans, helper.call(v, u, m));
            }
        }
        return ans;
    }
}
# @lc code=end