#
# @lc app=leetcode id=3786 lang=java
#
# [3786] Total Sum of Interaction Cost in Tree Groups
#

# @lc code=start
class Solution {
    public long interactionCosts(int n, int[][] edges, int[] group) {
        @SuppressWarnings("unchecked")
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            adj[u].add(v);
            adj[v].add(u);
        }
        int[] gsize = new int[21];
        for (int i = 0; i < n; i++) {
            gsize[group[i]]++;
        }
        long[] totalAns = new long[1];
        class DfsHelper {
            int dfs(int node, int par, int g, int[] grp, List<Integer>[] ad, int[] gs, long[] tans) {
                int cnt = (grp[node] == g ? 1 : 0);
                for (int nei : ad[node]) {
                    if (nei != par) {
                        int sub = dfs(nei, node, g, grp, ad, gs, tans);
                        tans[0] += (long) sub * (gs[g] - sub);
                        cnt += sub;
                    }
                }
                return cnt;
            }
        }
        DfsHelper helper = new DfsHelper();
        for (int g = 1; g <= 20; g++) {
            if (gsize[g] < 2) continue;
            helper.dfs(0, -1, g, group, adj, gsize, totalAns);
        }
        return totalAns[0];
    }
}
# @lc code=end