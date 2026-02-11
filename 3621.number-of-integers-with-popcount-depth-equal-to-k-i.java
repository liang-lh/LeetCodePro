#
# @lc app=leetcode id=3621 lang=java
#
# [3621] Number of Integers With Popcount-Depth Equal to K I
#

# @lc code=start
class Solution {
    public long popcountDepth(long n, int k) {
        if (k == 0) {
            return n >= 1 ? 1L : 0L;
        }
        int[] depths = new int[65];
        for (int m = 1; m <= 64; m++) {
            long num = m;
            int dep = 0;
            while (num > 1) {
                num = Long.bitCount(num);
                dep++;
            }
            depths[m] = dep;
        }
        String s = Long.toBinaryString(n);
        int length = s.length();
        int[] digits = new int[length];
        for (int i = 0; i < length; i++) {
            digits[i] = s.charAt(i) - '0';
        }
        long answer = 0L;
        for (int ones_cnt = 1; ones_cnt <= 64 && ones_cnt <= length; ones_cnt++) {
            if (depths[ones_cnt] != k - 1) continue;
            long[][][] dp_table = new long[length + 1][ones_cnt + 1][2];
            for (int cur_ones = 0; cur_ones <= ones_cnt; cur_ones++) {
                for (int tight = 0; tight < 2; tight++) {
                    dp_table[length][cur_ones][tight] = (cur_ones == ones_cnt ? 1L : 0L);
                }
            }
            for (int pos = length - 1; pos >= 0; pos--) {
                for (int cur_ones = 0; cur_ones <= ones_cnt; cur_ones++) {
                    for (int tight = 0; tight < 2; tight++) {
                        int upper = (tight == 1 ? digits[pos] : 1);
                        long total = 0L;
                        for (int d = 0; d <= upper; d++) {
                            int new_ones = cur_ones + d;
                            if (new_ones > ones_cnt) continue;
                            int new_tight = (tight == 1 && d == upper) ? 1 : 0;
                            total += dp_table[pos + 1][new_ones][new_tight];
                        }
                        dp_table[pos][cur_ones][tight] = total;
                    }
                }
            }
            answer += dp_table[0][0][1];
        }
        if (k == 1 && n >= 1) {
            answer--;
        }
        return answer;
    }
}
# @lc code=end