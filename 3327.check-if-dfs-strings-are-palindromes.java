#
# @lc app=leetcode id=3327 lang=java
#
# [3327] Check if DFS Strings Are Palindromes
#

# @lc code=start
class Solution {
    public boolean[] findAnswer(int[] parent, String s) {
        int n = s.length();
        int[] numChildren = new int[n];
        for (int i = 1; i < n; ++i) {
            ++numChildren[parent[i]];
        }
        int[][] children = new int[n][];
        for (int i = 0; i < n; ++i) {
            children[i] = new int[numChildren[i]];
        }
        int[] childIndex = new int[n];
        for (int i = 1; i < n; ++i) {
            int p = parent[i];
            children[p][childIndex[p]++] = i;
        }
        for (int i = 0; i < n; ++i) {
            if (numChildren[i] > 1) {
                java.util.Arrays.sort(children[i], 0, numChildren[i]);
            }
        }
        long MOD1 = 1000000007L;
        long MOD2 = 1000000009L;
        long BASE1 = 131L;
        long BASE2 = 137L;
        long[] pow1 = new long[n + 1];
        long[] pow2 = new long[n + 1];
        pow1[0] = 1L;
        pow2[0] = 1L;
        for (int i = 1; i <= n; ++i) {
            pow1[i] = pow1[i - 1] * BASE1 % MOD1;
            pow2[i] = pow2[i - 1] * BASE2 % MOD2;
        }
        int[] sz = new int[n];
        long[] fwd1 = new long[n];
        long[] fwd2 = new long[n];
        long[] rev1 = new long[n];
        long[] rev2 = new long[n];
        int[] unprocessedChildren = new int[n];
        for (int i = 0; i < n; ++i) {
            unprocessedChildren[i] = children[i].length;
        }
        java.util.Queue<Integer> q = new java.util.ArrayDeque<Integer>();
        for (int i = 0; i < n; ++i) {
            if (unprocessedChildren[i] == 0) {
                q.offer(i);
            }
        }
        while (!q.isEmpty()) {
            int node = q.poll();
            long h1 = 0L;
            long h2 = 0L;
            int totalSz = 0;
            int[] ch = children[node];
            int m = ch.length;
            for (int j = 0; j < m; ++j) {
                int y = ch[j];
                h1 = (h1 * pow1[sz[y]] % MOD1 + fwd1[y]) % MOD1;
                h2 = (h2 * pow2[sz[y]] % MOD2 + fwd2[y]) % MOD2;
                totalSz += sz[y];
            }
            long sch = (long)(s.charAt(node) - 'a' + 1);
            h1 = (h1 * pow1[1] % MOD1 + sch) % MOD1;
            h2 = (h2 * pow2[1] % MOD2 + sch) % MOD2;
            fwd1[node] = h1;
            fwd2[node] = h2;
            sz[node] = totalSz + 1;
            // reverse hash
            long hr1 = sch;
            long hr2 = sch;
            for (int j = m - 1; j >= 0; --j) {
                int y = ch[j];
                hr1 = (hr1 * pow1[sz[y]] % MOD1 + rev1[y]) % MOD1;
                hr2 = (hr2 * pow2[sz[y]] % MOD2 + rev2[y]) % MOD2;
            }
            rev1[node] = hr1;
            rev2[node] = hr2;
            // notify parent
            if (parent[node] != -1) {
                unprocessedChildren[parent[node]]--;
                if (unprocessedChildren[parent[node]] == 0) {
                    q.offer(parent[node]);
                }
            }
        }
        boolean[] answer = new boolean[n];
        for (int i = 0; i < n; ++i) {
            answer[i] = fwd1[i] == rev1[i] && fwd2[i] == rev2[i];
        }
        return answer;
    }
}
# @lc code=end