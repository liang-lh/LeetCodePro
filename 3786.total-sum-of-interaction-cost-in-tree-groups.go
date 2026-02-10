#
# @lc app=leetcode id=3786 lang=golang
#
# [3786] Total Sum of Interaction Cost in Tree Groups
#

# @lc code=start
func interactionCosts(n int, edges [][]int, group []int) int64 {
	adj := make([][]int, n)
	for _, e := range edges {
		u, v := e[0], e[1]
		adj[u] = append(adj[u], v)
		adj[v] = append(adj[v], u)
	}
	total := [21]int64{}
	for _, g := range group {
		total[g]++
	}
	var ans int64
	var dfs func(int, int) [21]int64
	dfs = func(u, p int) [21]int64 {
		cnt := [21]int64{}
		cnt[group[u]] = 1
		for _, v := range adj[u] {
			if v == p {
				continue
			}
			sub := dfs(v, u)
			for g := 1; g <= 20; g++ {
				ans += sub[g] * (total[g] - sub[g])
			}
			for g := 1; g <= 20; g++ {
				cnt[g] += sub[g]
			}
		}
		return cnt
	}
	dfs(0, -1)
	return ans
}

# @lc code=end