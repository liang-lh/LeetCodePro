#
# @lc app=leetcode id=3513 lang=golang
#
# [3513] Number of Unique XOR Triplets I
#

# @lc code=start
func uniqueXorTriplets(nums []int) int {
	n := len(nums)
	if n <= 2 {
		return n
	}
	bit := 0
	tmp := n
	for tmp > 0 {
		bit++
		tmp >>= 1
	}
	return 1 << bit
}
# @lc code=end