#
# @lc app=leetcode id=3743 lang=java
#
# [3743] Maximize Cyclic Partition Score
#

# @lc code=start
class Solution {
    private static final int LOG = 11;

    @FunctionalInterface
    interface GetRangeFunc {
        long get(int l, int r);
    }

    private long getRange(int l, int r, long[][] stMax, long[][] stMin, int[] logg) {
        if (l >= r) return 0;
        int len = r - l;
        int lv = logg[len];
        int pw = 1 << lv;
        long mx = Math.max(stMax[lv][l], stMax[lv][r - pw]);
        long mn = Math.min(stMin[lv][l], stMin[lv][r - pw]);
        return mx - mn;
    }

    private void dcOpt(int L, int R, int optL, int optR, long[] prev, long[] curr, int s, GetRangeFunc gr) {
        if (L > R) return;
        int m = (L + R) / 2;
        long bestVal = Long.MIN_VALUE / 2;
        int bestK = -1;
        int startK = Math.max(optL, L - 1);
        int endK = Math.min(m - 1, optR);
        for (int kk = startK; kk <= endK; kk++) {
            long v = prev[kk] + gr.get(s + kk, s + m);
            if (v > bestVal) {
                bestVal = v;
                bestK = kk;
            }
        }
        curr[m] = bestVal;
        dcOpt(L, m - 1, optL, bestK, prev, curr, s, gr);
        dcOpt(m + 1, R, bestK, optR, prev, curr, s, gr);
    }

    public long maximumScore(int[] nums, int k) {
        int n = nums.length;
        int N = 2 * n;
        long[] A = new long[N];
        for (int i = 0; i < n; i++) {
            A[i] = nums[i];
            A[n + i] = nums[i];
        }
        long[][] stMax = new long[LOG][N];
        long[][] stMin = new long[LOG][N];
        for (int i = 0; i < N; i++) {
            stMax[0][i] = stMin[0][i] = A[i];
        }
        for (int lv = 1; lv < LOG; lv++) {
            for (int i = 0; i + (1 << lv) <= N; i++) {
                stMax[lv][i] = Math.max(stMax[lv - 1][i], stMax[lv - 1][i + (1 << (lv - 1))]);
                stMin[lv][i] = Math.min(stMin[lv - 1][i], stMin[lv - 1][i + (1 << (lv - 1))]);
            }
        }
        int[] logg = new int[N + 1];
        logg[1] = 0;
        for (int i = 2; i <= N; i++) {
            logg[i] = logg[i / 2] + 1;
        }
        long ans = 0;
        for (int s = 0; s < n; s++) {
            long[][] dp = new long[k + 1][n + 1];
            for (int len = 1; len <= n; len++) {
                dp[1][len] = getRange(s, s + len, stMax, stMin, logg);
            }
            ans = Math.max(ans, dp[1][n]);
            for (int p = 2; p <= k; p++) {
                GetRangeFunc gr = (ll, rr) -> getRange(ll, rr, stMax, stMin, logg);
                dcOpt(p, n, p - 1, n - 1, dp[p - 1], dp[p], s, gr);
                ans = Math.max(ans, dp[p][n]);
            }
        }
        return ans;
    }
}
# @lc code=end