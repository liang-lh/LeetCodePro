#
# @lc app=leetcode id=3768 lang=java
#
# [3768] Minimum Inversion Count in Subarrays of Fixed Length
#

# @lc code=start
class Solution {
  public long minInversionCount(int[] nums, int k) {
    int n = nums.length;
    if (k == 0 || n == 0) return 0;
    int[] sortedVals = new int[n];
    for (int i = 0; i < n; i++) {
      sortedVals[i] = nums[i];
    }
    Arrays.sort(sortedVals);
    int maxr = 0;
    if (n > 0) maxr = 1;
    for (int i = 1; i < n; i++) {
      if (sortedVals[i] != sortedVals[i - 1]) {
        maxr++;
      }
    }
    int[] uniqueVals = new int[maxr];
    if (maxr > 0) {
      int ptr = 0;
      uniqueVals[ptr++] = sortedVals[0];
      for (int i = 1; i < n; i++) {
        if (sortedVals[i] != sortedVals[i - 1]) {
          uniqueVals[ptr++] = sortedVals[i];
        }
      }
    }
    int[] rank = new int[n];
    for (int i = 0; i < n; i++) {
      rank[i] = Arrays.binarySearch(uniqueVals, 0, maxr, nums[i]) + 1;
    }
    class Fenwick {
      long[] tree;
      int nn;
      public Fenwick(int _n) {
        nn = _n;
        tree = new long[_n + 2];
      }
      public void update(int idx, long val) {
        while (idx <= nn) {
          tree[idx] += val;
          idx += idx & -idx;
        }
      }
      public long query(int idx) {
        long s = 0;
        while (idx > 0) {
          s += tree[idx];
          idx -= idx & -idx;
        }
        return s;
      }
      public long query(int l, int r) {
        if (l > r) return 0;
        return query(r) - query(l - 1);
      }
    }
    Fenwick ft = new Fenwick(maxr);
    long inv = 0;
    for (int i = 0; i < k; i++) {
      int r = rank[i];
      inv += ft.query(r + 1, maxr);
      ft.update(r, 1);
    }
    long ans = inv;
    for (int st = 1; st <= n - k; st++) {
      int rm = rank[st - 1];
      long sub = ft.query(1, rm - 1);
      inv -= sub;
      ft.update(rm, -1);
      int adidx = st + k - 1;
      int ad = rank[adidx];
      long adinv = ft.query(ad + 1, maxr);
      inv += adinv;
      ft.update(ad, 1);
      if (inv < ans) ans = inv;
    }
    return ans;
  }
}
# @lc code=end