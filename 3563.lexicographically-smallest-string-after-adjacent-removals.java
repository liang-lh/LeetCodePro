#
# @lc app=leetcode id=3563 lang=java
#
# [3563] Lexicographically Smallest String After Adjacent Removals
#

# @lc code=start
class Solution {
    public String lexicographicallySmallestString(String s) {
        int n = s.length();
        boolean[][] can = new boolean[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            can[i][i] = true;
        }
        for (int len = 2; len <= n; len += 2) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len;
                for (int p = i + 1; p < j; p += 2) {
                    int x = s.charAt(i) - 'a';
                    int y = s.charAt(p) - 'a';
                    int d = Math.abs(x - y);
                    if ((d == 1 || d == 25) && can[i + 1][p] && can[p + 1][j]) {
                        can[i][j] = true;
                        break;
                    }
                }
            }
        }
        String[] best = new String[n + 1];
        best[n] = "";
        for (int i = n - 1; i >= 0; i--) {
            String cand1 = s.charAt(i) + best[i + 1];
            String min_str = cand1;
            for (int k = i + 2; k <= n; k += 2) {
                if (can[i][k]) {
                    String cand = best[k];
                    if (cand.compareTo(min_str) < 0) {
                        min_str = cand;
                    }
                }
            }
            best[i] = min_str;
        }
        return best[0];
    }
}
# @lc code=end