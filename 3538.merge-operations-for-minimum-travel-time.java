#
# @lc app=leetcode id=3538 lang=java
#
# [3538] Merge Operations for Minimum Travel Time
#

# @lc code=start
class Solution {
    public int minTravelTime(int l, int n, int k, int[] position, int[] time) {
        final int INF = 2000000000;
        final int MAXR = 101;
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + time[i];
        }
        int[][][] dp = new int[n][k + 1][MAXR];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                for (int r = 0; r < MAXR; r++) {
                    dp[i][j][r] = INF;
                }
            }
        }
        if (n > 0 && time[0] < MAXR) {
            dp[0][0][time[0]] = 0;
        }
        for (int p = 0; p < n; p++) {
            for (int j = 0; j <= k; j++) {
                for (int r = 0; r < MAXR; r++) {
                    if (dp[p][j][r] == INF) continue;
                    for (int i = p + 1; i < n; i++) {
                        int merges_add = i - p - 1;
                        if (j + merges_add > k) break;
                        long dist = (long) position[i] - position[p];
                        long added_cost = dist * r;
                        long temp = (long) dp[p][j][r] + added_cost;
                        if (temp >= INF) continue;
                        int new_cost = (int) temp;
                        int new_r = prefix[i + 1] - prefix[p + 1];
                        if (new_r < 0 || new_r >= MAXR) continue;
                        int nj = j + merges_add;
                        if (new_cost < dp[i][nj][new_r]) {
                            dp[i][nj][new_r] = new_cost;
                        }
                    }
                }
            }
        }
        int ans = INF;
        for (int r = 0; r < MAXR; r++) {
            if (dp[n - 1][k][r] < ans) {
                ans = dp[n - 1][k][r];
            }
        }
        return ans;
    }
}
# @lc code=end