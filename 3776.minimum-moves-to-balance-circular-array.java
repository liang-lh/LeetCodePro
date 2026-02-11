#
# @lc app=leetcode id=3776 lang=java
#
# [3776] Minimum Moves to Balance Circular Array
#

# @lc code=start
class Solution {
    public long minMoves(int[] balance) {
        int n = balance.length;
        long total = 0;
        int negIdx = -1;
        long deficit = 0;
        for (int i = 0; i < n; i++) {
            total += (long) balance[i];
            if (balance[i] < 0) {
                negIdx = i;
                deficit = - (long) balance[i];
            }
        }
        if (total < 0) return -1;
        if (negIdx == -1) return 0;
        int maxDist = n / 2;
        long[] supplyPerDist = new long[maxDist + 1];
        for (int j = 0; j < n; j++) {
            if (j == negIdx) continue;
            long sup = balance[j];
            if (sup > 0) {
                int delta = Math.abs(j - negIdx);
                int dist = Math.min(delta, n - delta);
                if (dist < supplyPerDist.length) {
                    supplyPerDist[dist] += sup;
                }
            }
        }
        long moves = 0;
        long remaining = deficit;
        for (int dist = 1; dist <= maxDist; dist++) {
            if (remaining <= 0) break;
            long avail = supplyPerDist[dist];
            long take = Math.min(remaining, avail);
            moves += take * (long) dist;
            remaining -= take;
        }
        return moves;
    }
}
# @lc code=end