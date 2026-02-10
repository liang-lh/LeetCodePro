#
# @lc app=leetcode id=3534 lang=golang
#
# [3534] Path Existence Queries in a Graph II
#

import "sort"

# @lc code=start
func pathExistenceQueries(n int, nums []int, maxDiff int, queries [][]int) []int {
	indices := make([]int, n)
	for i := range indices {
		indices[i] = i
	}
	sort.Slice(indices, func(a, b int) bool {
		return nums[indices[a]] < nums[indices[b]]
	})
	pos := make([]int, n)
	for i := range indices {
		pos[indices[i]] = i
	}
	comp := make([]int, n)
	if n > 0 {
		comp[indices[0]] = 0
	}
	cid := 0
	for i := 1; i < n; i++ {
		if nums[indices[i]]-nums[indices[i-1]] > maxDiff {
			cid++
		}
		comp[indices[i]] = cid
	}
	// compute reach
	reach := make([]int, n)
	j := 0
	for i := 0; i < n; i++ {
		for j < n && nums[indices[j]] <= nums[indices[i]]+maxDiff {
			j++
		}
		reach[i] = j - 1
	}
	// doubling table
	const LOG = 18
	par := make([][]int, n)
	for i := range par {
		par[i] = make([]int, LOG)
		par[i][0] = reach[i]
	}
	for d := 1; d < LOG; d++ {
		for i := 0; i < n; i++ {
			p := par[i][d-1]
			par[i][d] = par[p][d-1]
		}
	}
	// queries
	ans := make([]int, len(queries))
	for qi, q := range queries {
		u, v := q[0], q[1]
		if u == v {
			ans[qi] = 0
			continue
		}
		if comp[u] != comp[v] {
			ans[qi] = -1
			continue
		}
		pu := pos[u]
		pv := pos[v]
		if pu > pv {
			pu, pv = pv, pu
		}
		// bin search min k >=1 s.t. reach^k(pu) >= pv
		lo := 1
		hi := n
		for lo < hi {
			mid := lo + (hi-lo)/2
			curr := pu
			k := mid
			for d := 0; d < LOG; d++ {
				if k&1 == 1 {
					curr = par[curr][d]
				}
				k >>= 1
			}
			if curr >= pv {
				hi = mid
			} else {
				lo = mid + 1
			}
		}
		ans[qi] = lo
	}
	return ans
}
# @lc code=end