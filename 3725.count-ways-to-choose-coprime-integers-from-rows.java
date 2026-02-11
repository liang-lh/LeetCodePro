#
# @lc app=leetcode id=3725 lang=java
#
# [3725] Count Ways to Choose Coprime Integers from Rows
#

# @lc code=start
class Solution {
    public int countCoprime(int[][] mat) {
        final int MOD = 1000000007;
        int m = mat.length;
        if (m == 0) return 0;
        int maxv = 0;
        for (int[] row : mat) {
            for (int num : row) {
                maxv = Math.max(maxv, num);
            }
        }
        int[] mu = new int[maxv + 1];
        boolean[] vis = new boolean[maxv + 1];
        int[] primes = new int[maxv + 1];
        int pc = 0;
        mu[1] = 1;
        for (int i = 2; i <= maxv; ++i) {
            if (!vis[i]) {
                primes[pc++] = i;
                mu[i] = -1;
            }
            for (int j = 0; j < pc; ++j) {
                int p = primes[j];
                if ((long) i * p > maxv) break;
                vis[i * p] = true;
                if (i % p == 0) {
                    mu[i * p] = 0;
                    break;
                } else {
                    mu[i * p] = -mu[i];
                }
            }
        }
        long ans = 0;
        for (int d = 1; d <= maxv; ++d) {
            if (mu[d] == 0) continue;
            long ways = 1;
            boolean zero = false;
            for (int[] row : mat) {
                int c = 0;
                for (int num : row) {
                    if (num % d == 0) ++c;
                }
                ways = ways * c % MOD;
                if (c == 0) {
                    zero = true;
                    break;
                }
            }
            if (zero) continue;
            long contrib = (long) mu[d] * ways % MOD;
            ans = (ans + contrib + MOD) % MOD;
        }
        return (int) ans;
    }
}
# @lc code=end