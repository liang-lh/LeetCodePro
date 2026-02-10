#
# @lc app=leetcode id=3569 lang=golang
#
# [3569] Maximize Count of Distinct Primes After Split
#

import "container/heap"

# @lc code=start
func maximumCount(nums []int, queries [][]int) []int {
	const MAXV = 100010
	n := len(nums)

	isPrime := make([]bool, MAXV)
	for i := 0; i < MAXV; i++ {
		isPrime[i] = true
	}
	isPrime[0] = isPrime[1] = false
	for i := 2; i*i < MAXV; i++ {
		if isPrime[i] {
			for j := i*i; j < MAXV; j += i {
				isPrime[j] = false
			}
		}
	}

	type MinHeap []int
	func (h MinHeap) Len() int           { return len(h) }
	func (h MinHeap) Less(i, j int) bool { return h[i] < h[j] }
	func (h MinHeap) Swap(i, j int)      { h[i], h[j] = h[j], h[i] }
	func (h *MinHeap) Push(x any)        { *h = append(*h, x.(int)) }
	func (h *MinHeap) Pop() any {
		old := (*h)[len(*h)-1]
		*h = (*h)[:len(*h)-1]
		return old
	}

	type MaxHeap []int
	func (h MaxHeap) Len() int           { return len(h) }
	func (h MaxHeap) Less(i, j int) bool { return h[i] > h[j] }
	func (h MaxHeap) Swap(i, j int)      { h[i], h[j] = h[j], h[i] }
	func (h *MaxHeap) Push(x any)        { *h = append(*h, x.(int)) }
	func (h *MaxHeap) Pop() any {
		old := (*h)[len(*h)-1]
		*h = (*h)[:len(*h)-1]
		return old
	}

	min := func(a, b int) int {
		if a < b {
			return a
		}
		return b
	}
	max := func(a, b int) int {
		if a > b {
			return a
		}
		return b
	}

	getFirst := func(p int, nums []int, h *MinHeap, nn int) int {
		for h.Len() > 0 && nums[(*h)[0]] != p {
			heap.Pop(h)
		}
		if h.Len() == 0 {
			return nn
		}
		return (*h)[0]
	}

	getLast := func(p int, nums []int, h *MaxHeap, nn int) int {
		for h.Len() > 0 && nums[(*h)[0]] != p {
			heap.Pop(h)
		}
		if h.Len() == 0 {
			return -1
		}
		return (*h)[0]
	}

	type SegTree struct {
		tree, lazy []int
		n         int
	}

	newSegTree := func(m int) *SegTree {
		st := &SegTree{
			tree: make([]int, 4*(m+1)),
			lazy: make([]int, 4*(m+1)),
			n:    m,
		}
		return st
	}

	stPush := func(st *SegTree, v, tl, tr int) {
		if st.lazy[v] != 0 {
			st.tree[v] += st.lazy[v]
			if tl != tr {
				st.lazy[2*v] += st.lazy[v]
				st.lazy[2*v+1] += st.lazy[v]
			}
			st.lazy[v] = 0
		}
	}

	stUpdate := func(st *SegTree, v, tl, tr, l, r, add int) {
		stPush(st, v, tl, tr)
		if l > r {
			return
		}
		if tl == l && tr == r {
			st.lazy[v] += add
			stPush(st, v, tl, tr)
			return
		}
		tm := (tl + tr) / 2
		stUpdate(st, 2*v, tl, tm, l, min(r, tm), add)
		stUpdate(st, 2*v+1, tm+1, tr, max(l, tm+1), r, add)
		st.tree[v] = max(st.tree[2*v], st.tree[2*v+1])
	}

	var st *SegTree
	if n > 1 {
		st = newSegTree(n - 1)
	}

	if st != nil {
		st.Update = func(l, r, val int) {
			stUpdate(st, 1, 1, st.n, l, r, val)
		}
		st.QueryMax = func() int {
			stPush(st, 1, 1, st.n)
			return st.tree[1]
		}
	}

	minHeaps := make([]MinHeap, MAXV)
	maxHeaps := make([]MaxHeap, MAXV)
	freq := make([]int, MAXV)

	for i := 0; i < n; i++ {
		p := nums[i]
		if p < MAXV {
			heap.Push(&minHeaps[p], i)
			heap.Push(&maxHeaps[p], i)
			freq[p]++
		}
	}

	totalDistinct := 0
	for p := 2; p < MAXV; p++ {
		if isPrime[p] && freq[p] > 0 {
			totalDistinct++
		}
	}

	for p := 2; p < MAXV; p++ {
		if !isPrime[p] || freq[p] == 0 {
			continue
		}
		f := getFirst(p, nums, &minHeaps[p], n)
		l := getLast(p, nums, &maxHeaps[p], n)
		if f < l && st != nil {
			st.Update(f+1, l, 1)
		}
	}

	res := []int{}

	for _, q := range queries {
		idx := q[0]
		newv := q[1]
		oldv := nums[idx]
		if oldv == newv {
			ans := totalDistinct
			if st != nil {
				ans += st.QueryMax()
			}
			res = append(res, ans)
			continue
		}

		if isPrime[oldv] {
			f := getFirst(oldv, nums, &minHeaps[oldv], n)
			l := getLast(oldv, nums, &maxHeaps[oldv], n)
			if f < l && st != nil {
				st.Update(f+1, l, -1)
			}
		}

		freq[oldv]--
		if freq[oldv] == 0 && isPrime[oldv] {
			totalDistinct--
		}

		nums[idx] = newv

		if isPrime[oldv] && freq[oldv] > 0 {
			f := getFirst(oldv, nums, &minHeaps[oldv], n)
			l := getLast(oldv, nums, &maxHeaps[oldv], n)
			if f < l && st != nil {
				st.Update(f+1, l, 1)
			}
		}

		if isPrime[newv] {
			f := getFirst(newv, nums, &minHeaps[newv], n)
			l := getLast(newv, nums, &maxHeaps[newv], n)
			if f < l && st != nil {
				st.Update(f+1, l, -1)
			}
		}

		freq[newv]++
		if freq[newv] == 1 && isPrime[newv] {
			totalDistinct++
		}

		heap.Push(&minHeaps[newv], idx)
		heap.Push(&maxHeaps[newv], idx)

		if isPrime[newv] {
			f := getFirst(newv, nums, &minHeaps[newv], n)
			l := getLast(newv, nums, &maxHeaps[newv], n)
			if f < l && st != nil {
				st.Update(f+1, l, 1)
			}
		}

		ans := totalDistinct
		if st != nil {
			ans += st.QueryMax()
		}
		res = append(res, ans)
	}

	return res
}
# @lc code=end