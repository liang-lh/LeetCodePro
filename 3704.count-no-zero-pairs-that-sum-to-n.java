#
# @lc app=leetcode id=3704 lang=java
#
# [3704] Count No-Zero Pairs That Sum to N
#

# @lc code=start
class Solution {
    public long countNoZeroPairs(long n) {
        String s = String.valueOf(n);
        int L = s.length();
        long[][][][] dp = new long[L + 1][2][2][3];
        // Base case
        for (int sa = 0; sa < 2; sa++) {
            for (int sb = 0; sb < 2; sb++) {
                dp[L][sa][sb][0] = (sa == 1 && sb == 1) ? 1L : 0L;
            }
        }
        // Fill DP from pos = L-1 to 0
        for (int p = L - 1; p >= 0; p--) {
            int dig = s.charAt(p) - '0';
            for (int sa = 0; sa < 2; sa++) {
                for (int sb = 0; sb < 2; sb++) {
                    for (int da = (sa == 0 ? 0 : 1); da <= 9; da++) {
                        int nsa = sa | (da > 0 ? 1 : 0);
                        for (int db = (sb == 0 ? 0 : 1); db <= 9; db++) {
                            int nsb = sb | (db > 0 ? 1 : 0);
                            for (int cin = 0; cin < 3; cin++) {
                                long w = dp[p + 1][nsa][nsb][cin];
                                if (w == 0) continue;
                                int total = da + db + cin;
                                if (total % 10 == dig) {
                                    int cout = total / 10;
                                    dp[p][sa][sb][cout] += w;
                                }
                            }
                        }
                    }
                }
            }
        }
        return dp[0][0][0][0];
    }
}
# @lc code=end