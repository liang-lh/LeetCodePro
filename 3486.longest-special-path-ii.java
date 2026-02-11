#
# @lc app=leetcode id=3486 lang=java
#
# [3486] Longest Special Path II
#

import java.util.*;

# @lc code=start
class Solution {
    static class Holder {
        long maxLen = 0;
        int minNodes = Integer.MAX_VALUE;
        int[] freq = new int[50001];
        int distinctCnt = 0;
        int left = 0;
        List<Integer> pathVals = new ArrayList<>();
        List<Long> prefixLens = new ArrayList<>();
    }

    interface TriConsumer {
        void accept(int u, int p, long cumW);
    }

    public int[] longestSpecialPath(int[][] edges, int[] nums) {
        int n = nums.length;
        @SuppressWarnings("unchecked")
        List<int[]>[] adj = new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            adj[u].add(new int[]{v, w});
            adj[v].add(new int[]{u, w});
        }
        Holder holder = new Holder();
        TriConsumer dfs = new TriConsumer() {
            @Override
            public void accept(int u, int p, long cumW) {
                int val = nums[u];
                holder.pathVals.add(val);
                holder.prefixLens.add(cumW);
                int pathSz = holder.pathVals.size();
                holder.freq[val]++;
                if (holder.freq[val] == 1) holder.distinctCnt++;
                while (holder.left < pathSz && holder.distinctCnt < (pathSz - holder.left) - 1) {
                    int lval = holder.pathVals.get(holder.left);
                    holder.freq[lval]--;
                    if (holder.freq[lval] == 0) holder.distinctCnt--;
                    holder.left++;
                }
                long plen = holder.prefixLens.get(pathSz - 1) - holder.prefixLens.get(holder.left);
                int wsz = pathSz - holder.left;
                if (plen > holder.maxLen || (plen == holder.maxLen && wsz < holder.minNodes)) {
                    holder.maxLen = plen;
                    holder.minNodes = wsz;
                }
                for (int[] nei : adj[u]) {
                    int v = nei[0];
                    if (v != p) {
                        int oldLeft = holder.left;
                        accept(v, u, cumW + nei[1]);
                        while (holder.left > oldLeft) {
                            int cand = holder.left - 1;
                            int cval = holder.pathVals.get(cand);
                            holder.freq[cval]++;
                            if (holder.freq[cval] == 1) holder.distinctCnt++;
                            holder.left = cand;
                        }
                    }
                }
                holder.freq[val]--;
                if (holder.freq[val] == 0) holder.distinctCnt--;
                holder.pathVals.remove(holder.pathVals.size() - 1);
                holder.prefixLens.remove(holder.prefixLens.size() - 1);
            }
        };
        dfs.accept(0, -1, 0L);
        return new int[]{(int) holder.maxLen, holder.minNodes};
    }
}
# @lc code=end