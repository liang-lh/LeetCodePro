#
# @lc app=leetcode id=3721 lang=java
#
# [3721] Longest Balanced Subarray II
#

# @lc code=start
class Solution {
    private boolean canBalance(int[] nums, int L) {
        int n = nums.length;
        if (L == 0) return true;
        if (L > n) return false;
        final int MAX = 100000;
        int[] evenFreq = new int[MAX + 1];
        int[] oddFreq = new int[MAX + 1];
        int evenDistinct = 0;
        int oddDistinct = 0;
        // Initialize first window
        for (int i = 0; i < L; i++) {
            int num = nums[i];
            if ((num & 1) == 0) {  // even
                if (evenFreq[num]++ == 0) evenDistinct++;
            } else {  // odd
                if (oddFreq[num]++ == 0) oddDistinct++;
            }
        }
        if (evenDistinct == oddDistinct) return true;
        // Slide the window
        for (int i = L; i < n; i++) {
            // Remove nums[i - L]
            int rem = nums[i - L];
            if ((rem & 1) == 0) {
                if (--evenFreq[rem] == 0) evenDistinct--;
            } else {
                if (--oddFreq[rem] == 0) oddDistinct--;
            }
            // Add nums[i]
            int addNum = nums[i];
            if ((addNum & 1) == 0) {
                if (evenFreq[addNum]++ == 0) evenDistinct++;
            } else {
                if (oddFreq[addNum]++ == 0) oddDistinct++;
            }
            if (evenDistinct == oddDistinct) return true;
        }
        return false;
    }

    public int longestBalanced(int[] nums) {
        int n = nums.length;
        for (int L = n; L >= 0; L--) {
            if (canBalance(nums, L)) {
                return L;
            }
        }
        return 0;
    }
}
# @lc code=end