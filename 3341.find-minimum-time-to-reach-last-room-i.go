#
# @lc app=leetcode id=3341 lang=golang
#
# [3341] Find Minimum Time to Reach Last Room I
#

# @lc code=start
func minTimeToReach(moveTime [][]int) int {
	n, m := len(moveTime), len(moveTime[0])
	const INF int = 2000000000
	dist := make([][]int, n)
	for i := range dist {
		dist[i] = make([]int, m)
		for j := range dist[i] {
			dist[i][j] = INF
		}
	}
	dist[0][0] = 0

	vis := make([][]bool, n)
	for i := range vis {
		vis[i] = make([]bool, m)
	}

	maxx := func(a, b int) int {
		if a > b {
			return a
		}
		return b
	}

	dir := [][2]int{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}

	for {
		ri, rj, minT := -1, -1, INF
		for i := 0; i < n; i++ {
			for j := 0; j < m; j++ {
				if !vis[i][j] && dist[i][j] < minT {
					minT = dist[i][j]
					ri, rj = i, j
				}
			}
		}
		if ri == -1 {
			break
		}
		vis[ri][rj] = true

		for _, d := range dir {
			ni := ri + d[0]
			nj := rj + d[1]
			if ni >= 0 && ni < n && nj >= 0 && nj < m {
				cand := maxx(dist[ri][rj], moveTime[ni][nj]) + 1
				if cand < dist[ni][nj] {
					dist[ni][nj] = cand
				}
			}
		}
	}

	return dist[n-1][m-1]
}
# @lc code=end