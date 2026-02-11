#
# @lc app=leetcode id=3513 lang=java
#
# [3513] Number of Unique XOR Triplets I
#

# @lc code=start
class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int n = nums.length;
        if (n == 1) return 1;
        if (n == 2) return 2;
        int log = 31 - Integer.numberOfLeadingZeros(n);
        return 1 << (log + 1);
    }
}
# @lc code=end