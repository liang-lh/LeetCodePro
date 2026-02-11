#
# @lc app=leetcode id=3739 lang=java
#
# [3739] Count Subarrays With Majority Element II
#

# @lc code=start
class Solution {
    private static class FenwickTree {
        int[] tree;
        int n;
        public FenwickTree(int _n) {
            n = _n;
            tree = new int[_n + 1];
        }
        public void update(int idx, int delta) {
            while (idx <= n) {
                tree[idx] += delta;
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
        int n = nums.length;
        int OFFSET = 100001;
        int MAXN = 200010;
        FenwickTree ft = new FenwickTree(MAXN);
        int[] prefix = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + (nums[i - 1] == target ? 1 : -1);
        }
        long ans = 0;
        ft.update(prefix[0] + OFFSET, 1);
        for (int j = 1; j <= n; j++) {
            int cur = prefix[j] + OFFSET;
            ans += ft.query(cur - 1);
            ft.update(cur, 1);
        }
        return ans;
    }
}
# @lc code=end