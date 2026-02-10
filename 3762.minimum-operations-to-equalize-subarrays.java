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
            
            // Check if all elements have the same remainder mod k
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
            
            // If subarray has only one element
            if (l == r) {
                result[q] = 0;
                continue;
            }
            
            // Extract subarray and sort to find median
            int len = r - l + 1;
            int[] subarray = new int[len];
            for (int i = 0; i < len; i++) {
                subarray[i] = nums[l + i];
            }
            Arrays.sort(subarray);
            
            // Use median as target
            int median = subarray[len / 2];
            
            // Calculate total operations
            long operations = 0;
            for (int i = l; i <= r; i++) {
                operations += (long) Math.abs(nums[i] - median) / k;
            }
            
            result[q] = operations;
        }
        
        return result;
    }
}
# @lc code=end