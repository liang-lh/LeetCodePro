#
# @lc app=leetcode id=3621 lang=golang
#
# [3621] Number of Integers With Popcount-Depth Equal to K I
#

import "math/bits"

# @lc code=start
func popcountDepth(n int64, k int) int64 {
	if k == 0 {
		return 1
	}

	getDepth := func(x int64) int {
		d := 0
		for x != 1 {
			x = int64(bits.OnesCount64(uint64(x)))
			d++
			if x == 0 {
				break
			}
		}
		return d
	}

	depths := [65]int{}
	for i := 1; i <= 64; i++ {
		depths[i] = getDepth(int64(i))
	}

	var targets []int
	for s := 1; s <= 64; s++ {
		if depths[s] == k-1 {
			targets = append(targets, s)
		}
	}

	ans := int64(0)

	countWithPopcnt := func(nn int64, want int) int64 {
		if want == 0 || nn < 1 {
			return 0
		}

		digits := [64]int{}
		for i := 0; i < 64; i++ {
			digits[i] = int((nn >> uint(63-i)) & 1)
		}

		memo := [64][65][2]int64{}
		for i := 0; i < 64; i++ {
			for j := 0; j <= 64; j++ {
				memo[i][j][0] = -1
				memo[i][j][1] = -1
			}
		}

		var dfs func(int, int, int) int64
		dfs = func(pos, rem, tight int) int64 {
			if pos == 64 {
				return int64(rem == 0)
			}

			if memo[pos][rem][tight] != -1 {
				return memo[pos][rem][tight]
			}

			res := int64(0)
			maxDigit := 1
			if tight == 1 {
				maxDigit = digits[pos]
			}

			for d := 0; d <= maxDigit; d++ {
				if rem - d < 0 {
					continue
				}

				newTight := 0
				if tight == 1 && d == digits[pos] {
					newTight = 1
				}

				res += dfs(pos+1, rem-d, newTight)
			}

			memo[pos][rem][tight] = res
			return res
		}

		return dfs(0, want, 1)
	}

	for _, s := range targets {
		cnt := countWithPopcnt(n, s)
		if k == 1 && s == 1 {
			if cnt > 0 {
				cnt--
			}
		}
		ans += cnt
	}

	return ans
}
# @lc code=end