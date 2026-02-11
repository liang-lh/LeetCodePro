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
        int[] degree = new int[n];
        for (int i = 1; i < n; i++) {
            degree[par[i]]++;
        }
        int[][] chld = new int[n][];
        int[] ptr = new int[n];
        for (int i = 0; i < n; i++) {
            chld[i] = new int[degree[i]];
        }
        for (int i = 1; i < n; i++) {
            int p = par[i];
            chld[p][ptr[p]++] = i;
        }
        int[] masks = new int[n];
        long[] nodevals = new long[n];
        for (int i = 0; i < n; i++) {
            String s = Integer.toString(vals[i]);
            boolean[] digUsed = new boolean[10];
            boolean dup = false;
            int msk = 0;
            for (int k = 0; k < s.length(); k++) {
                int d = s.charAt(k) - '0';
                if (digUsed[d]) {
                    dup = true;
                    break;
                }
                digUsed[d] = true;
                msk |= (1 << d);
            }
            if (dup) {
                nodevals[i] = 0L;
                masks[i] = 0;
            } else {
                nodevals[i] = vals[i];
                masks[i] = msk;
            }
        }
        long[] maxScore = new long[n];
        int MS = 1 << 10;
        class DfsHelper {
            long[] dfs(int u, int[] masks, long[] nodevals, int[][] chld, long[] maxScore, int MS) {
                long[] curr = new long[MS];
                for (int i = 0; i < MS; i++) {
                    curr[i] = -1L;
                }
                curr[0] = 0L;
                for (int ci = 0; ci < chld[u].length; ci++) {
                    int v = chld[u][ci];
                    long[] chdp = dfs(v, masks, nodevals, chld, maxScore, MS);
                    long[] nextt = new long[MS];
                    for (int i = 0; i < MS; i++) {
                        nextt[i] = -1L;
                    }
                    java.util.ArrayList<java.lang.Integer> currValid = new java.util.ArrayList<java.lang.Integer>();
                    for (int m1 = 0; m1 < MS; m1++) {
                        if (curr[m1] >= 0L) {
                            currValid.add(m1);
                        }
                    }
                    java.util.ArrayList<java.lang.Integer> chValid = new java.util.ArrayList<java.lang.Integer>();
                    for (int m2 = 0; m2 < MS; m2++) {
                        if (chdp[m2] >= 0L) {
                            chValid.add(m2);
                        }
                    }
                    long est = (long) currValid.size() * chValid.size();
                    if (est > 500000L) {
                        // full loop
                        for (int m1 = 0; m1 < MS; m1++) {
                            if (curr[m1] < 0L) continue;
                            for (int m2 = 0; m2 < MS; m2++) {
                                if (chdp[m2] < 0L) continue;
                                if ((m1 & m2) != 0) continue;
                                int nm = m1 | m2;
                                long ns = curr[m1] + chdp[m2];
                                if (nextt[nm] < ns) {
                                    nextt[nm] = ns;
                                }
                            }
                        }
                    } else {
                        // list loop
                        for (int j = 0; j < currValid.size(); j++) {
                            int m1 = currValid.get(j);
                            for (int k = 0; k < chValid.size(); k++) {
                                int m2 = chValid.get(k);
                                if ((m1 & m2) == 0) {
                                    int nm = m1 | m2;
                                    long ns = curr[m1] + chdp[m2];
                                    if (nextt[nm] < ns) {
                                        nextt[nm] = ns;
                                    }
                                }
                            }
                        }
                    }
                    curr = nextt;
                }
                long maxU = 0L;
                for (int m = 0; m < MS; m++) {
                    if (curr[m] > maxU) {
                        maxU = curr[m];
                    }
                }
                int omask = masks[u];
                long oval = nodevals[u];
                long maxAddU = 0L;
                for (int m = 0; m < MS; m++) {
                    if (curr[m] < 0L) continue;
                    if ((m & omask) == 0) {
                        maxAddU = Math.max(maxAddU, curr[m] + oval);
                    }
                }
                maxU = Math.max(maxU, maxAddU);
                maxScore[u] = maxU;
                for (int m = 0; m < MS; m++) {
                    if (curr[m] < 0L) continue;
                    if ((m & omask) == 0) {
                        int nm = m | omask;
                        long ns = curr[m] + oval;
                        if (curr[nm] < ns) {
                            curr[nm] = ns;
                        }
                    }
                }
                return curr;
            }
        }
        DfsHelper helper = new DfsHelper();
        helper.dfs(0, masks, nodevals, chld, maxScore, MS);
        long ans = 0L;
        for (long x : maxScore) {
            ans = (ans + x) % MOD;
        }
        return (int) ans;
    }
}
# @lc code=end