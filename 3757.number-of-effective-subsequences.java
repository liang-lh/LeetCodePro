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
        int full_or = 0;
        for (int x : nums) {
            full_or |= x;
        }
        int[] active = new int[20];
        int m = 0;
        for (int b = 0; b < 20; b++) {
            if ((full_or & (1 << b)) != 0) {
                active[m++] = b;
            }
        }
        if (m == 0) return 0;
        final int BITS = 20;
        final int MAXM = 1 << BITS;
        long[] freq = new long[MAXM];
        for (int x : nums) {
            if (x < MAXM) freq[x]++;
        }
        long[] dp = new long[MAXM];
        for (int i = 0; i < MAXM; i++) {
            dp[i] = freq[i];
        }
        for (int b = 0; b < BITS; b++) {
            for (int mask = 0; mask < MAXM; mask++) {
                if ((mask & (1 << b)) != 0) {
                    dp[mask] += dp[mask ^ (1 << b)];
                }
            }
        }
        int ALL = (1 << BITS) - 1;
        final long MOD = 1000000007L;
        long ans = 0;
        for (int s = 1; s < (1 << m); s++) {
            int this_mask = 0;
            for (int j = 0; j < m; j++) {
                if ((s & (1 << j)) != 0) {
                    this_mask |= (1 << active[j]);
                }
            }
            int pop = Integer.bitCount(s);
            int comp = ALL ^ this_mask;
            long avoid = dp[comp];
            int forced = n - (int) avoid;
            long ways = mod_pow(2L, (long) (n - forced), MOD);
            long sign = (pop % 2 == 1) ? 1L : (MOD - 1L);
            ans = (ans + sign * ways % MOD) % MOD;
        }
        return (int) ans;
    }
    private long mod_pow(long base, long exp, long mod) {
        long res = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                res = res * base % mod;
            }
            base = base * base % mod;
            exp >>= 1;
        }
        return res;
    }
}
# @lc code=end