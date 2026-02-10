#
# @lc app=leetcode id=3598 lang=java
#
# [3598] Longest Common Prefix Between Adjacent Strings After Removals
#
# @lc code=start
class Solution {
    public int[] longestCommonPrefix(String[] words) {
        int n = words.length;
        int[] result = new int[n];
        
        // Edge case: if only one word, no adjacent pairs
        if (n == 1) {
            return result; // [0]
        }
        
        // Precompute common prefix lengths for all adjacent pairs
        int[] prefixLengths = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            prefixLengths[i] = commonPrefixLength(words[i], words[i + 1]);
        }
        
        // Precompute max prefix from left
        int[] maxPrefixFromLeft = new int[n - 1];
        maxPrefixFromLeft[0] = prefixLengths[0];
        for (int i = 1; i < n - 1; i++) {
            maxPrefixFromLeft[i] = Math.max(maxPrefixFromLeft[i - 1], prefixLengths[i]);
        }
        
        // Precompute max prefix from right
        int[] maxPrefixFromRight = new int[n - 1];
        maxPrefixFromRight[n - 2] = prefixLengths[n - 2];
        for (int i = n - 3; i >= 0; i--) {
            maxPrefixFromRight[i] = Math.max(maxPrefixFromRight[i + 1], prefixLengths[i]);
        }
        
        // For each removal
        for (int i = 0; i < n; i++) {
            int maxLen = 0;
            
            // Max from left pairs
            if (i >= 2) {
                maxLen = Math.max(maxLen, maxPrefixFromLeft[i - 2]);
            }
            
            // Max from right pairs
            if (i <= n - 3) {
                maxLen = Math.max(maxLen, maxPrefixFromRight[i + 1]);
            }
            
            // New pair formed
            if (i > 0 && i < n - 1) {
                maxLen = Math.max(maxLen, commonPrefixLength(words[i - 1], words[i + 1]));
            }
            
            result[i] = maxLen;
        }
        
        return result;
    }
    
    private int commonPrefixLength(String s1, String s2) {
        int minLen = Math.min(s1.length(), s2.length());
        int len = 0;
        for (int i = 0; i < minLen; i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                len++;
            } else {
                break;
            }
        }
        return len;
    }
}
# @lc code=end