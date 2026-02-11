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
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        long[] ldp = new long[n];
        java.util.Arrays.fill(ldp, Long.MIN_VALUE / 2);
        for (int i = 1; i < n; i++) {
            if (nums[i - 1] < nums[i]) {
                long prev = Math.max((long) nums[i - 1], ldp[i - 1]);
                ldp[i] = (long) nums[i] + prev;
            }
        }
        long[] rdp = new long[n];
        java.util.Arrays.fill(rdp, Long.MIN_VALUE / 2);
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                long nextv = Math.max((long) nums[i + 1], rdp[i + 1]);
                rdp[i] = (long) nums[i] + nextv;
            }
        }
        long[] Aval = new long[n];
        for (int i = 0; i < n; i++) {
            Aval[i] = ldp[i] - prefix[i + 1];
        }
        long[] Bval = new long[n];
        for (int i = 0; i < n; i++) {
            Bval[i] = prefix[i] + rdp[i];
        }
        long ans = Long.MIN_VALUE / 2;
        int dec_start = 0;
        long maxA = Long.MIN_VALUE / 2;
        for (int q = 1; q < n; q++) {
            if (nums[q - 1] <= nums[q]) {
                dec_start = q;
                maxA = Long.MIN_VALUE / 2;
            }
            if (q - 1 >= dec_start) {
                maxA = Math.max(maxA, Aval[q - 1]);
            }
            if (dec_start <= q - 1) {
                long cand = maxA + Bval[q];
                if (cand > ans) ans = cand;
            }
        }
        return ans;
    }
}
# @lc code=end