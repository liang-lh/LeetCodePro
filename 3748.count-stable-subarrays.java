#
# @lc app=leetcode id=3748 lang=java
#
# [3748] Count Stable Subarrays
#

# @lc code=start
class Solution {
    public long[] countStableSubarrays(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] start_pos = new int[n];
        int[] end_pos = new int[n];
        int cur_start = 0;
        for (int i = 1; i < n; ++i) {
            if (nums[i - 1] > nums[i]) {
                for (int j = cur_start; j < i; ++j) {
                    start_pos[j] = cur_start;
                    end_pos[j] = i - 1;
                }
                cur_start = i;
            }
        }
        for (int j = cur_start; j < n; ++j) {
            start_pos[j] = cur_start;
            end_pos[j] = n - 1;
        }
        long[] prefix_j = new long[n + 1];
        for (int i = 1; i <= n; ++i) {
            prefix_j[i] = prefix_j[i - 1] + (i - 1L);
        }
        long[] prefix_start_pos = new long[n + 1];
        for (int i = 1; i <= n; ++i) {
            prefix_start_pos[i] = prefix_start_pos[i - 1] + start_pos[i - 1];
        }
        long[] ans = new long[queries.length];
        for (int qi = 0; qi < queries.length; ++qi) {
            int l = queries[qi][0];
            int r = queries[qi][1];
            int run_end = end_pos[l];
            int partial_end = Math.min(r, run_end);
            long num_partial = (long) partial_end - l + 1;
            long sum_j = prefix_j[r + 1] - prefix_j[l];
            long count_tot = (long) r - l + 1;
            long sum_max_val = (long) l * num_partial;
            int next_j_start = partial_end + 1;
            if (next_j_start <= r) {
                sum_max_val += prefix_start_pos[r + 1] - prefix_start_pos[next_j_start];
            }
            ans[qi] = sum_j + count_tot - sum_max_val;
        }
        return ans;
    }
}
# @lc code=end