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
        for (int k = 1; k <= len; k++) {
            if (!isPrime(Long.parseLong(s.substring(0, k)))) {
                return false;
            }
            if (!isPrime(Long.parseLong(s.substring(len - k)))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPrime(long n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}
# @lc code=end