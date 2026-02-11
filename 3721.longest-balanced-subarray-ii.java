#
# @lc app=leetcode id=3721 lang=java
#
# [3721] Longest Balanced Subarray II
#

# @lc code=start
class Solution {
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int left = 0, right = n;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            if (check(nums, mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
    
    private boolean check(int[] nums, int len) {
        if (len == 0) return true;
        int n = nums.length;
        int[] freq = new int[100001];
        int ec = 0, oc = 0;
        for (int i = 0; i < len; i++) {
            int x = nums[i];
            if (freq[x]++ == 0) {
                if ((x & 1) == 0) ec++;
                else oc++;
            }
        }
        if (ec == oc) return true;
        for (int i = len; i < n; i++) {
            int x = nums[i - len];
            if (--freq[x] == 0) {
                if ((x & 1) == 0) ec--;
                else oc--;
            }
            x = nums[i];
            if (freq[x]++ == 0) {
                if ((x & 1) == 0) ec++;
                else oc++;
            }
            if (ec == oc) return true;
        }
        return false;
    }
}
# @lc code=end