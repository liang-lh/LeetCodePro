#
# @lc app=leetcode id=3785 lang=golang
#
# [3785] Minimum Swaps to Avoid Forbidden Values
#

# @lc code=start
func minSwaps(nums []int, forbidden []int) int {
	n := len(nums)
	freqNum := make(map[int]int)
	freqForb := make(map[int]int)
	badFreq := make(map[int]int)
	totalBad := 0
	for i := 0; i < n; i++ {
		freqNum[nums[i]]++
		freqForb[forbidden[i]]++
		if nums[i] == forbidden[i] {
			badFreq[nums[i]]++
			totalBad++
		}
	}
	for v, f := range freqForb {
		if f > n - freqNum[v] {
			return -1
		}
	}
	if totalBad == 0 {
		return 0
	}
	maxBad := 0
	for _, cnt := range badFreq {
		if cnt > maxBad {
			maxBad = cnt
		}
	}
	excess := 2*maxBad - totalBad
	if excess < 0 {
		excess = 0
	}
	matching := (totalBad - excess) / 2
	return totalBad - matching
}

# @lc code=end