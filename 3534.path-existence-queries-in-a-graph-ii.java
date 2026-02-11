#
# @lc app=leetcode id=3534 lang=java
#
# [3534] Path Existence Queries in a Graph II
#

# @lc code=start
class Solution {
  public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
    int m = queries.length;
    int[] answer = new int[m];

    // Compute sorted order by nums
    int[] order = new int[n];
    for (int i = 0; i < n; ++i) order[i] = i;
    java.util.Arrays.sort(order, (int x, int y) -> Integer.compare(nums[x], nums[y]));

    int[] pos = new int[n];
    for (int i = 0; i < n; ++i) {
      pos[order[i]] = i;
    }

    // Compute R[i]: farthest right directly reachable
    int[] R = new int[n];
    int jj = 0;
    for (int i = 0; i < n; ++i) {
      while (jj < n && (long) nums[order[jj]] <= (long) nums[order[i]] + maxDiff) {
        ++jj;
      }
      R[i] = jj - 1;
    }

    // Binary lifting for farthest reach with 2^k jumps
    final int LOG = 18;
    int[][] far = new int[LOG][n];
    for (int i = 0; i < n; ++i) {
      far[0][i] = R[i];
    }
    for (int k = 1; k < LOG; ++k) {
      for (int i = 0; i < n; ++i) {
        int mid = far[k - 1][i];
        far[k][i] = (mid < n ? far[k - 1][mid] : n - 1);
      }
    }

    // Compute components
    int[] comp = new int[n];
    if (n > 0) {
      comp[order[0]] = 0;
      int cid = 0;
      for (int i = 1; i < n; ++i) {
        if ((long) nums[order[i]] - nums[order[i - 1]] > maxDiff) {
          ++cid;
        }
        comp[order[i]] = cid;
      }
    }

    // Process each query
    for (int qi = 0; qi < m; ++qi) {
      int u = queries[qi][0];
      int v = queries[qi][1];
      if (u == v) {
        answer[qi] = 0;
        continue;
      }
      if (comp[u] != comp[v]) {
        answer[qi] = -1;
        continue;
      }
      int pa = pos[u];
      int pb = pos[v];
      if (pa > pb) {
        int tmp = pa;
        pa = pb;
        pb = tmp;
      }
      // Binary search minimal d
      int left = 1;
      int right = pb - pa;
      int minD = right;
      while (left <= right) {
        int md = left + (right - left) / 2;
        int curr = pa;
        for (int k = LOG - 1; k >= 0; --k) {
          if ((md & (1 << k)) != 0) {
            curr = far[k][curr];
          }
        }
        if (curr >= pb) {
          minD = md;
          right = md - 1;
        } else {
          left = md + 1;
        }
      }
      answer[qi] = minD;
    }
    return answer;
  }
}
# @lc code=end