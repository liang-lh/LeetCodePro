#
# @lc app=leetcode id=3621 lang=java
#
# [3621] Number of Integers With Popcount-Depth Equal to K I
#
# @lc code=start
import java.util.*;

class Solution {
    private Long[][][] memo;
    
    public long popcountDepth(long n, int k) {
        // Precompute depth for each possible popcount value
        int[] depth = new int[65];
        depth[1] = 0;
        for (int i = 2; i <= 64; i++) {
            depth[i] = 1 + depth[Integer.bitCount(i)];
        }
        
        // Special case: k = 0
        if (k == 0) {
            return n >= 1 ? 1 : 0;
        }
        
        // Find all popcount values with depth k-1
        List<Integer> validPopcounts = new ArrayList<>();
        for (int pc = 1; pc <= 64; pc++) {
            if (depth[pc] == k - 1) {
                validPopcounts.add(pc);
            }
        }
        
        if (validPopcounts.isEmpty()) {
            return 0;
        }
        
        // Count numbers in [1, n] with these popcounts using digit DP
        long result = 0;
        for (int targetPc : validPopcounts) {
            long count = countNumbersWithPopcount(n, targetPc);
            if (targetPc == 1) {
                count--; // Exclude x = 1 since it has depth 0
            }
            result += count;
        }
        
        return result;
    }
    
    private long countNumbersWithPopcount(long n, int targetPc) {
        String binary = Long.toBinaryString(n);
        int len = binary.length();
        memo = new Long[len][len + 1][2];
        return dp(binary, 0, 0, true, targetPc);
    }
    
    private long dp(String binary, int pos, int count, boolean tight, int targetPc) {
        if (count > targetPc) return 0; // Pruning
        
        if (pos == binary.length()) {
            return count == targetPc ? 1 : 0;
        }
        
        int tightIndex = tight ? 1 : 0;
        if (memo[pos][count][tightIndex] != null) {
            return memo[pos][count][tightIndex];
        }
        
        int limit = tight ? (binary.charAt(pos) - '0') : 1;
        long result = 0;
        
        for (int digit = 0; digit <= limit; digit++) {
            result += dp(binary, pos + 1, count + digit, tight && (digit == limit), targetPc);
        }
        
        memo[pos][count][tightIndex] = result;
        return result;
    }
}
# @lc code=end