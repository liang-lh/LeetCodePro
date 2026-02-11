#
# @lc app=leetcode id=3695 lang=java
#
# [3695] Maximize Alternating Sum Using Swaps
#

# @lc code=start
import java.util.*;
class Solution {
    private static class UF {
        int[] p;
        int[] rank;
        UF(int n) {
            p = new int[n];
            rank = new int[n];
            for(int i = 0; i < n; i++) {
                p[i] = i;
                rank[i] = 0;
            }
        }
        int find(int x) {
            if (p[x] != x) p[x] = find(p[x]);
            return p[x];
        }
        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) return;
            if (rank[px] < rank[py]) {
                p[px] = py;
            } else {
                p[py] = px;
                if (rank[px] == rank[py]) rank[px]++;
            }
        }
    }

    public long maxAlternatingSum(int[] nums, int[][] swaps) {
        int n = nums.length;
        UF uf = new UF(n);
        for (int[] sw : swaps) {
            uf.union(sw[0], sw[1]);
        }
        Map<Integer, List<Integer>> compValues = new HashMap<>();
        Map<Integer, Integer> posCounts = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int r = uf.find(i);
            compValues.computeIfAbsent(r, k -> new ArrayList<>()).add(nums[i]);
            if (i % 2 == 0) {
                posCounts.put(r, posCounts.getOrDefault(r, 0) + 1);
            }
        }
        long ans = 0;
        for (Map.Entry<Integer, List<Integer>> entry : compValues.entrySet()) {
            List<Integer> vals = entry.getValue();
            int pc = posCounts.getOrDefault(entry.getKey(), 0);
            long S = 0;
            for (int v : vals) {
                S += (long) v;
            }
            Collections.sort(vals, Collections.reverseOrder());
            long sp = 0;
            int take = Math.min(pc, vals.size());
            for (int j = 0; j < take; j++) {
                sp += (long) vals.get(j);
            }
            ans += 2 * sp - S;
        }
        return ans;
    }
}
# @lc code=end