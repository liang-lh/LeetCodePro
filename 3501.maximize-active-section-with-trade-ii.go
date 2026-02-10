#
# @lc app=leetcode id=3501 lang=golang
#
# [3501] Maximize Active Section with Trade II
#
# @lc code=start
func maxActiveSectionsAfterTrade(s string, queries [][]int) []int {
	const LOG = 17
	const MAXK = 50005
	n := len(s)
	totalOnes := 0
	for _, ch := range s {
		if ch == '1' {
			totalOnes++
		}
	}

	islandsSt := []int{}
	islandsEn := []int{}
	i := 0
	for i < n {
		if s[i] == '1' {
			st := i
			for i < n && s[i] == '1' {
				i++
			}
			en := i - 1
			islandsSt = append(islandsSt, st)
			islandsEn = append(islandsEn, en)
		} else {
			i++
		}
	}
	k := len(islandsSt)

	fullAdj := make([]int, k)
	for j := 0; j < k; j++ {
		prevB := 0
		if j > 0 {
			prevB = islandsEn[j-1] + 1
		}
		leftF := islandsSt[j] - prevB
		nextB := n
		if j+1 < k {
			nextB = islandsSt[j+1]
		}
		rightF := nextB - 1 - islandsEn[j]
		fullAdj[j] = leftF + rightF
	}

	// Sparse table
	st := make([][]int, LOG)
	logg := make([]int, n+2)
	logg[1] = 0
	for i := 2; i <= n; i++ {
		logg[i] = logg[i/2] + 1
	}
	for lv := 0; lv < LOG; lv++ {
		st[lv] = make([]int, k)
	}
	copy(st[0], fullAdj)
	for lv := 1; lv < LOG; lv++ {
		for i := 0; i+(1<<lv) <= k; i++ {
			st[lv][i] = maxInt(st[lv-1][i], st[lv-1][i+(1<<(lv-1))])
		}
	}

	qrmq := func(L, R int) int {
		if L > R {
			return 0
		}
		leng := R - L + 1
		lv := logg[leng]
		return maxInt(st[lv][L], st[lv][R-(1<<lv)+1])
	}

	// Binary search helpers
	lowerBound := func(arr []int, val int) int {
		lo := 0
		hi := k
		for lo < hi {
			mid := lo + (hi-lo)/2
			if arr[mid] >= val {
				hi = mid
			} else {
				lo = mid + 1
			}
		}
		return lo
	}

	ans := make([]int, len(queries))
	for idx, q := range queries {
		l, r := q[0], q[1]
		jLeft := lowerBound(islandsSt, l+1)
		jRight := lowerBound(islandsEn, r) - 1
		if jLeft > jRight {
			ans[idx] = totalOnes
			continue
		}

		maxG := 0
		// First: jLeft
		{
			prevB := 0
			if jLeft > 0 {
				prevB = islandsEn[jLeft-1] + 1
			}
			leftH := islandsSt[jLeft] - maxInt(l, prevB)
			nextB := n
			if jLeft+1 < k {
				nextB = islandsSt[jLeft+1]
			}
			rightH := minInt(r, nextB-1) - islandsEn[jLeft]
			maxG = maxInt(maxG, leftH+rightH)
		}
		// Last: jRight
		if jRight >= jLeft {
			prevB := 0
			if jRight > 0 {
				prevB = islandsEn[jRight-1] + 1
			}
			leftH := islandsSt[jRight] - maxInt(l, prevB)
			nextB := n
			if jRight+1 < k {
				nextB = islandsSt[jRight+1]
			}
			rightH := minInt(r, nextB-1) - islandsEn[jRight]
			maxG = maxInt(maxG, leftH+rightH)
		}
		// Middles
		if jLeft+1 <= jRight-1 {
			midMax := qrmq(jLeft+1, jRight-1)
			maxG = maxInt(maxG, midMax)
		}
		ans[idx] = totalOnes + maxG
	}
	return ans
}

func maxInt(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func minInt(a, b int) int {
	if a < b {
		return a
	}
	return b
}
# @lc code=end