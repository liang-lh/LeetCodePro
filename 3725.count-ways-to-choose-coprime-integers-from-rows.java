#
# @lc app=leetcode id=3725 lang=java
#
# [3725] Count Ways to Choose Coprime Integers from Rows
#
# @lc code=start
class Solution {
    public int countCoprime(int[][] mat) {
        final int MOD = 1_000_000_007;
        int m = mat.length;
        
        if (m == 0) return 0;
        
        // dp[gcd] = number of ways to achieve this gcd
        java.util.Map<Integer, Long> dp = new java.util.HashMap<>();
        
        // Initialize with first row
        for (int val : mat[0]) {
            dp.put(val, dp.getOrDefault(val, 0L) + 1);
        }
        
        // Process remaining rows
        for (int i = 1; i < m; i++) {
            java.util.Map<Integer, Long> nextDp = new java.util.HashMap<>();
            
            // Count occurrences of each unique value in current row
            java.util.Map<Integer, Integer> valCount = new java.util.HashMap<>();
            for (int val : mat[i]) {
                valCount.put(val, valCount.getOrDefault(val, 0) + 1);
            }
            
            // For each unique value in current row
            for (java.util.Map.Entry<Integer, Integer> valEntry : valCount.entrySet()) {
                int val = valEntry.getKey();
                int count = valEntry.getValue();
                
                // For each previous gcd state
                for (java.util.Map.Entry<Integer, Long> entry : dp.entrySet()) {
                    int prevGcd = entry.getKey();
                    long ways = entry.getValue();
                    
                    int newGcd = gcd(prevGcd, val);
                    long toAdd = (ways * count) % MOD;
                    nextDp.put(newGcd, (nextDp.getOrDefault(newGcd, 0L) + toAdd) % MOD);
                }
            }
            
            dp = nextDp;
        }
        
        return (int) dp.getOrDefault(1, 0L);
    }
    
    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}
# @lc code=end