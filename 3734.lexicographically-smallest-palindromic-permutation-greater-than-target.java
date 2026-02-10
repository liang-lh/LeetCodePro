#
# @lc app=leetcode id=3734 lang=java
#
# [3734] Lexicographically Smallest Palindromic Permutation Greater Than Target
#
# @lc code=start
class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        
        // Count character frequencies
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        // Check if palindrome is possible (at most one odd frequency)
        int oddCount = 0;
        int oddIdx = -1;
        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                oddCount++;
                oddIdx = i;
            }
        }
        
        if (oddCount > 1) return "";
        
        // Prepare half frequencies (characters for first half)
        int[] halfFreq = new int[26];
        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }
        
        int halfLen = n / 2;
        char[] firstHalf = new char[halfLen];
        
        // Build first half using backtracking
        if (buildGreater(firstHalf, 0, halfFreq, oddIdx, target, false)) {
            return constructPalindrome(firstHalf, oddIdx);
        }
        
        return "";
    }
    
    private boolean buildGreater(char[] firstHalf, int pos, int[] halfFreq, int oddIdx, 
                                  String target, boolean isGreater) {
        if (pos == firstHalf.length) {
            // Construct complete palindrome and verify it's strictly greater than target
            String result = constructPalindrome(firstHalf, oddIdx);
            return result.compareTo(target) > 0;
        }
        
        if (isGreater) {
            // Already greater than target, fill with smallest available characters
            for (int i = 0; i < 26; i++) {
                if (halfFreq[i] > 0) {
                    firstHalf[pos] = (char)('a' + i);
                    halfFreq[i]--;
                    if (buildGreater(firstHalf, pos + 1, halfFreq, oddIdx, target, true)) {
                        return true;
                    }
                    halfFreq[i]++;
                }
            }
        } else {
            // Not yet greater, try to make it greater
            char targetChar = target.charAt(pos);
            
            // Try characters from targetChar onwards
            for (int i = targetChar - 'a'; i < 26; i++) {
                if (halfFreq[i] > 0) {
                    char c = (char)('a' + i);
                    firstHalf[pos] = c;
                    halfFreq[i]--;
                    
                    boolean newIsGreater = (c > targetChar);
                    if (buildGreater(firstHalf, pos + 1, halfFreq, oddIdx, target, newIsGreater)) {
                        return true;
                    }
                    
                    halfFreq[i]++;
                }
            }
        }
        
        return false;
    }
    
    private String constructPalindrome(char[] firstHalf, int oddIdx) {
        StringBuilder sb = new StringBuilder();
        // Add first half
        for (char c : firstHalf) {
            sb.append(c);
        }
        // Add middle character if odd length
        if (oddIdx != -1) {
            sb.append((char)('a' + oddIdx));
        }
        // Add mirrored second half
        for (int i = firstHalf.length - 1; i >= 0; i--) {
            sb.append(firstHalf[i]);
        }
        return sb.toString();
    }
}
# @lc code=end