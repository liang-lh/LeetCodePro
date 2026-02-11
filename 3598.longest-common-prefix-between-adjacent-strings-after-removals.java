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
        int[] adj = new int[n - 1];
        for (int j = 0; j < n - 1; j++) {
            String s1 = words[j];
            String s2 = words[j + 1];
            int l = 0;
            int m = Math.min(s1.length(), s2.length());
            while (l < m && s1.charAt(l) == s2.charAt(l)) {
                l++;
            }
            adj[j] = l;
        }
        int[] pre = new int[n];
        pre[0] = 0;
        for (int j = 1; j < n; j++) {
            pre[j] = Math.max(pre[j - 1], adj[j - 1]);
        }
        int[] suf = new int[n];
        suf[n - 1] = 0;
        for (int j = n - 2; j >= 0; j--) {
            suf[j] = Math.max(suf[j + 1], adj[j]);
        }
        for (int i = 0; i < n; i++) {
            int mx = 0;
            if (i == 0) {
                mx = suf[1];
            } else if (i == n - 1) {
                mx = pre[n - 2];
            } else {
                int left = pre[i - 1];
                int right = suf[i + 1];
                String s1 = words[i - 1];
                String s2 = words[i + 1];
                int bridge = 0;
                int m = Math.min(s1.length(), s2.length());
                while (bridge < m && s1.charAt(bridge) == s2.charAt(bridge)) {
                    bridge++;
                }
                mx = Math.max(left, Math.max(right, bridge));
            }
            ans[i] = mx;
        }
        return ans;
    }
}
# @lc code=end