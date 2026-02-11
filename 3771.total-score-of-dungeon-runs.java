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
        for (int i = 0; i < n; i++) {
            long need = (long) hp - requirement[i];
            if (need < 0) continue;
            long thresh = prefix[i + 1] - need;
            // binary search: smallest j in [0,i] s.t. prefix[j] >= thresh
            int left = 0, right = i;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (prefix[mid] >= thresh) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            if (left <= i && prefix[left] >= thresh) {
                ans += (long) (i - left + 1);
            }
        }
        return ans;
    }
}
# @lc code=end