#
# @lc app=leetcode id=3458 lang=java
#
# [3458] Select K Disjoint Special Substrings
#

# @lc code=start
import java.util.*;

class Solution {
    public boolean maxSubstringLength(String s, int k) {
        int n = s.length();
        int[] first = new int[26];
        for (int i = 0; i < 26; i++) first[i] = n;
        int[] lastPos = new int[26];
        for (int i = 0; i < 26; i++) lastPos[i] = -1;
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            first[c] = Math.min(first[c], i);
            lastPos[c] = Math.max(lastPos[c], i);
        }
        List<int[]> cands = new ArrayList<>();
        for (int c = 0; c < 26; c++) {
            if (first[c] < n) {
                int st = first[c];
                int en = lastPos[c];
                boolean valid = true;
                for (int j = st; j <= en; j++) {
                    int ch = s.charAt(j) - 'a';
                    if (first[ch] < st || lastPos[ch] > en) {
                        valid = false;
                        break;
                    }
                }
                if (valid && (st > 0 || en < n - 1)) {
                    cands.add(new int[]{st, en});
                }
            }
        }
        Collections.sort(cands, (a, b) -> Integer.compare(a[1], b[1]));
        int cnt = 0;
        int prevEnd = -1;
        for (int[] iv : cands) {
            if (iv[0] > prevEnd) {
                cnt++;
                prevEnd = iv[1];
            }
        }
        return cnt >= k;
    }
}
# @lc code=end