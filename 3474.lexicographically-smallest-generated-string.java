#
# @lc app=leetcode id=3474 lang=java
#
# [3474] Lexicographically Smallest Generated String
#

# @lc code=start
class Solution {
    public String generateString(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int L = n + m - 1;
        char[] forced = new char[L];
        // No need to init to 0, default is '\0'
        boolean possible = true;
        // Step 1: Propagate 'T' constraints
        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'T') {
                for (int j = 0; j < m; j++) {
                    int p = i + j;
                    char ch = str2.charAt(j);
                    if (forced[p] != 0 && forced[p] != ch) {
                        possible = false;
                    }
                    forced[p] = ch;
                }
            }
        }
        if (!possible) return "";
        // Step 2: Check fully forced 'F'
        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'F') {
                boolean all_forced = true;
                boolean matches = true;
                for (int j = 0; j < m; j++) {
                    int p = i + j;
                    if (forced[p] == 0) {
                        all_forced = false;
                        break;
                    }
                    if (forced[p] != str2.charAt(j)) {
                        matches = false;
                    }
                }
                if (all_forced && matches) {
                    return "";
                }
            }
        }
        // Step 3: Precompute suffix_forced_match for 'F'
        boolean[][] suffix_match = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'F') {
                for (int jj = m - 1; jj >= 0; jj--) {
                    if (jj == m - 1) {
                        suffix_match[i][jj] = true;
                    } else {
                        int pp = i + jj + 1;
                        char needed = str2.charAt(jj + 1);
                        suffix_match[i][jj] = (forced[pp] != 0 && forced[pp] == needed) && suffix_match[i][jj + 1];
                    }
                }
            }
        }
        // Step 4: Build greedily
        char[] res = new char[L];
        int[] cur_match = new int[n];
        for (int p = 0; p < L; p++) {
            char c;
            if (forced[p] != 0) {
                c = forced[p];
            } else {
                boolean[] forbid = new boolean[26];
                int start_i = Math.max(0, p - m + 1);
                int end_i = Math.min(p, n - 1);
                for (int ii = start_i; ii <= end_i; ii++) {
                    if (str1.charAt(ii) == 'F') {
                        int jj = p - ii;
                        if (cur_match[ii] == jj && suffix_match[ii][jj]) {
                            forbid[str2.charAt(jj) - 'a'] = true;
                        }
                    }
                }
                int idx = 0;
                while (idx < 26 && forbid[idx]) {
                    idx++;
                }
                if (idx == 26) {
                    return "";
                }
                c = (char) ('a' + idx);
            }
            res[p] = c;
            // Update cur_match for covering 'F'
            int start_i = Math.max(0, p - m + 1);
            int end_i = Math.min(p, n - 1);
            for (int ii = start_i; ii <= end_i; ii++) {
                if (str1.charAt(ii) == 'F') {
                    int jj = p - ii;
                    if (cur_match[ii] == jj && c == str2.charAt(jj)) {
                        cur_match[ii]++;
                    }
                }
            }
        }
        // Step 5: Verify no full 'F' match
        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'F' && cur_match[i] == m) {
                return "";
            }
        }
        return new String(res);
    }
}
# @lc code=end