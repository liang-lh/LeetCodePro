#
# @lc app=leetcode id=3445 lang=java
#
# [3445] Maximum Difference Between Even and Odd Frequency II
#

# @lc code=start
class Solution {
    private static final int INF = 2000000000;
    private static final int MAXF = 30010;

    private static class SegTree {
        int[] tree;
        int nn;

        public SegTree(int _n) {
            nn = _n;
            tree = new int[4 * (nn + 1)];
            for (int i = 0; i < tree.length; i++) {
                tree[i] = INF;
            }
        }

        public void update(int idx, int val) {
            updateUtil(1, 0, nn, idx, val);
        }

        private void updateUtil(int node, int start, int end, int idx, int val) {
            if (start == end) {
                tree[node] = Math.min(tree[node], val);
                return;
            }
            int mid = (start + end) / 2;
            if (idx <= mid) {
                updateUtil(2 * node, start, mid, idx, val);
            } else {
                updateUtil(2 * node + 1, mid + 1, end, idx, val);
            }
            tree[node] = Math.min(tree[2 * node], tree[2 * node + 1]);
        }

        public int query(int left, int right) {
            if (left > right) return INF;
            return queryUtil(1, 0, nn, left, right);
        }

        private int queryUtil(int node, int start, int end, int left, int right) {
            if (right < start || end < left) return INF;
            if (left <= start && end <= right) return tree[node];
            int mid = (start + end) / 2;
            int p1 = queryUtil(2 * node, start, mid, left, right);
            int p2 = queryUtil(2 * node + 1, mid + 1, end, left, right);
            return Math.min(p1, p2);
        }
    }

    public int maxDifference(String s, int k) {
        int n = s.length();
        int[][] prefix = new int[5][n + 1];
        for (int i = 1; i <= n; ++i) {
            int ch = s.charAt(i - 1) - '0';
            for (int c = 0; c < 5; ++c) {
                prefix[c][i] = prefix[c][i - 1] + (c == ch ? 1 : 0);
            }
        }
        int ans = Integer.MIN_VALUE / 2;
        for (int a = 0; a < 5; ++a) {
            for (int b = 0; b < 5; ++b) {
                if (a == b) continue;
                int[] prea = prefix[a];
                int[] preb = prefix[b];
                int[] diff = new int[n + 1];
                for (int i = 0; i <= n; ++i) {
                    diff[i] = prea[i] - preb[i];
                }
                SegTree[] sts = new SegTree[4];
                for (int g = 0; g < 4; ++g) {
                    sts[g] = new SegTree(MAXF);
                }
                int pairMax = Integer.MIN_VALUE / 2;
                int curLmax = -1;
                for (int r = k - 1; r < n; ++r) {
                    int lmax = r - k + 1;
                    for (int newl = curLmax + 1; newl <= lmax; ++newl) {
                        int pa = prea[newl] % 2;
                        int pb = preb[newl] % 2;
                        int g = pa * 2 + pb;
                        int fbv = preb[newl];
                        if (fbv <= MAXF) {
                            sts[g].update(fbv, diff[newl]);
                        }
                    }
                    curLmax = lmax;
                    int paJ = prea[r + 1] % 2;
                    int pbJ = preb[r + 1] % 2;
                    int tpa = 1 - paJ;
                    int tpb = pbJ;
                    int g = tpa * 2 + tpb;
                    int M = preb[r + 1] - 2;
                    if (M >= 0) {
                        int minD = sts[g].query(0, Math.min(M, MAXF));
                        if (minD < INF) {
                            int cand = diff[r + 1] - minD;
                            pairMax = Math.max(pairMax, cand);
                        }
                    }
                }
                ans = Math.max(ans, pairMax);
            }
        }
        return ans == Integer.MIN_VALUE / 2 ? -1 : ans;
    }
}
# @lc code=end