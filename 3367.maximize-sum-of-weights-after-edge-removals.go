#
# @lc app=leetcode id=3367 lang=golang
#
# [3367] Maximize Sum of Weights after Edge Removals
#

# @lc code=start
import "sort"

type pair struct {
	to, weight int
}

func maximizeSumOfWeights(edges [][]int, k int) int64 {
	n := len(edges) + 1
	adj := make([][]pair, n)
	for _, e := range edges {
		u, v, w := e[0], e[1], e[2]
		adj[u] = append(adj[u], pair{v, w})
		adj[v] = append(adj[v], pair{u, w})
	}

	bestNo := make([]int64, n)
	bestYes := make([]int64, n)

	min := func(a, b int) int {
		if a < b {
			return a
		}
		return b
	}

	var dfs func(int, int)
	dfs = func(u, p int) {
		deltas := []int64{}
		base := int64(0)
		for _, pa := range adj[u] {
			v := pa.to
			if v == p {
				continue
			}
			dfs(v, u)
			bn := bestNo[v]
			by := bestYes[v]
			delta := int64(pa.weight) + by - bn
			base += bn
			if delta > 0 {
				deltas = append(deltas, delta)
			}
		}

		sort.Slice(deltas, func(i, j int) bool {
			return deltas[i] > deltas[j]
		})

		// bestNo[u]
		take := min(len(deltas), k)
		sumd := int64(0)
		for i := 0; i < take; i++ {
			sumd += deltas[i]
		}
		bestNo[u] = base + sumd

		// bestYes[u]
		take = min(len(deltas), k-1)
		sumd = int64(0)
		for i := 0; i < take; i++ {
			sumd += deltas[i]
		}
		bestYes[u] = base + sumd
	}

	dfs(0, -1)

	return bestNo[0]
}

# @lc code=end