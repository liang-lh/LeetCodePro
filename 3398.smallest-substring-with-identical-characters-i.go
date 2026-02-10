#
# @lc app=leetcode id=3398 lang=golang
#
# [3398] Smallest Substring With Identical Characters I
#
# @lc code=start
func minLength(s string, numOps int) int {
	n := len(s)
	runs := []int{}
	i := 0
	for i < n {
		j := i
		for j < n && s[j] == s[i] {
			j++
		}
		runs = append(runs, j - i)
		i = j
	}

	lo := 1
	hi := n
	for lo < hi {
		mid := lo + (hi - lo)/2
		need := 0
		for _, length := range runs {
			need += length / (mid + 1)
		}
		if need <= numOps {
			hi = mid
		} else {
			lo = mid + 1
		}
	}

	return lo
}
# @lc code=end