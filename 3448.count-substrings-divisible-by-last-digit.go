#
# @lc app=leetcode id=3448 lang=golang
#
# [3448] Count Substrings Divisible By Last Digit
#

# @lc code=start
func countSubstrings(s string) int64 {
	n := len(s)
	freq := [10][10]int64{}
	ans := int64(0)
	for i := 0; i < n; i++ {
		dig := int(s[i] - '0')
		for dd := 1; dd <= 9; dd++ {
			var newFreq [10]int64
			for r := 0; r < dd; r++ {
				contrib := (int64(r) * 10 + int64(dig)) % int64(dd)
				newFreq[contrib] += freq[dd][r]
			}
			smod := int64(dig) % int64(dd)
			newFreq[smod] += 1
			if dig == dd {
				ans += newFreq[0]
			}
			for r := 0; r < dd; r++ {
				freq[dd][r] = newFreq[r]
			}
		}
	}
	return ans
}
# @lc code=end