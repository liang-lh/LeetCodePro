#
# @lc app=leetcode id=3765 lang=golang
#
# [3765] Complete Prime Number
#

# @lc code=start
func completePrime(num int) bool {
	isPrime := func(x int64) bool {
		if x <= 1 {
			return false
		}
		if x == 2 || x == 3 {
			return true
		}
		if x % 2 == 0 || x % 3 == 0 {
			return false
		}
		i := int64(5)
		for i * i <= x {
			if x % i == 0 || x % (i + 2) == 0 {
				return false
			}
			i += 6
		}
		return true
	}

	digits := []int{}
	temp := num
	for temp > 0 {
		digits = append([]int{temp % 10}, digits...)
		temp /= 10
	}
	n := len(digits)

	// Check prefixes
	curr_pref := 0
	for i := 0; i < n; i++ {
		curr_pref = curr_pref * 10 + digits[i]
		if !isPrime(int64(curr_pref)) {
			return false
		}
	}

	// Check suffixes
	curr_suff := int64(0)
	power := int64(1)
	for i := n - 1; i >= 0; i-- {
		curr_suff += int64(digits[i]) * power
		if !isPrime(curr_suff) {
			return false
		}
		power *= 10
	}

	return true
}
# @lc code=end