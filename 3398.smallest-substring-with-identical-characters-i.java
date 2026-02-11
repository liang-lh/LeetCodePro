#
# @lc app=leetcode id=3398 lang=java
#
# [3398] Smallest Substring With Identical Characters I
#

# @lc code=start
class Solution {
    public int minLength(String s, int numOps) {
        int n = s.length();
        int left = 1, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canAchieve(mid, s, numOps)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean canAchieve(int K, String s, int numOps) {
        int n = s.length();
        int INF = n + 1;
        int[][][] dp = new int[n + 1][K + 1][2];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j][0] = INF;
                dp[i][j][1] = INF;
            }
        }
        int orig = s.charAt(0) - '0';
        dp[1][1][0] = (orig == 0 ? 0 : 1);
        dp[1][1][1] = (orig == 1 ? 0 : 1);
        for (int i = 1; i < n; i++) {
            orig = s.charAt(i) - '0';
            for (int prevJ = 1; prevJ <= K; prevJ++) {
                for (int prevC = 0; prevC < 2; prevC++) {
                    int prevCost = dp[i][prevJ][prevC];
                    if (prevCost > n) continue;
                    for (int newC = 0; newC < 2; newC++) {
                        int cost = (newC == orig ? 0 : 1);
                        int newJ = (newC == prevC ? prevJ + 1 : 1);
                        if (newJ > K) continue;
                        dp[i + 1][newJ][newC] = Math.min(dp[i + 1][newJ][newC], prevCost + cost);
                    }
                }
            }
        }
        int minFlips = INF;
        for (int j = 1; j <= K; j++) {
            minFlips = Math.min(minFlips, Math.min(dp[n][j][0], dp[n][j][1]));
        }
        return minFlips <= numOps;
    }
}
# @lc code=end