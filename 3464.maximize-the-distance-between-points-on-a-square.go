#
# @lc app=leetcode id=3464 lang=golang
#
# [3464] Maximize the Distance Between Points on a Square
#
# @lc code=start
func maxDistance(side int, points [][]int, k int) int {
    n := len(points)
    
    // Helper function to calculate Manhattan distance
    manhattanDist := func(p1, p2 []int) int {
        return abs(p1[0] - p2[0]) + abs(p1[1] - p2[1])
    }
    
    // Check if we can select k points with minimum distance d
    canSelect := func(d int) bool {
        // Use backtracking to find if we can select k points
        var backtrack func(idx int, selected []int) bool
        backtrack = func(idx int, selected []int) bool {
            if len(selected) == k {
                return true
            }
            if idx == n {
                return false
            }
            
            // Pruning: if remaining points + selected < k, we can't reach k
            if n - idx + len(selected) < k {
                return false
            }
            
            // Try selecting current point if compatible
            compatible := true
            for _, s := range selected {
                if manhattanDist(points[s], points[idx]) < d {
                    compatible = false
                    break
                }
            }
            
            if compatible {
                newSelected := make([]int, len(selected)+1)
                copy(newSelected, selected)
                newSelected[len(selected)] = idx
                if backtrack(idx+1, newSelected) {
                    return true
                }
            }
            
            // Try not selecting current point
            if backtrack(idx+1, selected) {
                return true
            }
            
            return false
        }
        
        return backtrack(0, []int{})
    }
    
    // Binary search on the answer
    left, right := 0, 2*side
    result := 0
    
    for left <= right {
        mid := (left + right) / 2
        if canSelect(mid) {
            result = mid
            left = mid + 1
        } else {
            right = mid - 1
        }
    }
    
    return result
}

func abs(x int) int {
    if x < 0 {
        return -x
    }
    return x
}
# @lc code=end