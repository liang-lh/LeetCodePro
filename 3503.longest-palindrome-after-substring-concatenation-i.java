#
# @lc app=leetcode id=3503 lang=java
#
# [3503] Longest Palindrome After Substring Concatenation I
#

# @lc code=start
class Solution {
    private static boolean isPalindrome(String str) {
        int L = str.length();
        for (int k = 0; k < L / 2; ++k) {
            if (str.charAt(k) != str.charAt(L - 1 - k)) {
                return false;
            }
        }
        return true;
    }

    public int longestPalindrome(String s, String t) {
        int n = s.length();
        int m = t.length();
        int ans = 0;

        // Case 1: substrings from s only
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j <= n; ++j) {
                String sub = s.substring(i, j);
                if (isPalindrome(sub)) {
                    ans = Math.max(ans, j - i);
                }
            }
        }

        // Case 2: substrings from t only
        for (int i = 0; i < m; ++i) {
            for (int j = i + 1; j <= m; ++j) {
                String sub = t.substring(i, j);
                if (isPalindrome(sub)) {
                    ans = Math.max(ans, j - i);
                }
            }
        }

        // Case 3: non-empty from s + non-empty from t
        for (int i = 0; i < n; ++i) {
            for (int lenS = 1; lenS <= n - i; ++lenS) {
                String left = s.substring(i, i + lenS);
                for (int k = 0; k < m; ++k) {
                    for (int lenT = 1; lenT <= m - k; ++lenT) {
                        String right = t.substring(k, k + lenT);
                        String combined = left + right;
                        if (isPalindrome(combined)) {
                            ans = Math.max(ans, combined.length());
                        }
                    }
                }
            }
        }

        return ans;
    }
}
# @lc code=end