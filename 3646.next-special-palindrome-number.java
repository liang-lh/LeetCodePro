#
# @lc app=leetcode id=3646 lang=java
#
# [3646] Next Special Palindrome Number
#
# @lc code=start
class Solution {
    public long specialPalindrome(long n) {
        long candidate = n + 1;
        while (candidate <= 1_000_000_000_000_000L) {
            if (isSpecialPalindrome(candidate)) {
                return candidate;
            }
            candidate++;
        }
        return -1;
    }
    
    private boolean isSpecialPalindrome(long num) {
        String s = String.valueOf(num);
        
        if (!isPalindrome(s)) {
            return false;
        }
        
        int[] freq = new int[10];
        for (char c : s.toCharArray()) {
            freq[c - '0']++;
        }
        
        for (int digit = 0; digit <= 9; digit++) {
            if (freq[digit] > 0 && freq[digit] != digit) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
# @lc code=end