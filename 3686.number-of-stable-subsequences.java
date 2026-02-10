#
# @lc app=leetcode id=3686 lang=java
#
# [3686] Number of Stable Subsequences
#
# @lc code=start
class Solution {
    public int countStableSubsequences(int[] nums) {
        int MOD = 1000000007;
        int n = nums.length;
        
        // dp[parity][count] = number of subsequences ending with 
        // 'count' consecutive elements of parity 'parity'
        // parity: 0=even, 1=odd; count: 1 or 2
        long[][] dp = new long[2][3];
        long empty = 1; // For starting new subsequences
        
        for (int i = 0; i < n; i++) {
            int parity = nums[i] % 2;
            long[][] newDp = new long[2][3];
            
            // Option 1: Don't include current element
            for (int p = 0; p < 2; p++) {
                for (int c = 1; c <= 2; c++) {
                    newDp[p][c] = dp[p][c];
                }
            }
            
            // Option 2: Include current element
            // Start new subsequence from empty
            newDp[parity][1] = (newDp[parity][1] + empty) % MOD;
            
            // Extend existing subsequences
            for (int p = 0; p < 2; p++) {
                if (p == parity) {
                    // Same parity: count=1 -> count=2
                    newDp[parity][2] = (newDp[parity][2] + dp[p][1]) % MOD;
                    // count=2 would become 3 (invalid), so skip
                } else {
                    // Different parity: any count -> count=1
                    newDp[parity][1] = (newDp[parity][1] + dp[p][1] + dp[p][2]) % MOD;
                }
            }
            
            dp = newDp;
        }
        
        // Sum all valid subsequences
        long result = 0;
        for (int p = 0; p < 2; p++) {
            for (int c = 1; c <= 2; c++) {
                result = (result + dp[p][c]) % MOD;
            }
        }
        
        return (int) result;
    }
}
# @lc code=end