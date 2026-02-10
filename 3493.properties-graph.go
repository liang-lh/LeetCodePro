#
# @lc app=leetcode id=3493 lang=golang
#
# [3493] Properties Graph
#

# @lc code=start
func numberOfComponents(properties [][]int, k int) int {
	n := len(properties)
	adj := make([][]int, n)

	intersect := func(a, b []int) int {
		sa := make(map[int]struct{})
		for _, x := range a {
			sa[x] = struct{}{}
		}
		sb := make(map[int]struct{})
		for _, x := range b {
			sb[x] = struct{}{}
		}
		res := 0
		for x := range sa {
			if _, ok := sb[x]; ok {
				res++
			}
		}
		return res
	}

	for i := 0; i < n; i++ {
		for j := i + 1; j < n; j++ {
			if intersect(properties[i], properties[j]) >= k {
				adj[i] = append(adj[i], j)
				adj[j] = append(adj[j], i)
			}
		}
	}

	visited := make([]bool, n)

	var dfs func(int)
	dfs = func(node int) {
		visited[node] = true
		for _, nei := range adj[node] {
			if !visited[nei] {
				dfs(nei)
			}
		}
	}

	components := 0
	for i := 0; i < n; i++ {
		if !visited[i] {
			components++
			dfs(i)
		}
	}

	return components
}
# @lc code=end