#
# @lc app=leetcode id=3455 lang=java
#
# [3455] Shortest Matching Substring
#

# @lc code=start
class Solution {
    public int shortestMatchingSubstring(String s, String p) {
        int n = s.length();
        int star1 = p.indexOf('*');
        int star2 = p.indexOf('*', star1 + 1);
        String A = p.substring(0, star1);
        String B = p.substring(star1 + 1, star2);
        String C = p.substring(star2 + 1);
        int la = A.length();
        int lb = B.length();
        int lc = C.length();
        java.util.ArrayList<Integer> posA = kmp(s, A);
        java.util.ArrayList<Integer> posB = kmp(s, B);
        java.util.ArrayList<Integer> posC = kmp(s, C);
        int minLen = Integer.MAX_VALUE;
        for (int sb : posB) {
            int maxl = sb - la;
            if (maxl < 0) continue;
            int l = findLargestLE(posA, maxl);
            if (l == -1) continue;
            int minsc = sb + lb;
            int sc = findSmallestGE(posC, minsc);
            if (sc == -1) continue;
            int r = sc + lc - 1;
            int curlen = r - l + 1;
            if (curlen >= 0 && curlen < minLen) {
                minLen = curlen;
            }
        }
        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }

    private java.util.ArrayList<Integer> kmp(String text, String pat) {
        int n = text.length();
        int m = pat.length();
        java.util.ArrayList<Integer> res = new java.util.ArrayList<>();
        if (m == 0) {
            for (int i = 0; i <= n; i++) {
                res.add(i);
            }
            return res;
        }
        int[] pi = computePrefixFunction(pat);
        int q = 0;
        for (int i = 0; i < n; i++) {
            while (q > 0 && pat.charAt(q) != text.charAt(i)) {
                q = pi[q - 1];
            }
            if (pat.charAt(q) == text.charAt(i)) {
                ++q;
            }
            if (q == m) {
                res.add(i - m + 1);
                q = pi[q - 1];
            }
        }
        return res;
    }

    private int[] computePrefixFunction(String pat) {
        int m = pat.length();
        int[] pi = new int[m];
        int k = 0;
        for (int i = 1; i < m; i++) {
            while (k > 0 && pat.charAt(k) != pat.charAt(i)) {
                k = pi[k - 1];
            }
            if (pat.charAt(k) == pat.charAt(i)) {
                ++k;
            }
            pi[i] = k;
        }
        return pi;
    }

    private int findLargestLE(java.util.ArrayList<Integer> list, int target) {
        int lo = 0;
        int hi = list.size() - 1;
        int ansIdx = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (list.get(mid) <= target) {
                ansIdx = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return ansIdx == -1 ? -1 : list.get(ansIdx);
    }

    private int findSmallestGE(java.util.ArrayList<Integer> list, int target) {
        int lo = 0;
        int hi = list.size() - 1;
        int ansIdx = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (list.get(mid) >= target) {
                ansIdx = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return ansIdx == -1 ? -1 : list.get(ansIdx);
    }
}
# @lc code=end