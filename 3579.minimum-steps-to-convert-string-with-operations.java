#
# @lc app=leetcode id=3579 lang=java
#
# [3579] Minimum Steps to Convert String with Operations
#

# @lc code=start
class Solution {
  public int minOperations(String word1, String word2) {
    int n = word1.length();
    int[] dp = new int[n + 1];
    for (int k = 1; k <= n; ++k) {
      dp[k] = n + 1;
    }
    dp[0] = 0;
    for (int i = 1; i <= n; ++i) {
      for (int j = 0; j < i; ++j) {
        int len = i - j;
        // no reverse
        int[][] freq1 = new int[26][26];
        int mis1 = 0;
        for (int p = 0; p < len; ++p) {
          int idx = j + p;
          char c = word1.charAt(idx);
          char tar = word2.charAt(j + p);
          int x = c - 'a';
          int y = tar - 'a';
          freq1[x][y]++;
          if (x != y) ++mis1;
        }
        int m1 = 0;
        for (int a = 0; a < 26; ++a) {
          for (int b = a + 1; b < 26; ++b) {
            m1 += Math.min(freq1[a][b], freq1[b][a]);
          }
        }
        int cost1 = mis1 - m1;
        // reverse
        int[][] freq2 = new int[26][26];
        int mis2 = 0;
        for (int p = 0; p < len; ++p) {
          int idx = j + len - 1 - p;
          char c = word1.charAt(idx);
          char tar = word2.charAt(j + p);
          int x = c - 'a';
          int y = tar - 'a';
          freq2[x][y]++;
          if (x != y) ++mis2;
        }
        int m2 = 0;
        for (int a = 0; a < 26; ++a) {
          for (int b = a + 1; b < 26; ++b) {
            m2 += Math.min(freq2[a][b], freq2[b][a]);
          }
        }
        int cost2 = 1 + mis2 - m2;
        int cost = Math.min(cost1, cost2);
        dp[i] = Math.min(dp[i], dp[j] + cost);
      }
    }
    return dp[n];
  }
}
# @lc code=end