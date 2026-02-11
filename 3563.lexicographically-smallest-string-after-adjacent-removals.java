#
# @lc app=leetcode id=3563 lang=java
#
# [3563] Lexicographically Smallest String After Adjacent Removals
#

# @lc code=start
class Solution {
    public String lexicographicallySmallestString(String s) {
        int n = s.length();
        boolean[][] can = new boolean[n+1][n+1];
        for (int i = 0; i <= n; i++) {
            can[i][i] = true;
        }
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len;
                boolean possible = false;
                for (int k = i + 1; k < j; k++) {
                    if (can[i][k] && can[k][j]) {
                        possible = true;
                        break;
                    }
                }
                if (len >= 2) {
                    char left = s.charAt(i);
                    char right = s.charAt(j - 1);
                    int dx = Math.abs((left - 'a') - (right - 'a'));
                    boolean consec = (dx == 1) || (left == 'a' && right == 'z') || (left == 'z' && right == 'a');
                    if (consec && can[i + 1][j - 1]) {
                        possible = true;
                    }
                }
                can[i][j] = possible;
            }
        }
        String[] best = new String[n + 1];
        best[n] = "";
        for (int i = n - 1; i >= 0; i--) {
            String minStr = s.substring(i);
            if (can[i][n]) {
                minStr = "";
            }
            for (int k = i; k < n; k++) {
                if (can[i][k]) {
                    String cand = s.charAt(k) + best[k + 1];
                    if (cand.compareTo(minStr) < 0) {
                        minStr = cand;
                    }
                }
            }
            best[i] = minStr;
        }
        return best[0];
    }
}
# @lc code=end