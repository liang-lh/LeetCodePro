#
# @lc app=leetcode id=3425 lang=java
#
# [3425] Longest Special Path
#
# @lc code=start
class Solution {
    private long maxLen = 0;
    private int minNodes = 1;
    
    public int[] longestSpecialPath(int[][] edges, int[] nums) {
        int n = nums.length;
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] e : edges) {
            graph.get(e[0]).add(new int[]{e[1], e[2]});
            graph.get(e[1]).add(new int[]{e[0], e[2]});
        }
        
        dfs(0, -1, graph, nums, new ArrayList<>(), new HashMap<>());
        
        return new int[]{(int)maxLen, minNodes};
    }
    
    private void dfs(int u, int parent, List<List<int[]>> graph, int[] nums,
                     List<Integer> pathEdges, Map<Integer, Integer> valueToDepth) {
        int val = nums[u];
        int currentDepth = pathEdges.size();
        
        int validStartDepth = 0;
        if (valueToDepth.containsKey(val)) {
            validStartDepth = valueToDepth.get(val) + 1;
        }
        
        long pathLen = 0;
        for (int i = validStartDepth; i < currentDepth; i++) {
            pathLen += pathEdges.get(i);
        }
        
        int nodeCount = currentDepth - validStartDepth + 1;
        
        if (pathLen > maxLen || (pathLen == maxLen && nodeCount < minNodes)) {
            maxLen = pathLen;
            minNodes = nodeCount;
        }
        
        int prevDepth = valueToDepth.getOrDefault(val, -1);
        valueToDepth.put(val, currentDepth);
        
        for (int[] next : graph.get(u)) {
            int v = next[0], edgeLen = next[1];
            if (v != parent) {
                pathEdges.add(edgeLen);
                dfs(v, u, graph, nums, pathEdges, valueToDepth);
                pathEdges.remove(pathEdges.size() - 1);
            }
        }
        
        if (prevDepth == -1) {
            valueToDepth.remove(val);
        } else {
            valueToDepth.put(val, prevDepth);
        }
    }
}
# @lc code=end