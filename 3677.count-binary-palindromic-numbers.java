#
# @lc app=leetcode id=3677 lang=java
#
# [3677] Count Binary Palindromic Numbers
#

# @lc code=start
class Solution {
  public int countBinaryPalindromes(long n) {
    if (n == 0) return 1;
    String bin = Long.toBinaryString(n);
    int L = bin.length();
    long ans = 1;
    for (int len = 1; len < L; len++) {
      ans += 1L << ((len - 1) / 2);
    }
    int half = (L + 1) / 2;
    String prefix = bin.substring(0, half);
    long smaller = 0;
    for (int i = 0; i < half; i++) {
      if (prefix.charAt(i) == '1') {
        smaller += 1L << (half - i - 1);
      }
    }
    smaller -= 1L << (half - 1);
    long palin = 0;
    for (int i = 0; i < half; i++) {
      if (prefix.charAt(i) == '1') {
        palin |= (1L << i) | (1L << (L - 1 - i));
      }
    }
    long eq = (palin <= n) ? 1L : 0L;
    ans += smaller + eq;
    return (int) ans;
  }
}
# @lc code=end