#
# @lc app=leetcode id=3757 lang=golang
#
# [3757] Number of Effective Subsequences
#

# @lc code=start
func countEffective(nums []int) int {
	mod := int64(1000000007)

	full_or := 0
	for _, num := range nums {
		full_or |= num
	}

	bits := []int{}
	for b := 0; b < 32; b++ {
		if (full_or & (1 << b)) != 0 {
			bits = append(bits, b)
		}
	}

	m := len(bits)
	if m == 0 {
		return 0
	}

	max_mask := 1 << m

	F := make([]int64, max_mask)
	for _, num := range nums {
		w := 0
		for j := 0; j < m; j++ {
			if (num & (1 << bits[j])) != 0 {
				w |= (1 << j)
			}
		}
		F[w]++
	}

	sub_sum := make([]int64, max_mask)
	copy(sub_sum, F)
	for j := 0; j < m; j++ {
		bit := 1 << j
		for u := 0; u < max_mask; u++ {
			if (u & bit) != 0 {
				sub_sum[u] += sub_sum[u ^ bit]
			}
		}
	}

	ans := int64(0)

	all_mask := max_mask - 1

	var cntbits func(int) int = func(x int) int {
		c := 0
		x2 := x
		for x2 > 0 {
			c++
			x2 &= x2 - 1
		}
		return c
	}

	popc := make([]int, max_mask)
	for i := 0; i < max_mask; i++ {
		popc[i] = cntbits(i)
	}

	var powmod func(int64, int64, int64) int64 = func(base, exp, md int64) int64 {
		res := int64(1)
		base %= md
		for exp > 0 {
			if exp&1 == 1 {
				res = res * base % md
			}
			base = base * base % md
			exp >>= 1
		}
		return res
	}

	for t := 1; t <= all_mask; t++ {
		comp := all_mask ^ t
		zero_cnt := sub_sum[comp]

		p := popc[t]
		ways := powmod(2, zero_cnt, mod)

		if p%2 == 1 {
			ans = (ans + ways) % mod
		} else {
			ans = (ans - ways + mod) % mod
		}
	}

	return int(ans)
}
# @lc code=end