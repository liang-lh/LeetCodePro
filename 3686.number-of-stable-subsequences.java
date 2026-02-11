#
# @lc app=leetcode id=3686 lang=java
#
# [3686] Number of Stable Subsequences
#

# @lc code=start
class Solution {
    public int countStableSubsequences(int[] nums) {
        final int MOD = 1000000007;
        long a = 0, b = 0, c = 0, d = 0;
        for (int num : nums) {
            int p = num % 2;
            long na, nb, nc, nd;
            if (p == 0) {
                na = (a + 1 + c + d) % MOD;
                nb = (b + a) % MOD;
                nc = c;
                nd = d;
            } else {
                nc = (c + 1 + a + b) % MOD;
                nd = (d + c) % MOD;
                na = a;
                nb = b;
            }
            a = na;
            b = nb;
            c = nc;
            d = nd;
        }
        return (int) ((a + b + c + d) % MOD);
    }
}
# @lc code=end