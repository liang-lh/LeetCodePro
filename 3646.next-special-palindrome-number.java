#
# @lc app=leetcode id=3646 lang=java
#
# [3646] Next Special Palindrome Number
#

# @lc code=start
class Solution {
    public long specialPalindrome(long n) {
        java.util.TreeSet<java.lang.Long> specials = new java.util.TreeSet<>();
        int[] evens = {2, 4, 6, 8};
        // Even subsets
        for (int mask = 0; mask < 16; mask++) {
            int[] freq = new int[10];
            int L = 0;
            for (int b = 0; b < 4; b++) {
                if ((mask & (1 << b)) != 0) {
                    int e = evens[b];
                    freq[e] = e;
                    L += e;
                }
            }
            if (L == 0 || L > 20) continue;
            int half = L / 2;
            int[] rem = freq.clone();
            java.lang.StringBuilder prefix = new java.lang.StringBuilder();
            generate(specials, rem, prefix, half, L);
        }
        // Odd-inclusive subsets
        int[] odds = {1, 3, 5, 7, 9};
        for (int i = 0; i < 5; i++) {
            int od = odds[i];
            for (int mask = 0; mask < 16; mask++) {
                int[] freq = new int[10];
                freq[od] = od;
                int L = od;
                for (int b = 0; b < 4; b++) {
                    if ((mask & (1 << b)) != 0) {
                        int e = evens[b];
                        freq[e] = e;
                        L += e;
                    }
                }
                if (L > 20) continue;
                int half = L / 2;
                int[] rem = freq.clone();
                java.lang.StringBuilder prefix = new java.lang.StringBuilder();
                generate(specials, rem, prefix, half, L);
            }
        }
        return specials.higher(n);
    }

    private void generate(java.util.TreeSet<java.lang.Long> specials, int[] rem, java.lang.StringBuilder prefix, int half, int L) {
        int targetLen = half + (L % 2);
        if (prefix.length() == targetLen) {
            java.lang.String leftStr = prefix.toString();
            java.lang.StringBuilder revBuilder = new java.lang.StringBuilder(leftStr.substring(0, half));
            revBuilder.reverse();
            java.lang.String fullStr = leftStr + revBuilder.toString();
            if (fullStr.length() <= 18) {
                try {
                    long num = java.lang.Long.parseLong(fullStr);
                    specials.add(num);
                } catch (java.lang.NumberFormatException e) {
                    // ignore too large
                }
            }
            return;
        }
        int pos = prefix.length();
        int req = ((L % 2 == 1) && (pos == half)) ? 1 : 2;
        for (int d = 1; d <= 9; d++) {
            if (rem[d] >= req) {
                rem[d] -= req;
                prefix.append((char) ('0' + d));
                generate(specials, rem, prefix, half, L);
                prefix.deleteCharAt(prefix.length() - 1);
                rem[d] += req;
            }
        }
    }
}
# @lc code=end