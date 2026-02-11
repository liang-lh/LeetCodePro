#
# @lc app=leetcode id=3695 lang=java
#
# [3695] Maximize Alternating Sum Using Swaps
#

# @lc code=start
import java.util.*;
class Solution {
    public long maxAlternatingSum(int[] nums, int[][] swaps) {
        int n = nums.length;
        UnionFind uf = new UnionFind(n);
        for (int[] sw : swaps) {
            uf.union(sw[0], sw[1]);
        }
        Map<Integer, List<Integer>> compValues = new HashMap<>();
        Map<Integer, Integer> compEvenCnt = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = uf.find(i);
            compValues.computeIfAbsent(root, k -> new ArrayList<>()).add(nums[i]);
            if ((i & 1) == 0) {
                compEvenCnt.put(root, compEvenCnt.getOrDefault(root, 0) + 1);
            }
        }
        long total = 0;
        for (Map.Entry<Integer, List<Integer>> entry : compValues.entrySet()) {
            List<Integer> vals = entry.getValue();
            vals.sort((a, b) -> Integer.compare(b, a));
            int rootKey = entry.getKey();
            int E = compEvenCnt.getOrDefault(rootKey, 0);
            int sz = vals.size();
            long contrib = 0;
            for (int j = 0; j < E; j++) {
                contrib += vals.get(j);
            }
            for (int j = E; j < sz; j++) {
                contrib -= vals.get(j);
            }
            total += contrib;
        }
        return total;
    }

    static class UnionFind {
        int[] parent;
        int[] rank;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) return;
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else {
                parent[py] = px;
                if (rank[px] == rank[py]) rank[px]++;
            }
        }
    }
}
# @lc code=end