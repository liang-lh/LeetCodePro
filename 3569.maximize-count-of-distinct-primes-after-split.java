#
# @lc app=leetcode id=3569 lang=java
#
# [3569] Maximize Count of Distinct Primes After Split
#

# @lc code=start
class Solution {
    private static boolean[] computePrimes(int maxVal) {
        boolean[] primes = new boolean[maxVal];
        java.util.Arrays.fill(primes, true);
        primes[0] = primes[1] = false;
        for (int i = 2; i * i < maxVal; i++) {
            if (primes[i]) {
                for (int j = i * i; j < maxVal; j += i) {
                    primes[j] = false;
                }
            }
        }
        return primes;
    }

    private static class SegmentTree {
        private int n;
        private long[] tree;
        private long[] lazy;

        public SegmentTree(int nn) {
            n = nn;
            tree = new long[4 * nn + 10];
            lazy = new long[4 * nn + 10];
        }

        private void push(int node, int start, int end) {
            if (lazy[node] != 0) {
                tree[node] += lazy[node];
                if (start != end) {
                    lazy[2 * node] += lazy[node];
                    lazy[2 * node + 1] += lazy[node];
                }
                lazy[node] = 0;
            }
        }

        private void updateRange(int node, int start, int end, int l, int r, long val) {
            push(node, start, end);
            if (start > end || start > r || end < l) return;
            if (start >= l && end <= r) {
                lazy[node] += val;
                push(node, start, end);
                return;
            }
            int mid = (start + end) / 2;
            updateRange(2 * node, start, mid, l, r, val);
            updateRange(2 * node + 1, mid + 1, end, l, r, val);
            tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
        }

        public void update(int l, int r, long val) {
            if (l > r || l < 1 || r > n - 1) return;
            updateRange(1, 1, n - 1, l, r, val);
        }

        private long queryMax(int node, int start, int end, int l, int r) {
            push(node, start, end);
            if (start > end || start > r || end < l) return Long.MIN_VALUE / 2;
            if (start >= l && end <= r) {
                return tree[node];
            }
            int mid = (start + end) / 2;
            long p1 = queryMax(2 * node, start, mid, l, r);
            long p2 = queryMax(2 * node + 1, mid + 1, end, l, r);
            return Math.max(p1, p2);
        }

        public long getMax() {
            return queryMax(1, 1, n - 1, 1, n - 1);
        }
    }

    public int[] maximumCount(int[] nums, int[][] queries) {
        int n = nums.length;
        int MAX_VAL = 100010;
        boolean[] isPrime = computePrimes(MAX_VAL);
        java.util.Map<Integer, java.util.TreeSet<Integer>> positions = new java.util.HashMap<>();
        int totalDistinctPrimes = 0;
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            if (num < MAX_VAL && isPrime[num]) {
                java.util.TreeSet<Integer> set = positions.computeIfAbsent(num, k -> new java.util.TreeSet<>());
                if (set.isEmpty()) {
                    totalDistinctPrimes++;
                }
                set.add(i);
            }
        }
        SegmentTree st = new SegmentTree(n);
        for (var entry : positions.entrySet()) {
            java.util.TreeSet<Integer> set = entry.getValue();
            if (set.size() >= 2) {
                int left = set.first();
                int right = set.last();
                st.update(left + 1, right, 1L);
            }
        }
        int[] answer = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            int idx = q[0];
            int newVal = q[1];
            int oldVal = nums[idx];
            nums[idx] = newVal;
            // Handle oldVal removal
            if (oldVal < MAX_VAL && isPrime[oldVal]) {
                java.util.TreeSet<Integer> set = positions.get(oldVal);
                if (set != null) {
                    boolean hadContribution = set.size() >= 2;
                    int oldLeft = hadContribution ? set.first() : -1;
                    int oldRight = hadContribution ? set.last() : -1;
                    set.remove(idx);
                    if (set.isEmpty()) {
                        positions.remove(oldVal);
                        totalDistinctPrimes--;
                    } else {
                        boolean hasContribution = set.size() >= 2;
                        int newLeft = hasContribution ? set.first() : -1;
                        int newRight = hasContribution ? set.last() : -1;
                        if (hadContribution) {
                            st.update(oldLeft + 1, oldRight, -1L);
                        }
                        if (hasContribution) {
                            st.update(newLeft + 1, newRight, 1L);
                        }
                    }
                }
            }
            // Handle newVal addition
            if (newVal < MAX_VAL && isPrime[newVal]) {
                java.util.TreeSet<Integer> set = positions.computeIfAbsent(newVal, k -> new java.util.TreeSet<>());
                boolean wasEmpty = set.isEmpty();
                boolean hadContributionBefore = set.size() >= 2;
                int leftBefore = hadContributionBefore ? set.first() : -1;
                int rightBefore = hadContributionBefore ? set.last() : -1;
                set.add(idx);
                boolean hasContributionAfter = set.size() >= 2;
                int leftAfter = hasContributionAfter ? set.first() : -1;
                int rightAfter = hasContributionAfter ? set.last() : -1;
                if (wasEmpty) {
                    totalDistinctPrimes++;
                }
                if (hadContributionBefore) {
                    st.update(leftBefore + 1, rightBefore, -1L);
                }
                if (hasContributionAfter) {
                    st.update(leftAfter + 1, rightAfter, 1L);
                }
            }
            long maxCrossing = st.getMax();
            answer[i] = totalDistinctPrimes + (int) maxCrossing;
        }
        return answer;
    }
}
# @lc code=end