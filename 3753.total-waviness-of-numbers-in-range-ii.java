#
# @lc app=leetcode id=3753 lang=java
#
# [3753] Total Waviness of Numbers in Range II
#
# @lc code=start
class Solution {
    private Long[][][][] dp;
    private String num;
    private int len;
    
    public long totalWaviness(long num1, long num2) {
        return solve(num2) - solve(num1 - 1);
    }
    
    private long solve(long n) {
        if (n < 100) return 0; // Numbers < 100 have waviness 0
        
        num = String.valueOf(n);
        len = num.length();
        dp = new Long[len][11][11][2]; // 11 to use index 10 for 'not started'
        
        return dfs(0, 10, 10, 1); // 10 represents -1 (not started)
    }
    
    private long dfs(int pos, int prev, int prevPrev, int tight) {
        if (pos == len) {
            return 0;
        }
        
        if (dp[pos][prev][prevPrev][tight] != null) {
            return dp[pos][prev][prevPrev][tight];
        }
        
        int limit = tight == 1 ? (num.charAt(pos) - '0') : 9;
        long result = 0;
        
        for (int digit = 0; digit <= limit; digit++) {
            int newTight = (tight == 1 && digit == limit) ? 1 : 0;
            
            long contribution = 0;
            
            // Check if prev forms a peak or valley
            // prev is at position pos-1, which should not be first or last digit
            // pos >= 2 ensures prev is at position >= 1 (not first)
            // and since we're building, prev at pos-1 < len-1 (not last)
            if (prev < 10 && prevPrev < 10 && pos >= 2) {
                if (prev > prevPrev && prev > digit) {
                    contribution = 1; // peak
                } else if (prev < prevPrev && prev < digit) {
                    contribution = 1; // valley
                }
            }
            
            if (prev == 10) { // Haven't started yet
                if (digit == 0) {
                    // Leading zero, continue without starting
                    result += dfs(pos + 1, 10, 10, newTight);
                } else {
                    // Start the number
                    result += dfs(pos + 1, digit, 10, newTight);
                }
            } else if (prevPrev == 10) {
                // Second digit
                result += dfs(pos + 1, digit, prev, newTight);
            } else {
                // Third digit onwards
                result += contribution + dfs(pos + 1, digit, prev, newTight);
            }
        }
        
        dp[pos][prev][prevPrev][tight] = result;
        return result;
    }
}
# @lc code=end