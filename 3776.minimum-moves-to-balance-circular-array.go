#
# @lc app=leetcode id=3776 lang=golang
#
# [3776] Minimum Moves to Balance Circular Array
#

# @lc code=start
func minMoves(balance []int) int64 {
	n := len(balance)
	if n == 0 {
		return 0
	}

	var total int64
	var deficit int64
	negPos := -1

	for i := 0; i < n; i++ {
		total += int64(balance[i])
		if balance[i] < 0 {
			negPos = i
			deficit = int64(-balance[i])
		}
	}

	if total < 0 {
		return -1
	}
	if deficit == 0 {
		return 0
	}

	supply := make([]int64, n)

	for i := 0; i < n; i++ {
		if i == negPos {
			continue
		}
		d := i - negPos
		if d < 0 {
			d = -d
		}
		md := d
		if n - d < md {
			md = n - d
		}
		supply[md] += int64(balance[i])
	}

	var ans int64
	rem := deficit

	for dist := 1; dist < n; dist++ {
		if rem <= 0 {
			break
		}
		take := supply[dist]
		if take > rem {
			take = rem
		}
		ans += take * int64(dist)
		rem -= take
	}

	if rem > 0 {
		return -1
	}

	return ans
}
# @lc code=end