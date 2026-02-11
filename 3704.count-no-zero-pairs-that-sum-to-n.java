#
# @lc app=leetcode id=3704 lang=java
#
# [3704] Count No-Zero Pairs That Sum to N
#

# @lc code=start
class Solution {
    public long countNoZeroPairs(long n) {
        String s = Long.toString(n);
        int len = s.length();
        int[] digits = new int[len];
        for (int i = 0; i < len; ++i) {
            digits[i] = s.charAt(len - 1 - i) - '0';
        }
        final int STATES = 32;
        long[][] dp = new long[len + 1][STATES];
        int init_st = 0;
        dp[0][init_st] = 1;
        for (int p = 0; p < len; ++p) {
            for (int st = 0; st < STATES; ++st) {
                if (dp[p][st] == 0) continue;
                int carry = st & 1;
                int hb = (st >> 1) & 1;
                int swb = (st >> 2) & 1;
                int ha = (st >> 3) & 1;
                int swa = (st >> 4) & 1;
                for (int da = 0; da < 10; ++da) {
                    boolean valid_da;
                    int new_swa;
                    int new_ha = ha | (da != 0 ? 1 : 0);
                    if (swa == 1) {
                        valid_da = (da == 0);
                        new_swa = 1;
                    } else {
                        valid_da = true;
                        new_swa = (da == 0) ? 1 : 0;
                    }
                    if (!valid_da) continue;
                    for (int db = 0; db < 10; ++db) {
                        boolean valid_db;
                        int new_swb;
                        int new_hb = hb | (db != 0 ? 1 : 0);
                        if (swb == 1) {
                            valid_db = (db == 0);
                            new_swb = 1;
                        } else {
                            valid_db = true;
                            new_swb = (db == 0) ? 1 : 0;
                        }
                        if (!valid_db) continue;
                        int total = da + db + carry;
                        if (total % 10 != digits[p]) continue;
                        int new_carry = total / 10;
                        int new_st = (new_swa << 4) | (new_ha << 3) | (new_swb << 2) | (new_hb << 1) | new_carry;
                        dp[p + 1][new_st] += dp[p][st];
                    }
                }
            }
        }
        long ans = 0;
        for (int st = 0; st < STATES; ++st) {
            int carry = st & 1;
            int hb = (st >> 1) & 1;
            int ha = (st >> 3) & 1;
            if (carry == 0 && ha == 1 && hb == 1) {
                ans += dp[len][st];
            }
        }
        return ans;
    }
}
# @lc code=end