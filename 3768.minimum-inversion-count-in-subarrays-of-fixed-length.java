#
# @lc app=leetcode id=3768 lang=java
#
# [3768] Minimum Inversion Count in Subarrays of Fixed Length
#

# @lc code=start
class Solution {
    class Fenwick {
        private int[] tree;
        private int n;
        public Fenwick(int _n) {
            n = _n;
            tree = new int[n + 2];
        }
        public void update(int idx, int val) {
            while (idx <= n) {
                tree[idx] += val;
                idx += idx & -idx;
            }
        }
        public int query(int idx) {
            int sum = 0;
            while (idx > 0) {
                sum += tree[idx];
                idx -= idx & -idx;
            }
            return sum;
        }
    }
    public long minInversionCount(int[] nums, int k) {
        int len = nums.length;
        int[] sortedVals = nums.clone();
        java.util.Arrays.sort(sortedVals);
        int m = 1;
        for (int i = 1; i < len; ++i) {
            if (sortedVals[i] != sortedVals[i - 1]) {
                sortedVals[m++] = sortedVals[i];
            }
        }
        int[] ranks = new int[len];
        for (int i = 0; i < len; ++i) {
            int v = nums[i];
            int low = 0, high = m;
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (sortedVals[mid] < v) {
                    low = mid + 1;
                } else {
                    high = mid;
                }
            }
            ranks[i] = low + 1;
        }
        Fenwick ft = new Fenwick(m);
        long inv = 0;
        int L = 0;
        for (int i = 0; i < k; ++i) {
            int rk = ranks[i];
            inv += (long)ft.query(m) - ft.query(rk);
            ft.update(rk, 1);
        }
        long minInv = inv;
        for (int i = k; i < len; ++i) {
            int rkL = ranks[L];
            inv -= ft.query(rkL - 1);
            ft.update(rkL, -1);
            ++L;
            int rk = ranks[i];
            inv += (long)ft.query(m) - ft.query(rk);
            ft.update(rk, 1);
            if (inv < minInv) minInv = inv;
        }
        return minInv;
    }
}
# @lc code=end