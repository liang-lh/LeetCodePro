#
# @lc app=leetcode id=3579 lang=java
#
# [3579] Minimum Steps to Convert String with Operations
#

# @lc code=start
class Solution {
    public int minOperations(String word1, String word2) {
        int n = word1.length();
        int INF = 201;
        int[] dp = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            dp[i] = INF;
        }
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                int c = getCost(word1, word2, j, i - 1);
                if (dp[j] < INF) {
                    dp[i] = Math.min(dp[i], dp[j] + c);
                }
            }
        }
        return dp[n];
    }

    private int getCost(String s, String t, int l, int r) {
        int m = r - l + 1;
        if (m == 0) return 0;
        // no reverse, swaps
        int[][] cnt = new int[26][26];
        int f = 0;
        for (int p = l; p <= r; p++) {
            int x = s.charAt(p) - 'a';
            int y = t.charAt(p) - 'a';
            cnt[x][y]++;
            if (x == y) f++;
        }
        int k = 0;
        for (int x = 0; x < 26; x++) {
            for (int y = x + 1; y < 26; y++) {
                k += Math.min(cnt[x][y], cnt[y][x]);
            }
        }
        int ops1 = m - f - k;
        // rev + swaps after (s_rev vs t)
        int[][] cnt_rev = new int[26][26];
        int f_rev = 0;
        for (int i = 0; i < m; i++) {
            int sp = l + (m - 1 - i);
            int tp = l + i;
            int x = s.charAt(sp) - 'a';
            int y = t.charAt(tp) - 'a';
            cnt_rev[x][y]++;
            if (x == y) f_rev++;
        }
        int k_rev = 0;
        for (int x = 0; x < 26; x++) {
            for (int y = x + 1; y < 26; y++) {
                k_rev += Math.min(cnt_rev[x][y], cnt_rev[y][x]);
            }
        }
        int ops3 = m - f_rev - k_rev + 1;
        // rev + swaps before (s vs t_rev)
        int[][] cnt_pre = new int[26][26];
        int f_pre = 0;
        for (int i = 0; i < m; i++) {
            int sp = l + i;
            int tp = l + (m - 1 - i);
            int x = s.charAt(sp) - 'a';
            int y = t.charAt(tp) - 'a';
            cnt_pre[x][y]++;
            if (x == y) f_pre++;
        }
        int k_pre = 0;
        for (int x = 0; x < 26; x++) {
            for (int y = x + 1; y < 26; y++) {
                k_pre += Math.min(cnt_pre[x][y], cnt_pre[y][x]);
            }
        }
        int ops4 = m - f_pre - k_pre + 1;
        int ans = ops1;
        ans = Math.min(ans, ops3);
        ans = Math.min(ans, ops4);
        return ans;
    }
}
# @lc code=end