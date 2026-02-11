#
# @lc app=leetcode id=3420 lang=java
#
# [3420] Count Non-Decreasing Subarrays After K Operations
#
# @lc code=start
class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) return 0;
        long ans = 0;
        long cost = 0;
        long cur_max = nums[0];
        int rm_pos = 0;
        int r = 1;
        for (int l = 0; l < n; ++l) {
            while (r < n) {
                long contrib = Math.max(cur_max, (long) nums[r]) - nums[r];
                if (cost + contrib > k) break;
                cost += contrib;
                cur_max = Math.max(cur_max, (long) nums[r]);
                if ((long) nums[r] == cur_max) rm_pos = r;
                ++r;
            }
            ans += (long) (r - l);
            if (l + 1 < n) {
                long contrib_removed = Math.max(0L, (long) nums[l] - nums[l + 1]);
                cost -= contrib_removed;
                if (cost < 0) cost = 0;
                if ((long) nums[l] == cur_max && rm_pos == l) {
                    cur_max = nums[l + 1];
                    rm_pos = l + 1;
                }
            }
        }
        return ans;
    }
}
# @lc code=end