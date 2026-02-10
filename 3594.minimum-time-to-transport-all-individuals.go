#
# @lc app=leetcode id=3594 lang=golang
#
# [3594] Minimum Time to Transport All Individuals
#

# @lc code=start
func minTime(n int, k int, m int, time []int, mul []float64) float64 {
	const MS = 1 << 12
	const PS = 2
	const MS_ = 6
	var dp [MS][PS][MS_]float64
	var inq [MS][PS][MS_]bool
	inf := math.Inf(1)
	max_mask := 1 << n
	for i := 0; i < max_mask; i++ {
		for j := 0; j < PS; j++ {
			for l := 0; l < m; l++ {
				dp[i][j][l] = inf
			}
		}
	}
	full := max_mask - 1
	type State struct {
		mask, pos, stage int
	}
	q := []State{}
	dp[0][0][0] = 0
	q = append(q, State{0, 0, 0})
	inq[0][0][0] = true
	func gen_groups(start, cur_mask, cur_max, size, basemask int, cb func(int, int)) {
		if size == 0 {
			cb(cur_mask, cur_max)
			return
		}
		for i := start; i < n; i++ {
			if (basemask & (1 << i)) == 0 {
				continue
			}
			nmax := cur_max
			if time[i] > nmax {
				nmax = time[i]
			}
			gen_groups(i+1, cur_mask|(1<<i), nmax, size-1, basemask, cb)
		}
	}
	for len(q) > 0 {
		curr := q[0]
		q = q[1:]
		inq[curr.mask][curr.pos][curr.stage] = false
		ct := dp[curr.mask][curr.pos][curr.stage]
		if curr.pos == 0 {
			basemask := full ^ curr.mask
			for sz := 1; sz <= k; sz++ {
				gen_groups(0, 0, 0, sz, basemask, func(smask, maxt int) {
					d := float64(maxt) * mul[curr.stage]
					adv := int(math.Floor(d)) % m
					ns := (curr.stage + adv) % m
					nmask := curr.mask | smask
					nt := ct + d
					if nt < dp[nmask][1][ns] {
						dp[nmask][1][ns] = nt
						if !inq[nmask][1][ns] {
							inq[nmask][1][ns] = true
							q = append(q, State{nmask, 1, ns})
						}
					}
				})
			}
		} else {
			if curr.mask == full {
				continue
			}
			for r := 0; r < n; r++ {
				if (curr.mask & (1 << r)) == 0 {
					continue
				}
				rt := float64(time[r]) * mul[curr.stage]
				adv := int(math.Floor(rt)) % m
				ns := (curr.stage + adv) % m
				nmask := curr.mask ^ (1 << r)
				nt := ct + rt
				if nt < dp[nmask][0][ns] {
				dp[nmask][0][ns] = nt
					if !inq[nmask][0][ns] {
						inq[nmask][0][ns] = true
						q = append(q, State{nmask, 0, ns})
					}
				}
			}
		}
	}
	ans := math.Inf(1)
	for j := 0; j < 2; j++ {
		for l := 0; l < m; l++ {
			if dp[full][j][l] < ans {
				ans = dp[full][j][l]
			}
		}
	}
	if math.IsInf(ans, 1) {
		return -1.0
	}
	return ans
}
# @lc code=end