#
# @lc app=leetcode id=3367 lang=java
#
# [3367] Maximize Sum of Weights after Edge Removals
#
# @lc code=start
class Solution {
    private List<List<int[]>> adj;
    private int K;
    private long[][] dp;
    
    public long maximizeSumOfWeights(int[][] edges, int k) {
        K = k;
        int n = edges.length + 1;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            adj.get(u).add(new int[]{v, w});
            adj.get(v).add(new int[]{u, w});
        }
        
        dp = new long[n][2];
        dfs(0, -1);
        return dp[0][0];
    }
    
    private void dfs(int v, int parent) {
        List<Long> gains = new ArrayList<>();
        long baseSum = 0;
        
        for (int[] edge : adj.get(v)) {
            int child = edge[0];
            int weight = edge[1];
            if (child == parent) continue;
            
            dfs(child, v);
            baseSum += dp[child][0];
            long gain = weight + dp[child][1] - dp[child][0];
            gains.add(gain);
        }
        
        Collections.sort(gains, Collections.reverseOrder());
        
        // dp[v][0]: can use up to k edges
        dp[v][0] = baseSum;
        for (int i = 0; i < Math.min(K, gains.size()); i++) {
            if (gains.get(i) > 0) {
                dp[v][0] += gains.get(i);
            } else {
                break; // Remaining gains are non-positive, skip them
            }
        }
        
        // dp[v][1]: can use up to k-1 edges
        dp[v][1] = baseSum;
        for (int i = 0; i < Math.min(K - 1, gains.size()); i++) {
            if (gains.get(i) > 0) {
                dp[v][1] += gains.get(i);
            } else {
                break; // Remaining gains are non-positive, skip them
            }
        }
    }
}
# @lc code=end