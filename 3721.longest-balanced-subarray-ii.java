#
# @lc app=leetcode id=3721 lang=java
#
# [3721] Longest Balanced Subarray II
#
# @lc code=start
class Solution {
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int maxLen = 0;
        
        for (int i = 0; i < n; i++) {
            java.util.Set<Integer> evenSet = new java.util.HashSet<>();
            java.util.Set<Integer> oddSet = new java.util.HashSet<>();
            
            for (int j = i; j < n; j++) {
                if (nums[j] % 2 == 0) {
                    evenSet.add(nums[j]);
                } else {
                    oddSet.add(nums[j]);
                }
                
                if (evenSet.size() == oddSet.size()) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        
        return maxLen;
    }
}
# @lc code=end