#
# @lc app=leetcode id=3585 lang=golang
#
# [3585] Find Weighted Median Node in Tree
#

# @lc code=start
func findMedian(n int, edges [][]int, queries [][]int) []int {
	const LOG = 18
	type Pair struct {
		to int
		w  int64
	}
	adj := make([][]Pair, n)
	for _, e := range edges {
		u, v := e[0], e[1]
		w := int64(e[2])
		adj[u] = append(adj[u], Pair{v, w})
		adj[v] = append(adj[v], Pair{u, w})
	}
	parent := make([][]int, LOG)
	distPar := make([][]int64, LOG)
	depth := make([]int, n)
	distRoot := make([]int64, n)
	for i := range parent {
		parent[i] = make([]int, n)
		for j := range parent[i] {
			parent[i][j] = -1
		}
	}
	for i := range distPar {
		distPar[i] = make([]int64, n)
	}
	// BFS from root 0
	visited := make([]bool, n)
	q := make([]int, 0, n)
	qi := 0
	parent[0][0] = -1
	depth[0] = 0
	distRoot[0] = 0
	visited[0] = true
	q = append(q, 0)
	for qi < len(q) {
		u := q[qi]
		qi++
		for _, pr := range adj[u] {
			v := pr.to
			w := pr.w
			if !visited[v] {
				visited[v] = true
				parent[0][v] = u
				distPar[0][v] = w
				depth[v] = depth[u] + 1
				distRoot[v] = distRoot[u] + w
				q = append(q, v)
			}
		}
	}
	// Build lifting tables
	for k := 1; k < LOG; k++ {
		for i := 0; i < n; i++ {
			p := parent[k-1][i]
			if p != -1 {
				pp := parent[k-1][p]
				parent[k][i] = pp
				if pp != -1 {
					distPar[k][i] = distPar[k-1][i] + distPar[k-1][p]
				}
			}
		}
	}
	lcaFunc := func(u, v int) int {
		if depth[u] > depth[v] {
			u, v = v, u
		}
		// Equalize depths
		diff := depth[v] - depth[u]
		for k := 0; k < LOG; k++ {
			if (diff & (1 << k)) != 0 {
				v = parent[k][v]
			}
		}
		if u == v {
			return u
		}
		for k := LOG - 1; k >= 0; k-- {
			if parent[k][u] != parent[k][v] {
				u = parent[k][u]
				v = parent[k][v]
			}
		}
		return parent[0][u]
	}
	ans := make([]int, len(queries))
	for j := range queries {
		u := queries[j][0]
		v := queries[j][1]
		if u == v {
			ans[j] = u
			continue
		}
		l := lcaFunc(u, v)
		dul := distRoot[u] - distRoot[l]
		dlv := distRoot[v] - distRoot[l]
		total := dul + dlv
		target := (total + 1) / 2
		if dul >= target {
			// Lift from u
			cur := u
			cum := int64(0)
			needed := target
			for k := LOG - 1; k >= 0; k-- {
				if parent[k][cur] != -1 && cum + distPar[k][cur] < needed {
					cum += distPar[k][cur]
					cur = parent[k][cur]
				}
			}
			x := parent[0][cur]
			ans[j] = x
		} else {
			// Lift from v
			addNeeded := target - dul
			maxAllow := dlv - addNeeded
			cur := v
			cum := int64(0)
			for k := LOG - 1; k >= 0; k-- {
				if parent[k][cur] != -1 && cum + distPar[k][cur] <= maxAllow {
					cum += distPar[k][cur]
					cur = parent[k][cur]
				}
			}
			ans[j] = cur
		}
	}
	return ans
}
# @lc code=end