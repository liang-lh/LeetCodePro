#
# @lc app=leetcode id=3501 lang=java
#
# [3501] Maximize Active Section with Trade II
#

# @lc code=start
class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int total_ones = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') total_ones++;
        }
        java.util.ArrayList<Integer> ps_ = new java.util.ArrayList<>();
        java.util.ArrayList<Integer> qs_ = new java.util.ArrayList<>();
        java.util.ArrayList<Integer> ls_ = new java.util.ArrayList<>();
        java.util.ArrayList<Integer> rs_ = new java.util.ArrayList<>();
        for (int i = 0; i < n; ) {
            if (i > 0 && s.charAt(i) == '1' && s.charAt(i - 1) == '0') {
                int p = i;
                int q = i;
                while (q + 1 < n && s.charAt(q + 1) == '1') q++;
                if (q + 1 < n && s.charAt(q + 1) == '0') {
                    // left_len
                    int ll = 0;
                    for (int pos = p - 1; pos >= 0 && s.charAt(pos) == '0'; pos--) ll++;
                    // right_len
                    int rr = 0;
                    for (int pos = q + 1; pos < n && s.charAt(pos) == '0'; pos++) rr++;
                    ps_.add(p);
                    qs_.add(q);
                    ls_.add(ll);
                    rs_.add(rr);
                }
                i = q + 1;
            } else {
                i++;
            }
        }
        int m = ps_.size();
        if (m == 0) {
            java.util.List<Integer> answer = new java.util.ArrayList<>();
            for (int[] qu : queries) {
                answer.add(total_ones);
            }
            return answer;
        }
        int[] Ps = new int[m];
        int[] Qs = new int[m];
        int[] Ls = new int[m];
        int[] Rs = new int[m];
        int[] Vals = new int[m];
        for (int j = 0; j < m; j++) {
            Ps[j] = ps_.get(j);
            Qs[j] = qs_.get(j);
            Ls[j] = ls_.get(j);
            Rs[j] = rs_.get(j);
            Vals[j] = Ls[j] + Rs[j];
        }
        // sparse table
        int LOG = 18;
        int[][] sparse = new int[LOG][m];
        for (int j = 0; j < m; j++) {
            sparse[0][j] = Vals[j];
        }
        for (int k = 1; k < LOG; k++) {
            for (int j = 0; j + (1 << k) <= m; j++) {
                sparse[k][j] = Math.max(sparse[k - 1][j], sparse[k - 1][j + (1 << (k - 1))]);
            }
        }
        java.util.List<Integer> answer = new java.util.ArrayList<>();
        for (int[] qu : queries) {
            int l = qu[0];
            int r = qu[1];
            // jL: first Ps[j] > l
            int jL = 0;
            int jH = m;
            while (jL < jH) {
                int mid = (jL + jH) / 2;
                if (Ps[mid] > l) {
                    jH = mid;
                } else {
                    jL = mid + 1;
                }
            }
            // jR: first Qs[j] >= r , -1
            int low = 0;
            int hi = m;
            while (low < hi) {
                int mid = (low + hi) / 2;
                if (Qs[mid] >= r) {
                    hi = mid;
                } else {
                    low = mid + 1;
                }
            }
            int jR = low - 1;
            if (jL > jR) {
                answer.add(total_ones);
                continue;
            }
            int numc = jR - jL + 1;
            int mg = 0;
            if (numc <= 2) {
                for (int jj = jL; jj <= jR; jj++) {
                    int pj = Ps[jj];
                    int qj = Qs[jj];
                    int ljv = Ls[jj];
                    int rjv = Rs[jj];
                    int le = Math.min(pj - l, ljv);
                    int re = Math.min(r - qj, rjv);
                    mg = Math.max(mg, le + re);
                }
            } else {
                int lefti = jL + 1;
                int righti = jR - 1;
                int klen = righti - lefti + 1;
                int kk = 31 - Integer.numberOfLeadingZeros(klen);
                int imax = Math.max(sparse[kk][lefti], sparse[kk][righti - (1 << kk) + 1]);
                mg = imax;
                // jL
                {
                    int pj = Ps[jL];
                    int qj = Qs[jL];
                    int ljv = Ls[jL];
                    int rjv = Rs[jL];
                    int le = Math.min(pj - l, ljv);
                    int re = Math.min(r - qj, rjv);
                    mg = Math.max(mg, le + re);
                }
                // jR
                {
                    int pj = Ps[jR];
                    int qj = Qs[jR];
                    int ljv = Ls[jR];
                    int rjv = Rs[jR];
                    int le = Math.min(pj - l, ljv);
                    int re = Math.min(r - qj, rjv);
                    mg = Math.max(mg, le + re);
                }
            }
            answer.add(total_ones + mg);
        }
        return answer;
    }
}
# @lc code=end