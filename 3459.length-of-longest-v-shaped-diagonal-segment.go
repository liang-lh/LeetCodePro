//
// @lc app=leetcode id=3459 lang=golang
//
// [3459] Length of Longest V-Shaped Diagonal Segment
//
// @lc code=start
func lenOfVDiagonal(grid [][]int) int {
    n := len(grid)
    m := len(grid[0])
    
    // Four diagonal directions: (dr, dc)
    directions := [][]int{{1, 1}, {1, -1}, {-1, -1}, {-1, 1}}
    
    // Get expected value at sequence index
    getExpected := func(idx int) int {
        if idx == 0 {
            return 1
        }
        // Pattern: 2, 0, 2, 0, ...
        if (idx-1)%2 == 0 {
            return 2
        }
        return 0
    }
    
    maxLen := 0
    
    // Try each cell with value 1 as starting point
    for i := 0; i < n; i++ {
        for j := 0; j < m; j++ {
            if grid[i][j] != 1 {
                continue
            }
            
            // Try each diagonal direction
            for _, dir := range directions {
                dr, dc := dir[0], dir[1]
                r, c := i, j
                length := 0
                
                // Extend in the first direction
                for r >= 0 && r < n && c >= 0 && c < m && grid[r][c] == getExpected(length) {
                    length++
                    
                    // Try making a clockwise 90-degree turn at this position
                    // Clockwise turn: (dr, dc) -> (dc, -dr)
                    turnDr, turnDc := dc, -dr
                    tr, tc := r + turnDr, c + turnDc
                    turnLength := length
                    
                    // Extend in the new direction after turn
                    for tr >= 0 && tr < n && tc >= 0 && tc < m && grid[tr][tc] == getExpected(turnLength) {
                        turnLength++
                        tr += turnDr
                        tc += turnDc
                    }
                    
                    if turnLength > maxLen {
                        maxLen = turnLength
                    }
                    
                    // Move to next position in first direction
                    r += dr
                    c += dc
                }
                
                // Also consider the case without any turn
                if length > maxLen {
                    maxLen = length
                }
            }
        }
    }
    
    return maxLen
}
// @lc code=end