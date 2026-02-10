#
# @lc app=leetcode id=3729 lang=java
#
# [3729] Count Distinct Subarrays Divisible by K in Sorted Array
#
# @lc code=start
import java.util.HashSet;
import java.util.Set;

class Solution {
    public long numGoodSubarrays(int[] nums, int k) {
        Set<String> distinctSubarrays = new HashSet<>();
        int n = nums.length;
        
        for (int i = 0; i < n; i++) {
            long sum = 0;
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < n; j++) {
                sum += nums[j];
                if (j > i) sb.append(",");
                sb.append(nums[j]);
                
                if (sum % k == 0) {
                    distinctSubarrays.add(sb.toString());
                }
            }
        }
        
        return distinctSubarrays.size();
    }
}
# @lc code=end