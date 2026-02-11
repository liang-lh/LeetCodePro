#
# @lc app=leetcode id=3548 lang=java
#
# [3548] Equal Sum Grid Partition II
#

# @lc code=start
class Solution {
    public boolean canPartitionGrid(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        long total = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                total += grid[i][j];
            }
        }
        long[] prefixRow = new long[m + 1];
        long[] rowSum = new long[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rowSum[i] += grid[i][j];
            }
            prefixRow[i + 1] = prefixRow[i] + rowSum[i];
        }
        long[] prefixCol = new long[n + 1];
        long[] colSum = new long[n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                colSum[j] += grid[i][j];
            }
            prefixCol[j + 1] = prefixCol[j] + colSum[j];
        }
        final int MAXV = 100000;
        int[] minR = new int[MAXV + 1];
        int[] maxR = new int[MAXV + 1];
        int[] minC = new int[MAXV + 1];
        int[] maxC = new int[MAXV + 1];
        for (int v = 0; v <= MAXV; v++) {
            minR[v] = m + 1;
            maxR[v] = -1;
            minC[v] = n + 1;
            maxC[v] = -1;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int v = grid[i][j];
                minR[v] = Math.min(minR[v], i);
                maxR[v] = Math.max(maxR[v], i);
                minC[v] = Math.min(minC[v], j);
                maxC[v] = Math.max(maxC[v], j);
            }
        }
        // Horizontal cuts
        for (int k = 1; k < m; k++) {
            long sum1 = prefixRow[k];
            long sum2 = total - sum1;
            if (sum1 == sum2) return true;
            // Discount from top
            if (sum1 > sum2) {
                long dif = sum1 - sum2;
                if (dif > MAXV) continue;
                int d = (int) dif;
                int rr = k, cc = n;
                boolean ok = false;
                if (rr >= 2 && cc >= 2) {
                    if (minR[d] < k) ok = true;
                } else if (rr == 1 && cc >= 2) {
                    if (grid[0][0] == d || grid[0][n - 1] == d) ok = true;
                } else if (cc == 1 && rr >= 2) {
                    if (grid[0][0] == d || grid[k - 1][0] == d) ok = true;
                }
                if (ok) return true;
            }
            // Discount from bot
            if (sum2 > sum1) {
                long dif = sum2 - sum1;
                if (dif > MAXV) continue;
                int d = (int) dif;
                int rr = m - k, cc = n;
                boolean ok = false;
                if (rr >= 2 && cc >= 2) {
                    if (maxR[d] >= k) ok = true;
                } else if (rr == 1 && cc >= 2) {
                    if (grid[k][0] == d || grid[k][n - 1] == d) ok = true;
                } else if (cc == 1 && rr >= 2) {
                    if (grid[k][0] == d || grid[m - 1][0] == d) ok = true;
                }
                if (ok) return true;
            }
        }
        // Vertical cuts
        for (int k = 1; k < n; k++) {
            long sum1 = prefixCol[k];
            long sum2 = total - sum1;
            if (sum1 == sum2) return true;
            // Discount from left
            if (sum1 > sum2) {
                long dif = sum1 - sum2;
                if (dif > MAXV) continue;
                int d = (int) dif;
                int rr = m, cc = k;
                boolean ok = false;
                if (rr >= 2 && cc >= 2) {
                    if (minC[d] < k) ok = true;
                } else if (rr == 1 && cc >= 2) {
                    if (grid[0][0] == d || grid[0][k - 1] == d) ok = true;
                } else if (cc == 1 && rr >= 2) {
                    if (grid[0][0] == d || grid[m - 1][0] == d) ok = true;
                }
                if (ok) return true;
            }
            // Discount from right
            if (sum2 > sum1) {
                long dif = sum2 - sum1;
                if (dif > MAXV) continue;
                int d = (int) dif;
                int rr = m, cc = n - k;
                boolean ok = false;
                if (rr >= 2 && cc >= 2) {
                    if (maxC[d] >= k) ok = true;
                } else if (rr == 1 && cc >= 2) {
                    if (grid[0][k] == d || grid[0][n - 1] == d) ok = true;
                } else if (cc == 1 && rr >= 2) {
                    if (grid[0][k] == d || grid[m - 1][k] == d) ok = true;
                }
                if (ok) return true;
            }
        }
        return false;
    }
}
# @lc code=end