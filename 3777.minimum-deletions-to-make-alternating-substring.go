#
# @lc app=leetcode id=3777 lang=golang
#
# [3777] Minimum Deletions to Make Alternating Substring
#

# @lc code=start
func minDeletions(s string, queries [][]int) []int {
	n := len(s)
	m := n - 1
	if m < 0 {
		var ans []int
		for _, q := range queries {
			if q[0] == 2 {
				ans = append(ans, 0)
			}
		}
		return ans
	}
	tree := make([]int, m+2)
	update := func(idx int, delta int) {
		idx++ // 1-based
		for idx <= m {
			tree[idx] += delta
			idx += idx & -idx
		}
	}
	prefix := func(idx int) int {
		idx++ // 1-based
		res := 0
		for idx > 0 {
			res += tree[idx]
			idx -= idx & -idx
		}
		return res
	}
	rangeSum := func(left, right int) int {
		if left > right {
			return 0
		}
		res := prefix(right)
		if left > 0 {
			res -= prefix(left - 1)
		}
		return res
	}
	// Init BIT
	for i := 0; i < m; i++ {
		d := 0
		if s[i] != s[i+1] {
			d = 1
		}
		update(i, d)
	}
	var ans []int
	for _, q := range queries {
		tp := q[0]
		if tp == 1 {
			j := q[1]
			if j > 0 {
				curr := rangeSum(j-1, j-1)
				delta := 1 - 2 * curr
				update(j-1, delta)
			}
			if j < n-1 {
				curr := rangeSum(j, j)
				delta := 1 - 2 * curr
				update(j, delta)
			}
		} else {
			l, r := q[1], q[2]
			length := r - l + 1
			if length == 1 {
				ans = append(ans, 0)
				continue
			}
			sumd := rangeSum(l, r-1)
			dels := length - 1 - sumd
			ans = append(ans, dels)
		}
	}
	return ans
}
# @lc code=end