#
# @lc app=leetcode id=3757 lang=java
#
# [3757] Number of Effective Subsequences
#
# @lc code=start
import java.util.*;

class Solution {
    public int countEffective(int[] nums) {
        final int MOD = 1000000007;
        int n = nums.length;
        
        // Calculate total OR of all elements
        int totalOr = 0;
        for (int num : nums) {
            totalOr |= num;
        }
        
        // Find which bit positions are set in totalOr
        List<Integer> bits = new ArrayList<>();
        for (int b = 0; b < 20; b++) {
            if ((totalOr & (1 << b)) != 0) {
                bits.add(b);
            }
        }
        
        int numBits = bits.size();
        
        // Use inclusion-exclusion over subsets of bits
        long answer = 0;
        
        // Iterate through all non-empty subsets of bits
        for (int mask = 1; mask < (1 << numBits); mask++) {
            Set<Integer> indices = new HashSet<>();
            
            // Find all elements that have at least one bit from current subset
            for (int i = 0; i < numBits; i++) {
                if ((mask & (1 << i)) != 0) {
                    int bit = bits.get(i);
                    for (int j = 0; j < n; j++) {
                        if ((nums[j] & (1 << bit)) != 0) {
                            indices.add(j);
                        }
                    }
                }
            }
            
            // Count subsequences that include all these indices
            // We can choose any subset of the remaining elements
            int remaining = n - indices.size();
            long count = modPow(2, remaining, MOD);
            
            // Apply inclusion-exclusion principle
            // Add for odd cardinality, subtract for even
            if (Integer.bitCount(mask) % 2 == 1) {
                answer = (answer + count) % MOD;
            } else {
                answer = (answer - count + MOD) % MOD;
            }
        }
        
        return (int) answer;
    }
    
    // Fast modular exponentiation
    private long modPow(long base, int exp, int mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }
}
# @lc code=end