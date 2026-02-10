#
# @lc app=leetcode id=3605 lang=golang
#
# [3605] Minimum Stability Factor of Array
#

# @lc code=start
func minStable(nums []int, maxC int) int {
	n := len(nums)
	if n == 0 {
		return 0
	}

	logg := make([]int, n+1)
	logg[1] = 0
	for i := 2; i <= n; i++ {
		logg[i] = logg[i/2] + 1
	}

	const LOG = 18
	st := make([][]int, LOG)
	for i := 0; i < LOG; i++ {
		st[i] = make([]int, n)
	}

	for i := 0; i < n; i++ {
		st[0][i] = nums[i]
	}

	myGcd := func(a, b int) int {
		if b == 0 {
			return a
		}
		for b != 0 {
			a, b = b, a%b
		}
		return a
	}

	for j := 1; j < LOG; j++ {
		for i := 0; i+(1<<j) <= n; i++ {
			st[j][i] = myGcd(st[j-1][i], st[j-1][i+(1<<(j-1))])
		}
	}

	query := func(l, r int) int {
		le := r - l + 1
		j := logg[le]
		return myGcd(st[j][l], st[j][r-(1<<j)+1])
	}

	check := func(K int) bool {
		W := K + 1
		if W > n {
			return true
		}

		var bad []int
		for i := 0; i <= n-W; i++ {
			if query(i, i+W-1) >= 2 {
				bad = append(bad, i)
			}
		}

		if len(bad) == 0 {
			return true
		}

		mm := len(bad)
		changes := 0
		idx := 0
		for idx < mm {
			changes++
			pick := bad[idx] + W - 1
			for idx < mm && bad[idx] <= pick {
				idx++
			}
		}
		return changes <= maxC
	}

	lo := 0
	hi := n
	for lo < hi {
		mid := (lo + hi) / 2
		if check(mid) {
			hi = mid
		} else {
			lo = mid + 1
		}
	}

	return lo
}

# @lc code=end