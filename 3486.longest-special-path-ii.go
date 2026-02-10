#
# @lc app=leetcode id=3486 lang=golang
#
# [3486] Longest Special Path II
#
# @lc code=start
func longestSpecialPath(edges [][]int, nums []int) []int {
    n := len(nums)
    
    // Build adjacency list
    graph := make([][][2]int, n)
    for _, edge := range edges {
        u, v, length := edge[0], edge[1], edge[2]
        graph[u] = append(graph[u], [2]int{v, length})
        graph[v] = append(graph[v], [2]int{u, length})
    }
    
    // Establish parent-child relationships via BFS from root 0
    parent := make([]int, n)
    children := make([][][2]int, n)
    visited := make([]bool, n)
    queue := []int{0}
    visited[0] = true
    parent[0] = -1
    
    for len(queue) > 0 {
        node := queue[0]
        queue = queue[1:]
        
        for _, next := range graph[node] {
            child, length := next[0], next[1]
            if !visited[child] {
                visited[child] = true
                parent[child] = node
                children[node] = append(children[node], [2]int{child, length})
                queue = append(queue, child)
            }
        }
    }
    
    maxLength := 0
    minNodes := 0
    
    // Check if frequency map is valid (at most one value appears twice)
    isValid := func(freq map[int]int) bool {
        twoCount := 0
        for _, count := range freq {
            if count > 2 {
                return false
            }
            if count == 2 {
                twoCount++
                if twoCount > 1 {
                    return false
                }
            }
        }
        return true
    }
    
    // DFS exploring only downward paths (parent to children)
    var dfs func(node, pathLen, nodeCount int, freq map[int]int)
    dfs = func(node, pathLen, nodeCount int, freq map[int]int) {
        // Add current node value
        freq[nums[node]]++
        
        // Check if current path is valid
        if isValid(freq) {
            // Update result
            if pathLen > maxLength {
                maxLength = pathLen
                minNodes = nodeCount
            } else if pathLen == maxLength && nodeCount < minNodes {
                minNodes = nodeCount
            }
            
            // Explore downward to children only
            for _, next := range children[node] {
                child, edgeLen := next[0], next[1]
                dfs(child, pathLen + edgeLen, nodeCount + 1, freq)
            }
        }
        
        // Backtrack
        freq[nums[node]]--
        if freq[nums[node]] == 0 {
            delete(freq, nums[node])
        }
    }
    
    // Start DFS from every node as potential ancestor
    for start := 0; start < n; start++ {
        freq := make(map[int]int)
        dfs(start, 0, 1, freq)
    }
    
    return []int{maxLength, minNodes}
}
# @lc code=end