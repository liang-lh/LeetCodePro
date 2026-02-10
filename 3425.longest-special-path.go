#
# @lc app=leetcode id=3425 lang=golang
#
# [3425] Longest Special Path
#

# @lc code=start
func longestSpecialPath(edges [][]int, nums []int) []int {
	type Node struct {
		To, W int
	}

	n := len(nums)
	adj := make([][]Node, n)
	for _, edge := range edges {
		a, b, c := edge[0], edge[1], edge[2]
		adj[a] = append(adj[a], Node{b, c})
		adj[b] = append(adj[b], Node{a, c})
	}

	const MAXV = 50010
	lastPos := make([]int, MAXV)
	for i := 0; i < MAXV; i++ {
		lastPos[i] = -1
	}

	pathCum := []int{}
	maxL := 0
	minN := 1

	var dfs func(int, int, int, int)
	dfs = func(u, p, cumw, curL int) {
		val := nums[u]
		oldP := lastPos[val]
		nL := curL
		if oldP >= nL {
			nL = oldP + 1
		}

		pathCum = append(pathCum, cumw)
		pos := len(pathCum) - 1
		lastPos[val] = pos

		sLen := cumw
		if nL > 0 {
			sLen -= pathCum[nL]
		}
		nd := pos - nL + 1

		if sLen > maxL || (sLen == maxL && nd < minN) {
			maxL = sLen
			minN = nd
		}

		for _, nb := range adj[u] {
			if nb.To == p {
				continue
			}
			dfs(nb.To, u, cumw + nb.W, nL)
		}

		pathCum = pathCum[:len(pathCum)-1]
		lastPos[val] = oldP
	}

	dfs(0, -1, 0, 0)

	return []int{maxL, minN}
}
# @lc code=end