#
# @lc app=leetcode id=3464 lang=golang
#
# [3464] Maximize the Distance Between Points on a Square
#

# @lc code=start
func maxDistance(side int, points [][]int, k int) int {
	lo, hi := 0, 2*side
	for lo < hi {
		mid := (lo + hi + 1) / 2
		if check(side, points, k, mid) {
			lo = mid
		} else {
			hi = mid - 1
		}
	}
	return lo
}

func check(side int, points [][]int, k, d int) bool {
	bottoms, rights, tops, lefts := []int{}, []int{}, []int{}, []int{}
	for _, p := range points {
		x, y := p[0], p[1]
		if y == 0 {
			bottoms = append(bottoms, x)
		} else if x == side {
			rights = append(rights, y)
		} else if y == side {
			tops = append(tops, x)
		} else if x == 0 {
			lefts = append(lefts, y)
		}
	}
	sort.Ints(bottoms)
	sort.Ints(rights)
	sort.Ints(tops)
	sort.Ints(lefts)

	type SideInfo struct {
		minmax [26]int
		maxmin [26]int
		mp     int
	}
	getInfo := func(ps []int) SideInfo {
		info := SideInfo{}
		for i := range info.minmax[:] {
			info.minmax[i] = -1
			info.maxmin[i] = -1
		}
		m := len(ps)
		if m == 0 {
			return info
		}
		// forward greedy: minmax
		var fgreedy []int
		prev := int64(-2000000000)
		for _, cur := range ps {
			if int64(cur)-prev >= int64(d) {
				fgreedy = append(fgreedy, cur)
				prev = int64(cur)
			}
		}
		info.mp = len(fgreedy)
		for rr := 1; rr <= info.mp && rr <= 25; rr++ {
			info.minmax[rr] = fgreedy[rr-1]
		}
		// backward greedy: maxmin
		var bgreedy []int
		prev = 2000000002
		for ii := m - 1; ii >= 0; ii-- {
			cur := int64(ps[ii])
			if prev-cur >= int64(d) {
				bgreedy = append(bgreedy, ps[ii])
				prev = cur
			}
		}
		for rr := 1; rr <= len(bgreedy) && rr <= 25; rr++ {
			info.maxmin[rr] = bgreedy[rr-1]
		}
		return info
	}
	infB := getInfo(bottoms)
	infR := getInfo(rights)
	infT := getInfo(tops)
	infL := getInfo(lefts)

	delta := 0
	if d > side {
		delta = d - side
	}

	for aa := 0; aa <= k; aa++ {
		if aa > 0 && (aa > infB.mp || infB.minmax[aa] == -1) {
			continue
		}
		for bb := 0; bb <= k-aa; bb++ {
			if bb > 0 && (bb > infR.mp || infR.minmax[bb] == -1) {
				continue
			}
			for cc := 0; cc <= k-aa-bb; cc++ {
				if cc > 0 && (cc > infT.mp || infT.minmax[cc] == -1) {
					continue
				}
				ee := k - aa - bb - cc
				if ee > infL.mp || (ee > 0 && infL.minmax[ee] == -1) {
					continue
				}
				ok := true
				// adjacent bottom-right
				if aa > 0 && bb > 0 {
					if infR.maxmin[bb] < infB.minmax[aa]+(d-side) {
						ok = false
					}
				}
				// right-top
				if bb > 0 && cc > 0 {
					if infR.minmax[bb]+infT.minmax[cc] > 2*side-d {
						ok = false
					}
				}
				// top-left
				if cc > 0 && ee > 0 {
					if infT.maxmin[cc] < infL.minmax[ee]+(d-side) {
						ok = false
					}
				}
				// left-bottom
				if ee > 0 && aa > 0 {
					if infB.maxmin[aa]+infL.maxmin[ee] < d {
						ok = false
					}
				}
				// opposite bottom-top
				if aa > 0 && cc > 0 {
					sep1 := infB.minmax[aa] + delta <= infT.maxmin[cc]
					sep2 := infT.minmax[cc] + delta <= infB.maxmin[aa]
					if delta > 0 && !sep1 && !sep2 {
						ok = false
					}
				}
				// opposite left-right
				if ee > 0 && bb > 0 {
					sep1 := infL.minmax[ee] + delta <= infR.maxmin[bb]
					sep2 := infR.minmax[bb] + delta <= infL.maxmin[ee]
					if delta > 0 && !sep1 && !sep2 {
						ok = false
					}
				}
				if ok {
					return true
				}
			}
		}
	}
	return false
}

# @lc code=end