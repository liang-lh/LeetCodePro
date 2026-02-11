#
# @lc app=leetcode id=3425 lang=java
#
# [3425] Longest Special Path
#

# @lc code=start
import java.util.*;

class Solution {
    public int[] longestSpecialPath(int[][] edges, int[] nums) {
        int n = nums.length;
        List<List<int[]>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            adj.get(u).add(new int[]{v, w});
            adj.get(v).add(new int[]{u, w});
        }
        int[] leftArr = {0};
        long[] maxLenArr = {0L};
        int[] minNodesArr = {1};
        List<Long> pathDist = new ArrayList<>();
        Map<Integer, Integer> valueToPos = new HashMap<>();

        class DfsHelper {
            public void dfs(int u, int parent, long edgeWeight, List<Long> pathDist, Map<Integer, Integer> valueToPos,
                            int[] leftArr, long[] maxLenArr, int[] minNodesArr,
                            List<List<int[]>> adj, int[] nums) {
                int depth = pathDist.size();
                int val = nums[u];
                int tempLeft = leftArr[0];
                Integer prevOcc = valueToPos.get(val);
                if (prevOcc != null && prevOcc >= leftArr[0]) {
                    tempLeft = Math.max(tempLeft, prevOcc + 1);
                }
                int oldLastPos = (prevOcc != null ? prevOcc : -1);
                long currDist = (depth == 0 ? 0L : pathDist.get(depth - 1)) + edgeWeight;
                pathDist.add(currDist);
                valueToPos.put(val, depth);
                long thisLen = pathDist.get(depth) - pathDist.get(tempLeft);
                int thisNodes = depth - tempLeft + 1;
                if (thisLen > maxLenArr[0]) {
                    maxLenArr[0] = thisLen;
                    minNodesArr[0] = thisNodes;
                } else if (thisLen == maxLenArr[0]) {
                    minNodesArr[0] = Math.min(minNodesArr[0], thisNodes);
                }
                int savedLeft = leftArr[0];
                leftArr[0] = tempLeft;
                for (int[] nei : adj.get(u)) {
                    int v = nei[0];
                    int w = nei[1];
                    if (v != parent) {
                        dfs(v, u, w, pathDist, valueToPos, leftArr, maxLenArr, minNodesArr, adj, nums);
                    }
                }
                leftArr[0] = savedLeft;
                pathDist.remove(pathDist.size() - 1);
                if (oldLastPos == -1) {
                    valueToPos.remove(val);
                } else {
                    valueToPos.put(val, oldLastPos);
                }
            }
        }
        DfsHelper helper = new DfsHelper();
        helper.dfs(0, -1, 0L, pathDist, valueToPos, leftArr, maxLenArr, minNodesArr, adj, nums);
        return new int[]{(int)maxLenArr[0], minNodesArr[0]};
    }
}
# @lc code=end