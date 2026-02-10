#
# @lc app=leetcode id=3553 lang=golang
#
# [3553] Minimum Weighted Subgraph With the Required Paths II
#

# @lc code=start
func minimumWeight(edges [][]int, queries [][]int) []int {
	n := len(edges) + 1
	const LOG = 18

	adj := make([][][2]int, n)
	for _, edge := range edges {
		u, v, w := edge[0], edge[1], edge[2]
		adj[u] = append(adj[u], [2]int{v, w})
		adj[v] = append(adj[v], [2]int{u, w})
	}

	parent := make([]int, n)
	for i := range parent {
		parent[i] = -1
	}
	parent[0] = 0

	weightedDepth := make([]int, n)
	lev := make([]int, n)

	// BFS for depths and parents
	q := []int{0}
	front := 0
	weightedDepth[0] = 0
	lev[0] = 0
	for front < len(q) {
		u := q[front]
		front++
		for _, e := range adj[u] {
			v, w := e[0], e[1]
			if parent[v] == -1 {
				parent[v] = u
				weightedDepth[v] = weightedDepth[u] + w
				lev[v] = lev[u] + 1
				q = append(q, v)
			}
		}
	}

	// Binary lifting
	par := make([][]int, LOG)
	for k := 0; k < LOG; k++ {
		par[k] = make([]int, n)
		for i := 0; i < n; i++ {
			par[k][i] = -1
		}
	}

	for i := 0; i < n; i++ {
		par[0][i] = parent[i]
	}

	for k := 1; k < LOG; k++ {
		for i := 0; i < n; i++ {
			p := par[k-1][i]
			if p != -1 {
				par[k][i] = par[k-1][p]
			}
		}
	}

	getLCA := func(u, v int) int {
		if lev[u] > lev[v] {
			u, v = v, u
		}
		diff := lev[v] - lev[u]
		for k := 0; k < LOG; k++ {
			if (diff & (1 << k)) != 0 {
				v = par[k][v]
			}
		}
		if u == v {
			return u
		}
		for k := LOG - 1; k >= 0; k-- {
			if par[k][u] != par[k][v] {
				u = par[k][u]
				v = par[k][v]
			}
		}
		return par[0][u]
	}

	getDist := func(u, v int) int {
		lca := getLCA(u, v)
		return weightedDepth[u] + weightedDepth[v] - 2 * weightedDepth[lca]
	}

	ans := make([]int, len(queries))
	for i, query := range queries {
		s1, s2, d := query[0], query[1], query[2]
		d12 := getDist(s1, s2)
		d1d := getDist(s1, d)
		d2d := getDist(s2, d)
		ans[i] = (d12 + d1d + d2d) / 2
	}

	return ans
}
# @lc code=end