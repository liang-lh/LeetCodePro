#
# @lc app=leetcode id=3414 lang=golang
#
# [3414] Maximum Score of Non-overlapping Intervals
#

# @lc code=start
func maximumWeight(intervals [][]int) []int {
	n := len(intervals)
	if n == 0 {
		return []int{}
	}
	type Inter struct {
		Start, End, Weight, Idx int
	}
	type State struct {
		Score   int64
		Indices []int
	}
	tints := make([]Inter, n)
	for i := range intervals {
		ints[i] = Inter{intervals[i][0], intervals[i][1], intervals[i][2], i}
	}
	sort.Slice(tints, func(p, q int) bool {
		return tints[p].End < tints[q].End
	})
	dp := make([][]State, n+1)
	for i := range dp {
		dp[i] = make([]State, 5)
	}
	for i := 0; i <= n; i++ {
		for j := 1; j < 5; j++ {
			dp[i][j].Score = -1
		}
	}
	dp[0][0] = State{0, []int{}}
	lexSmaller := func(a, b []int) bool {
		minLen := len(a)
		if len(b) < minLen {
			minLen = len(b)
		}
		for k := 0; k < minLen; k++ {
			if a[k] < b[k] {
				return true
			}
			if a[k] > b[k] {
				return false
			}
		}
		return len(a) < len(b)
	}
	for i := 0; i < n; i++ {
		for j := 0; j < 5; j++ {
			dp[i+1][j] = dp[i][j]
		}
		// binsearch prevK
		l, r := 0, i-1
		prevK := -1
		st := tints[i].Start
		for l <= r {
			m := l + (r-l)/2
			if tints[m].End < st {
				prevK = m
				l = m + 1
			} else {
				r = m - 1
			}
		}
		prevPos := 0
		if prevK >= 0 {
			prevPos = prevK + 1
		}
		for j := 1; j < 5; j++ {
			ps := dp[prevPos][j-1].Score
			if ps < 0 {
				continue
			}
			newScore := ps + int64(tints[i].Weight)
			pIndices := dp[prevPos][j-1].Indices
			newLen := len(pIndices) + 1
			newIndices := make([]int, newLen)
			copy(newIndices, pIndices)
			newIndices[newLen-1] = tints[i].Idx
			sort.Ints(newIndices)
			newS := State{newScore, newIndices}
			cScore := dp[i+1][j].Score
			if cScore < 0 || newScore > cScore || (newScore == cScore && lexSmaller(newIndices, dp[i+1][j].Indices)) {
				dp[i+1][j] = newS
			}
		}
	}
	// find best
	var maxScore int64 = -1
	best := []int{}
	for j := 0; j < 5; j++ {
		s := dp[n][j]
		if s.Score > maxScore {
			maxScore = s.Score
			best = make([]int, len(s.Indices))
			copy(best, s.Indices)
		} else if s.Score == maxScore && lexSmaller(s.Indices, best) {
			best = make([]int, len(s.Indices))
			copy(best, s.Indices)
		}
	}
	return best
}
# @lc code=end