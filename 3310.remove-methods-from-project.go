#
# @lc app=leetcode id=3310 lang=golang
#
# [3310] Remove Methods From Project
#
# @lc code=start
func remainingMethods(n int, k int, invocations [][]int) []int {
    // Build adjacency list
    graph := make(map[int][]int)
    for _, inv := range invocations {
        graph[inv[0]] = append(graph[inv[0]], inv[1])
    }
    
    // Find all suspicious methods using DFS from k
    suspicious := make(map[int]bool)
    var dfs func(int)
    dfs = func(node int) {
        if suspicious[node] {
            return
        }
        suspicious[node] = true
        for _, neighbor := range graph[node] {
            dfs(neighbor)
        }
    }
    dfs(k)
    
    // Check if any non-suspicious method invokes a suspicious method
    canRemove := true
    for _, inv := range invocations {
        from, to := inv[0], inv[1]
        if !suspicious[from] && suspicious[to] {
            canRemove = false
            break
        }
    }
    
    // Build result
    var result []int
    if !canRemove {
        // Cannot remove, return all methods
        for i := 0; i < n; i++ {
            result = append(result, i)
        }
    } else {
        // Can remove, return non-suspicious methods
        for i := 0; i < n; i++ {
            if !suspicious[i] {
                result = append(result, i)
            }
        }
    }
    
    return result
}
# @lc code=end