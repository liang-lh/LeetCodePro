#
# @lc app=leetcode id=3765 lang=java
#
# [3765] Complete Prime Number
#
# @lc code=start
class Solution {
    public boolean completePrime(int num) {
        String s = String.valueOf(num);
        int len = s.length();
        
        // Check all prefixes
        for (int i = 1; i <= len; i++) {
            String prefix = s.substring(0, i);
            int prefixNum = Integer.parseInt(prefix);
            if (!isPrime(prefixNum)) {
                return false;
            }
        }
        
        // Check all suffixes
        for (int i = 0; i < len; i++) {
            String suffix = s.substring(i);
            int suffixNum = Integer.parseInt(suffix);
            if (!isPrime(suffixNum)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
# @lc code=end