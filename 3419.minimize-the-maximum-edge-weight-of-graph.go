#
# @lc app=leetcode id=3419 lang=golang
#
# [3419] Minimize the Maximum Edge Weight of Graph
#
# @lc code=start
func minMaxWeight(n int, edges [][]int, threshold int) int {
    if len(edges) == 0 {
        return -1
    }
    
    // Extract unique weights and sort
    weightSet := make(map[int]bool)
    for _, e := range edges {
        weightSet[e[2]] = true
    }
    weights := make([]int, 0, len(weightSet))
    for w := range weightSet {
        weights = append(weights, w)
    }
    sort.Ints(weights)
    
    // Binary search on weights
    left, right := 0, len(weights)-1
    result := -1
    
    for left <= right {
        mid := (left + right) / 2
        maxWeight := weights[mid]
        
        if isValid(n, edges, threshold, maxWeight) {
            result = maxWeight
            right = mid - 1
        } else {
            left = mid + 1
        }
    }
    
    return result
}

func isValid(n int, edges [][]int, threshold int, maxWeight int) bool {
    // Filter edges with weight <= maxWeight
    filtered := make([][]int, 0)
    for _, e := range edges {
        if e[2] <= maxWeight {
            filtered = append(filtered, e)
        }
    }
    
    // Group edges by source node
    outgoing := make(map[int][][]int)
    for _, e := range filtered {
        from := e[0]
        outgoing[from] = append(outgoing[from], e)
    }
    
    // For each node exceeding threshold, keep threshold edges
    // Strategy: keep edges with smallest weights (greedy approximation)
    selected := make([][]int, 0)
    for from := 0; from < n; from++ {
        edgeList := outgoing[from]
        if len(edgeList) <= threshold {
            selected = append(selected, edgeList...)
        } else {
            // Sort by weight and keep threshold smallest
            sort.Slice(edgeList, func(i, j int) bool {
                return edgeList[i][2] < edgeList[j][2]
            })
            selected = append(selected, edgeList[:threshold]...)
        }
    }
    
    // Build reverse graph
    reverseGraph := make(map[int][]int)
    for _, e := range selected {
        from, to := e[0], e[1]
        reverseGraph[to] = append(reverseGraph[to], from)
    }
    
    // BFS from node 0 in reverse graph
    visited := make([]bool, n)
    queue := []int{0}
    visited[0] = true
    
    for len(queue) > 0 {
        node := queue[0]
        queue = queue[1:]
        
        for _, neighbor := range reverseGraph[node] {
            if !visited[neighbor] {
                visited[neighbor] = true
                queue = append(queue, neighbor)
            }
        }
    }
    
    // Check if all nodes are reachable
    for i := 0; i < n; i++ {
        if !visited[i] {
            return false
        }
    }
    
    return true
}
# @lc code=end