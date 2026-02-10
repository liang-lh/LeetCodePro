#
# @lc app=leetcode id=3544 lang=golang
#
# [3544] Subtree Inversion Sum
#

# @lc code=start
func subtreeInversionSum(edges [][]int, nums []int, k int) int64 {
	n := len(nums)
	adj := make([][]int, n)
	for _, e := range edges {
		adj[e[0]] = append(adj[e[0]], e[1])
		adj[e[1]] = append(adj[e[1]], e[0])
	}
	children := make([][]int, n)
	var build func(int, int)
	build = func(u, p int) {
		for _, v := range adj[u] {
			if v != p {
				children[u] = append(children[u], v)
				build(v, u)
			}
		}
	}
	build(0, -1)
	maxdp := make([][]int64, n)
	mindp := make([][]int64, n)
	for i := 0; i < n; i++ {
		maxdp[i] = make([]int64, k+1)
		mindp[i] = make([]int64, k+1)
	}
	var dfs func(int)
	dfs = func(u int) {
		for _, c := range children[u] {
			dfs(c)
		}
		num := int64(nums[u])
		for d := 1; d <= k; d++ {
			allowed := d == k
			// inv0 s=1
			cd0 := d
			if d < k {
				cd0 = d + 1
			}
			sum_best0 := int64(0)
			sum_worst0 := int64(0)
			for _, c := range children[u] {
				sum_best0 += maxdp[c][cd0]
				sum_worst0 += mindp[c][cd0]
			}
			max0 := num + sum_best0
			min0 := num + sum_worst0
			maxdp[u][d] = max0
			mindp[u][d] = min0
			if allowed {
				cd1 := 1
				sum_best1 := int64(0)
				sum_worst1 := int64(0)
				for _, c := range children[u] {
					sum_best1 -= mindp[c][cd1]
					sum_worst1 -= maxdp[c][cd1]
				}
				max1 := -num + sum_best1
				min1 := -num + sum_worst1
				if max1 > maxdp[u][d] {
					maxdp[u][d] = max1
				}
				if min1 < mindp[u][d] {
					mindp[u][d] = min1
				}
			}
		}
	}
	dfs(0)
	return maxdp[0][k]
}
# @lc code=end