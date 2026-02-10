#
# @lc app=leetcode id=3419 lang=golang
#
# [3419] Minimize the Maximum Edge Weight of Graph
#

# @lc code=start
func minMaxWeight(n int, edges [][]int, threshold int) int {
	maxW := 0
	for _, edge := range edges {
		if edge[2] > maxW {
			maxW = edge[2]
		}
	}

	left := 0
	right := maxW
	ans := -1
	for left <= right {
		mid := left + (right-left)/2
		rev := make([][]int, n)
		for _, edge := range edges {
			if edge[2] <= mid {
				rev[edge[1]] = append(rev[edge[1]], edge[0])
			}
		}
		// BFS from 0 in reverse graph
		visited := make([]bool, n)
		q := make([]int, 0, n)
		q = append(q, 0)
		visited[0] = true
		count := 1
		qi := 0
		for qi < len(q) {
			u := q[qi]
			qi++
			for _, v := range rev[u] {
				if !visited[v] {
					visited[v] = true
					count++
					q = append(q, v)
				}
			}
		}
		if count == n {
			ans = mid
			right = mid - 1
		} else {
			left = mid + 1
		}
	}

	return ans
}
# @lc code=end