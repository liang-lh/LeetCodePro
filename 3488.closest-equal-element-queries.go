#
# @lc app=leetcode id=3488 lang=golang
#
# [3488] Closest Equal Element Queries
#
# @lc code=start
func solveQueries(nums []int, queries []int) []int {
    n := len(nums)
    
    // Build map: value -> list of indices
    valueToIndices := make(map[int][]int)
    for i, num := range nums {
        valueToIndices[num] = append(valueToIndices[num], i)
    }
    
    result := make([]int, len(queries))
    
    for i, queryIdx := range queries {
        value := nums[queryIdx]
        indices := valueToIndices[value]
        
        // If only one occurrence, no other index with same value
        if len(indices) <= 1 {
            result[i] = -1
            continue
        }
        
        minDist := n // Maximum possible distance
        
        // Check all other indices with same value
        for _, idx := range indices {
            if idx == queryIdx {
                continue
            }
            
            // Calculate circular distance
            diff := abs(queryIdx - idx)
            dist := min(diff, n - diff)
            
            minDist = min(minDist, dist)
        }
        
        result[i] = minDist
    }
    
    return result
}

func min(a, b int) int {
    if a < b {
        return a
    }
    return b
}

func abs(x int) int {
    if x < 0 {
        return -x
    }
    return x
}
# @lc code=end