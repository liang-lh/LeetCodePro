#
# @lc app=leetcode id=3544 lang=java
#
# [3544] Subtree Inversion Sum
#

# @lc code=start
import java.util.*;

class Solution {
    private long[][] dp;
    private List<Integer>[] adj;
    private int[] nums_arr;
    private int KK;

    public long subtreeInversionSum(int[][] edges, int[] nums, int k) {
        int n = nums.length;
        adj = new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }
        dp = new long[n][k + 1];
        nums_arr = nums;
        KK = k;
        dfs(0, -1);
        return dp[0][k];
    }

    private void dfs(int u, int par) {
        List<Integer> children = new ArrayList<>();
        for (int v : adj[u]) {
            if (v != par) {
                dfs(v, u);
                children.add(v);
            }
        }
        long[] childTotal = new long[KK + 1];
        for (int t = 1; t <= KK; t++) {
            for (int v : children) {
                childTotal[t] += dp[v][t];
            }
        }
        for (int s = 1; s <= KK; s++) {
            int sch = Math.min(s + 1, KK);
            long opt1 = (long) nums_arr[u] + childTotal[sch];
            if (s < KK) {
                dp[u][s] = opt1;
            } else {
                long opt2 = - (long) nums_arr[u] - childTotal[1];
                dp[u][s] = Math.max(opt1, opt2);
            }
        }
    }
}
# @lc code=end