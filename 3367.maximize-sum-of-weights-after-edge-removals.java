#
# @lc app=leetcode id=3367 lang=java
#
# [3367] Maximize Sum of Weights after Edge Removals
#

import java.util.*;

# @lc code=start
class Solution {
    public long maximizeSumOfWeights(int[][] edges, int k) {
        int n = edges.length + 1;
        List<List<int[]>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>()) ;
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            adj.get(u).add(new int[]{v, w});
            adj.get(v).add(new int[]{u, w});
        }
        return dfs(0, -1, adj, k)[0];
    }

    private long[] dfs(int u, int p, List<List<int[]>> adj, int k) {
        List<Long> deltas = new ArrayList<>();
        long C = 0L;
        for (int[] pair : adj.get(u)) {
            int v = pair[0];
            int w = pair[1];
            if (v == p) continue;
            long[] res = dfs(v, u, adj, k);
            C += res[0];
            long keepv = (long) w + res[1];
            deltas.add(keepv - res[0]);
        }
        int m = deltas.size();
        Long[] del_arr = deltas.toArray(new Long[m]);
        Arrays.sort(del_arr, (a, b) -> Long.compare(b, a));
        // muk_local: up to k
        long muk_local = 0L;
        {
            long cum = 0L;
            for (int i = 0; i < m && i < k; i++) {
                if (del_arr[i] < 0L) break;
                cum += del_arr[i];
            }
            muk_local = cum;
        }
        // mum1_local: up to k-1
        long mum1_local = 0L;
        {
            long cum = 0L;
            int lim = k - 1;
            if (lim > 0) {
                for (int i = 0; i < m && i < lim; i++) {
                    if (del_arr[i] < 0L) break;
                    cum += del_arr[i];
                }
                mum1_local = cum;
            }
        }
        long total_k = C + muk_local;
        long total_km1 = C + mum1_local;
        return new long[]{total_k, total_km1};
    }
}
# @lc code=end