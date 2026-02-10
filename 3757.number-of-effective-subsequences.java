#
# @lc app=leetcode id=3757 lang=java
#
# [3757] Number of Effective Subsequences
#
# @lc code=start
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int countEffective(int[] nums) {
        int MOD = 1000000007;
        int n = nums.length;
        
        // Calculate total OR of all elements
        int totalOR = 0;
        for (int num : nums) {
            totalOR |= num;
        }
        
        // DP: dp[or_value] = count of subsequences where remaining elements have OR = or_value
        Map<Integer, Long> dp = new HashMap<>();
        dp.put(0, 1L);  // Initial state: processed zero elements, remaining OR = 0
        
        // Process each element
        for (int num : nums) {
            Map<Integer, Long> newDp = new HashMap<>();
            
            for (Map.Entry<Integer, Long> entry : dp.entrySet()) {
                int orVal = entry.getKey();
                long count = entry.getValue();
                
                // Option 1: Remove this element (include in subsequence)
                // Remaining OR stays the same
                newDp.put(orVal, (newDp.getOrDefault(orVal, 0L) + count) % MOD);
                
                // Option 2: Keep this element (don't include in subsequence)
                // Remaining OR becomes (orVal | num)
                int newOr = orVal | num;
                newDp.put(newOr, (newDp.getOrDefault(newOr, 0L) + count) % MOD);
            }
            
            dp = newDp;
        }
        
        // Count effective subsequences (where remaining OR < totalOR)
        long result = 0;
        for (Map.Entry<Integer, Long> entry : dp.entrySet()) {
            if (entry.getKey() < totalOR) {
                result = (result + entry.getValue()) % MOD;
            }
        }
        
        return (int) result;
    }
}
# @lc code=end