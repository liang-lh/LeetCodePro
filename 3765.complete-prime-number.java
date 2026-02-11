#
# @lc app=leetcode id=3765 lang=java
#
# [3765] Complete Prime Number
#

# @lc code=start
class Solution {
    public boolean completePrime(int num) {
        String s = String.valueOf(num);
        int n = s.length();
        for (int k = 1; k <= n; ++k) {
            int prefix = Integer.parseInt(s.substring(0, k));
            if (!isPrime(prefix)) return false;
            int suffix = Integer.parseInt(s.substring(n - k));
            if (!isPrime(suffix)) return false;
        }
        return true;
    }
    private boolean isPrime(int x) {
        if (x <= 1) return false;
        if (x == 2 || x == 3) return true;
        if (x % 2 == 0 || x % 3 == 0) return false;
        for (int i = 5; i * i <= x; i += 6) {
            if (x % i == 0 || x % (i + 2) == 0) return false;
        }
        return true;
    }
}
# @lc code=end