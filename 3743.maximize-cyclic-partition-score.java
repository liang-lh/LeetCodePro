#
# @lc app=leetcode id=3743 lang=java
#
# [3743] Maximize Cyclic Partition Score
#
# @lc code=start
class Solution {
  public long maximumScore(int[] nums, int k) {
    int n = nums.length;
    int N = 2 * n;
    int[] a = new int[N];
    for (int i = 0; i < n; ++i) {
      a[i] = a[i + n] = nums[i];
    }
    long[][] rng = new long[N][N];
    for (int i = 0; i < N; ++i) {
      long mx = a[i];
      long mn = a[i];
      rng[i][i] = 0;
      for (int j = i + 1; j < N; ++j) {
        mx = Math.max(mx, a[j]);
        mn = Math.min(mn, a[j]);
        rng[i][j] = mx - mn;
      }
    }
    long ans = 0;
    long INF = Long.MIN_VALUE / 2;
    for (int s = 0; s < n; ++s) {
      long[] dps = new long[n + 1];
      long[] dpt = new long[n + 1];
      long[] prev = dps;
      Arrays.fill(prev, INF);
      prev[0] = 0;
      for (int len = 1; len <= n; ++len) {
        prev[len] = rng[s][s + len - 1];
      }
      long localMax = prev[n];
      long[] target;
      int maxParts = Math.min(k, n);
      for (int parts = 2; parts <= maxParts; ++parts) {
        target = (prev == dps ? dpt : dps);
        Arrays.fill(target, INF);
        knuth(parts, n, parts - 1, n, prev, target, s, rng, n);
        prev = target;
        if (prev[n] > localMax) {
          localMax = prev[n];
        }
      }
      if (localMax > ans) {
        ans = localMax;
      }
    }
    return ans;
  }
  private void knuth(int L, int R, int optleft, int optright, long[] prev, long[] curr, int s, long[][] rng, int nn) {
    if (L > R) return;
    int mid = (L + R) / 2;
    long best = Long.MIN_VALUE / 2;
    int bestk = optleft;
    int upper = Math.min(mid - 1, optright);
    for (int k = optleft; k <= upper; ++k) {
      if (prev[k] == Long.MIN_VALUE / 2) continue;
      long cost = rng[s + k][s + mid - 1];
      long val = prev[k] + cost;
      if (val > best) {
        best = val;
        bestk = k;
      }
    }
    curr[mid] = best;
    knuth(L, mid - 1, optleft, bestk, prev, curr, s, rng, nn);
    knuth(mid + 1, R, bestk, optright, prev, curr, s, rng, nn);
  }
}
# @lc code=end