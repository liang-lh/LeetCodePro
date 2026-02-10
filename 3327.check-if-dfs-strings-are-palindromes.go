#
# @lc app=leetcode id=3327 lang=golang
#
# [3327] Check if DFS Strings Are Palindromes
#

# @lc code=start
import ("sort")

const (
	MOD1 uint64 = 1000000007
	BASE1 uint64 = 131
	MOD2 uint64 = 1000000009
	BASE2 uint64 = 137
)

func findAnswer(parent []int, s string) []bool {
	n := len(parent)
	if n == 0 {
		return []bool{}
	}
	children := make([][]int, n)
	for i := 1; i < n; i++ {
		p := parent[i]
		children[p] = append(children[p], i)
	}
	for i := 0; i < n; i++ {
		sort.Ints(children[i])
	}

	pow1 := make([]uint64, n+1)
	pow1[0] = 1
	for i := 1; i <= n; i++ {
		pow1[i] = (pow1[i-1] * BASE1) % MOD1
	}

	pow2 := make([]uint64, n+1)
	pow2[0] = 1
	for i := 1; i <= n; i++ {
		pow2[i] = (pow2[i-1] * BASE2) % MOD2
	}

	sz := make([]int, n)
	fwd1 := make([]uint64, n)
	fwd2 := make([]uint64, n)
	rev1 := make([]uint64, n)
	rev2 := make([]uint64, n)

	remaining := make([]int, n)
	for i := 0; i < n; i++ {
		remaining[i] = len(children[i])
	}

	q := []int{}
	qidx := 0
	for i := 0; i < n; i++ {
		if remaining[i] == 0 {
			q = append(q, i)
		}
	}

	answer := make([]bool, n)

	for qidx < len(q) {
		u := q[qidx]
		qidx++

		ch := uint64(s[u] - 'a' + 1)

		numc := len(children[u])
		child_len := 0

		h1 := uint64(0)
		h2 := uint64(0)

		for i := 0; i < numc; i++ {
			y := children[u][i]
			subsz := sz[y]
			child_len += subsz

			h1 = (h1 * pow1[subsz] % MOD1 + fwd1[y]) % MOD1
			h2 = (h2 * pow2[subsz] % MOD2 + fwd2[y]) % MOD2
		}

		h1 = (h1 * pow1[1] % MOD1 + ch) % MOD1
		h2 = (h2 * pow2[1] % MOD2 + ch) % MOD2

		fwd1[u] = h1
		fwd2[u] = h2

		rc1 := uint64(0)
		rc2 := uint64(0)

		for i := numc - 1; i >= 0; i-- {
			y := children[u][i]
			subsz := sz[y]
			rc1 = (rc1 * pow1[subsz] % MOD1 + rev1[y]) % MOD1
			rc2 = (rc2 * pow2[subsz] % MOD2 + rev2[y]) % MOD2
		}

		rev1[u] = (ch * pow1[child_len] % MOD1 + rc1) % MOD1
		rev2[u] = (ch * pow2[child_len] % MOD2 + rc2) % MOD2

		sz[u] = child_len + 1

		answer[u] = fwd1[u] == rev1[u] && fwd2[u] == rev2[u]

		p := parent[u]
		if p != -1 {
			remaining[p]--
			if remaining[p] == 0 {
				q = append(q, p)
			}
		}
	}

	return answer
}
# @lc code=end