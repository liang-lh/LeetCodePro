#
# @lc app=leetcode id=3670 lang=java
#
# [3670] Maximum Product of Two Integers With No Common Bits
#
# @lc code=start
class Solution {
    public long maxProduct(int[] nums) {
        long maxProduct = 0;
        int n = nums.length;
        
        // Try all pairs of distinct indices
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Check if they have no common set bits using bitwise AND
                if ((nums[i] & nums[j]) == 0) {
                    long product = (long) nums[i] * nums[j];
                    maxProduct = Math.max(maxProduct, product);
                }
            }
        }
        
        return maxProduct;
    }
}
# @lc code=end