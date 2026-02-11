#
# @lc app=leetcode id=3640 lang=java
#
# [3640] Trionic Array II
#

# @lc code=start
class Solution {
    public long maxSumTrionic(int[] nums) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 1; i <= n; ++i) {
            prefix[i] = prefix[i - 1] + nums[i - 1];
        }
        long INF = -1000000000000000000L;
        long[] left = new long[n];
        long[] rightDp = new long[n];
        for (int i = 0; i < n; ++i) {
            left[i] = INF;
            rightDp[i] = INF;
        }
        for (int i = 1; i < n; ++i) {
            if (nums[i] > nums[i - 1]) {
                left[i] = (long) nums[i] + Math.max((long) nums[i - 1], left[i - 1]);
            }
        }
        for (int i = n - 2; i >= 0; --i) {
            if (nums[i] < nums[i + 1]) {
                rightDp[i] = (long) nums[i] + Math.max((long) nums[i + 1], rightDp[i + 1]);
            }
        }
        long[] val = new long[n];
        for (int i = 0; i < n; ++i) {
            val[i] = left[i] == INF ? INF : left[i] - prefix[i + 1];
        }
        long ans = INF;
        long curMax = INF;
        for (int q = 1; q < n; ++q) {
            if (nums[q - 1] > nums[q]) {
                curMax = Math.max(curMax, val[q - 1]);
                if (curMax != INF && rightDp[q] != INF) {
                    long cand = curMax + prefix[q + 1] + rightDp[q] - nums[q];
                    if (cand > ans) {
                        ans = cand;
                    }
                }
            } else {
                curMax = INF;
            }
        }
        return ans;
    }
}
# @lc code=end