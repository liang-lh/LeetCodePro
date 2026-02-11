#
# @lc app=leetcode id=3525 lang=java
#
# [3525] Find X Value of Array II
#

# @lc code=start
class Solution {
    private static class SegTree {
        int n;
        int kk;
        int[] totalTree;
        int[][] prefixTree;

        public SegTree(int nn, int kkk) {
            n = nn;
            kk = kkk;
            totalTree = new int[4 * (nn + 1)];
            prefixTree = new int[4 * (nn + 1)][6];
        }

        void build(int node, int l, int r, int[] mods) {
            if (l == r) {
                for (int i = 0; i < kk; i++) {
                    prefixTree[node][i] = 0;
                }
                int md = mods[l];
                prefixTree[node][md] = 1;
                totalTree[node] = md;
                return;
            }
            int mid = (l + r) >> 1;
            build(2 * node, l, mid, mods);
            build(2 * node + 1, mid + 1, r, mods);
            merge(node, 2 * node, 2 * node + 1);
        }

        void merge(int p, int leftn, int rightn) {
            for (int i = 0; i < kk; i++) {
                prefixTree[p][i] = prefixTree[leftn][i];
            }
            totalTree[p] = (int) ((long) totalTree[leftn] * totalTree[rightn] % kk);
            for (int qq = 0; qq < kk; qq++) {
                int cnt = prefixTree[rightn][qq];
                if (cnt != 0) {
                    int newm = (int) ((long) totalTree[leftn] * qq % kk);
                    prefixTree[p][newm] += cnt;
                }
            }
        }

        void update(int node, int l, int r, int idx, int newmod) {
            if (l == r) {
                for (int i = 0; i < kk; i++) {
                    prefixTree[node][i] = 0;
                }
                prefixTree[node][newmod] = 1;
                totalTree[node] = newmod;
                return;
            }
            int mid = (l + r) >> 1;
            if (idx <= mid) {
                update(2 * node, l, mid, idx, newmod);
            } else {
                update(2 * node + 1, mid + 1, r, idx, newmod);
            }
            merge(node, 2 * node, 2 * node + 1);
        }

        private void get_nodes(int node, int nl, int nr, int ql, int qr, int[] covers, int[] cidx) {
            if (ql > nr || qr < nl) return;
            if (ql <= nl && nr <= qr) {
                covers[cidx[0]] = node;
                cidx[0]++;
                return;
            }
            int mid = (nl + nr) >> 1;
            get_nodes(2 * node, nl, mid, ql, qr, covers, cidx);
            get_nodes(2 * node + 1, mid + 1, nr, ql, qr, covers, cidx);
        }

        int get_count(int starti, int xi) {
            int[] covers = new int[32];
            int[] cidx = new int[1];
            get_nodes(1, 0, n - 1, starti, n - 1, covers, cidx);
            int numc = cidx[0];
            int[] curr_pc = new int[kk];
            int curr_tp = 0;
            boolean first = true;
            for (int ii = 0; ii < numc; ii++) {
                int tnode = covers[ii];
                if (first) {
                    for (int j = 0; j < kk; j++) {
                        curr_pc[j] = prefixTree[tnode][j];
                    }
                    curr_tp = totalTree[tnode];
                    first = false;
                    continue;
                }
                int[] temp_pc = new int[kk];
                for (int j = 0; j < kk; j++) {
                    temp_pc[j] = curr_pc[j];
                }
                int next_tp = totalTree[tnode];
                for (int qq = 0; qq < kk; qq++) {
                    int cnt = prefixTree[tnode][qq];
                    if (cnt != 0) {
                        int newm = (int) ((long) curr_tp * qq % kk);
                        temp_pc[newm] += cnt;
                    }
                }
                curr_tp = (int) ((long) curr_tp * next_tp % kk);
                curr_pc = temp_pc;
            }
            return curr_pc[xi];
        }

        void update(int idx, int newmod) {
            update(1, 0, n - 1, idx, newmod);
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        int n = nums.length;
        SegTree st = new SegTree(n, k);
        int[] mods = new int[n];
        for (int i = 0; i < n; i++) {
            mods[i] = nums[i] % k;
        }
        st.build(1, 0, n - 1, mods);
        int[] result = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            int newmod = q[1] % k;
            st.update(q[0], newmod);
            result[i] = st.get_count(q[2], q[3]);
        }
        return result;
    }
}
# @lc code=end
