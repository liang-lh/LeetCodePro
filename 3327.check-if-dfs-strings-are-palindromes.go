#
# @lc app=leetcode id=3327 lang=golang
#
# [3327] Check if DFS Strings Are Palindromes
#

# @lc code=start
import ("sort")

func findAnswer(parent []int, s string) []bool {
	n := len(parent)
	if n == 0 {
		return []bool{}
	}

	children := make([][]int, n)
	for i := 1; i < n; i++ {
		children[parent[i]] = append(children[parent[i]], i)
	}

	for i := range children {
		sort.Ints(children[i])
	}

	pow1 := make([]uint64, n+1)
	pow1[0] = 1
	const base1 uint64 = 131
	for i := 1; i <= n; i++ {
		pow1[i] = pow1[i-1] * base1
	}

	pow2 := make([]uint64, n+1)
	pow2[0] = 1
	const base2 uint64 = 137
	for i := 1; i <= n; i++ {
		pow2[i] = pow2[i-1] * base2
	}

	sz := make([]int, n)
	hfwd1 := make([]uint64, n)
	hfwd2 := make([]uint64, n)
	hrev1 := make([]uint64, n)
	hrev2 := make([]uint64, n)
	answer := make([]bool, n)

	pending := make([]int, n)
	for i := range children {
		pending[i] = len(children[i])
	}

	q := []int{}
	for i := 0; i < n; i++ {
		if pending[i] == 0 {
			q = append(q, i)
		}
	}

	qi := 0
	for qi < len(q) {
		u := q[qi]
		qi++

		var fwd1, fwd2 uint64 = 0, 0
		childLen := 0
		for _, c := range children[u] {
			fwd1 = fwd1 * pow1[sz[c]] + hfwd1[c]
			fwd2 = fwd2 * pow2[sz[c]] + hfwd2[c]
			childLen += sz[c]
		}

		sz[u] = childLen + 1
		val := uint64(s[u] - 'a' + 1)
		hfwd1[u] = fwd1 * pow1[1] + val
		hfwd2[u] = fwd2 * pow2[1] + val

		var rs1, rs2 uint64 = 0, 0
		for j := len(children[u]) - 1; j >= 0; j-- {
			c := children[u][j]
			rs1 = rs1 * pow1[sz[c]] + hrev1[c]
			rs2 = rs2 * pow2[sz[c]] + hrev2[c]
		}

		hrev1[u] = val * pow1[childLen] + rs1
		hrev2[u] = val * pow2[childLen] + rs2

		answer[u] = hfwd1[u] == hrev1[u] && hfwd2[u] == hrev2[u]

		if parent[u] != -1 {
			pending[parent[u]]--
			if pending[parent[u]] == 0 {
				q = append(q, parent[u])
			}
		}
	}

	return answer
}
# @lc code=end