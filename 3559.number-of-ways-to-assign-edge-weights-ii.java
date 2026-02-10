#
# @lc app=leetcode id=3559 lang=java
#
# [3559] Number of Ways to Assign Edge Weights II
#
# @lc code=start
import java.util.*;

class Solution {
    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        int n = edges.length + 1; // n nodes, n-1 edges
        int MOD = 1000000007;
        
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        int[] result = new int[queries.length];
        
        for (int i = 0; i < queries.length; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            
            // Find path length from u to v
            int pathLength = findPathLength(graph, n, u, v);
            
            if (pathLength == 0) {
                result[i] = 0;
            } else {
                // Calculate 2^(pathLength-1) mod MOD
                result[i] = (int) modPow(2, pathLength - 1, MOD);
            }
        }
        
        return result;
    }
    
    private int findPathLength(List<List<Integer>> graph, int n, int start, int end) {
        if (start == end) return 0;
        
        Queue<Integer> queue = new LinkedList<>();
        int[] dist = new int[n + 1];
        boolean[] visited = new boolean[n + 1];
        
        Arrays.fill(dist, -1);
        queue.offer(start);
        visited[start] = true;
        dist[start] = 0;
        
        while (!queue.isEmpty()) {
            int node = queue.poll();
            
            if (node == end) {
                return dist[end];
            }
            
            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    dist[neighbor] = dist[node] + 1;
                    queue.offer(neighbor);
                }
            }
        }
        
        return 0; // Should not reach here if tree is valid
    }
    
    private long modPow(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if (exp % 2 == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp /= 2;
        }
        return result;
    }
}
# @lc code=end