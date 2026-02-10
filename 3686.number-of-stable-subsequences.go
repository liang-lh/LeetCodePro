#
# @lc app=leetcode id=3686 lang=golang
#
# [3686] Number of Stable Subsequences
#

# @lc code=start
func countStableSubsequences(nums []int) int {
	const MOD int64 = 1000000007
	sum1 := [2]int64{0, 0}
	sum2 := [2]int64{0, 0}
	var total int64 = 0
	for _, num := range nums {
		p := num & 1
		opp := 1 - p
		contrib1 := (int64(1) + sum1[opp] + sum2[opp]) % MOD
		contrib2 := sum1[p]
		total = (total + contrib1 + contrib2) % MOD
		sum1[p] = (sum1[p] + contrib1) % MOD
		sum2[p] = (sum2[p] + contrib2) % MOD
	}
	return int(total)
}
# @lc code=end