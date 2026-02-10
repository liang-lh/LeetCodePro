#
# @lc app=leetcode id=3771 lang=golang
#
# [3771] Total Score of Dungeon Runs
#

# @lc code=start
func totalScore(hp int, damage []int, requirement []int) int64 {
	n := len(damage)
	prefix := make([]int64, n+1)
	for i := 1; i <= n; i++ {
		prefix[i] = prefix[i-1] + int64(damage[i-1])
	}
	total := int64(0)
	for k := 0; k < n; k++ {
		thresh := prefix[k+1] - int64(hp) + int64(requirement[k])
		// binary search for smallest j <= k s.t. prefix[j] >= thresh
		left := 0
		right := k
		for left < right {
			mid := left + (right - left) / 2
			if prefix[mid] >= thresh {
				right = mid
			} else {
				left = mid + 1
			}
		}
		idx := left
		if idx <= k && prefix[idx] >= thresh {
			total += int64(k - idx + 1)
		}
	}
	return total
}
# @lc code=end