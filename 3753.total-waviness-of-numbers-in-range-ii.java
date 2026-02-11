#
# @lc app=leetcode id=3753 lang=java
#
# [3753] Total Waviness of Numbers in Range II
#

# @lc code=start
class Solution {
    public long totalWaviness(long num1, long num2) {
        return calc(num2) - calc(num1 - 1);
    }

    private long calc(long n) {
        if (n <= 0) return 0L;
        String str = String.valueOf(n);
        int L = str.length();
        int[] digits = new int[L];
        for (int i = 0; i < L; i++) {
            digits[i] = str.charAt(i) - '0';
        }
        long[][][][] cntMemo = new long[L + 1][2][11][11];
        long[][][][] sumMemo = new long[L + 1][2][11][11];
        for (int i = 0; i <= L; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 11; k++) {
                    for (int m = 0; m < 11; m++) {
                        cntMemo[i][j][k][m] = -1L;
                        sumMemo[i][j][k][m] = -1L;
                    }
                }
            }
        }
        long[] res = solve(0, 1, 0, 0, cntMemo, sumMemo, digits, L);
        return res[1];
    }

    private long[] solve(int idx, int tight, int p2, int p1, long[][][][] cntMemo, long[][][][] sumMemo, int[] digits, int L) {
        if (idx == L) {
            return new long[]{p1 != 0 ? 1L : 0L, 0L};
        }
        if (cntMemo[idx][tight][p2][p1] != -1L) {
            return new long[]{cntMemo[idx][tight][p2][p1], sumMemo[idx][tight][p2][p1]};
        }
        long tot_cnt = 0L;
        long tot_sum = 0L;
        int up = tight == 1 ? digits[idx] : 9;
        for (int d = 0; d <= up; d++) {
            int ntight = (tight == 1 && d == up) ? 1 : 0;
            int np2, np1;
            if (p1 == 0 && d == 0) {
                np2 = 0;
                np1 = 0;
            } else {
                np1 = d + 1;
                np2 = p1;
            }
            long[] res = solve(idx + 1, ntight, np2, np1, cntMemo, sumMemo, digits, L);
            long this_cnt = res[0];
            long this_sumw = res[1];
            long contrib = 0L;
            if (p1 != 0 && p2 != 0) {
                int leftv = p2 - 1;
                int centerv = p1 - 1;
                int rightv = d;
                if ((centerv > leftv && centerv > rightv) || (centerv < leftv && centerv < rightv)) {
                    contrib = this_cnt;
                }
            }
            tot_cnt += this_cnt;
            tot_sum += this_sumw + contrib;
        }
        cntMemo[idx][tight][p2][p1] = tot_cnt;
        sumMemo[idx][tight][p2][p1] = tot_sum;
        return new long[]{tot_cnt, tot_sum};
    }
}
# @lc code=end