#
# @lc app=leetcode id=3739 lang=java
#
# [3739] Count Subarrays With Majority Element II
#
# @lc code=start
class Solution {
    public long countMajoritySubarrays(int[] nums, int target) {
        long result = 0;
        int n = nums.length;
        
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = i; j < n; j++) {
                if (nums[j] == target) {
                    count++;
                }
                int length = j - i + 1;
                if (2 * count > length) {
                    result++;
                }
            }
        }
        
        return result;
    }
}
# @lc code=end