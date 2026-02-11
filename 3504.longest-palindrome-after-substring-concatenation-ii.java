#
# @lc app=leetcode id=3504 lang=java
#
# [3504] Longest Palindrome After Substring Concatenation II
#

# @lc code=start
class Solution {
    public int longestPalindrome(String s, String t) {
        int ns = s.length();
        int nt = t.length();
        boolean[][] palS = computePal(s, ns);
        int[] maxStartS = computeMaxStart(palS, ns);
        int lpsS = 0;
        for (int i = 0; i < ns; i++) {
            lpsS = Math.max(lpsS, maxStartS[i]);
        }
        boolean[][] palT = computePal(t, nt);
        int[] maxEndT = computeMaxEnd(palT, nt);
        int lpsT = 0;
        for (int j = 0; j < nt; j++) {
            lpsT = Math.max(lpsT, maxEndT[j]);
        }
        int ans = Math.max(lpsS, lpsT);
        String rt = new StringBuilder(t).reverse().toString();
        RollingHash hs = new RollingHash(s);
        RollingHash hrt = new RollingHash(rt);
        for (int sx = 0; sx < ns; sx++) {
            for (int cy = 0; cy < nt; cy++) {
                int left = 0;
                int right = Math.min(ns - sx, nt - cy);
                while (left <= right) {
                    int mid = left + (right - left + 1) / 2;
                    int rtst = nt - cy - mid;
                    if (rtst >= 0 && hs.same(sx, sx + mid - 1, hrt, rtst, rtst + mid - 1)) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
                int k = right;
                if (k >= 1) {
                    // s extra
                    int temp = 2 * k;
                    if (sx + k < ns) {
                        temp += maxStartS[sx + k];
                    }
                    ans = Math.max(ans, temp);
                    // t extra
                    temp = 2 * k;
                    if (cy > 0) {
                        temp += maxEndT[cy - 1];
                    }
                    ans = Math.max(ans, temp);
                }
            }
        }
        return ans;
    }
    private boolean[][] computePal(String str, int n) {
        boolean[][] pal = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            pal[i][i] = true;
        }
        for (int i = 0; i < n - 1; i++) {
            if (str.charAt(i) == str.charAt(i + 1)) {
                pal[i][i + 1] = true;
            }
        }
        for (int leng = 3; leng <= n; leng++) {
            for (int i = 0; i <= n - leng; i++) {
                int j = i + leng - 1;
                if (str.charAt(i) == str.charAt(j) && pal[i + 1][j - 1]) {
                    pal[i][j] = true;
                }
            }
        }
        return pal;
    }
    private int[] computeMaxStart(boolean[][] pal, int n) {
        int[] maxSt = new int[n];
        for (int i = 0; i < n; i++) {
            int mx = 1;
            for (int j = i; j < n; j++) {
                if (pal[i][j]) {
                    mx = j - i + 1;
                }
            }
            maxSt[i] = mx;
        }
        return maxSt;
    }
    private int[] computeMaxEnd(boolean[][] pal, int n) {
        int[] maxEn = new int[n];
        for (int j = 0; j < n; j++) {
            int mx = 1;
            for (int i = j; i >= 0; i--) {
                if (pal[i][j]) {
                    mx = j - i + 1;
                }
            }
            maxEn[j] = mx;
        }
        return maxEn;
    }
    private static class RollingHash {
        private long[] h1, p1, h2, p2;
        private final long MOD1 = 1000000007L;
        private final long MOD2 = 1000000009L;
        private final long BASE1 = 131L;
        private final long BASE2 = 137L;
        private final int n;
        public RollingHash(String s) {
            n = s.length();
            h1 = new long[n + 1];
            p1 = new long[n + 1];
            h2 = new long[n + 1];
            p2 = new long[n + 1];
            p1[0] = 1;
            p2[0] = 1;
            for (int i = 0; i < n; i++) {
                long val = s.charAt(i) - 'a' + 1;
                h1[i + 1] = (h1[i] * BASE1 + val) % MOD1;
                p1[i + 1] = p1[i] * BASE1 % MOD1;
                h2[i + 1] = (h2[i] * BASE2 + val) % MOD2;
                p2[i + 1] = p2[i] * BASE2 % MOD2;
            }
        }
        public long getHash1(int l, int r) {
            long res = (h1[r + 1] - h1[l] * p1[r - l + 1] % MOD1 + MOD1) % MOD1;
            return res;
        }
        public long getHash2(int l, int r) {
            long res = (h2[r + 1] - h2[l] * p2[r - l + 1] % MOD2 + MOD2) % MOD2;
            return res;
        }
        public boolean same(int l1, int r1, RollingHash other, int l2, int r2) {
            return getHash1(l1, r1) == other.getHash1(l2, r2) && getHash2(l1, r1) == other.getHash2(l2, r2);
        }
    }
}
# @lc code=end