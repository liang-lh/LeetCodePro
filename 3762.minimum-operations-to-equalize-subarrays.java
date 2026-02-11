#
# @lc app=leetcode id=3762 lang=java
#
# [3762] Minimum Operations to Equalize Subarrays
#

# @lc code=start
class Solution {
    static class FenwickTree {
        long[] tree;
        int n;
        FenwickTree(int sz) {
            n = sz + 2;
            tree = new long[n + 1];
        }
        void update(int idx, long val) {
            while (idx <= n) {
                tree[idx] += val;
                idx += idx & -idx;
            }
        }
        long query(int idx) {
            long sum = 0;
            while (idx > 0) {
                sum += tree[idx];
                idx -= idx & -idx;
            }
            return sum;
        }
    }

    static class Query {
        int L, R, index;
        Query(int l, int r, int id) {
            L = l;
            R = r;
            index = id;
        }
    }

    public long[] minOperations(int[] nums, int k, int[][] queries) {
        int n = nums.length;
        int q = queries.length;
        int[] mods = new int[n];
        int[] quot = new int[n];
        for (int i = 0; i < n; i++) {
            mods[i] = nums[i] % k;
            quot[i] = nums[i] / k;
        }
        if (q == 0) return new long[0];

        // Sparse Table for RMQ min/max mods
        final int LOG = 16;
        int[][] stmin = new int[LOG][n];
        int[][] stmax = new int[LOG][n];
        for (int i = 0; i < n; i++) {
            stmin[0][i] = stmax[0][i] = mods[i];
        }
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i + (1 << j) <= n; i++) {
                stmin[j][i] = Math.min(stmin[j - 1][i], stmin[j - 1][i + (1 << (j - 1))]);
                stmax[j][i] = Math.max(stmax[j - 1][i], stmax[j - 1][i + (1 << (j - 1))]);
            }
        }
        int[] lg = new int[n + 1];
        for (int i = 2; i <= n; i++) lg[i] = lg[i / 2] + 1;

        // Compress quotients
        java.util.HashSet<Integer> uset = new java.util.HashSet<>();
        for (int v : quot) uset.add(v);
        java.util.ArrayList<Integer> suniq = new java.util.ArrayList<>(uset);
        java.util.Collections.sort(suniq);
        int dsize = suniq.size();
        java.util.HashMap<Integer, Integer> crank = new java.util.HashMap<>();
        for (int i = 0; i < dsize; i++) {
            crank.put(suniq.get(i), i + 1);
        }
        int[] ranks = new int[n];
        for (int i = 0; i < n; i++) {
            ranks[i] = crank.get(quot[i]);
        }

        // Mo's queries
        int block = 250;
        java.util.List<Query> qlist = new java.util.ArrayList<>();
        for (int i = 0; i < q; i++) {
            qlist.add(new Query(queries[i][0], queries[i][1], i));
        }
        qlist.sort((a, b) -> {
            int ba = a.L / block;
            int bb = b.L / block;
            if (ba != bb) return ba - bb;
            return a.R - b.R;
        });

        FenwickTree ftcnt = new FenwickTree(dsize);
        FenwickTree ftsum = new FenwickTree(dsize);
        long[] ans = new long[q];
        int curl = 0, curr = -1;
        long totcnt = 0, totsum = 0;

        for (Query que : qlist) {
            int tl = que.L, tr = que.R;
            // Add right
            while (curr < tr) {
                curr++;
                int rk = ranks[curr];
                long val = quot[curr];
                ftcnt.update(rk, 1);
                ftsum.update(rk, val);
                totcnt++;
                totsum += val;
            }
            // Add left
            while (curl > tl) {
                curl--;
                int rk = ranks[curl];
                long val = quot[curl];
                ftcnt.update(rk, 1);
                ftsum.update(rk, val);
                totcnt++;
                totsum += val;
            }
            // Remove right
            while (curr > tr) {
                int rk = ranks[curr];
                long val = quot[curr];
                ftcnt.update(rk, -1);
                ftsum.update(rk, -val);
                totcnt--;
                totsum -= val;
                curr--;
            }
            // Remove left
            while (curl < tl) {
                int rk = ranks[curl];
                long val = quot[curl];
                ftcnt.update(rk, -1);
                ftsum.update(rk, -val);
                totcnt--;
                totsum -= val;
                curl++;
            }
            // Check mods and compute
            int sublen = tr - tl + 1;
            int klog = lg[sublen];
            int rmin = Math.min(stmin[klog][tl], stmin[klog][tr - (1 << klog) + 1]);
            int rmax = Math.max(stmax[klog][tl], stmax[klog][tr - (1 << klog) + 1]);
            if (rmin != rmax) {
                ans[que.index] = -1;
                continue;
            }
            long clen = totcnt;
            long target = (clen + 1) / 2;
            // Binary search medrank
            int lo = 1, hi = dsize;
            int medrk = hi + 1;
            while (lo <= hi) {
                int mi = (lo + hi) / 2;
                if (ftcnt.query(mi) >= target) {
                    medrk = mi;
                    hi = mi - 1;
                } else {
                    lo = mi + 1;
                }
            }
            if (medrk > dsize) {
                ans[que.index] = -1;
                continue;
            }
            long medv = suniq.get(medrk - 1);
            long lcnt = medrk == 1 ? 0 : ftcnt.query(medrk - 1);
            long lsum = medrk == 1 ? 0 : ftsum.query(medrk - 1);
            long leqc = ftcnt.query(medrk);
            long leqs = ftsum.query(medrk);
            long gcnt = clen - leqc;
            long gsum = totsum - leqs;
            long leftcost = medv * lcnt - lsum;
            long rightcost = gsum - medv * gcnt;
            ans[que.index] = leftcost + rightcost;
        }
        return ans;
    }
}
# @lc code=end