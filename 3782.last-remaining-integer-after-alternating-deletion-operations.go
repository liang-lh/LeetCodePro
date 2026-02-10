#
# @lc app=leetcode id=3782 lang=golang
#
# [3782] Last Remaining Integer After Alternating Deletion Operations
#

# @lc code=start
func lastInteger(n int64) int64 {
	lengthHistory := []int64{n}
	cur := n
	for cur > 1 {
		cur = (cur + 1) / 2
		lengthHistory = append(lengthHistory, cur)
	}
	pos := int64(1)
	for i := len(lengthHistory) - 1; i >= 1; i-- {
		prevLen := lengthHistory[i-1]
		step := i
		if step%2 == 1 {
			pos = 2*pos - 1
		} else {
			if prevLen%2 == 0 {
				pos = 2 * pos
			} else {
				pos = 2*pos - 1
			}
		}
	}
	return pos
}
# @lc code=end