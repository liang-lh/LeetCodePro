#
# @lc app=leetcode id=3721 lang=java
#
# [3721] Longest Balanced Subarray II
#
# @lc code=start
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int maxLength = 0;
        
        // Try all possible starting positions
        for (int i = 0; i < n; i++) {
            Set<Integer> distinctEven = new HashSet<>();
            Set<Integer> distinctOdd = new HashSet<>();
            
            // Expand from starting position i
            for (int j = i; j < n; j++) {
                // Add current number to appropriate set
                if (nums[j] % 2 == 0) {
                    distinctEven.add(nums[j]);
                } else {
                    distinctOdd.add(nums[j]);
                }
                
                // Check if balanced
                if (distinctEven.size() == distinctOdd.size()) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        
        return maxLength;
    }
}
# @lc code=end