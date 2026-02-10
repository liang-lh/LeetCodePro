#
# @lc app=leetcode id=3739 lang=java
#
# [3739] Count Subarrays With Majority Element II
#

# @lc code=start
class Solution {
    public long countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;
        long count = 0;
        
        // Use TreeMap to maintain sorted prefix sums
        TreeMap<Integer, Long> prefixCount = new TreeMap<>();
        prefixCount.put(0, 1L); // Empty prefix has sum 0
        
        int prefixSum = 0;
        for (int i = 0; i < n; i++) {
            // Transform: target -> +1, others -> -1
            prefixSum += (nums[i] == target) ? 1 : -1;
            
            // For subarray [j, i] to have target as majority:
            // sum(j, i) > 0, which means prefixSum[i] > prefixSum[j-1]
            // Count all prefixes with sum < current prefixSum
            Map<Integer, Long> lessThan = prefixCount.headMap(prefixSum);
            for (Long val : lessThan.values()) {
                count += val;
            }
            
            // Add current prefix sum
            prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0L) + 1);
        }
        
        return count;
    }
}
# @lc code=end