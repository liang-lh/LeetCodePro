#
# @lc app=leetcode id=3448 lang=java
#
# [3448] Count Substrings Divisible By Last Digit
#

# @lc code=start
class Solution {
    public long countSubstrings(String s) {
        int n = s.length();
        long ans = 0;
        for (int d = 1; d <= 9; d++) {
            long[] cur = new long[10];
            long[] nxt = new long[10];
            for (int i = 0; i < n; i++) {
                int dig = s.charAt(i) - '0';
                for (int k = 0; k < 10; k++) {
                    nxt[k] = 0;
                }
                for (int m = 0; m < d; m++) {
                    long val = cur[m];
                    if (val == 0) continue;
                    int newm = (int) (((long) m * 10 + dig) % d);
                    nxt[newm] += val;
                }
                int sm = dig % d;
                nxt[sm]++;
                if (dig == d) {
                    ans += nxt[0];
                }
                for (int k = 0; k < d; k++) {
                    cur[k] = nxt[k];
                }
            }
        }
        return ans;
    }
}
# @lc code=end