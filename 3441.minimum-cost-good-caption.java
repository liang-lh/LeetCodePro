#
# @lc app=leetcode id=3441 lang=java
#
# [3441] Minimum Cost Good Caption
#
# @lc code=start
import java.util.*;

class Solution {
    public String minCostGoodCaption(String caption) {
        int n = caption.length();
        if (n < 3) return "";
        
        int[] dp = new int[n + 1];
        String[] result = new String[n + 1];
        
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        result[0] = "";
        
        for (int i = 3; i <= n; i++) {
            for (int j = 0; j <= i - 3; j++) {
                if (dp[j] == Integer.MAX_VALUE) continue;
                
                String segment = caption.substring(j, i);
                int len = i - j;
                
                // Collect candidates: unique chars in segment + neighbors
                Set<Character> candidates = new HashSet<>();
                for (char ch : segment.toCharArray()) {
                    candidates.add(ch);
                    if (ch > 'a') candidates.add((char)(ch - 1));
                    if (ch < 'z') candidates.add((char)(ch + 1));
                }
                
                int minCost = Integer.MAX_VALUE;
                char bestChar = 'a';
                
                for (char c : candidates) {
                    int cost = 0;
                    for (char ch : segment.toCharArray()) {
                        cost += Math.abs(ch - c);
                    }
                    if (cost < minCost || (cost == minCost && c < bestChar)) {
                        minCost = cost;
                        bestChar = c;
                    }
                }
                
                int totalCost = dp[j] + minCost;
                String newResult = result[j] + String.valueOf(bestChar).repeat(len);
                
                if (totalCost < dp[i] || (totalCost == dp[i] && (result[i] == null || newResult.compareTo(result[i]) < 0))) {
                    dp[i] = totalCost;
                    result[i] = newResult;
                }
            }
        }
        
        return dp[n] == Integer.MAX_VALUE ? "" : result[n];
    }
}
# @lc code=end