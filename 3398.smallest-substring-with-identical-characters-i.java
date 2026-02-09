#
# @lc app=leetcode id=3398 lang=java
#
# [3398] Smallest Substring With Identical Characters I
#
# @lc code=start
class Solution {
    public int minLength(String s, int numOps) {
        int n = s.length();
        
        if (numOps == 0) {
            // Can't flip anything, return current max run length
            return getMaxRunLength(s);
        }
        
        // Special case: check if we can make alternating pattern
        int flips0 = 0; // flips to make "010101..."
        int flips1 = 0; // flips to make "101010..."
        for (int i = 0; i < n; i++) {
            char expected0 = (i % 2 == 0) ? '0' : '1';
            char expected1 = (i % 2 == 0) ? '1' : '0';
            if (s.charAt(i) != expected0) flips0++;
            if (s.charAt(i) != expected1) flips1++;
        }
        
        if (Math.min(flips0, flips1) <= numOps) {
            return 1;
        }
        
        // Binary search on the answer
        int left = 2, right = n;
        int result = n;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canAchieve(s, numOps, mid)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    private int getMaxRunLength(String s) {
        int maxRun = 1;
        int currentRun = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                currentRun++;
                maxRun = Math.max(maxRun, currentRun);
            } else {
                currentRun = 1;
            }
        }
        return maxRun;
    }
    
    private boolean canAchieve(String s, int numOps, int maxLen) {
        int n = s.length();
        int flipsNeeded = 0;
        int i = 0;
        
        while (i < n) {
            int j = i;
            // Find the end of current run
            while (j < n && s.charAt(j) == s.charAt(i)) {
                j++;
            }
            
            int runLen = j - i;
            if (runLen > maxLen) {
                // Need to break this run
                flipsNeeded += runLen / (maxLen + 1);
            }
            
            i = j;
        }
        
        return flipsNeeded <= numOps;
    }
}
# @lc code=end