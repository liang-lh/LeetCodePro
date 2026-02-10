#
# @lc app=leetcode id=3373 lang=golang
#
# [3373] Maximize the Number of Target Nodes After Connecting Trees II
#

# @lc code=start
func maxTargetNodes(edges1 [][]int, edges2 [][]int) []int {

	bfs := func(adj [][]int, nn int) []int {
		color := make([]int, nn)
		for i := range color {
			color[i] = -1
		}
		q := []int{}
		qi := 0
		color[0] = 0
		q = append(q, 0)
		for qi < len(q) {
			u := q[qi]
			qi++
			for _, v := range adj[u] {
				if color[v] == -1 {
					color[v] = 1 - color[u]
					q = append(q, v)
				}
			}
		}
		return color
	}

	n := len(edges1) + 1
	m := len(edges2) + 1

	adj1 := make([][]int, n)
	for _, e := range edges1 {
		u, v := e[0], e[1]
		adj1[u] = append(adj1[u], v)
		adj1[v] = append(adj1[v], u)
	}

	adj2 := make([][]int, m)
	for _, e := range edges2 {
		u, v := e[0], e[1]
		adj2[u] = append(adj2[u], v)
		adj2[v] = append(adj2[v], u)
	}

	color1 := bfs(adj1, n)
	cnt0_1, cnt1_1 := 0, 0
	for _, c := range color1 {
		if c == 0 {
			cnt0_1++
		} else {
			cnt1_1++
		}
	}

	color2 := bfs(adj2, m)
	cnt0_2, cnt1_2 := 0, 0
	for _, c := range color2 {
		if c == 0 {
			cnt0_2++
		} else {
			cnt1_2++
		}
	}

	max2 := cnt0_2
	if cnt1_2 > max2 {
		max2 = cnt1_2
	}

	ans := make([]int, n)
	for i := 0; i < n; i++ {
		if color1[i] == 0 {
			ans[i] = cnt0_1 + max2
		} else {
			ans[i] = cnt1_1 + max2
		}
	}

	return ans
}
# @lc code=end