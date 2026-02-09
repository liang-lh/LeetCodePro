#
# @lc app=leetcode id=3434 lang=java
#
# [3434] Maximum Frequency After Subarray Operation
#
# @lc code=start
class Solution {
    public int maxFrequency(int[] nums, int k) {
        int n = nums.length;
        int baseCount = 0;
        
        // Count initial frequency of k
        for (int num : nums) {
            if (num == k) {
                baseCount++;
            }
        }
        
        int maxFreq = baseCount; // At least we have the base count
        
        // Try all possible values of x
        for (int x = -50; x <= 50; x++) {
            int[] benefit = new int[n];
            
            for (int i = 0; i < n; i++) {
                if (nums[i] == k - x && nums[i] != k) {
                    benefit[i] = 1;  // Gain: this element becomes k
                } else if (nums[i] == k && x != 0) {
                    benefit[i] = -1;  // Loss: this k becomes non-k
                } else {
                    benefit[i] = 0;  // No change
                }
            }
            
            // Kadane's algorithm to find maximum subarray sum
            int maxBenefit = 0;  // Can choose empty subarray
            int currentSum = 0;
            
            for (int b : benefit) {
                currentSum = Math.max(0, currentSum + b);
                maxBenefit = Math.max(maxBenefit, currentSum);
            }
            
            int freq = baseCount + maxBenefit;
            maxFreq = Math.max(maxFreq, freq);
        }
        
        return maxFreq;
    }
}
# @lc code=end