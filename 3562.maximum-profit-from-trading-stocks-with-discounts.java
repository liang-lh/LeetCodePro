//
// @lc app=leetcode id=3562 lang=java
//
// [3562] Maximum Profit from Trading Stocks with Discounts
//
import java.util.*;

// @lc code=start
class Solution {
    private Map<Integer, List<Integer>> children;
    private int[] present, future;
    private Integer[][][] dp;
    
    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
        this.present = present;
        this.future = future;
        this.children = new HashMap<>();
        this.dp = new Integer[n][budget + 1][2];
        
        // Build tree from hierarchy
        for (int i = 0; i < n; i++) {
            children.put(i, new ArrayList<>());
        }
        for (int[] edge : hierarchy) {
            int parent = edge[0] - 1;  // Convert to 0-indexed
            int child = edge[1] - 1;
            children.get(parent).add(child);
        }
        
        return dfs(0, budget, 0);
    }
    
    private int dfs(int node, int budget, int parentBought) {
        if (dp[node][budget][parentBought] != null) {
            return dp[node][budget][parentBought];
        }
        
        int cost = (parentBought == 1) ? present[node] / 2 : present[node];
        int profit = future[node] - cost;
        
        List<Integer> kids = children.get(node);
        
        // Option 1: Don't buy at this node
        int result = solveKnapsack(kids, budget, 0);
        
        // Option 2: Buy at this node
        if (budget >= cost) {
            int buyOption = profit + solveKnapsack(kids, budget - cost, 1);
            result = Math.max(result, buyOption);
        }
        
        dp[node][budget][parentBought] = result;
        return result;
    }
    
    private int solveKnapsack(List<Integer> kids, int budget, int parentBought) {
        if (kids.isEmpty()) return 0;
        
        int n = kids.size();
        int[][] knapsack = new int[n + 1][budget + 1];
        
        for (int i = 0; i < n; i++) {
            int child = kids.get(i);
            for (int b = 0; b <= budget; b++) {
                knapsack[i + 1][b] = knapsack[i][b];
                for (int spend = 0; spend <= b; spend++) {
                    int childProfit = dfs(child, spend, parentBought);
                    knapsack[i + 1][b] = Math.max(knapsack[i + 1][b], 
                                                   knapsack[i][b - spend] + childProfit);
                }
            }
        }
        
        return knapsack[n][budget];
    }
}
// @lc code=end