#
# @lc app=leetcode id=3504 lang=java
#
# [3504] Longest Palindrome After Substring Concatenation II
#
# @lc code=start
class Solution {
    public int longestPalindrome(String s, String t) {
        int n = s.length();
        int m = t.length();
        int maxLen = 0;
        
        // For each possible boundary between s and t substrings
        // i represents where we end in s, j represents where we start in t
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                // Even-length palindrome: expand from boundary
                int len = 0;
                int left = i - 1, right = j;
                while (left >= 0 && right < m && s.charAt(left) == t.charAt(right)) {
                    len += 2;
                    left--;
                    right++;
                }
                maxLen = Math.max(maxLen, len);
                
                // Odd-length palindrome with center at last char of s substring
                if (i > 0) {
                    len = 1;
                    left = i - 2;
                    right = j;
                    while (left >= 0 && right < m && s.charAt(left) == t.charAt(right)) {
                        len += 2;
                        left--;
                        right++;
                    }
                    maxLen = Math.max(maxLen, len);
                }
                
                // Odd-length palindrome with center at first char of t substring
                if (j < m) {
                    len = 1;
                    left = i - 1;
                    right = j + 1;
                    while (left >= 0 && right < m && s.charAt(left) == t.charAt(right)) {
                        len += 2;
                        left--;
                        right++;
                    }
                    maxLen = Math.max(maxLen, len);
                }
            }
        }
        
        // Check palindromes entirely within s
        maxLen = Math.max(maxLen, longestPalindromeInString(s));
        
        // Check palindromes entirely within t
        maxLen = Math.max(maxLen, longestPalindromeInString(t));
        
        return maxLen;
    }
    
    private int longestPalindromeInString(String str) {
        int n = str.length();
        int maxLen = 1;
        
        for (int i = 0; i < n; i++) {
            // Odd-length palindrome centered at i
            int left = i - 1, right = i + 1;
            int len = 1;
            while (left >= 0 && right < n && str.charAt(left) == str.charAt(right)) {
                len += 2;
                left--;
                right++;
            }
            maxLen = Math.max(maxLen, len);
            
            // Even-length palindrome centered between i and i+1
            if (i + 1 < n && str.charAt(i) == str.charAt(i + 1)) {
                left = i - 1;
                right = i + 2;
                len = 2;
                while (left >= 0 && right < n && str.charAt(left) == str.charAt(right)) {
                    len += 2;
                    left--;
                    right++;
                }
                maxLen = Math.max(maxLen, len);
            }
        }
        
        return maxLen;
    }
}
# @lc code=end