#
# @lc app=leetcode id=3509 lang=golang
#
# [3509] Maximum Product of Subsequences With an Alternating Sum Equal to K
#

# @lc code=start
func maxProduct(nums []int, k int, limit int) int {
	const SHIFT = 1800
	const MAXS = 3601

	current := [2][3601]int{}
	for p := 0; p < 2; p++ {
		for s := 0; s < MAXS; s++ {
			current[p][s] = -1
		}
	}

	n := len(nums)
	for i := 0; i < n; i++ {
		x := nums[i]
		newdp := [2][3601]int{}
		for p := 0; p < 2; p++ {
			for s := 0; s < MAXS; s++ {
				newdp[p][s] = -1
			}
		}

		// skip
		for p := 0; p < 2; p++ {
			for s := 0; s < MAXS; s++ {
				if current[p][s] > newdp[p][s] {
					newdp[p][s] = current[p][s]
				}
			}
		}

		// continuation pick
		for p := 0; p < 2; p++ {
			for s := 0; s < MAXS; s++ {
				if current[p][s] != -1 {
					sign := 1
					if p == 1 {
						sign = -1
					}
					delta := sign * x
					news := s + delta
					if news >= 0 && news < MAXS {
						newprod64 := int64(current[p][s]) * int64(x)
						if newprod64 <= int64(limit) {
							newp := 1 - p
							newprod := int(newprod64)
							if newdp[newp][news] < newprod {
								newdp[newp][news] = newprod
							}
						}
					}
				}
			}
		}

		// start new
		prod64 := int64(x)
		if prod64 <= int64(limit) {
			starts := SHIFT + x
			startp := 1
			if starts >= 0 && starts < MAXS {
				startprod := x
				if newdp[startp][starts] < startprod {
					newdp[startp][starts] = startprod
				}
			}
		}

		// copy to current
		for p := 0; p < 2; p++ {
			for s := 0; s < MAXS; s++ {
				current[p][s] = newdp[p][s]
			}
		}
	}

	target := SHIFT + k
	if target < 0 || target >= MAXS {
		return -1
	}

	ans := -1
	if current[0][target] > ans {
		ans = current[0][target]
	}
	if current[1][target] > ans {
		ans = current[1][target]
	}

	return ans
}
# @lc code=end