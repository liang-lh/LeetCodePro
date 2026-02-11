#
# @lc app=leetcode id=3399 lang=java
#
# [3399] Smallest Substring With Identical Characters II
#

# @lc code=start
class Solution {
    public int minLength(String s, int numOps) {
        int n = s.length();
        int left = 1, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canAchieve(s, numOps, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean canAchieve(String s, int numOps, int maxLen) {
        long need1 = simulate(s, maxLen, false);
        if (need1 <= numOps) {
            return true;
        }
        long need2 = simulate(s, maxLen, true);
        return need2 <= numOps;
    }

    private long simulate(String s, int maxLen, boolean flipFirst) {
        int n = s.length();
        long need = flipFirst ? 1L : 0L;
        int currCh = flipFirst ? 1 - (s.charAt(0) - '0') : (s.charAt(0) - '0');
        int currLen = 1;
        for (int i = 1; i < n; ++i) {
            int origCh = s.charAt(i) - '0';
            int preferCh = origCh;
            if (preferCh == currCh && currLen == maxLen) {
                ++need;
                preferCh = 1 - origCh;
            }
            int chosenCh = preferCh;
            if (chosenCh == currCh) {
                ++currLen;
            } else {
                currCh = chosenCh;
                currLen = 1;
            }
        }
        return need;
    }
}
# @lc code=end