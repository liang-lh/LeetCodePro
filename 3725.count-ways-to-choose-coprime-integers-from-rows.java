#
# @lc app=leetcode id=3725 lang=java
#
# [3725] Count Ways to Choose Coprime Integers from Rows
#

# @lc code=start
class Solution {
    public int countCoprime(int[][] mat) {
        final int MAX = 150;
        final long MOD = 1000000007L;

        int[] mu = new int[MAX + 1];
        boolean[] vis = new boolean[MAX + 1];
        int[] primes = new int[MAX + 1];
        int pcnt = 0;
        mu[1] = 1;
        for (int i = 2; i <= MAX; i++) {
            if (!vis[i]) {
                primes[pcnt++] = i;
                mu[i] = -1;
            }
            for (int j = 0; j < pcnt; j++) {
                int p = primes[j];
                if ((long) i * p > MAX) break;
                vis[i * p] = true;
                if (i % p == 0) {
                    mu[i * p] = 0;
                    break;
                } else {
                    mu[i * p] = -mu[i];
                }
            }
        }

        int m = mat.length;
        long ans = 0;
        for (int d = 1; d <= MAX; d++) {
            if (mu[d] == 0) continue;
            long ways = 1;
            for (int r = 0; r < m; r++) {
                int cnt = 0;
                for (int num : mat[r]) {
                    if (num % d == 0) cnt++;
                }
                ways = ways * cnt % MOD;
            }
            if (mu[d] == 1) {
                ans = (ans + ways) % MOD;
            } else {
                ans = (ans - ways + MOD) % MOD;
            }
        }
        return (int) ans;
    }
}
# @lc code=end