#
# @lc app=leetcode id=3762 lang=golang
#
# [3762] Minimum Operations to Equalize Subarrays
#

# @lc code=start
import (
	"math"
	"sort"
)

func minOperations(nums []int, k int, queries [][]int) []int64 {
	n := len(nums)

	if n == 0 {
		return []int64{}
	}

	q := len(queries)

	pos := make([]int64, n)
	mods := make([]int, n)

	for i := 0; i < n; i++ {
		pos[i] = int64(nums[i]) / int64(k)
		mods[i] = nums[i] % k
	}

	all_pos := make([]int64, n)
	copy(all_pos, pos)
	sort.Slice(all_pos, func(i, j int) bool { return all_pos[i] < all_pos[j] })

	unique_pos := make([]int64, 0, n)
	for i := 0; i < n; i++ {
		if i == 0 || all_pos[i] != all_pos[i-1] {
			unique_pos = append(unique_pos, all_pos[i])
		}
	}

	M := len(unique_pos)

	rank := make([]int, n)
	for i := 0; i < n; i++ {
		rank[i] = sort.Search(M, func(j int) bool { return unique_pos[j] >= pos[i] }) + 1
	}

	type Fenwick struct {
		n   int
		tree []int64
	}

	func newFenwick(size int) *Fenwick {
		return &Fenwick{size, make([]int64, size+2)}
	}

	func (f *Fenwick) update(idx int, val int64) {
		for idx <= f.n {
			f.tree[idx] += val
			idx += idx & -idx
		}
	}

	func (f *Fenwick) query(idx int) int64 {
		sum := int64(0)
		for ; idx > 0; idx -= idx & -idx {
			sum += f.tree[idx]
		}
		return sum
	}

	type Query struct {
		idx, l, r int
	}

	qrys := make([]Query, q)
	for i := 0; i < q; i++ {
		qrys[i] = Query{i, queries[i][0], queries[i][1]}
	}

	block_size := int(math.Sqrt(float64(n)))
	if block_size == 0 {
		block_size = 1
	}

	sort.Slice(qrys, func(i, j int) bool {
		bi := qrys[i].l / block_size
		bj := qrys[j].l / block_size
		if bi != bj {
			return bi < bj
		}
		return qrys[i].r < qrys[j].r
	})

	ft_count := newFenwick(M)
	ft_sum := newFenwick(M)

	mod_freq := make(map[int]int)

	current_l := 0
	current_r := -1
	var current_total_sum int64 = 0
	current_distinct := 0

	ans := make([]int64, q)

	add := func(idx int) {
		md := mods[idx]
		mod_freq[md]++
		if mod_freq[md] == 1 {
			current_distinct++
		}
	
		current_total_sum += pos[idx]
	
		rk := rank[idx]
		ft_count.update(rk, 1)
		ft_sum.update(rk, pos[idx])
	}

	remove := func(idx int) {
		md := mods[idx]
		mod_freq[md]--
		if mod_freq[md] == 0 {
			current_distinct--
		}
	
		current_total_sum -= pos[idx]
	
		rk := rank[idx]
		ft_count.update(rk, -1)
		ft_sum.update(rk, -pos[idx])
	}

	get_sum_smallest := func(kk int) int64 {
		if kk == 0 {
			return 0
		}
	
		lo := 1
		hi := M
		for lo < hi {
			mid := (lo + hi) / 2
			if ft_count.query(mid) >= int64(kk) {
				hi = mid
			} else {
				lo = mid + 1
			}
		}
	
		rr := lo
	
		prev_c := ft_count.query(rr - 1)
		sum_prev := ft_sum.query(rr - 1)
		need := int64(kk) - prev_c
		p_rr := unique_pos[rr-1]
		return sum_prev + need * p_rr
	}

	get_kth := func(kk int) int64 {
		lo := 1
		hi := M
		for lo < hi {
			mid := (lo + hi) / 2
			if ft_count.query(mid) >= int64(kk) {
				hi = mid
			} else {
				lo = mid + 1
			}
		}
	
		return unique_pos[lo-1]
	}

	for _, qq := range qrys {
		ql := qq.l
		qr := qq.r

		for current_l > ql {
			current_l--
			add(current_l)
		}

		for current_r < qr {
			current_r++
			add(current_r)
		}

		for current_l < ql {
			remove(current_l)
			current_l++
		}

		for current_r > qr {
			remove(current_r)
			current_r--
		}

		length := current_r - current_l + 1

		if current_distinct == 1 {
			h := length / 2
			sum_bot := get_sum_smallest(h)
			if length%2 == 0 {
				ans[qq.idx] = current_total_sum - 2 * sum_bot
			} else {
				med := get_kth(h + 1)
				ans[qq.idx] = current_total_sum - 2*sum_bot - med
			}
		} else {
			ans[qq.idx] = -1
		}
	}

	return ans
}
# @lc code=end