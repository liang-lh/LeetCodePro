#
# @lc app=leetcode id=3776 lang=java
#
# [3776] Minimum Moves to Balance Circular Array
#
# @lc code=start
import java.util.*;

class Solution {
    public long minMoves(int[] balance) {
        int n = balance.length;
        long sum = 0;
        int negIdx = -1;
        
        // Find negative index and calculate sum
        for (int i = 0; i < n; i++) {
            sum += balance[i];
            if (balance[i] < 0) {
                negIdx = i;
            }
        }
        
        // If sum is negative, impossible
        if (sum < 0) {
            return -1;
        }
        
        // If no negative index, already balanced
        if (negIdx == -1) {
            return 0;
        }
        
        long needed = -balance[negIdx];
        
        // Create list of (distance, amount) pairs
        List<long[]> sources = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i != negIdx && balance[i] > 0) {
                int dist = Math.min(Math.abs(i - negIdx), n - Math.abs(i - negIdx));
                sources.add(new long[]{dist, balance[i]});
            }
        }
        
        // Sort by distance (greedy: use closest sources first)
        sources.sort((a, b) -> Long.compare(a[0], b[0]));
        
        long moves = 0;
        for (long[] source : sources) {
            long dist = source[0];
            long amount = source[1];
            long take = Math.min(amount, needed);
            moves += take * dist;
            needed -= take;
            if (needed == 0) break;
        }
        
        return moves;
    }
}
# @lc code=end