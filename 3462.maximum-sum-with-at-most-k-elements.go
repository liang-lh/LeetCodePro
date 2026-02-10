#
# @lc app=leetcode id=3462 lang=golang
#
# [3462] Maximum Sum With at Most K Elements
#

# @lc code=start
func maxSum(grid [][]int, limits []int, k int) int64 {
	candidates := []int{}
	n := len(grid)
	for i := 0; i < n; i++ {
		row := grid[i]
		m := len(row)
		rowCopy := make([]int, m)
		copy(rowCopy, row)
		sort.Slice(rowCopy, func(p, q int) bool {
			return rowCopy[p] > rowCopy[q]
		})
		numTake := limits[i]
		if numTake > m {
			numTake = m
		}
		for j := 0; j < numTake; j++ {
			candidates = append(candidates, rowCopy[j])
		}
	}
	sort.Slice(candidates, func(p, q int) bool {
		return candidates[p] > candidates[q]
	})
	sum := int64(0)
	num := k
	if num > len(candidates) {
		num = len(candidates)
	}
	for i := 0; i < num; i++ {
		sum += int64(candidates[i])
	}
	return sum
}
# @lc code=end