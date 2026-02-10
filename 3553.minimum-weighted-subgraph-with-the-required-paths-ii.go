//
// @lc app=leetcode id=3553 lang=golang
//
// [3553] Minimum Weighted Subgraph With the Required Paths II
//
// @lc code=start
func minimumWeight(edges [][]int, queries [][]int) []int {
    n := len(edges) + 1
    
    // Build adjacency list
    graph := make([][][2]int, n)
    for _, edge := range edges {
        u, v, w := edge[0], edge[1], edge[2]
        graph[u] = append(graph[u], [2]int{v, w})
        graph[v] = append(graph[v], [2]int{u, w})
    }
    
    // Function to find path from src to dest using BFS
    findPath := func(src, dest int) [][3]int {
        parent := make([]int, n)
        parentWeight := make([]int, n)
        visited := make([]bool, n)
        queue := []int{src}
        visited[src] = true
        parent[src] = -1
        
        // BFS to find path
        for len(queue) > 0 {
            node := queue[0]
            queue = queue[1:]
            
            if node == dest {
                break
            }
            
            for _, neighbor := range graph[node] {
                next, weight := neighbor[0], neighbor[1]
                if !visited[next] {
                    visited[next] = true
                    parent[next] = node
                    parentWeight[next] = weight
                    queue = append(queue, next)
                }
            }
        }
        
        // Reconstruct path edges
        var pathEdges [][3]int
        curr := dest
        for parent[curr] != -1 {
            p := parent[curr]
            w := parentWeight[curr]
            // Normalize edge (smaller node first for deduplication)
            if p < curr {
                pathEdges = append(pathEdges, [3]int{p, curr, w})
            } else {
                pathEdges = append(pathEdges, [3]int{curr, p, w})
            }
            curr = p
        }
        return pathEdges
    }
    
    result := make([]int, len(queries))
    for i, query := range queries {
        src1, src2, dest := query[0], query[1], query[2]
        
        // Find paths from both sources to destination
        path1 := findPath(src1, dest)
        path2 := findPath(src2, dest)
        
        // Use map to get unique edges (union of both paths)
        edgeSet := make(map[[3]int]bool)
        for _, edge := range path1 {
            edgeSet[edge] = true
        }
        for _, edge := range path2 {
            edgeSet[edge] = true
        }
        
        // Sum weights of unique edges
        totalWeight := 0
        for edge := range edgeSet {
            totalWeight += edge[2]
        }
        
        result[i] = totalWeight
    }
    
    return result
}
// @lc code=end