#
# @lc app=leetcode id=3598 lang=golang
#
# [3598] Longest Common Prefix Between Adjacent Strings After Removals
#

# @lc code=start
func longestCommonPrefix(words []string) []int {
	maxx := func(a, b int) int {
		if a > b {
			return a
		}
		return b
	}

	lcp := func(a, b string) int {
		minl := len(a)
		if minl > len(b) {
			minl = len(b)
		}
		for i := 0; i < minl; i++ {
			if a[i] != b[i] {
				return i
			}
		}
		return minl
	}

	n := len(words)
	if n <= 1 {
		return []int{0}
	}

	m := n - 1
	adj := make([]int, m)
	for i := 0; i < m; i++ {
		adj[i] = lcp(words[i], words[i+1])
	}

	const LOG = 18
	st := make([][]int, m)
	for i := range st {
		st[i] = make([]int, LOG)
		st[i][0] = adj[i]
	}

	for j := 1; j < LOG; j++ {
		for ii := 0; ii+(1<<j) <= m; ii++ {
			st[ii][j] = maxx(st[ii][j-1], st[ii+(1<<(j-1))][j-1])
		}
	}

	logg := make([]int, m+2)
	logg[0] = 0
	logg[1] = 0
	for i := 2; i <= m; i++ {
		logg[i] = logg[i>>1] + 1
	}

	qmax := func(L, R int) int {
		if L > R {
			return 0
		}
		ln := R - L + 1
		k := logg[ln]
		return maxx(st[L][k], st[R-(1<<k)+1][k])
	}

	ans := make([]int, n)
	for i := 0; i < n; i++ {
		orig := 0
		if i == 0 {
			orig = qmax(1, m-1)
		} else if i == n-1 {
			orig = qmax(0, m-2)
		} else {
			orig = maxx(qmax(0, i-2), qmax(i+1, m-1))
		}
		newp := 0
		if i > 0 && i < n-1 {
			newp = lcp(words[i-1], words[i+1])
		}
		ans[i] = maxx(orig, newp)
	}

	return ans
}
# @lc code=end