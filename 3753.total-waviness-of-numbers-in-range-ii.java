//
// @lc app=leetcode id=3753 lang=java
//
// [3753] Total Waviness of Numbers in Range II
//
// @lc code=start
class Solution {
    public long totalWaviness(long num1, long num2) {
        return solve(num2) - (num1 > 0 ? solve(num1 - 1) : 0);
    }
    
    private long solve(long num) {
        if (num < 100) return 0; // Numbers with < 3 digits have waviness 0
        
        String s = String.valueOf(num);
        int n = s.length();
        Long[][][][][] memo = new Long[n][11][11][2][2];
        return dfs(0, -1, -1, 1, 0, s, memo);
    }
    
    private long dfs(int pos, int prev, int prev2, int tight, int started, String num, Long[][][][][] memo) {
        if (pos == num.length()) {
            return 0;
        }
        
        int p = prev + 1, pp = prev2 + 1;
        if (memo[pos][p][pp][tight][started] != null) {
            return memo[pos][p][pp][tight][started];
        }
        
        int limit = tight == 1 ? (num.charAt(pos) - '0') : 9;
        long res = 0;
        
        for (int d = 0; d <= limit; d++) {
            if (started == 0 && d == 0) {
                // Leading zero
                res += dfs(pos + 1, -1, -1, 0, 0, num, memo);
            } else {
                int newTight = (tight == 1 && d == limit) ? 1 : 0;
                
                // Check if prev is a peak or valley
                long waviness = 0;
                if (prev != -1 && prev2 != -1) {
                    if (prev2 < prev && prev > d) {
                        waviness = 1; // Peak
                    } else if (prev2 > prev && prev < d) {
                        waviness = 1; // Valley
                    }
                }
                
                res += waviness + dfs(pos + 1, d, prev, newTight, 1, num, memo);
            }
        }
        
        memo[pos][p][pp][tight][started] = res;
        return res;
    }
}
// @lc code=end