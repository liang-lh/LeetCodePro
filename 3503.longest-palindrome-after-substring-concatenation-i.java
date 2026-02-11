#
# @lc app=leetcode id=3503 lang=java
#
# [3503] Longest Palindrome After Substring Concatenation I
#

# @lc code=start
class Solution {
  public int longestPalindrome(String s, String t) {
    int ans = 0;
    int ns = s.length();
    int nt = t.length();
    for (int i = 0; i <= ns; ++i) {
      for (int j = i; j <= ns; ++j) {
        int leftLen = j - i;
        for (int p = 0; p <= nt; ++p) {
          for (int q = p; q <= nt; ++q) {
            int rightLen = q - p;
            int totalLen = leftLen + rightLen;
            if (totalLen <= ans) continue;
            boolean isPal = true;
            for (int k = 0; k < totalLen / 2; ++k) {
              // left position k
              char leftChar;
              if (k < leftLen) {
                leftChar = s.charAt(i + k);
              } else {
                leftChar = t.charAt(p + k - leftLen);
              }
              // right position totalLen - 1 - k
              int rightPos = totalLen - 1 - k;
              char rightChar;
              if (rightPos < leftLen) {
                rightChar = s.charAt(i + rightPos);
              } else {
                rightChar = t.charAt(p + rightPos - leftLen);
              }
              if (leftChar != rightChar) {
                isPal = false;
                break;
              }
            }
            if (isPal) {
              ans = Math.max(ans, totalLen);
            }
          }
        }
      }
    }
    return ans;
  }
}
# @lc code=end