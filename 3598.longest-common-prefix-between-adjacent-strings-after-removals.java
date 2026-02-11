#
# @lc app=leetcode id=3598 lang=java
#
# [3598] Longest Common Prefix Between Adjacent Strings After Removals
#

# @lc code=start
class Solution {
    public int[] longestCommonPrefix(String[] words) {
        int n = words.length;
        int[] ans = new int[n];
        if (n <= 1) {
            return ans;
        }
        int m = n - 1;
        int[] prefix = new int[m];
        for (int j = 0; j < m; j++) {
            prefix[j] = getLCP(words[j], words[j + 1]);
        }
        int[] premax = new int[m];
        premax[0] = prefix[0];
        for (int j = 1; j < m; j++) {
            premax[j] = Math.max(premax[j - 1], prefix[j]);
        }
        int[] sufmax = new int[m];
        sufmax[m - 1] = prefix[m - 1];
        for (int j = m - 2; j >= 0; j--) {
            sufmax[j] = Math.max(sufmax[j + 1], prefix[j]);
        }
        for (int i = 0; i < n; i++) {
            int max_kept = 0;
            if (i >= 2) {
                max_kept = Math.max(max_kept, premax[i - 2]);
            }
            if (i <= n - 3) {
                max_kept = Math.max(max_kept, sufmax[i + 1]);
            }
            int new_lcp = 0;
            if (i >= 1 && i <= n - 2) {
                new_lcp = getLCP(words[i - 1], words[i + 1]);
            }
            ans[i] = Math.max(max_kept, new_lcp);
        }
        return ans;
    }
    
    private int getLCP(String a, String b) {
        int len = Math.min(a.length(), b.length());
        for (int k = 0; k < len; k++) {
            if (a.charAt(k) != b.charAt(k)) {
                return k;
            }
        }
        return len;
    }
}
# @lc code=end