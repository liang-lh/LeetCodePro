#
# @lc app=leetcode id=3695 lang=golang
#
# [3695] Maximize Alternating Sum Using Swaps
#

# @lc code=start
func maxAlternatingSum(nums []int, swaps [][]int) int64 {
	type UF struct {
		parent []int
		rank   []int
	}

	ufFind := func(n int) *UF {
		uf := &UF{parent: make([]int, n), rank: make([]int, n)}
		for i := range uf.parent {
			uf.parent[i] = i
		}
		return uf
	}

	find := func(uf *UF, x int) int {
		if uf.parent[x] != x {
			uf.parent[x] = find(uf, uf.parent[x])
		}
		return uf.parent[x]
	}

	union := func(uf *UF, x, y int) {
		px := find(uf, x)
		py := find(uf, y)
		if px == py {
			return
		}
		if uf.rank[px] < uf.rank[py] {
			uf.parent[px] = py
		} else {
			uf.parent[py] = px
			if uf.rank[px] == uf.rank[py] {
				uf.rank[px]++
			}
		}
	}

	n := len(nums)
	uf := ufFind(n)
	for _, sw := range swaps {
		union(uf, sw[0], sw[1])
	}

	compVals := make(map[int][]int64)
	compEven := make(map[int]int)
	for i := 0; i < n; i++ {
		ro := find(uf, i)
		compVals[ro] = append(compVals[ro], int64(nums[i]))
		if i%2 == 0 {
			compEven[ro]++
		}
	}

	var total int64
	for ro, vals := range compVals {
		sort.Slice(vals, func(a, b int) bool { return vals[a] > vals[b] })
		evenCnt := compEven[ro]
		if evenCnt > len(vals) {
			evenCnt = len(vals)
		}
		sumEven := int64(0)
		for j := 0; j < evenCnt; j++ {
			sumEven += vals[j]
		}
		var totalComp int64
		for _, v := range vals {
			totalComp += v
		}
		total += 2*sumEven - totalComp
	}

	return total
}

# @lc code=end