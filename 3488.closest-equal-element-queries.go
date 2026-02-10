#
# @lc app=leetcode id=3488 lang=golang
#
# [3488] Closest Equal Element Queries
#

# @lc code=start
func solveQueries(nums []int, queries []int) []int {
	min := func(a, b int) int {
		if a < b {
			return a
		}
		return b
	}
	n := len(nums)
	pos := make(map[int][]int)
	for i := range nums {
		pos[nums[i]] = append(pos[nums[i]], i)
	}
	ans := make([]int, n)
	for i := range ans {
		ans[i] = -1
	}
	for _, p := range pos {
		m := len(p)
		if m < 2 {
			continue
		}
		for k := 0; k < m; k++ {
			var left, right int
			if k == 0 {
				left = p[0] + n - p[m-1]
			} else {
				left = p[k] - p[k-1]
			}
			if k == m-1 {
				right = p[0] + n - p[m-1]
			} else {
				right = p[k+1] - p[k]
			}
			ans[p[k]] = min(left, right)
		}
	}
	result := make([]int, len(queries))
	for i, q := range queries {
		result[i] = ans[q]
	}
	return result
}
# @lc code=end