#
# @lc app=leetcode id=3530 lang=java
#
# [3530] Maximum Profit from Valid Topological Order in DAG
#

# @lc code=start
class Solution {
    public int maxProfit(int n, int[][] edges, int[] score) {
        int N = 1 << n;
        int[] dp = new int[N];
        dp[0] = 0;
        int[] pred_mask = new int[n];
        for (int[] edge : edges) {
            pred_mask[edge[1]] |= (1 << edge[0]);
        }
        for (int mask = 0; mask < N; mask++) {
            if (mask != 0 && dp[mask] == 0) continue;
            int pos = Integer.bitCount(mask) + 1;
            for (int v = 0; v < n; v++) {
                if ((mask & (1 << v)) == 0 && (pred_mask[v] & mask) == pred_mask[v]) {
                    int next_mask = mask | (1 << v);
                    dp[next_mask] = Math.max(dp[next_mask], dp[mask] + score[v] * pos);
                }
            }
        }
        return dp[N - 1];
    }
}
# @lc code=end