#
# @lc app=leetcode id=3559 lang=golang
#
# [3559] Number of Ways to Assign Edge Weights II
#

# @lc code=start
func assignEdgeWeights(edges [][]int, queries [][]int) []int {
	const MOD int = 1000000007
	const LOG int = 18
	n := len(edges) + 1
	adj := make([][]int, n+1)
	for _, e := range edges {
		adj[e[0]] = append(adj[e[0]], e[1])
		adj[e[1]] = append(adj[e[1]], e[0])
	}
	parent := make([][]int, n+1)
	for i := 1; i <= n; i++ {
		parent[i] = make([]int, LOG)
	}
	depth := make([]int, n+1)
	visited := make([]bool, n+1)

	// BFS to compute depth and parent[0]
	q := make([]int, 0, n+1)
	front := 0
	q = append(q, 1)
	visited[1] = true
	parent[1][0] = 1
	depth[1] = 0
	for front < len(q) {
		u := q[front]
		front++
		for _, v := range adj[u] {
			if !visited[v] {
				visited[v] = true
				parent[v][0] = u
				depth[v] = depth[u] + 1
				q = append(q, v)
			}
		}
	}

	// Build binary lifting table
	for j := 1; j < LOG; j++ {
		for i := 1; i <= n; i++ {
			parent[i][j] = parent[parent[i][j-1]][j-1]
		}
	}

	// Modular exponentiation
	modpow := func(base, exp int) int {
		res := 1
		base %= MOD
		for exp > 0 {
			if exp&1 == 1 {
				res = int(int64(res) * int64(base) % int64(MOD))
			}
			base = int(int64(base) * int64(base) % int64(MOD))
			exp >>= 1
		}
		return res
	}

	// LCA function
	getLCA := func(u, v int) int {
		if depth[u] > depth[v] {
			u, v = v, u
		}
		diff := depth[v] - depth[u]
		for j := 0; j < LOG; j++ {
			if diff&(1<<j) != 0 {
				v = parent[v][j]
			}
		}
		if u == v {
			return u
		}
		for j := LOG - 1; j >= 0; j-- {
			if parent[u][j] != parent[v][j] {
				u = parent[u][j]
				v = parent[v][j]
			}
		}
		return parent[u][0]
	}

	answer := make([]int, len(queries))
	for i := range queries {
		u := queries[i][0]
		v := queries[i][1]
		if u == v {
			answer[i] = 0
			continue
		}
		lca_node := getLCA(u, v)
		path_len := depth[u] + depth[v] - 2*depth[lca_node]
		answer[i] = modpow(2, path_len-1)
	}
	return answer
}
# @lc code=end