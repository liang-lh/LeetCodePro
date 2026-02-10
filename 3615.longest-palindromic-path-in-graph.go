#
# @lc app=leetcode id=3615 lang=golang
#
# [3615] Longest Palindromic Path in Graph
#

# @lc code=start
func maxLen(n int, edges [][]int, label string) int {
	adj := make([][]int, n)
	for _, e := range edges {
		u, v := e[0], e[1]
		adj[u] = append(adj[u], v)
		adj[v] = append(adj[v], u)
	}

	N := 1 << n
	memo := make([]int, n*n*N)

	maxx := func(a, b int) int {
		if a > b {
			return a
		}
		return b
	}

	var dfs func(int, int, int) int
	dfs = func(u, v, mask int) int {
		idx := (u*n + v)*N + mask
		if memo[idx] > 0 {
			return memo[idx]
		}
		res := 1
		for _, nu := range adj[u] {
			if (mask & (1 << nu)) != 0 {
				continue
			}
			lch := label[nu]
			for _, nv := range adj[v] {
				if (mask & (1 << nv)) != 0 {
					continue
				}
				if label[nv] != lch {
					continue
				}
				if nu == nv {
					continue
				}
				newmask := mask | (1 << nu) | (1 << nv)
				cand := 1 + dfs(nu, nv, newmask)
				if cand > res {
					res = cand
				}
			}
		}
		memo[idx] = res
		return res
	}

	ans := 1

	// odd centers
	for c := 0; c < n; c++ {
		cmask := 1 << c
		for _, l := range adj[c] {
			for _, r := range adj[c] {
				if l == r {
					continue
				}
				if label[l] != label[r] {
					continue
				}
				initmask := cmask | (1 << l) | (1 << r)
				arm := dfs(l, r, initmask)
				total := 2*arm + 1
				if total > ans {
					ans = total
				}
			}
		}
	}

	// even centers
	for _, e := range edges {
		for k := 0; k < 2; k++ {
			a := e[k]
			b := e[1 - k]
			if label[a] != label[b] {
				continue
			}
			ans = maxx(ans, 2)
			amask := (1 << a) | (1 << b)
			for _, l := range adj[a] {
				if l == b {
					continue
				}
				for _, r := range adj[b] {
					if r == a {
						continue
					}
					if l == r {
						continue
					}
					if label[l] != label[r] {
						continue
					}
					initmask := amask | (1 << l) | (1 << r)
					arm := dfs(l, r, initmask)
					total := 2 + 2*arm
					if total > ans {
						ans = total
					}
				}
			}
		}
	}

	return ans
}
# @lc code=end