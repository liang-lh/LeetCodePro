#
# @lc app=leetcode id=3413 lang=java
#
# [3413] Maximum Coins From K Consecutive Bags
#
# @lc code=start
import java.util.*;

class Solution {
    public long maximumCoins(int[][] coins, int k) {
        // Sort segments by left position
        Arrays.sort(coins, (a, b) -> Integer.compare(a[0], b[0]));
        
        long maxCoins = 0;
        Set<Long> criticalStarts = new HashSet<>();
        
        // Identify critical starting positions for the k-window
        for (int[] coin : coins) {
            long left = coin[0];
            long right = coin[1];
            
            // Window starts at segment beginning
            criticalStarts.add(left);
            
            // Window ends at segment end
            long startToEndAtRight = right - k + 1;
            if (startToEndAtRight >= 1) {
                criticalStarts.add(startToEndAtRight);
            }
        }
        
        // Calculate maximum coins for each critical starting position
        for (long start : criticalStarts) {
            long coinsCollected = calculateCoinsForWindow(coins, start, k);
            maxCoins = Math.max(maxCoins, coinsCollected);
        }
        
        return maxCoins;
    }
    
    private long calculateCoinsForWindow(int[][] coins, long start, int k) {
        long total = 0;
        long end = start + k - 1;
        
        for (int[] coin : coins) {
            long left = coin[0];
            long right = coin[1];
            long coinsPerBag = coin[2];
            
            // Calculate overlap between window [start, end] and segment [left, right]
            long overlapStart = Math.max(start, left);
            long overlapEnd = Math.min(end, right);
            
            if (overlapStart <= overlapEnd) {
                long overlapLength = overlapEnd - overlapStart + 1;
                total += overlapLength * coinsPerBag;
            }
        }
        
        return total;
    }
}
# @lc code=end