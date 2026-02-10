#
# @lc app=leetcode id=3525 lang=golang
#
# [3525] Find X Value of Array II
#

# @lc code=start
func resultArray(nums []int, k int, queries [][]int) []int {
	n := len(nums)
	if n == 0 {
		return []int{}
	}
	mods := make([]int, n)
	for i := 0; i < n; i++ {
		mods[i] = nums[i] % k
	}
	const MAXK = 5
	type Node struct {
		endmod [MAXK]int
		cnt    [MAXK][MAXK]int
	}
	tree := make([]Node, 4*n+10)
	initS := 1 % k

	makeNeutral := func(kk int) Node {
		var nd Node
		for s := 0; s < kk; s++ {
			nd.endmod[s] = s
		}
		return nd
	}
	makeLeaf := func(m, kk int) Node {
		var nd Node
		for s := 0; s < kk; s++ {
			np := int(int64(s) * int64(m) % int64(kk))
			nd.endmod[s] = np
			nd.cnt[s][np] = 1
		}
		return nd
	}
	combine := func(a Node, b Node, kk int) Node {
		var res Node
		for s := 0; s < kk; s++ {
			mid := a.endmod[s]
			res.endmod[s] = b.endmod[mid]
			for t := 0; t < kk; t++ {
				res.cnt[s][t] = a.cnt[s][t] + b.cnt[mid][t]
			}
		}
		return res
	}

	var build func(v, tl, tr int)
	build = func(v, tl, tr int) {
		if tl == tr {
			tree[v] = makeLeaf(mods[tl], k)
			return
		}
		tm := (tl + tr) >> 1
		build(2*v, tl, tm)
		build(2*v+1, tm+1, tr)
		tree[v] = combine(tree[2*v], tree[2*v+1], k)
	}
	build(1, 0, n-1)

	var updatef func(v, tl, tr, pos, newm int)
	updatef = func(v, tl, tr, pos, newm int) {
		if tl == tr {
			tree[v] = makeLeaf(newm, k)
			return
		}
		tm := (tl + tr) >> 1
		if pos <= tm {
			updatef(2*v, tl, tm, pos, newm)
		} else {
			updatef(2*v+1, tm+1, tr, pos, newm)
		}
		tree[v] = combine(tree[2*v], tree[2*v+1], k)
	}

	var queryf func(v, tl, tr, l, r int) Node
	queryf = func(v, tl, tr, l, r int) Node {
		if l > tr || r < tl {
			return makeNeutral(k)
		}
		if l <= tl && tr <= r {
			return tree[v]
		}
		tm := (tl + tr) >> 1
		le := queryf(2*v, tl, tm, l, r)
		ri := queryf(2*v+1, tm+1, tr, l, r)
		return combine(le, ri, k)
	}

	ans := make([]int, len(queries))
	for i := 0; i < len(queries); i++ {
		q := queries[i]
		idx := q[0]
		val := q[1]
		st := q[2]
		xi := q[3]
		newm := val % k
		updatef(1, 0, n-1, idx, newm)
		comb := queryf(1, 0, n-1, st, n-1)
		ans[i] = comb.cnt[initS][xi]
	}
	return ans
}
# @lc code=end