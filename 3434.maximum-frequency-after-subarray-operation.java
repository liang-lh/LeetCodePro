#
# @lc app=leetcode id=3434 lang=java
#
# [3434] Maximum Frequency After Subarray Operation
#

# @lc code=start
class Solution {
  public int maxFrequency(int[] nums, int k) {
    int n = nums.length;
    int totalK = 0;
    for (int num : nums) {
      if (num == k) totalK++;
    }
    int ans = totalK;
    for (int v = 1; v <= 50; v++) {
      if (v == k) continue;
      int maxEnd = 0;
      int maxFar = 0;
      for (int i = 0; i < n; i++) {
        int score = 0;
        if (nums[i] == v) score = 1;
        else if (nums[i] == k) score = -1;
        maxEnd += score;
        if (maxEnd < 0) maxEnd = 0;
        if (maxEnd > maxFar) maxFar = maxEnd;
      }
      if (totalK + maxFar > ans) ans = totalK + maxFar;
    }
    return ans;
  }
}
# @lc code=end