#
# @lc app=leetcode id=3530 lang=golang
#
# [3530] Maximum Profit from Valid Topological Order in DAG
#
# @lc code=start
func maxProfit(n int, edges [][]int, score []int) int {
    // Build adjacency list (predecessors for each node)
    predecessors := make([][]int, n)
    for _, edge := range edges {
        u, v := edge[0], edge[1]
        predecessors[v] = append(predecessors[v], u)
    }
    
    // DP with bitmask
    // dp[mask] = max profit when nodes in mask have been processed
    dp := make([]int, 1<<n)
    for i := range dp {
        dp[i] = -1
    }
    dp[0] = 0
    
    for mask := 0; mask < (1 << n); mask++ {
        if dp[mask] == -1 {
            continue
        }
        
        // Count number of nodes already processed (1-based position for next node)
        position := 0
        for i := 0; i < n; i++ {
            if mask & (1 << i) != 0 {
                position++
            }
        }
        position++ // Convert to 1-based position for next node
        
        // Try adding each unprocessed node
        for node := 0; node < n; node++ {
            if mask & (1 << node) != 0 {
                continue // already processed
            }
            
            // Check if all predecessors are processed
            canAdd := true
            for _, pred := range predecessors[node] {
                if mask & (1 << pred) == 0 {
                    canAdd = false
                    break
                }
            }
            
            if canAdd {
                newMask := mask | (1 << node)
                newProfit := dp[mask] + score[node] * position
                if dp[newMask] < newProfit {
                    dp[newMask] = newProfit
                }
            }
        }
    }
    
    return dp[(1<<n)-1]
}
# @lc code=end