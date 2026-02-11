import java.util.*;
#
# @lc app=leetcode id=3646 lang=java
#
# [3646] Next Special Palindrome Number
#
# @lc code=start
class Solution {
    private static final String MAX_LONG_STR = "9223372036854775807";

    public long specialPalindrome(long n) {
        List<Long> specials = new ArrayList<>();
        for (int mask = 1; mask < (1 << 9); mask++) {
            int L = 0;
            int[] req = new int[10];
            for (int k = 1; k <= 9; k++) {
                if ((mask & (1 << (k - 1))) != 0) {
                    L += k;
                    req[k] = k;
                }
            }
            if (L == 0 || L > 19) continue;
            int num_odd = 0;
            int odd_d = -1;
            for (int d = 1; d <= 9; d++) {
                if (req[d] % 2 == 1) {
                    num_odd++;
                    odd_d = d;
                }
            }
            boolean is_odd_len = (L % 2 == 1);
            if ((is_odd_len && num_odd != 1) || (!is_odd_len && num_odd != 0)) continue;
            int[] half_cnt = new int[10];
            for (int d = 1; d <= 9; d++) {
                half_cnt[d] = req[d] / 2;
            }
            int left_len = L / 2;
            char[] path = new char[left_len];
            int[] remain = new int[10];
            for (int d = 1; d <= 9; d++) remain[d] = half_cnt[d];
            generate(0, path, remain, specials, L, odd_d, left_len);
        }
        Collections.sort(specials);
        for (long x : specials) {
            if (x > n) {
                return x;
            }
        }
        return -1L; // Theoretically unreachable
    }

    private void generate(int pos, char[] path, int[] remain, List<Long> specials, int L, int odd_d, int left_len) {
        if (pos == left_len) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < left_len; i++) {
                sb.append(path[i]);
            }
            String left = sb.toString();
            if (L % 2 == 1) {
                sb.append((char) ('0' + odd_d));
            }
            sb.append(new StringBuilder(left).reverse().toString());
            String numStr = sb.toString();
            if (numStr.length() == 19 && numStr.compareTo(MAX_LONG_STR) > 0) {
                return;
            }
            long num;
            try {
                num = Long.parseLong(numStr);
            } catch (NumberFormatException e) {
                return;
            }
            specials.add(num);
            return;
        }
        for (int d = 1; d <= 9; d++) {
            if (remain[d] > 0) {
                path[pos] = (char) ('0' + d);
                remain[d]--;
                generate(pos + 1, path, remain, specials, L, odd_d, left_len);
                remain[d]++;
            }
        }
    }
}
# @lc code=end