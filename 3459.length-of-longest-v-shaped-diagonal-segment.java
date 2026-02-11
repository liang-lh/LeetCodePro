#
# @lc app=leetcode id=3459 lang=java
#
# [3459] Length of Longest V-Shaped Diagonal Segment
#

# @lc code=start
class Solution {
    public int lenOfVDiagonal(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][][] fwdOdd = new int[n][m][4];
        int[][][] fwdEven = new int[n][m][4];
        int[][] dirs = {{1,1}, {1,-1}, {-1,-1}, {-1,1}};
        // Precompute forward extensions
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int d = 0; d < 4; d++) {
                    // fwdOdd: start exp=2
                    {
                        int cnt = 0;
                        int ci = i, cj = j;
                        int exp = 2;
                        while (true) {
                            int ni = ci + dirs[d][0];
                            int nj = cj + dirs[d][1];
                            if (ni < 0 || ni >= n || nj < 0 || nj >= m || grid[ni][nj] != exp) {
                                break;
                            }
                            cnt++;
                            ci = ni;
                            cj = nj;
                            exp = 2 - exp;
                        }
                        fwdOdd[i][j][d] = cnt;
                    }
                    // fwdEven: start exp=0
                    {
                        int cnt = 0;
                        int ci = i, cj = j;
                        int exp = 0;
                        while (true) {
                            int ni = ci + dirs[d][0];
                            int nj = cj + dirs[d][1];
                            if (ni < 0 || ni >= n || nj < 0 || nj >= m || grid[ni][nj] != exp) {
                                break;
                            }
                            cnt++;
                            ci = ni;
                            cj = nj;
                            exp = 2 - exp;
                        }
                        fwdEven[i][j][d] = cnt;
                    }
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    ans = Math.max(ans, 1);
                    for (int d = 0; d < 4; d++) {
                        ans = Math.max(ans, 1 + fwdOdd[i][j][d]);
                        // Simulate first leg for possible turn
                        int ci = i, cj = j;
                        int len = 1;
                        int nextExp = 2;
                        while (true) {
                            int ni = ci + dirs[d][0];
                            int nj = cj + dirs[d][1];
                            if (ni < 0 || ni >= n || nj < 0 || nj >= m || grid[ni][nj] != nextExp) {
                                break;
                            }
                            ci = ni;
                            cj = nj;
                            len++;
                            int dNext = (d + 1) % 4;
                            int expAfter = (len % 2 == 0) ? 0 : 2;
                            int addLen = expAfter == 2 ? fwdOdd[ci][cj][dNext] : fwdEven[ci][cj][dNext];
                            ans = Math.max(ans, len + addLen);
                            nextExp = 2 - nextExp;
                        }
                    }
                }
            }
        }
        return ans;
    }
}
# @lc code=end