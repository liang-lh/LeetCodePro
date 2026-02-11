#
# @lc app=leetcode id=3501 lang=java
#
# [3501] Maximize Active Section with Trade II
#
# @lc code=start
import java.util.*;
class Solution {
    static class Island {
        int start, end, fullL, fullR, lstart, rend;
        Island(int s1, int e1, int fl, int fr, int ls, int re) {
            start = s1; end = e1; fullL = fl; fullR = fr; lstart = ls; rend = re;
        }
    }

    static class SegTree {
        int[] tree;
        int n;
        SegTree(int[] arr) {
            n = arr.length;
            tree = new int[4 * n];
            build(1, 0, n - 1, arr);
        }
        void build(int node, int s, int e, int[] arr) {
            if (s == e) {
                tree[node] = arr[s];
                return;
            }
            int m = (s + e) / 2;
            build(2 * node, s, m, arr);
            build(2 * node + 1, m + 1, e, arr);
            tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
        }
        int query(int node, int s, int e, int qs, int qe) {
            if (qs > e || qe < s) return Integer.MIN_VALUE / 2;
            if (qs <= s && e <= qe) return tree[node];
            int m = (s + e) / 2;
            return Math.max(query(2 * node, s, m, qs, qe), query(2 * node + 1, m + 1, e, qs, qe));
        }
        int get(int l, int r) {
            if (l > r) return Integer.MIN_VALUE / 2;
            return query(1, 0, n - 1, l, r);
        }
    }

    private int lowerBound(int[] arr, int val) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = (low + high) / 2;
            if (arr[mid] >= val) high = mid;
            else low = mid + 1;
        }
        return low;
    }

    private int upperBound(int[] arr, int val) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = (low + high) / 2;
            if (arr[mid] > val) high = mid;
            else low = mid + 1;
        }
        return low;
    }

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int total = 0;
        for (int j = 0; j < n; ++j) if (s.charAt(j) == '1') ++total;
        List<Island> islandsList = new ArrayList<>();
        int ii = 0;
        while (ii < n) {
            if (s.charAt(ii) == '0') {
                while (ii < n && s.charAt(ii) == '0') ++ii;
                continue;
            }
            int st = ii;
            while (ii < n && s.charAt(ii) == '1') ++ii;
            int en = ii - 1;
            // left hole: fullL, lstart = st - fullL
            int fl = 0, ls = -1;
            if (st > 0 && s.charAt(st - 1) == '0') {
                int j = st - 1;
                fl = 1;
                while (j > 0 && s.charAt(j - 1) == '0') {
                    --j;
                    ++fl;
                }
                ls = j;  // fix: start of left hole
            } else {
                continue;
            }
            // right hole: fullR, rend = en + fullR
            int fr = 0, re = -1;
            if (en < n - 1 && s.charAt(en + 1) == '0') {
                int j = en + 1;
                fr = 1;
                while (j < n - 1 && s.charAt(j + 1) == '0') {
                    ++j;
                    ++fr;
                }
                re = j;  // fix: end of right hole
            } else {
                continue;
            }
            islandsList.add(new Island(st, en, fl, fr, ls, re));
        }
        int m = islandsList.size();
        if (m == 0) {
            int ql = queries.length;
            List<Integer> ans = new ArrayList<>(ql);
            for (int q = 0; q < ql; ++q) ans.add(total);
            return ans;
        }
        int[] istart = new int[m];
        int[] iend = new int[m];
        int[] lstart_pos = new int[m];
        int[] rend_pos = new int[m];
        int[] fullL = new int[m];
        int[] fullR = new int[m];
        for (int k = 0; k < m; ++k) {
            Island isl = islandsList.get(k);
            istart[k] = isl.start;
            iend[k] = isl.end;
            lstart_pos[k] = isl.lstart;
            rend_pos[k] = isl.rend;
            fullL[k] = isl.fullL;
            fullR[k] = isl.fullR;
        }
        int[] valA = new int[m];  // st + fullR
        int[] valB = new int[m];  // st - en
        int[] valC = new int[m];  // fullL + fullR
        int[] valD = new int[m];  // fullL - en
        for (int k = 0; k < m; ++k) {
            valA[k] = istart[k] + fullR[k];
            valB[k] = istart[k] - iend[k];
            valC[k] = fullL[k] + fullR[k];
            valD[k] = fullL[k] - iend[k];
        }
        SegTree segA = new SegTree(valA);
        SegTree segB = new SegTree(valB);
        SegTree segC = new SegTree(valC);
        SegTree segD = new SegTree(valD);
        int qlen = queries.length;
        List<Integer> ans = new ArrayList<>(qlen);
        for (int q = 0; q < qlen; ++q) {
            int li = queries[q][0];
            int ri = queries[q][1];
            int idxL = lowerBound(istart, li + 1);
            int idxR = upperBound(iend, ri - 1) - 1;
            if (idxL > idxR) {
                ans.add(total);
                continue;
            }
            int pp = lowerBound(lstart_pos, li);
            int qq = upperBound(rend_pos, ri) - 1;
            int mg = 0;
            // Case A: left partial (lstart < li), right full (rend <= ri)
            {
                int al = idxL;
                int ar = Math.min(idxR, Math.min(pp - 1, qq));
                if (al <= ar) {
                    int vmax = segA.get(al, ar);
                    int ga = vmax - li;
                    mg = Math.max(mg, ga);
                }
            }
            // Case B: left partial, right partial
            {
                int bl = Math.max(idxL, qq + 1);
                int br = Math.min(idxR, pp - 1);
                if (bl <= br) {
                    int vmax = segB.get(bl, br);
                    int gb = vmax + ri - li;
                    mg = Math.max(mg, gb);
                }
            }
            // Case C: left full, right full
            {
                int cl = Math.max(idxL, pp);
                int cr = Math.min(idxR, qq);
                if (cl <= cr) {
                    int vmax = segC.get(cl, cr);
                    mg = Math.max(mg, vmax);
                }
            }
            // Case D: left full, right partial
            {
                int dl = Math.max(idxL, pp);
                int dr = idxR;
                dl = Math.max(dl, qq + 1);
                if (dl <= dr) {
                    int vmax = segD.get(dl, dr);
                    int gd = vmax + ri;
                    mg = Math.max(mg, gd);
                }
            }
            ans.add(total + Math.max(0, mg));
        }
        return ans;
    }
}
# @lc code=end