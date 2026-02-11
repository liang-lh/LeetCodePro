#
# @lc app=leetcode id=3677 lang=java
#
# [3677] Count Binary Palindromic Numbers
#

# @lc code=start
class Solution {
    public int countBinaryPalindromes(long n) {
        if (n == 0) return 1;
        int bitlen = 64 - Long.numberOfLeadingZeros(n);
        long cnt = 1L; // 0
        for (int len = 1; len < bitlen; len++) {
            int llen = (len + 1) / 2;
            cnt += 1L << (llen - 1);
        }
        // length = bitlen
        int len = bitlen;
        int half = len / 2;
        int llen = (len + 1) / 2;
        long istart = 1L << (llen - 1);
        long iend = 1L << llen;
        for (long i = istart; i < iend; i++) {
            long prefix = i >> (len % 2);
            long rev = 0;
            long temp = prefix;
            for (int j = 0; j < half; j++) {
                rev = (rev << 1) | (temp & 1);
                temp >>= 1;
            }
            long num = (i << half) | rev;
            if (num > n) break;
            cnt++;
        }
        return (int) cnt;
    }
}
# @lc code=end