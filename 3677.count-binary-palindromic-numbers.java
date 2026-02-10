#
# @lc app=leetcode id=3677 lang=java
#
# [3677] Count Binary Palindromic Numbers
#
# @lc code=start
class Solution {
    public int countBinaryPalindromes(long n) {
        if (n == 0) return 1;
        
        int count = 1; // for 0
        
        // Get bit length of n
        String nBinary = Long.toBinaryString(n);
        int maxLen = nBinary.length();
        
        // Count palindromes for all lengths < maxLen
        for (int len = 1; len < maxLen; len++) {
            if (len == 1) {
                count += 1; // "1"
            } else if (len % 2 == 0) {
                // Even length: 2^(len/2 - 1) palindromes
                count += (1 << (len / 2 - 1));
            } else {
                // Odd length: 2^((len+1)/2 - 1) palindromes
                count += (1 << ((len + 1) / 2 - 1));
            }
        }
        
        // Count palindromes of length maxLen that are <= n
        count += countPalindromesOfLength(maxLen, n);
        
        return count;
    }
    
    private int countPalindromesOfLength(int len, long n) {
        int count = 0;
        
        if (len == 1) {
            return 1; // "1" is always <= n for n >= 1
        }
        
        // Generate all palindromes of this length
        int halfLen = (len + 1) / 2;
        long maxHalf = 1L << (halfLen - 1); // start from 10...0 (first bit is 1)
        long endHalf = 1L << halfLen; // end at 100...0
        
        for (long half = maxHalf; half < endHalf; half++) {
            long palindrome = makePalindrome(half, len);
            if (palindrome <= n) {
                count++;
            } else {
                break; // Since we're generating in order
            }
        }
        
        return count;
    }
    
    private long makePalindrome(long half, int len) {
        String halfStr = Long.toBinaryString(half);
        StringBuilder sb = new StringBuilder(halfStr);
        
        if (len % 2 == 0) {
            // Even length: mirror all bits
            String reversed = new StringBuilder(halfStr).reverse().toString();
            sb.append(reversed);
        } else {
            // Odd length: mirror all bits except the middle one
            String reversed = new StringBuilder(halfStr.substring(0, halfStr.length() - 1)).reverse().toString();
            sb.append(reversed);
        }
        
        return Long.parseLong(sb.toString(), 2);
    }
}
# @lc code=end