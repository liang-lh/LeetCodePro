#
# @lc app=leetcode id=3734 lang=java
#
# [3734] Lexicographically Smallest Palindromic Permutation Greater Than Target
#

# @lc code=start
class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] total = new int[26];
        for (char ch : s.toCharArray()) {
            total[ch - 'a']++;
        }
        int leftEnd = (n - 1) / 2;
        for (int k = n - 1; k >= 0; k--) {
            char[] res = new char[n];
            boolean validPrefix = true;
            for (int j = 0; j < k; j++) {
                char ch = target.charAt(j);
                int mir = n - 1 - j;
                if (res[j] != 0 && res[j] != ch) {
                    validPrefix = false;
                    break;
                }
                res[j] = ch;
                if (res[mir] != 0 && res[mir] != ch) {
                    validPrefix = false;
                    break;
                }
                res[mir] = ch;
            }
            if (!validPrefix) continue;
            int[] cnt = new int[26];
            System.arraycopy(total, 0, cnt, 0, 26);
            boolean feasible = true;
            for (int i = 0; i < n; i++) {
                if (res[i] != 0) {
                    int idx = res[i] - 'a';
                    cnt[idx]--;
                    if (cnt[idx] < 0) {
                        feasible = false;
                        break;
                    }
                }
            }
            if (!feasible) continue;
            int mirK = n - 1 - k;
            char tgtK = target.charAt(k);
            char chosenC = 0;
            boolean mirrorAlreadySet = (mirK < k);
            int deduct = 1;
            if (mirK > k) deduct = 2;
            if (mirrorAlreadySet) {
                char forced = res[mirK];
                int idx = forced - 'a';
                if (forced > tgtK && cnt[idx] >= 1) {
                    chosenC = forced;
                }
            } else {
                for (char c = (char)(tgtK + 1); c <= 'z'; c++) {
                    int idx = c - 'a';
                    if (cnt[idx] >= deduct) {
                        chosenC = c;
                        break;
                    }
                }
            }
            if (chosenC == 0) continue;
            res[k] = chosenC;
            if (mirK != k) {
                res[mirK] = chosenC;
            }
            cnt[chosenC - 'a'] -= deduct;
            boolean canFill = true;
            for (int i = k + 1; i <= leftEnd; i++) {
                if (res[i] != 0) continue;
                int mirI = n - 1 - i;
                int need = (mirI == i ? 1 : 2);
                char d = 0;
                for (char cc = 'a'; cc <= 'z'; cc++) {
                    int idx = cc - 'a';
                    if (cnt[idx] >= need) {
                        d = cc;
                        break;
                    }
                }
                if (d == 0) {
                    canFill = false;
                    break;
                }
                res[i] = d;
                res[mirI] = d;
                cnt[d - 'a'] -= need;
            }
            if (!canFill) continue;
            feasible = true;
            for (int i = 0; i < n; i++) {
                if (res[i] == 0) {
                    feasible = false;
                    break;
                }
            }
            if (!feasible) continue;
            for (int i = 0; i < 26; i++) {
                if (cnt[i] != 0) {
                    feasible = false;
                    break;
                }
            }
            if (feasible) {
                return new String(res);
            }
        }
        return "";
    }
}
# @lc code=end