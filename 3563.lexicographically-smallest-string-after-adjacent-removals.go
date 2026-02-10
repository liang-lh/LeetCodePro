#
# @lc app=leetcode id=3563 lang=golang
#
# [3563] Lexicographically Smallest String After Adjacent Removals
#

# @lc code=start
func lexicographicallySmallestString(s string) string {
	n := len(s)
	if n == 0 {
		return ""
	}
	ss := []byte(s)
	canEmpty := make([][]bool, n)
	for i := range canEmpty {
		canEmpty[i] = make([]bool, n)
	}
	isConsec := func(a, b byte) bool {
		d := int(a) - int(b)
		if d < 0 {
			d = -d
		}
		return d == 1 || d == 25
	}
	for leng := 2; leng <= n; leng += 2 {
		for i := 0; i <= n-leng; i++ {
			j := i + leng - 1
			for k := i; k < j; k++ {
				leftOk := (k == i || canEmpty[i][k-1])
				mStart, mEnd := k+1, j-1
				midOk := (mStart > mEnd || canEmpty[mStart][mEnd])
				if isConsec(ss[k], ss[j]) && leftOk && midOk {
					canEmpty[i][j] = true
					break
				}
			}
		}
	}
	best := make([]string, n+1)
	best[n] = ""
	emptyable := func(l, r int) bool {
		if l > r {
			return true
		}
		if l == r {
			return false
		}
		return canEmpty[l][r]
	}
	for pos := n-1; pos >= 0; pos-- {
		var minS string
		have := false
		if emptyable(pos, n-1) {
			minS = ""
			have = true
		}
		for j := pos; j < n; j++ {
			if emptyable(pos, j-1) {
				cand := string(ss[j]) + best[j+1]
				if !have || cand < minS {
					minS = cand
					have = true
				}
			}
		}
		best[pos] = minS
	}
	return best[0]
}
# @lc code=end