#
# @lc app=leetcode id=3575 lang=java
#
# [3575] Maximum Good Subtree Score
#

# @lc code=start
class Solution {
    public int goodSubtreeSum(int[] vals, int[] par) {
        final int MOD = 1000000007;
        int n = vals.length;
        int N = 1 << 10;
        boolean[] canUse = new boolean[n];
        int[] dMask = new int[n];
        for (int i = 0; i < n; ++i) {
            String s = Integer.toString(vals[i]);
            int[] count = new int[10];
            for (char c : s.toCharArray()) {
                ++count[c - '0'];
            }
            int mask = 0;
            boolean valid = true;
            for (int d = 0; d < 10; ++d) {
                if (count[d] > 1) {
                    valid = false;
                }
                if (count[d] > 0) {
                    mask |= (1 << d);
                }
            }
            canUse[i] = valid;
            dMask[i] = mask;
        }
        java.util.List<Integer>[] adj = new java.util.List[n];
        for (int i = 0; i < n; ++i) {
            adj[i] = new java.util.ArrayList<Integer>();
        }
        for (int i = 1; i < n; ++i) {
            adj[par[i]].add(i);
        }
        final long[] total = {0L};
        java.util.function.Function<Integer, long[]> dfsFunc = new java.util.function.Function<Integer, long[]>() {
            @Override
            public long[] apply(Integer u) {
                long[] dp = new long[N];
                java.util.Arrays.fill(dp, Long.MIN_VALUE / 2);
                dp[0] = 0L;
                for (int v : adj[u]) {
                    long[] childDp = apply(v);
                    long[] newDp = new long[N];
                    java.util.Arrays.fill(newDp, Long.MIN_VALUE / 2);
                    for (int m1 = 0; m1 < N; ++m1) {
                        if (dp[m1] == Long.MIN_VALUE / 2) continue;
                        for (int m2 = 0; m2 < N; ++m2) {
                            if (childDp[m2] == Long.MIN_VALUE / 2) continue;
                            if ((m1 & m2) == 0) {
                                int newMask = m1 | m2;
                                newDp[newMask] = Math.max(newDp[newMask], dp[m1] + childDp[m2]);
                            }
                        }
                    }
                    dp = newDp;
                }
                long[] fullDp = java.util.Arrays.copyOf(dp, N);
                if (canUse[u]) {
                    int mu = dMask[u];
                    for (int m = 0; m < N; ++m) {
                        if (dp[m] == Long.MIN_VALUE / 2) continue;
                        if ((m & mu) == 0) {
                            int nm = m | mu;
                            fullDp[nm] = Math.max(fullDp[nm], dp[m] + (long) vals[u]);
                        }
                    }
                }
                long maxS = 0L;
                for (long v : fullDp) {
                    maxS = Math.max(maxS, v);
                }
                total[0] = (total[0] + maxS % MOD + MOD) % MOD;
                return fullDp;
            }
        };
        dfsFunc.apply(0);
        return (int) total[0];
    }
}
# @lc code=end