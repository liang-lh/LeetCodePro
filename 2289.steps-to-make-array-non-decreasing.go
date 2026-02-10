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
	st := []int{}
	ans := 0
	for i := 0; i < n; i++ {
		maxDel := 0
		for len(st) > 0 && nums[st[len(st)-1]] <= nums[i] {
			idx := st[len(st)-1]
			st = st[:len(st)-1]
			if dp[idx] > maxDel {
				maxDel = dp[idx]
			}
		}
		if len(st) > 0 {
			dp[i] = maxDel + 1
		}
		if dp[i] > ans {
			ans = dp[i]
		}
		st = append(st, i)
	}
	return ans
}
# @lc code=end