#
# @lc app=leetcode id=3515 lang=golang
#
# [3515] Shortest Path in a Weighted Tree
#
# @lc code=start
func treeQueries(n int, edges [][]int, queries [][]int) []int {
    // Store edge weights in a map
    edgeWeight := make(map[[2]int]int)
    
    // Build adjacency list (without weights, just connections)
    graph := make(map[int][]int, n+1)
    
    for _, edge := range edges {
        u, v, w := edge[0], edge[1], edge[2]
        graph[u] = append(graph[u], v)
        graph[v] = append(graph[v], u)
        key := [2]int{min(u, v), max(u, v)}
        edgeWeight[key] = w
    }
    
    result := []int{}
    
    for _, query := range queries {
        if len(query) == 4 && query[0] == 1 {
            // Update query
            u, v, w := query[1], query[2], query[3]
            key := [2]int{min(u, v), max(u, v)}
            edgeWeight[key] = w
        } else if len(query) == 2 && query[0] == 2 {
            // Distance query
            x := query[1]
            dist := computeDistance(graph, edgeWeight, 1, x, n)
            result = append(result, dist)
        }
    }
    
    return result
}

func computeDistance(graph map[int][]int, edgeWeight map[[2]int]int, start, target, n int) int {
    if start == target {
        return 0
    }
    
    visited := make([]bool, n+1)
    queue := []State{{start, 0}}
    visited[start] = true
    
    for len(queue) > 0 {
        curr := queue[0]
        queue = queue[1:]
        
        if curr.node == target {
            return curr.dist
        }
        
        for _, neighbor := range graph[curr.node] {
            if !visited[neighbor] {
                visited[neighbor] = true
                key := [2]int{min(curr.node, neighbor), max(curr.node, neighbor)}
                weight := edgeWeight[key]
                queue = append(queue, State{neighbor, curr.dist + weight})
            }
        }
    }
    
    return -1 // Should not reach here in a connected tree
}

type State struct {
    node int
    dist int
}

func min(a, b int) int {
    if a < b {
        return a
    }
    return b
}

func max(a, b int) int {
    if a > b {
        return a
    }
    return b
}
# @lc code=end