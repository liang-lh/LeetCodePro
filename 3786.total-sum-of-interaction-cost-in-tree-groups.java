#
# @lc app=leetcode id=3786 lang=java
#
# [3786] Total Sum of Interaction Cost in Tree Groups
#
# @lc code=start
import java.util.*;

class Solution {
    private List<List<Integer>> adj;
    private int[] group;
    private Map<Integer, Integer> totalCount;
    private long totalCost;
    
    public long interactionCosts(int n, int[][] edges, int[] group) {
        this.group = group;
        this.totalCost = 0;
        
        // Build adjacency list
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }
        
        // Count total nodes per group
        totalCount = new HashMap<>();
        for (int g : group) {
            totalCount.put(g, totalCount.getOrDefault(g, 0) + 1);
        }
        
        // DFS from root 0
        dfs(0, -1);
        
        return totalCost;
    }
    
    private Map<Integer, Integer> dfs(int node, int parent) {
        // Count of each group in the subtree rooted at node
        Map<Integer, Integer> subtreeCount = new HashMap<>();
        subtreeCount.put(group[node], 1);
        
        for (int child : adj.get(node)) {
            if (child == parent) continue;
            
            Map<Integer, Integer> childCount = dfs(child, node);
            
            // The edge (node, child) separates the tree
            // Calculate contribution of this edge
            for (int g : totalCount.keySet()) {
                int countInChild = childCount.getOrDefault(g, 0);
                int countOutside = totalCount.get(g) - countInChild;
                totalCost += (long) countInChild * countOutside;
            }
            
            // Merge childCount into subtreeCount
            for (Map.Entry<Integer, Integer> entry : childCount.entrySet()) {
                subtreeCount.put(entry.getKey(), 
                    subtreeCount.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }
        }
        
        return subtreeCount;
    }
}
# @lc code=end