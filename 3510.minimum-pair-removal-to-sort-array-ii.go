#
# @lc app=leetcode id=3510 lang=golang
#
# [3510] Minimum Pair Removal to Sort Array II
#

# @lc code=start
import "container/heap"

type Node struct {
	val  int64
	prev int
	next int
	pos  int
}

type Entry struct {
	sum  int64
	pos  int
	left int
}

type PriorityQueue []*Entry

func (pq PriorityQueue) Len() int {
	return len(pq)
}

func (pq PriorityQueue) Less(i, j int) bool {
	if pq[i].sum != pq[j].sum {
		return pq[i].sum < pq[j].sum
	}
	if pq[i].pos != pq[j].pos {
		return pq[i].pos < pq[j].pos
	}
	return pq[i].left < pq[j].left
}

func (pq PriorityQueue) Swap(i, j int) {
	pq[i], pq[j] = pq[j], pq[i]
}

func (pq *PriorityQueue) Push(x interface{}) {
	*pq = append(*pq, x.(*Entry))
}

func (pq *PriorityQueue) Pop() interface{} {
	old := *pq
	n := len(old)
	item := old[n-1]
	*pq = old[:n-1]
	return item
}

func minimumPairRemoval(nums []int) int {
	n := len(nums)
	if n <= 1 {
		return 0
	}
	nodes := make([]Node, n)
	deleted := make([]bool, n)
	for i := 0; i < n; i++ {
		nodes[i].val = int64(nums[i])
		nodes[i].pos = i
		nodes[i].prev = -1
		nodes[i].next = -1
	}
	for i := 0; i < n-1; i++ {
		nodes[i].next = i + 1
		nodes[i+1].prev = i
	}
	pq := &PriorityQueue{}
	heap.Init(pq)
	bad := 0
	for i := 0; i < n-1; i++ {
		r := nodes[i].next
		if nodes[i].val > nodes[r].val {
			bad++
		}
		sumv := nodes[i].val + nodes[r].val
		heap.Push(pq, &Entry{sumv, nodes[i].pos, i})
	}
	currentLen := n
	ops := 0
	for bad > 0 && currentLen > 1 {
		var entry *Entry
		for pq.Len() > 0 {
			e := heap.Pop(pq).(*Entry)
			l := e.left
			if deleted[l] {
				continue
			}
			r := nodes[l].next
			if r == -1 || deleted[r] || nodes[r].prev != l {
				continue
			}
			if nodes[l].val + nodes[r].val != e.sum {
				continue
			}
			entry = e
			break
		}
		if entry == nil {
			break
		}
		l := entry.left
		r := nodes[l].next
		// subtract old bads
		if nodes[l].val > nodes[r].val {
			bad--
		}
		p := nodes[l].prev
		if p != -1 {
			if nodes[p].val > nodes[l].val {
				bad--
			}
		}
		nn := nodes[r].next
		if nn != -1 {
			if nodes[r].val > nodes[nn].val {
				bad--
			}
		}
		// merge r into l
		nodes[l].val += nodes[r].val
		deleted[r] = true
		nodes[l].next = nn
		if nn != -1 {
			nodes[nn].prev = l
		}
		currentLen--
		ops++
		// add new bads
		if p != -1 {
			if nodes[p].val > nodes[l].val {
				bad++
			}
		}
		if nn != -1 {
			if nodes[l].val > nodes[nn].val {
				bad++
			}
		}
		// push new pairs if exist
		if p != -1 {
			newsum := nodes[p].val + nodes[l].val
			heap.Push(pq, &Entry{newsum, nodes[p].pos, p})
		}
		if nn != -1 {
			newsum := nodes[l].val + nodes[nn].val
			heap.Push(pq, &Entry{newsum, nodes[l].pos, l})
		}
	}
	return ops
}
# @lc code=end