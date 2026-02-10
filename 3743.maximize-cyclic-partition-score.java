#
# @lc app=leetcode id=3743 lang=java
#
# [3743] Maximize Cyclic Partition Score
#
# @lc code=start
class Solution {
    public long maximumScore(int[] nums, int k) {
        int n = nums.length;
        long maxScore = 0;
        
        // Try all starting positions for the cyclic partition
        for (int start = 0; start < n; start++) {
            // Precompute all ranges for this rotation in O(n²)
            long[][] range = new long[n][n];
            for (int i = 0; i < n; i++) {
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;
                for (int j = i; j < n; j++) {
                    int val = nums[(start + j) % n];
                    min = Math.min(min, val);
                    max = Math.max(max, val);
                    range[i][j] = (long)max - min;
                }
            }
            
            // Solve linear DP: maximize score with at most k partitions
            long score = computeMaxScore(range, n, k);
            maxScore = Math.max(maxScore, score);
        }
        
        return maxScore;
    }
    
    private long computeMaxScore(long[][] range, int n, int k) {
        // dp[i][j] = max score for first i elements with exactly j partitions
        long[][] dp = new long[n + 1][k + 1];
        
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = Long.MIN_VALUE / 2;
            }
        }
        
        dp[0][0] = 0;
        
        // DP transition in O(n²k)
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(i, k); j++) {
                for (int last = j - 1; last < i; last++) {
                    if (dp[last][j - 1] > Long.MIN_VALUE / 2) {
                        dp[i][j] = Math.max(dp[i][j], dp[last][j - 1] + range[last][i - 1]);
                    }
                }
            }
        }
        
        // Return maximum score across all partition counts up to k
        long result = Long.MIN_VALUE / 2;
        for (int j = 1; j <= k; j++) {
            result = Math.max(result, dp[n][j]);
        }
        
        return result;
    }
}
# @lc code=end