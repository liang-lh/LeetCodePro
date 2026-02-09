#
# @lc app=leetcode id=3448 lang=java
#
# [3448] Count Substrings Divisible By Last Digit
#
# @lc code=start
class Solution {
    public long countSubstrings(String s) {
        long count = 0;
        int n = s.length();
        
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') continue;
            
            int d = s.charAt(i) - '0';
            int mod = 0;
            int power = 1;
            
            for (int j = i; j >= 0; j--) {
                int digit = s.charAt(j) - '0';
                mod = (mod + digit * power) % d;
                power = (power * 10) % d;
                if (mod == 0) {
                    count++;
                }
            }
        }
        
        return count;
    }
}
# @lc code=end