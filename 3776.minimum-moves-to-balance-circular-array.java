#
# @lc app=leetcode id=3776 lang=java
#
# [3776] Minimum Moves to Balance Circular Array
#

# @lc code=start
class Solution {
    public long minMoves(int[] balance) {
        int n = balance.length;
        long sum = 0;
        int negIdx = -1;
        long need = 0;
        for (int i = 0; i < n; i++) {
            sum += balance[i];
            if (balance[i] < 0) {
                negIdx = i;
                need = -(long) balance[i];
            }
        }
        if (sum < 0) {
            return -1;
        }
        if (need == 0) {
            return 0;
        }
        long[] supply = new long[n];
        for (int i = 0; i < n; i++) {
            if (i == negIdx) continue;
            int dist1 = (i - negIdx + n) % n;
            int dist2 = (negIdx - i + n) % n;
            int dist = Math.min(dist1, dist2);
            supply[dist] += (long) balance[i];
        }
        long ans = 0;
        for (int d = 1; d < n; d++) {
            long take = Math.min(need, supply[d]);
            ans += take * d;
            need -= take;
            if (need <= 0) break;
        }
        return ans;
    }
};
# @lc code=end