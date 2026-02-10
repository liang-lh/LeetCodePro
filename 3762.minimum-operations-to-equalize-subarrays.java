#
# @lc app=leetcode id=3762 lang=java
#
# [3762] Minimum Operations to Equalize Subarrays
#
# @lc code=start
import java.util.Arrays;

class Solution {
    public long[] minOperations(int[] nums, int k, int[][] queries) {
        long[] result = new long[queries.length];
        
        for (int q = 0; q < queries.length; q++) {
            int l = queries[q][0];
            int r = queries[q][1];
            
            // Check if all elements in subarray have same remainder mod k
            int remainder = nums[l] % k;
            boolean possible = true;
            
            for (int i = l; i <= r; i++) {
                if (nums[i] % k != remainder) {
                    possible = false;
                    break;
                }
            }
            
            if (!possible) {
                result[q] = -1;
                continue;
            }
            
            // If only one element, no operations needed
            if (l == r) {
                result[q] = 0;
                continue;
            }
            
            // Find the median as the target
            int[] subarray = new int[r - l + 1];
            for (int i = l; i <= r; i++) {
                subarray[i - l] = nums[i];
            }
            Arrays.sort(subarray);
            int median = subarray[(r - l) / 2];
            
            // Calculate minimum operations
            long operations = 0;
            for (int i = l; i <= r; i++) {
                operations += Math.abs(nums[i] - median) / k;
            }
            
            result[q] = operations;
        }
        
        return result;
    }
}
# @lc code=end