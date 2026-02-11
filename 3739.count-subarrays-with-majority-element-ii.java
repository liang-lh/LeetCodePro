#
# @lc app=leetcode id=3739 lang=java
#
# [3739] Count Subarrays With Majority Element II
#

# @lc code=start
class Solution {
    private static class FenwickTree {
        long[] tree;
        int n;
        public FenwickTree(int _n) {
            n = _n;
            tree = new long[_n + 1];
        }
        public void update(int idx, long val) {
            while (idx <= n) {
                tree[idx] += val;
                idx += idx & -idx;
            }
        }
        public long query(int idx) {
            long sum = 0;
            while (idx > 0) {
                sum += tree[idx];
                idx -= idx & -idx;
            }
            return sum;
        }
    }

    public long countMajoritySubarrays(int[] nums, int target) {
        final int OFFSET = 100001;
        final int MAXN = 200002;
        FenwickTree ft = new FenwickTree(MAXN);
        ft.update(0 + OFFSET, 1L);
        int pref = 0;
        long ans = 0L;
        for (int num : nums) {
            pref += (num == target ? 1 : -1);
            ans += ft.query(pref - 1 + OFFSET);
            ft.update(pref + OFFSET, 1L);
        }
        return ans;
    }
}
# @lc code=end