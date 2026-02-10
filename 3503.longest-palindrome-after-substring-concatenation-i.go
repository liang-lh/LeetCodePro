#
# @lc app=leetcode id=3503 lang=golang
#
# [3503] Longest Palindrome After Substring Concatenation I
#

# @lc code=start
func longestPalindrome(s string, t string) int {
	isPalindrome := func(str string) bool {
		n := len(str)
		for i := 0; i < n/2; i++ {
			if str[i] != str[n-1-i] {
				return false
			}
		}
		return true
	}

	maxLen := 0
	ns := len(s)
	nt := len(t)
	for i := 0; i <= ns; i++ {
		for j := i; j <= ns; j++ {
			subS := s[i:j]
			for k := 0; k <= nt; k++ {
				for l := k; l <= nt; l++ {
					subT := t[k:l]
					concat := subS + subT
					if isPalindrome(concat) {
						if len(concat) > maxLen {
							maxLen = len(concat)
						}
					}
				}
			}
		}
	}

	return maxLen
}
# @lc code=end