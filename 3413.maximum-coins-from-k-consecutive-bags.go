#
# @lc app=leetcode id=3413 lang=golang
#
# [3413] Maximum Coins From K Consecutive Bags
#

# @lc code=start
func maximumCoins(coins [][]int, k int) int64 {
	sort.Slice(coins, func(i, j int) bool {
		return coins[i][0] < coins[j][0]
	})

	n := len(coins)
	prefix := make([]int64, n+1)
	for i := 0; i < n; i++ {
		li := int64(coins[i][0])
		ri := int64(coins[i][1])
		ci := int64(coins[i][2])
		ln := ri - li + 1
		prefix[i+1] = prefix[i] + ci * ln
	}

	// events: li and ri+1, no sort needed
	ev := make([]int64, 0, 2*n)
	for i := 0; i < n; i++ {
		ev = append(ev, int64(coins[i][0]))
		ev = append(ev, int64(coins[i][1])+1)
	}

	// candidates
	cands := make([]int64, 0, 4*n)
	for _, e := range ev {
		cands = append(cands, e)
		xc := e - int64(k)
		if xc >= 1 {
			cands = append(cands, xc)
		}
	}

	ans := int64(0)

	max64 := func(a, b int64) int64 {
		if a > b {
			return a
		}
		return b
	}
	min64 := func(a, b int64) int64 {
		if a < b {
			return a
		}
		return b
	}

	getSum := func(ql, qr int64) int64 {
		if ql > qr {
			return 0
		}

		// first i with ri >= ql (smallest)
		l, r := 0, n-1
		fi := n
		for l <= r {
			mid := (l + r) / 2
			if int64(coins[mid][1]) >= ql {
				fi = mid
				r = mid - 1
			} else {
				l = mid + 1
			}
		}
		if fi == n {
			return 0
		}

		// last j with li <= qr (largest)
		l, r = 0, n-1
		lj := -1
		for l <= r {
			mid := (l + r) / 2
			if int64(coins[mid][0]) <= qr {
				lj = mid
				l = mid + 1
			} else {
				r = mid - 1
			}
		}
		if lj < fi {
			return 0
		}

		i0 := fi
		j0 := lj
		sm := int64(0)

		// partial i0
		li_ := int64(coins[i0][0])
		ri_ := int64(coins[i0][1])
		ci_ := int64(coins[i0][2])
		intl := max64(li_, ql)
		intr := min64(ri_, qr)
		if intl <= intr {
			sm += ci_ * (intr - intl + 1)
		}

		if i0 < j0 {
			// partial j0
			li_ = int64(coins[j0][0])
			ri_ = int64(coins[j0][1])
			ci_ = int64(coins[j0][2])
			intl = max64(li_, ql)
			intr = min64(ri_, qr)
			if intl <= intr {
				sm += ci_ * (intr - intl + 1)
			}
			// full i0+1 to j0-1
			if i0+1 <= j0-1 {
				sm += prefix[j0] - prefix[i0+1]
			}
		}

		return sm
	}

	for _, x := range cands {
		if x >= 1 {
			qr := x + int64(k) - 1
			curs := getSum(x, qr)
			if curs > ans {
				ans = curs
			}
		}
	}

	return ans
}
# @lc code=end