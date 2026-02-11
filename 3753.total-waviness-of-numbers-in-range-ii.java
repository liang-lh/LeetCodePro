#
# @lc app=leetcode id=3753 lang=java
#
# [3753] Total Waviness of Numbers in Range II
#

# @lc code=start
import java.util.Arrays;

class Solution {
    private static final int MAX_LEN = 18;
    private static final int P_SIZE = 11;
    private long[][][][] countMemo = new long[MAX_LEN + 1][2][P_SIZE][P_SIZE];
    private long[][][][] wavMemo = new long[MAX_LEN + 1][2][P_SIZE][P_SIZE];
    private char[] digits;
    private int nLen;

    public long totalWaviness(long num1, long num2) {
        return calculate(num2) - calculate(num1 - 1);
    }

    private long calculate(long n) {
        if (n < 100) {
            return 0L;
        }
        String s = String.valueOf(n);
        int len = s.length();
        long ans = 0L;
        for (int d = 3; d < len; ++d) {
            StringBuilder sb = new StringBuilder(d);
            for (int i = 0; i < d; ++i) {
                sb.append('9');
            }
            ans += dp(sb.toString());
        }
        ans += dp(s);
        return ans;
    }

    private long dp(String upper) {
        digits = upper.toCharArray();
        nLen = digits.length;
        // reset memos
        for (int p = 0; p <= MAX_LEN; ++p) {
            for (int t = 0; t < 2; ++t) {
                for (int pp = 0; pp < P_SIZE; ++pp) {
                    Arrays.fill(countMemo[p][t][pp], -1L);
                    Arrays.fill(wavMemo[p][t][pp], -1L);
                }
            }
        }
        return dfs(0, 1, -1, -1)[1];
    }

    private long[] dfs(int pos, int tight, int prevPD, int prevD) {
        if (pos == nLen) {
            return new long[]{1L, 0L};
        }
        int pdIdx = prevPD + 1;
        int pIdx = prevD + 1;
        if (countMemo[pos][tight][pdIdx][pIdx] != -1L) {
            return new long[]{countMemo[pos][tight][pdIdx][pIdx], wavMemo[pos][tight][pdIdx][pIdx]};
        }
        int up = tight == 1 ? digits[pos] - '0' : 9;
        int low = pos == 0 ? 1 : 0;
        long totCount = 0L;
        long totWav = 0L;
        for (int cur = low; cur <= up; ++cur) {
            int newTight = (tight == 1 && cur == up) ? 1 : 0;
            int newPrevPD = prevD;
            int newPrevD = cur;
            long contrib = 0L;
            if (pos >= 2 && prevPD >= 0 && prevD >= 0) {
                if ((prevD > prevPD && prevD > cur) || (prevD < prevPD && prevD < cur)) {
                    contrib = 1L;
                }
            }
            long[] sub = dfs(pos + 1, newTight, newPrevPD, newPrevD);
            totCount += sub[0];
            totWav += contrib * sub[0] + sub[1];
        }
        countMemo[pos][tight][pdIdx][pIdx] = totCount;
        wavMemo[pos][tight][pdIdx][pIdx] = totWav;
        return new long[]{totCount, totWav};
    }
}
# @lc code=end