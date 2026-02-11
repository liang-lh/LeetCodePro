#
# @lc app=leetcode id=3548 lang=java
#
# [3548] Equal Sum Grid Partition II
#

# @lc code=start
import java.util.*;

class Solution {
    public boolean canPartitionGrid(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        long S = 0;
        long[] rowSum = new long[m];
        long[] colSum = new long[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                long val = grid[i][j];
                S += val;
                rowSum[i] += val;
                colSum[j] += val;
            }
        }
        long[] preRow = new long[m + 1];
        for (int i = 1; i <= m; i++) {
            preRow[i] = preRow[i - 1] + rowSum[i - 1];
        }
        long[] preCol = new long[n + 1];
        for (int j = 1; j <= n; j++) {
            preCol[j] = preCol[j - 1] + colSum[j - 1];
        }
        Map<Integer, Integer> minR = new HashMap<>();
        Map<Integer, Integer> maxR = new HashMap<>();
        Map<Integer, Integer> minC = new HashMap<>();
        Map<Integer, Integer> maxC = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int val = grid[i][j];
                minR.put(val, Math.min(minR.getOrDefault(val, m), i));
                maxR.put(val, Math.max(maxR.getOrDefault(val, -1), i));
                minC.put(val, Math.min(minC.getOrDefault(val, n), j));
                maxC.put(val, Math.max(maxC.getOrDefault(val, -1), j));
            }
        }
        // Horizontal cuts
        for (int k = 1; k < m; k++) {
            long A = preRow[k];
            long B = S - A;
            if (A == B) return true;
            long dv = Math.abs(A - B);
            if (dv < 1 || dv > 100000) continue;
            int v = (int) dv;
            if (A > B) { // discount top
                int rs = 0, re = k - 1, cs = 0, ce = n - 1;
                int hh = k, ww = n;
                if ((long) hh * ww <= 1) continue;
                boolean ok = false;
                if (hh >= 2 && ww >= 2) {
                    int mr = minR.getOrDefault(v, m);
                    if (mr <= re) ok = true;
                } else if (hh == 1) {
                    if (grid[rs][cs] == v || grid[rs][ce] == v) ok = true;
                } else if (ww == 1) {
                    if (grid[rs][cs] == v || grid[re][cs] == v) ok = true;
                }
                if (ok) return true;
            } else { // discount bottom
                int rs = k, re = m - 1, cs = 0, ce = n - 1;
                int hh = m - k, ww = n;
                if ((long) hh * ww <= 1) continue;
                boolean ok = false;
                if (hh >= 2 && ww >= 2) {
                    int mr = maxR.getOrDefault(v, -1);
                    if (mr >= rs) ok = true;
                } else if (hh == 1) {
                    if (grid[rs][cs] == v || grid[rs][ce] == v) ok = true;
                } else if (ww == 1) {
                    if (grid[rs][cs] == v || grid[re][cs] == v) ok = true;
                }
                if (ok) return true;
            }
        }
        // Vertical cuts
        for (int k = 1; k < n; k++) {
            long A = preCol[k];
            long B = S - A;
            if (A == B) return true;
            long dv = Math.abs(A - B);
            if (dv < 1 || dv > 100000) continue;
            int v = (int) dv;
            if (A > B) { // discount left
                int rs = 0, re = m - 1, cs = 0, ce = k - 1;
                int hh = m, ww = k;
                if ((long) hh * ww <= 1) continue;
                boolean ok = false;
                if (hh >= 2 && ww >= 2) {
                    int mc = minC.getOrDefault(v, n);
                    if (mc <= ce) ok = true;
                } else if (hh == 1) {
                    if (grid[rs][cs] == v || grid[rs][ce] == v) ok = true;
                } else if (ww == 1) {
                    if (grid[rs][cs] == v || grid[re][cs] == v) ok = true;
                }
                if (ok) return true;
            } else { // discount right
                int rs = 0, re = m - 1, cs = k, ce = n - 1;
                int hh = m, ww = n - k;
                if ((long) hh * ww <= 1) continue;
                boolean ok = false;
                if (hh >= 2 && ww >= 2) {
                    int mc = maxC.getOrDefault(v, -1);
                    if (mc >= cs) ok = true;
                } else if (hh == 1) {
                    if (grid[rs][cs] == v || grid[rs][ce] == v) ok = true;
                } else if (ww == 1) {
                    if (grid[rs][cs] == v || grid[re][cs] == v) ok = true;
                }
                if (ok) return true;
            }
        }
        return false;
    }
}
# @lc code=end