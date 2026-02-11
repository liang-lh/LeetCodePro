#
# @lc app=leetcode id=3771 lang=java
#
# [3771] Total Score of Dungeon Runs
#

# @lc code=start
class Solution {
    public long totalScore(int hp, int[] damage, int[] requirement) {
        int n = damage.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + damage[i];
        }
        long ans = 0;
        for (int k = 0; k < n; k++) {
            long thresh = prefix[k + 1] + (long) requirement[k] - hp;
            int left = 0, right = k;
            int jmin = k + 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (prefix[mid] >= thresh) {
                    jmin = mid;
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            if (jmin <= k) {
                ans += (k - jmin + 1L);
            }
        }
        return ans;
    }
}
# @lc code=end