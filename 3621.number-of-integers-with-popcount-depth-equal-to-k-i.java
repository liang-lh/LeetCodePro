#
# @lc app=leetcode id=3621 lang=java
#
# [3621] Number of Integers With Popcount-Depth Equal to K I
#
# @lc code=start
class Solution {
    public long popcountDepth(long n, int k) {
        // Compute the depth for each possible popcount value (1 to 64)
        int[] depth = new int[65];
        depth[1] = 0;  // Popcount value 1 has depth 0
        
        for (int pc = 2; pc <= 64; pc++) {
            depth[pc] = depth[Integer.bitCount(pc)] + 1;
        }
        
        // Special case: depth 0 means the number is 1
        if (k == 0) {
            return n >= 1 ? 1 : 0;
        }
        
        // Find all popcount values that have depth k-1
        java.util.List<Integer> targetPopcounts = new java.util.ArrayList<>();
        for (int pc = 1; pc <= 64; pc++) {
            if (depth[pc] == k - 1) {
                targetPopcounts.add(pc);
            }
        }
        
        // Count numbers in [1, n] with each target popcount
        long result = 0;
        for (int targetPc : targetPopcounts) {
            result += countWithPopcount(n, targetPc);
        }
        
        // Special case: exclude 1 from the count if its popcount is in targetPopcounts
        // because 1 has depth 0, not k
        if (n >= 1 && targetPopcounts.contains(1)) {
            result--;
        }
        
        return result;
    }
    
    private long countWithPopcount(long n, int targetPc) {
        String bits = Long.toBinaryString(n);
        int len = bits.length();
        
        Long[][][] memo = new Long[len + 1][65][2];
        
        return dp(0, 0, 1, bits, targetPc, memo);
    }
    
    private long dp(int pos, int popcount, int tight, String bits, int targetPc, Long[][][] memo) {
        if (pos == bits.length()) {
            return popcount == targetPc ? 1 : 0;
        }
        
        if (memo[pos][popcount][tight] != null) {
            return memo[pos][popcount][tight];
        }
        
        int limit = tight == 1 ? bits.charAt(pos) - '0' : 1;
        long result = 0;
        
        for (int digit = 0; digit <= limit; digit++) {
            int newTight = (tight == 1 && digit == limit) ? 1 : 0;
            result += dp(pos + 1, popcount + digit, newTight, bits, targetPc, memo);
        }
        
        memo[pos][popcount][tight] = result;
        return result;
    }
}
# @lc code=end