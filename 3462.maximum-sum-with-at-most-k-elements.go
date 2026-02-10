#
# @lc app=leetcode id=3462 lang=golang
#
# [3462] Maximum Sum With at Most K Elements
#

# @lc code=start
import "sort"

func maxSum(grid [][]int, limits []int, k int) int64 {
    available := []int{}
    
    for i := 0; i < len(grid); i++ {
        row := make([]int, len(grid[i]))
        copy(row, grid[i])
        sort.Sort(sort.Reverse(sort.IntSlice(row)))
        
        count := limits[i]
        if count > len(row) {
            count = len(row)
        }
        for j := 0; j < count; j++ {
            available = append(available, row[j])
        }
    }
    
    sort.Sort(sort.Reverse(sort.IntSlice(available)))
    
    sum := int64(0)
    count := k
    if count > len(available) {
        count = len(available)
    }
    for i := 0; i < count; i++ {
        sum += int64(available[i])
    }
    
    return sum
}
# @lc code=end