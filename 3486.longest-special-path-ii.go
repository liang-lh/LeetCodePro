#
# @lc app=leetcode id=3486 lang=golang
#
# [3486] Longest Special Path II
#

# @lc code=start
func longestSpecialPath(edges [][]int, nums []int) []int {
	n := len(nums)
	graph := make([][][2]int, n)
	for _, e := range edges {
		u, v, w := e[0], e[1], e[2]
		graph[u] = append(graph[u], [2]int{v, w})
		graph[v] = append(graph[v], [2]int{u, w})
	}
	children := make([][][2]int, n)
	var buildTree func(u, p int)
	buildTree = func(u, p int) {
		for _, pair := range graph[u] {
			v, w := pair[0], pair[1]
			if v != p {
				children[u] = append(children[u], [2]int{v, w})
				buildTree(v, u)
			}
		}
	}
	buildTree(0, -1)

	max_down := make([]int64, n)
	max64 := func(a, b int64) int64 {
		if a > b {
			return a
		}
		return b
	}
	var dfsMaxDown func(u int) int64
	dfsMaxDown = func(u int) int64 {
		res := int64(0)
		for _, pair := range children[u] {
			v, w := pair[0], pair[1]
			res = max64(res, int64(w) + dfsMaxDown(v))
		}
		max_down[u] = res
		return res
	}
	dfsMaxDown(0)

	var maxLen int64 = 0
	minNodes := n + 1
	minInt := func(a, b int) int {
		if a < b {
			return a
		}
		return b
	}

	const MAX_VAL = 50005
	var cnt [50005]int

	var specialDFS func(u int, curLen int64, curNodes int, hasDup bool)
	specialDFS = func(u int, curLen int64, curNodes int, hasDup bool) {
		// Update globals
		if curLen > maxLen {
			maxLen = curLen
			minNodes = curNodes
		} else if curLen == maxLen {
			minNodes = minInt(minNodes, curNodes)
		}

		// Try children
		for _, pair := range children[u] {
			v, w := pair[0], pair[1]
			if curLen + int64(w) + max_down[v] < maxLen {
				continue
			}
			val := nums[v]
			c := cnt[val]
			can := false
			newDup := hasDup
			if c == 0 {
				can = true
			} else if c == 1 && !hasDup {
				newDup = true
				can = true
			}
			if can {
				cnt[val]++
				specialDFS(v, curLen + int64(w), curNodes + 1, newDup)
				cnt[val]--
			}
		}
	}

	for i := 0; i < n; i++ {
		val := nums[i]
		cnt[val] = 1
		specialDFS(i, 0, 1, false)
		cnt[val] = 0
	}

	return []int{int(maxLen), minNodes}
}
# @lc code=end