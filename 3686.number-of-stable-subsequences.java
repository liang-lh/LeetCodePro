#
# @lc app=leetcode id=3686 lang=java
#
# [3686] Number of Stable Subsequences
#

# @lc code=start
class Solution {
    public int countStableSubsequences(int[] nums) {
        final long MOD = 1000000007L;
        long o1 = 0, o2 = 0, e1 = 0, e2 = 0;
        for (int num : nums) {
            int p = num % 2;
            long no1 = 0, no2 = 0, ne1 = 0, ne2 = 0;
            if (p == 1) {
                no1 = (o1 + e1 + e2 + 1) % MOD;
                no2 = (o2 + o1) % MOD;
                ne1 = e1 % MOD;
                ne2 = e2 % MOD;
            } else {
                ne1 = (e1 + o1 + o2 + 1) % MOD;
                ne2 = (e2 + e1) % MOD;
                no1 = o1 % MOD;
                no2 = o2 % MOD;
            }
            o1 = no1;
            o2 = no2;
            e1 = ne1;
            e2 = ne2;
        }
        return (int) ((o1 + o2 + e1 + e2) % MOD);
    }
}
# @lc code=end