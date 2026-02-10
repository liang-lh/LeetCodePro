#
# @lc app=leetcode id=3310 lang=golang
#
# [3310] Remove Methods From Project
#

# @lc code=start
func remainingMethods(n int, k int, invocations [][]int) []int {
	adj := make([][]int, n)
	for _, inv := range invocations {
		a, b := inv[0], inv[1]
		adj[a] = append(adj[a], b)
	}

	vis := make([]bool, n)
	vis[k] = true

	q := make([]int, 0, n)
	q = append(q, k)
	front := 0

	for front < len(q) {
		u := q[front]
		front++
		for _, v := range adj[u] {
			if !vis[v] {
				vis[v] = true
				q = append(q, v)
			}
		}
	}

	canRemove := true
	for _, inv := range invocations {
		a, b := inv[0], inv[1]
		if !vis[a] && vis[b] {
			canRemove = false
			break
		}
	}

	if !canRemove {
		res := make([]int, n)
		for i := range res {
			res[i] = i
		}
		return res
	}

	res := make([]int, 0, n)
	for i := 0; i < n; i++ {
		if !vis[i] {
			res = append(res, i)
		}
	}

	return res
}
# @lc code=end