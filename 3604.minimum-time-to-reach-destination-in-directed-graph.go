#
# @lc app=leetcode id=3604 lang=golang
#
# [3604] Minimum Time to Reach Destination in Directed Graph
#

# @lc code=start
func minTime(n int, edges [][]int) int {
	adj := make([][][3]int, n)
	for _, e := range edges {
		u, v, s, ee := e[0], e[1], e[2], e[3]
		adj[u] = append(adj[u], [3]int{v, s, ee})
	}

	const INF int64 = 1 << 60
	dist := make([]int64, n)
	for i := range dist {
		dist[i] = INF
	}
	dist[0] = 0

	type Item struct {
		t int64
		u int
	}

	type MinHeap []Item

	func (h MinHeap) Len() int { return len(h) }

	func (h MinHeap) Less(i, j int) bool {
		return h[i].t < h[j].t || (h[i].t == h[j].t && h[i].u < h[j].u)
	}

	func (h *MinHeap) Swap(i, j int) {
		h[i], h[j] = h[j], h[i]
	}

	func (h *MinHeap) siftUp(j int) {
		i := j
		for i > 0 {
			p := (i - 1) / 2
			if !h.Less(i, p) {
				break
			}
			h.Swap(p, i)
			i = p
		}
	}

	func (h *MinHeap) siftDown(j int) {
		n := h.Len()
		i := j
		for {
			l := 2*i + 1
			if l >= n { break }
			r := l + 1
			if r < n && h.Less(r, l) { l = r }
			if h.Less(l, i) {
				h.Swap(i, l)
				i = l
			} else {
				break
			}
		}
	}

	func (h *MinHeap) Push(ii Item) {
		*h = append(*h, ii)
		h.siftUp(h.Len() - 1)
	}

	func (h *MinHeap) Pop() Item {
		n := h.Len()
		if n == 0 {
			var zero Item
			return zero
		}
		root := (*h)[0]
		last := (*h)[n-1]
		*h = (*h)[:n-1]
		if n > 1 {
			(*h)[0] = last
			h.siftDown(0)
		}
		return root
	}

	h := &MinHeap{}
	h.Push(Item{t: 0, u: 0})

	max := func(a, b int64) int64 {
		if a > b {
			return a
		}
		return b
	}

	for h.Len() > 0 {
		cur := h.Pop()
		if cur.t > dist[cur.u] {
			continue
		}
		u := cur.u
		tu := cur.t
		for _, e := range adj[u] {
			v := e[0]
			st := int64(e[1])
			en := int64(e[2])
			dep := max(tu, st)
			if dep > en {
				continue
			}
			nt := dep + 1
			if nt < dist[v] {
				dist[v] = nt
				h.Push(Item{t: nt, u: v})
			}
		}
	}

	if dist[n-1] == INF {
		return -1
	}
	return int(dist[n-1])
}
# @lc code=end