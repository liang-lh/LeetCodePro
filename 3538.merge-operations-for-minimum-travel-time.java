#
# @lc app=leetcode id=3538 lang=java
#
# [3538] Merge Operations for Minimum Travel Time
#

# @lc code=start
class Solution {
    public int minTravelTime(int l, int n, int k, int[] position, int[] time) {
        final int INF = 2000000000;
        int[][][] dp = new int[n][k + 1][101];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                for (int t = 0; t <= 100; t++) {
                    dp[i][j][t] = INF;
                }
            }
        }
        dp[0][0][time[0]] = 0;
        long[] presum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            presum[i + 1] = presum[i] + time[i];
        }
        for (int i = 1; i < n; i++) {
            for (int p = 0; p < i; p++) {
                int merges_add = i - p - 1;
                if (merges_add < 0) continue;
                for (int jprev = 0; jprev <= k - merges_add; jprev++) {
                    for (int s = 0; s <= 100; s++) {
                        if (dp[p][jprev][s] == INF) continue;
                        long sumadd = presum[i] - presum[p + 1];
                        int effnew = time[i] + (int) sumadd;
                        if (effnew > 100) continue;
                        long addcost = (long) (position[i] - position[p]) * s;
                        long newcost = (long) dp[p][jprev][s] + addcost;
                        int jj = jprev + merges_add;
                        if (newcost < dp[i][jj][effnew]) {
                            dp[i][jj][effnew] = (int) newcost;
                        }
                    }
                }
            }
        }
        int ans = INF;
        for (int t = 0; t <= 100; t++) {
            ans = Math.min(ans, dp[n - 1][k][t]);
        }
        return ans;
    }
}
# @lc code=end