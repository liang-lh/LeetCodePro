#
# @lc app=leetcode id=3544 lang=golang
#
# [3544] Subtree Inversion Sum
#
# @lc code=start
func subtreeInversionSum(edges [][]int, nums []int, k int) int64 {
    n := len(nums)
    
    // Build adjacency list from edges
    graph := make([][]int, n)
    for _, edge := range edges {
        u, v := edge[0], edge[1]
        graph[u] = append(graph[u], v)
        graph[v] = append(graph[v], u)
    }
    
    // Build rooted tree starting from node 0
    children := make([][]int, n)
    visited := make([]bool, n)
    
    var buildTree func(int)
    buildTree = func(node int) {
        visited[node] = true
        for _, neighbor := range graph[node] {
            if !visited[neighbor] {
                children[node] = append(children[node], neighbor)
                buildTree(neighbor)
            }
        }
    }
    buildTree(0)
    
    // Memoization for DP
    type State struct {
        node, cooldown, parity int
    }
    memo := make(map[State]int64)
    
    var dfs func(int, int, int) int64
    dfs = func(node, cooldown, parity int) int64 {
        state := State{node, cooldown, parity}
        if val, exists := memo[state]; exists {
            return val
        }
        
        // Current node value with parity applied
        val := int64(nums[node])
        if parity == 1 {
            val = -val
        }
        
        // Option 1: Don't invert this node
        result := val
        for _, child := range children[node] {
            nextCooldown := cooldown
            if nextCooldown > 0 {
                nextCooldown--
            }
            result += dfs(child, nextCooldown, parity)
        }
        
        // Option 2: Invert this node (if allowed)
        if cooldown == 0 {
            inverted := -val
            for _, child := range children[node] {
                inverted += dfs(child, k-1, 1-parity)
            }
            if inverted > result {
                result = inverted
            }
        }
        
        memo[state] = result
        return result
    }
    
    return dfs(0, 0, 0)
}
# @lc code=end