#
# @lc app=leetcode id=3420 lang=java
#
# [3420] Count Non-Decreasing Subarrays After K Operations
#
# @lc code=start
class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        long count = 0;
        
        // Try all possible subarrays
        for (int i = 0; i < n; i++) {
            long operations = 0;
            int maxSoFar = nums[i];
            
            for (int j = i; j < n; j++) {
                // To make subarray non-decreasing, each element must be >= max so far
                // Cost to increase nums[j] to maxSoFar
                if (nums[j] < maxSoFar) {
                    operations += maxSoFar - nums[j];
                } else {
                    // Update max if current element is larger
                    maxSoFar = nums[j];
                }
                
                // Check if we can make this subarray non-decreasing with k operations
                if (operations <= k) {
                    count++;
                } else {
                    // No point continuing - cost only increases with longer subarrays
                    break;
                }
            }
        }
        
        return count;
    }
}
# @lc code=end