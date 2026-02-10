#
# @lc app=leetcode id=3748 lang=java
#
# [3748] Count Stable Subarrays
#
# @lc code=start
class Solution {
    public long[] countStableSubarrays(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] reach = new int[n];
        
        // Compute reach[i] - furthest index where non-decreasing subarray starting at i extends
        reach[n-1] = n-1;
        for (int i = n-2; i >= 0; i--) {
            if (nums[i] <= nums[i+1]) {
                reach[i] = reach[i+1];
            } else {
                reach[i] = i;
            }
        }
        
        // Process queries
        int q = queries.length;
        long[] ans = new long[q];
        
        for (int i = 0; i < q; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            long count = 0;
            
            // For each starting position, count stable subarrays within [l,r]
            for (int start = l; start <= r; start++) {
                int end = Math.min(reach[start], r);
                count += (end - start + 1);
            }
            
            ans[i] = count;
        }
        
        return ans;
    }
}
# @lc code=end