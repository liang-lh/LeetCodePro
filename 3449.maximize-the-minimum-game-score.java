#
# @lc app=leetcode id=3449 lang=java
#
# [3449] Maximize the Minimum Game Score
#

# @lc code=start
class Solution {
    public long maxScore(int[] points, int m) {
        int n = points.length;
        long low = 0;
        long high = 0;
        for (int p : points) {
            high = Math.max(high, 1L * p * m);
        }
        while (low < high) {
            long mid = low + (high - low + 1) / 2;
            if (canAchieve(points, (long) m, mid)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }
    private boolean canAchieve(int[] points, long m, long x) {
        int n = points.length;
        long[] need = new long[n];
        long[] suffixMax = new long[n];
        suffixMax[n - 1] = (x + points[n - 1] - 1L) / points[n - 1];
        need[n - 1] = suffixMax[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            need[i] = (x + points[i] - 1L) / points[i];
            suffixMax[i] = Math.max(need[i], suffixMax[i + 1]);
        }
        long total = 0;
        long depLeft = 0;
        for (int i = 0; i < n; i++) {
            long depRight = (i < n - 1 ? suffixMax[i + 1] : 0L);
            long k = Math.max(need[i], depLeft + depRight);
            total += k;
            if (total > m) return false;
            depLeft = Math.max(0L, k - depRight);
        }
        return true;
    }
}
# @lc code=end