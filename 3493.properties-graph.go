#
# @lc app=leetcode id=3493 lang=golang
#
# [3493] Properties Graph
#
# @lc code=start
func numberOfComponents(properties [][]int, k int) int {
    n := len(properties)
    
    // Helper function to calculate intersection count of distinct integers
    intersect := func(a, b []int) int {
        setA := make(map[int]bool)
        for _, val := range a {
            setA[val] = true
        }
        
        count := 0
        counted := make(map[int]bool)
        for _, val := range b {
            if setA[val] && !counted[val] {
                count++
                counted[val] = true
            }
        }
        return count
    }
    
    // Union-Find data structure
    parent := make([]int, n)
    for i := range parent {
        parent[i] = i
    }
    
    var find func(int) int
    find = func(x int) int {
        if parent[x] != x {
            parent[x] = find(parent[x]) // Path compression
        }
        return parent[x]
    }
    
    union := func(x, y int) {
        rootX := find(x)
        rootY := find(y)
        if rootX != rootY {
            parent[rootX] = rootY
        }
    }
    
    // Build the graph by checking all pairs
    for i := 0; i < n; i++ {
        for j := i + 1; j < n; j++ {
            if intersect(properties[i], properties[j]) >= k {
                union(i, j)
            }
        }
    }
    
    // Count connected components
    components := make(map[int]bool)
    for i := 0; i < n; i++ {
        components[find(i)] = true
    }
    
    return len(components)
}
# @lc code=end