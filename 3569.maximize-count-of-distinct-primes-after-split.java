#
# @lc app=leetcode id=3569 lang=java
#
# [3569] Maximize Count of Distinct Primes After Split
#

# @lc code=start
class Solution {
    static class SegTree {
        private int n;
        private int[] tree;
        private int[] lazy;

        public SegTree(int _n) {
            n = _n;
            tree = new int[4 * (_n + 1)];
            lazy = new int[4 * (_n + 1)];
        }

        private void push(int node, int start, int end) {
            if (lazy[node] != 0) {
                tree[node] += lazy[node];
                if (start != end) {
                    lazy[node * 2] += lazy[node];
                    lazy[node * 2 + 1] += lazy[node];
                }
                lazy[node] = 0;
            }
        }

        private void update(int node, int start, int end, int l, int r, int val) {
            push(node, start, end);
            if (start > end || start > r || end < l) {
                return;
            }
            if (start >= l && end <= r) {
                lazy[node] += val;
                push(node, start, end);
                return;
            }
            int mid = (start + end) / 2;
            update(node * 2, start, mid, l, r, val);
            update(node * 2 + 1, mid + 1, end, l, r, val);
            tree[node] = Math.max(tree[node * 2], tree[node * 2 + 1]);
        }

        private int query(int node, int start, int end, int l, int r) {
            push(node, start, end);
            if (start > end || start > r || end < l) {
                return Integer.MIN_VALUE / 2;
            }
            if (start >= l && end <= r) {
                return tree[node];
            }
            int mid = (start + end) / 2;
            int p1 = query(node * 2, start, mid, l, r);
            int p2 = query(node * 2 + 1, mid + 1, end, l, r);
            return Math.max(p1, p2);
        }

        public void update(int l, int r, int val) {
            update(1, 1, n, l, r, val);
        }

        public int query(int l, int r) {
            return query(1, 1, n, l, r);
        }
    }

    public int[] maximumCount(int[] nums, int[][] queries) {
        int n = nums.length;
        final int MAXV = 100010;
        boolean[] isPrime = new boolean[MAXV];
        for (int i = 2; i < MAXV; i++) {
            isPrime[i] = true;
        }
        isPrime[0] = isPrime[1] = false;
        for (long i = 2; i * i < MAXV; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < MAXV; j += i) {
                    isPrime[(int) j] = false;
                }
            }
        }

        java.util.HashMap<Integer, java.util.TreeSet<Integer>> posMap = new java.util.HashMap<>();
        int totalDistinct = 0;
        for (int i = 0; i < n; i++) {
            int v = nums[i];
            if (v < MAXV && isPrime[v]) {
                java.util.TreeSet<Integer> s = posMap.computeIfAbsent(v, java.util.TreeSet::new);
                if (s.isEmpty()) {
                    totalDistinct++;
                }
                s.add(i);
            }
        }

        SegTree st = new SegTree(n - 1);
        for (var entry : posMap.entrySet()) {
            java.util.TreeSet<Integer> s = entry.getValue();
            if (s.size() >= 2) {
                int L = s.first() + 1;
                int R = s.last();
                st.update(L, R, 1);
            }
        }

        int[] ans = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int idx = queries[q][0];
            int val = queries[q][1];
            int oldv = nums[idx];
            nums[idx] = val;

            // handle old
            if (oldv < MAXV && isPrime[oldv]) {
                java.util.TreeSet<Integer> s = posMap.get(oldv);
                if (s.size() >= 2) {
                    int L = s.first() + 1;
                    int R = s.last();
                    st.update(L, R, -1);
                }
                s.remove(idx);
                if (s.isEmpty()) {
                    posMap.remove(oldv);
                    totalDistinct--;
                } else if (s.size() >= 2) {
                    int L = s.first() + 1;
                    int R = s.last();
                    st.update(L, R, 1);
                }
            }

            // handle new
            if (val < MAXV && isPrime[val]) {
                java.util.TreeSet<Integer> s = posMap.computeIfAbsent(val, java.util.TreeSet::new);
                boolean wasEmpty = s.isEmpty();
                if (s.size() >= 2) {
                    int L = s.first() + 1;
                    int R = s.last();
                    st.update(L, R, -1);
                }
                s.add(idx);
                if (wasEmpty) {
                    totalDistinct++;
                }
                if (s.size() >= 2) {
                    int L = s.first() + 1;
                    int R = s.last();
                    st.update(L, R, 1);
                }
            }

            int maxIntersect = st.query(1, n - 1);
            ans[q] = totalDistinct + maxIntersect;
        }
        return ans;
    }
}
# @lc code=end