#
# @lc app=leetcode id=3441 lang=java
#
# [3441] Minimum Cost Good Caption
#

# @lc code=start
class Solution {
  public String minCostGoodCaption(String caption) {
    int n = caption.length();
    if (n < 3) return "";
    final int INF = 2000000000;
    int[][][] dp = new int[n + 1][26][3];
    int[][][] bwd = new int[n + 1][26][3];
    int[] vals = new int[n];
    for (int i = 0; i < n; i++) {
      vals[i] = caption.charAt(i) - 'a';
    }
    // init to INF
    for (int i = 0; i <= n; i++) {
      for (int j = 0; j < 26; j++) {
        for (int k = 0; k < 3; k++) {
          dp[i][j][k] = INF;
          bwd[i][j][k] = INF;
        }
      }
    }
    // forward DP
    for (int c = 0; c < 26; c++) {
      dp[1][c][0] = Math.abs(vals[0] - c);
    }
    for (int i = 1; i < n; i++) {
      for (int p = 0; p < 26; p++) {
        for (int pk = 0; pk < 3; pk++) {
          if (dp[i][p][pk] >= INF) continue;
          // continue
          int add = Math.abs(vals[i] - p);
          int nk = (pk == 0 ? 1 : 2);
          dp[i + 1][p][nk] = Math.min(dp[i + 1][p][nk], dp[i][p][pk] + add);
          // new run
          if (pk == 2) {
            for (int nc = 0; nc < 26; nc++) {
              int nadd = Math.abs(vals[i] - nc);
              dp[i + 1][nc][0] = Math.min(dp[i + 1][nc][0], dp[i][p][pk] + nadd);
            }
          }
        }
      }
    }
    int minCost = INF;
    for (int c = 0; c < 26; c++) {
      minCost = Math.min(minCost, dp[n][c][2]);
    }
    if (minCost >= INF) return "";
    // backward DP
    for (int c = 0; c < 26; c++) {
      bwd[n][c][2] = 0;
    }
    for (int i = n - 1; i >= 0; i--) {
      for (int p = 0; p < 26; p++) {
        for (int pk = 0; pk < 3; pk++) {
          // continue
          int nkk = (pk == 0 ? 1 : 2);
          int costCont = INF;
          if (bwd[i + 1][p][nkk] < INF) {
            costCont = Math.abs(vals[i] - p) + bwd[i + 1][p][nkk];
          }
          // new run
          int costNew = INF;
          if (pk == 2) {
            for (int nc = 0; nc < 26; nc++) {
              if (bwd[i + 1][nc][0] < INF) {
                costNew = Math.min(costNew, Math.abs(vals[i] - nc) + bwd[i + 1][nc][0]);
              }
            }
          }
          bwd[i][p][pk] = Math.min(costCont, costNew);
        }
      }
    }
    // reconstruct
    StringBuilder sb = new StringBuilder();
    int curCost = 0;
    int curC = 0;
    int curPk = 0;
    int curPos = 0;
    // position 0
    for (int cc = 0; cc < 26; cc++) {
      int add = Math.abs(vals[0] - cc);
      if (bwd[1][cc][0] < INF && add + bwd[1][cc][0] == minCost) {
        sb.append((char) ('a' + cc));
        curCost = add;
        curC = cc;
        curPk = 0;
        curPos = 1;
        break;
      }
    }
    // rest
    for (int pos = 1; pos < n; pos++) {
      for (int cc = 0; cc < 26; cc++) {
        int add = Math.abs(vals[pos] - cc);
        int nextC = -1;
        int nextPk = -1;
        boolean canContinue = (cc == curC);
        boolean canNew = (curPk == 2 && cc != curC);
        if (canContinue) {
          nextC = curC;
          nextPk = (curPk == 0 ? 1 : 2);
        } else if (canNew) {
          nextC = cc;
          nextPk = 0;
        }
        if (nextC != -1 && bwd[pos + 1][nextC][nextPk] < INF) {
          int projected = curCost + add + bwd[pos + 1][nextC][nextPk];
          if (projected == minCost) {
            sb.append((char) ('a' + cc));
            curCost += add;
            curC = nextC;
            curPk = nextPk;
            break;
          }
        }
      }
    }
    return sb.toString();
  }
}
# @lc code=end