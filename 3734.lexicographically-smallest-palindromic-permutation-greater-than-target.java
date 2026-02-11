#
# @lc app=leetcode id=3734 lang=java
#
# [3734] Lexicographically Smallest Palindromic Permutation Greater Than Target
#

# @lc code=start
class Solution {
  public String lexPalindromicPermutation(String s, String target) {
    int n = s.length();
    int[] freq = new int[26];
    for (char ch : s.toCharArray()) {
      freq[ch - 'a']++;
    }
    int odd_cnt = 0;
    char mid_c = 0;
    for (int i = 0; i < 26; i++) {
      if (freq[i] % 2 == 1) {
        odd_cnt++;
        mid_c = (char) (i + 'a');
      }
    }
    if (odd_cnt != (n % 2)) {
      return "";
    }
    int[] pair_cnt = new int[26];
    for (int i = 0; i < 26; i++) {
      pair_cnt[i] = freq[i] / 2;
    }
    int m = n / 2;
    // Check equal left case
    String tgt_left = target.substring(0, m);
    int[] used = new int[26];
    for (int j = 0; j < m; j++) {
      used[tgt_left.charAt(j) - 'a']++;
    }
    boolean eq_possible = true;
    for (int i = 0; i < 26; i++) {
      if (used[i] != pair_cnt[i]) {
        eq_possible = false;
        break;
      }
    }
    if (eq_possible) {
      StringBuilder sb = new StringBuilder(tgt_left);
      if (n % 2 == 1) {
        sb.append(mid_c);
      }
      String rev = new StringBuilder(tgt_left).reverse().toString();
      sb.append(rev);
      String candidate = sb.toString();
      if (candidate.compareTo(target) > 0) {
        return candidate;
      }
    }
    // Next greater left
    String ref = tgt_left;
    int len_ = m;
    int pivot = -1;
    for (int kk = len_ - 1; kk >= 0; kk--) {
      int[] temp_rem = new int[26];
      for (int ii = 0; ii < 26; ii++) {
        temp_rem[ii] = pair_cnt[ii];
      }
      boolean ok = true;
      for (int j = 0; j < kk; j++) {
        int id = ref.charAt(j) - 'a';
        if (temp_rem[id] == 0) {
          ok = false;
          break;
        }
        temp_rem[id]--;
      }
      if (!ok) {
        continue;
      }
      char tc = ref.charAt(kk);
      boolean can_inc = false;
      for (char ch = (char) (tc + 1); ch <= 'z'; ch++) {
        int id = ch - 'a';
        if (temp_rem[id] > 0) {
          can_inc = true;
          break;
        }
      }
      if (can_inc) {
        pivot = kk;
        break;
      }
    }
    if (pivot == -1) {
      return "";
    }
    // Build left
    StringBuilder left_sb = new StringBuilder();
    int[] rrem = new int[26];
    for (int ii = 0; ii < 26; ii++) {
      rrem[ii] = pair_cnt[ii];
    }
    for (int j = 0; j < pivot; j++) {
      char chh = ref.charAt(j);
      left_sb.append(chh);
      rrem[chh - 'a']--;
    }
    char tcc = ref.charAt(pivot);
    char inc_chh = 0;
    for (char ch = (char) (tcc + 1); ch <= 'z'; ch++) {
      if (rrem[ch - 'a'] > 0) {
        inc_chh = ch;
        break;
      }
    }
    left_sb.append(inc_chh);
    rrem[inc_chh - 'a']--;
    for (int j = pivot + 1; j < m; j++) {
      char sm_ch = 0;
      for (char ch = 'a'; ch <= 'z'; ch++) {
        if (rrem[ch - 'a'] > 0) {
          sm_ch = ch;
          break;
        }
      }
      left_sb.append(sm_ch);
      rrem[sm_ch - 'a']--;
    }
    String leftt = left_sb.toString();
    StringBuilder full_sb = new StringBuilder(leftt);
    if (n % 2 == 1) {
      full_sb.append(mid_c);
    }
    String reev = new StringBuilder(leftt).reverse().toString();
    full_sb.append(reev);
    return full_sb.toString();
  }
}
# @lc code=end