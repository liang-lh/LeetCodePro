#
# @lc app=leetcode id=3757 lang=java
#
# [3757] Number of Effective Subsequences
#

# @lc code=start
class Solution {
    public int countEffective(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        int S = 0;
        for (int x : nums) S |= x;
        int k = 0;
        for (int b = 0; b < 32; b++) {
            if ((S & (1 << b)) != 0) k++;
        }
        int[] bits = new int[k];
        int idx = 0;
        for (int b = 0; b < 32; b++) {
            if ((S & (1 << b)) != 0) {
                bits[idx++] = b;
            }
        }
        int MS = 1 << k;
        long[] fr = new long[MS];
        for (int num : nums) {
            int mask = 0;
            for (int j = 0; j < k; j++) {
                if ((num & (1 << bits[j])) != 0) {
                    mask |= (1 << j);
                }
            }
            fr[mask]++;
        }
        long[] dp = new long[MS];
        for (int i = 0; i < MS; i++) dp[i] = fr[i];
        for (int b = 0; b < k; b++) {
            for (int mask = 0; mask < MS; mask++) {
                if ((mask & (1 << b)) == 0) {
                    dp[mask | (1 << b)] += dp[mask];
                }
            }
        }
        final int MOD = 1000000007;
        long[] pow2 = new long[n + 1];
        pow2[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow2[i] = pow2[i - 1] * 2L % MOD;
        }
        long ans = 0;
        int full = (1 << k) - 1;
        for (int m = 1; m < MS; m++) {
            int pop = Integer.bitCount(m);
            long disj = dp[full ^ m];
            long ways = pow2[(int) disj];
            if ((pop & 1) == 1) {
                ans = (ans + ways) % MOD;
            } else {
                ans = (ans + MOD - ways) % MOD;
            }
        }
        return (int) ans;
    }
}
# @lc code=end