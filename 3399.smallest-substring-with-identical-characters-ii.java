#
# @lc app=leetcode id=3399 lang=java
#
# [3399] Smallest Substring With Identical Characters II
#
# @lc code=start
class Solution {
    public int minLength(String s, int numOps) {
        int n = s.length();
        
        // Find all consecutive runs of identical characters
        java.util.List<Integer> runs = new java.util.ArrayList<>();
        int count = 1;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                runs.add(count);
                count = 1;
            }
        }
        runs.add(count);
        
        // Find maximum run length
        int maxRun = 0;
        for (int run : runs) {
            maxRun = Math.max(maxRun, run);
        }
        
        // If already alternating (max run length is 1), return 1
        if (maxRun == 1) {
            return 1;
        }
        
        // If no operations allowed, return current max run length
        if (numOps == 0) {
            return maxRun;
        }
        
        // Check if we can achieve alternating pattern (length 1)
        if (canAchieveAlternating(s, numOps)) {
            return 1;
        }
        
        // Binary search for the minimum achievable max length
        int left = 2, right = maxRun;
        int result = maxRun;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canAchieve(runs, mid, numOps)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    private boolean canAchieveAlternating(String s, int numOps) {
        int n = s.length();
        int diff1 = 0, diff2 = 0;
        
        // Count differences from pattern "010101..."
        for (int i = 0; i < n; i++) {
            char expected = (i % 2 == 0) ? '0' : '1';
            if (s.charAt(i) != expected) {
                diff1++;
            }
        }
        
        // Count differences from pattern "101010..."
        for (int i = 0; i < n; i++) {
            char expected = (i % 2 == 0) ? '1' : '0';
            if (s.charAt(i) != expected) {
                diff2++;
            }
        }
        
        return Math.min(diff1, diff2) <= numOps;
    }
    
    private boolean canAchieve(java.util.List<Integer> runs, int maxLen, int numOps) {
        int flipsNeeded = 0;
        for (int run : runs) {
            if (run > maxLen) {
                // To break a run of length 'run' into segments of max length 'maxLen',
                // we need to flip every (maxLen + 1)th position
                flipsNeeded += run / (maxLen + 1);
            }
        }
        return flipsNeeded <= numOps;
    }
}
# @lc code=end