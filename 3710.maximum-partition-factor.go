#
# @lc app=leetcode id=3710 lang=golang
#
# [3710] Maximum Partition Factor
#

# @lc code=start
func maxPartitionFactor(points [][]int) int {
	mandist := func(a, b []int) int64 {
		dx := int64(a[0] - b[0])
		if dx < 0 {
			dx = -dx
		}
		dy := int64(a[1] - b[1])
		if dy < 0 {
			dy = -dy
		}
		return dx + dy
	}

	canPartition := func(D int64) bool {
		n := len(points)
		adj := make([][]int, n)
		for i := 0; i < n; i++ {
			for j := i + 1; j < n; j++ {
				if mandist(points[i], points[j]) < D {
					adj[i] = append(adj[i], j)
					adj[j] = append(adj[j], i)
				}
			}
		}

		color := make([]int, n)
		for i := range color {
			color[i] = -1
		}

		bfs := func(start int, adj [][]int, color []int) bool {
			const N = 501
			var q [N]int
			head, tail := 0, 0
			q[tail] = start
			tail++
			color[start] = 0
			for head < tail {
				u := q[head]
				head++
				for _, v := range adj[u] {
					if color[v] == -1 {
						color[v] = 1 - color[u]
						q[tail] = v
						tail++
					} else if color[v] == color[u] {
						return false
					}
				}
			}
			return true
		}

		for i := 0; i < n; i++ {
			if color[i] == -1 {
				if !bfs(i, adj, color) {
					return false
				}
			}
		}
		return true
	}

	n := len(points)
	if n == 2 {
		return 0
	}

	var maxD int64 = 0
	for i := 0; i < n; i++ {
		for j := i + 1; j < n; j++ {
			d := mandist(points[i], points[j])
			if d > maxD {
				maxD = d
			}
		}
	}

	lo, hi := int64(0), maxD
	ans := int64(0)
	for lo <= hi {
		mid := lo + (hi - lo)/2
		if canPartition(mid) {
			ans = mid
			lo = mid + 1
		} else {
			hi = mid - 1
		}
	}

	return int(ans)
}
# @lc code=end