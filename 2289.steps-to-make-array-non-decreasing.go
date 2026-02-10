#
# @lc app=leetcode id=2289 lang=golang
#
# [2289] Steps to Make Array Non-decreasing
#

# @lc code=start
func totalSteps(nums []int) int {
	n := len(nums)
	if n == 0 {
		return 0
	}
	dp := make([]int, n)
	stack := []int{}
	ans := 0
	for i := 0; i < n; i++ {
		curMax := 0
		for len(stack) > 0 && nums[stack[len(stack)-1]] <= nums[i] {
			j := stack[len(stack)-1]
			stack = stack[:len(stack)-1]
			if dp[j] > curMax {
				curMax = dp[j]
			}
		}
		if len(stack) > 0 {
			dp[i] = curMax + 1
			if dp[i] > ans {
				ans = dp[i]
			}
		}
		stack = append(stack, i)
	}
	return ans
}
# @lc code=end