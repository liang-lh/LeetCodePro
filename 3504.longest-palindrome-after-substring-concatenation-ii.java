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
        
        // Palindromes in s
        boolean[][] palS = new boolean[ns][ns];
        int maxS = 1;
        for (int i = 0; i < ns; i++) {
            palS[i][i] = true;
        }
        for (int i = 0; i < ns - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                palS[i][i + 1] = true;
                maxS = 2;
            }
        }
        for (int len = 3; len <= ns; len++) {
            for (int i = 0; i <= ns - len; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && palS[i + 1][j - 1]) {
                    palS[i][j] = true;
                    maxS = Math.max(maxS, len);
                }
            }
        }
        
        // Palindromes in t
        boolean[][] palT = new boolean[nt][nt];
        int maxT = 1;
        for (int i = 0; i < nt; i++) {
            palT[i][i] = true;
        }
        for (int i = 0; i < nt - 1; i++) {
            if (t.charAt(i) == t.charAt(i + 1)) {
                palT[i][i + 1] = true;
                maxT = 2;
            }
        }
        for (int len = 3; len <= nt; len++) {
            for (int i = 0; i <= nt - len; i++) {
                int j = i + len - 1;
                if (t.charAt(i) == t.charAt(j) && palT[i + 1][j - 1]) {
                    palT[i][j] = true;
                    maxT = Math.max(maxT, len);
                }
            }
        }
        
        int ans = Math.max(maxS, maxT);
        
        // Equal arms: 2 * LCS(s, reverse(t))
        String rt = new StringBuilder(t).reverse().toString();
        int lcsEq = lcsSubstr(s, rt);
        ans = Math.max(ans, 2 * lcsEq);
        
        // Centers in s
        int[][] dpSrt = lcsDp(s, rt);
        int[] maxKendS = new int[ns];
        for (int p = 0; p < ns; p++) {
            int mx = 0;
            for (int q = 1; q <= nt; q++) {
                mx = Math.max(mx, dpSrt[p + 1][q]);
            }
            maxKendS[p] = mx;
        }
        for (int l = 0; l < ns; l++) {
            for (int r = l; r < ns; r++) {
                if (palS[l][r]) {
                    int d = r - l + 1;
                    int k = (l == 0 ? 0 : maxKendS[l - 1]);
                    ans = Math.max(ans, 2 * k + d);
                }
            }
        }
        
        // Centers in t
        String rs = new StringBuilder(s).reverse().toString();
        int[][] dpTrs = lcsDp(t, rs);
        int[] maxKendT = new int[nt];
        for (int p = 0; p < nt; p++) {
            int mx = 0;
            for (int q = 1; q <= ns; q++) {
                mx = Math.max(mx, dpTrs[p + 1][q]);
            }
            maxKendT[p] = mx;
        }
        int[] maxKstartT = new int[nt];
        for (int st = 0; st < nt; st++) {
            int curmax = 0;
            int rem = nt - st;
            for (int k = 1; k <= rem; k++) {
                int ep = st + k - 1;
                if (maxKendT[ep] >= k) {
                    curmax = k;
                }
            }
            maxKstartT[st] = curmax;
        }
        for (int l = 0; l < nt; l++) {
            for (int r = l; r < nt; r++) {
                if (palT[l][r]) {
                    int d = r - l + 1;
                    int k = 0;
                    if (r + 1 < nt) {
                        k = maxKstartT[r + 1];
                    }
                    ans = Math.max(ans, 2 * k + d);
                }
            }
        }
        
        return ans;
    }
    
    private int lcsSubstr(String a, String b) {
        int la = a.length();
        int lb = b.length();
        int[][] dp = new int[la + 1][lb + 1];
        int mx = 0;
        for (int i = 1; i <= la; i++) {
            for (int j = 1; j <= lb; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    mx = Math.max(mx, dp[i][j]);
                }
            }
        }
        return mx;
    }
    
    private int[][] lcsDp(String a, String b) {
        int la = a.length();
        int lb = b.length();
        int[][] dp = new int[la + 1][lb + 1];
        for (int i = 1; i <= la; i++) {
            for (int j = 1; j <= lb; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }
        return dp;
    }
}
# @lc code=end