#
# @lc app=leetcode id=3310 lang=golang
#
# [3310] Remove Methods From Project
#

# @lc code=start
func remainingMethods(n int, k int, invocations [][]int) []int {
	adj := make([][]int, n)
	for _, inv := range invocations {
		adj[inv[0]] = append(adj[inv[0]], inv[1])
	}
	susp := make([]bool, n)
	var stack []int
	susp[k] = true
	stack = append(stack, k)
	for len(stack) > 0 {
		u := stack[len(stack)-1]
		stack = stack[:len(stack)-1]
		for _, v := range adj[u] {
			if !susp[v] {
				susp[v] = true
				stack = append(stack, v)
			}
		}
	}
	canRemove := true
	for _, inv := range invocations {
		if susp[inv[1]] && !susp[inv[0]] {
			canRemove = false
			break
		}
	}
	if !canRemove {
		res := make([]int, n)
		for i := 0; i < n; i++ {
			res[i] = i
		}
		return res
	}
	var res []int
	for i := 0; i < n; i++ {
		if !susp[i] {
			res = append(res, i)
		}
	}
	return res
}

# @lc code=end