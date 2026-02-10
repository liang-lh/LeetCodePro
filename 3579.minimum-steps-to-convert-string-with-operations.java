#
# @lc app=leetcode id=3579 lang=java
#
# [3579] Minimum Steps to Convert String with Operations
#
# @lc code=start
class Solution {
    public int minOperations(String word1, String word2) {
        int n = word1.length();
        int[] dp = new int[n + 1];
        
        for (int i = 1; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                String s1 = word1.substring(j, i);
                String s2 = word2.substring(j, i);
                int cost = minCost(s1, s2);
                dp[i] = Math.min(dp[i], dp[j] + cost);
            }
        }
        
        return dp[n];
    }
    
    private int minCost(String s1, String s2) {
        if (s1.equals(s2)) return 0;
        
        int costNoReverse = calcCost(s1, s2);
        String rev = new StringBuilder(s1).reverse().toString();
        int costReverse = 1 + calcCost(rev, s2);
        
        return Math.min(costNoReverse, costReverse);
    }
    
    private int calcCost(String s1, String s2) {
        int n = s1.length();
        if (s1.equals(s2)) return 0;
        
        boolean[] used = new boolean[n];
        int swaps = 0;
        
        for (int i = 0; i < n; i++) {
            if (used[i] || s1.charAt(i) == s2.charAt(i)) continue;
            
            for (int j = i + 1; j < n; j++) {
                if (used[j] || s1.charAt(j) == s2.charAt(j)) continue;
                
                if (s1.charAt(i) == s2.charAt(j) && s1.charAt(j) == s2.charAt(i)) {
                    swaps++;
                    used[i] = used[j] = true;
                    break;
                }
            }
        }
        
        int mismatches = 0;
        for (int i = 0; i < n; i++) {
            if (!used[i] && s1.charAt(i) != s2.charAt(i)) {
                mismatches++;
            }
        }
        
        return swaps + mismatches;
    }
}
# @lc code=end