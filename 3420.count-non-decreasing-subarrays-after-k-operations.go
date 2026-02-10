#
# @lc app=leetcode id=3420 lang=golang
#
# [3420] Count Non-Decreasing Subarrays After K Operations
#

# @lc code=start
func countNonDecreasingSubarrays(nums []int, k int) int64 {
	n := len(nums)
	if n == 0 {
		return 0
	}
	prefix := make([]int64, n+1)
	for i := 0; i < n; i++ {
		prefix[i+1] = prefix[i] + int64(nums[i])
	}
	ng := make([]int, n)
	stk := []int{}
	for i := n - 1; i >= 0; i-- {
		for len(stk) > 0 && nums[stk[len(stk)-1]] <= nums[i] {
			stk = stk[:len(stk)-1]
		}
		if len(stk) > 0 {
			ng[i] = stk[len(stk)-1]
		} else {
			ng[i] = n
		}
		stk = append(stk, i)
	}
	ne := make([]int, n)
	ne[n-1] = n - 1
	for i := n - 2; i >= 0; i-- {
		if nums[i] <= nums[i+1] {
			ne[i] = ne[i+1]
		} else {
			ne[i] = i
		}
	}
	const LOG = 18
	parr := make([][]int, LOG)
	cumcost := make([][]int64, LOG)
	rfar := make([][]int, LOG)
	for p := 0; p < LOG; p++ {
		parr[p] = make([]int, n)
		cumcost[p] = make([]int64, n)
		rfar[p] = make([]int, n)
	}
	for i := 0; i < n; i++ {
		nextp := ng[i]
		endseg := n - 1
		if nextp < n {
			endseg = nextp - 1
		}
		freer := ne[i]
		var fcost int64 = 0
		if freer < endseg {
			ndips64 := int64(endseg - freer)
			sumdip := prefix[endseg+1] - prefix[freer+1]
			fcost = ndips64 * int64(nums[i]) - sumdip
		}
		parr[0][i] = nextp
		cumcost[0][i] = fcost
		if nextp >= n {
			rfar[0][i] = n - 1
		} else {
			rfar[0][i] = ne[nextp]
		}
	}
	for p := 1; p < LOG; p++ {
		for i := 0; i < n; i++ {
			mid := parr[p-1][i]
			if mid >= n {
				parr[p][i] = n
				cumcost[p][i] = cumcost[p-1][i]
				rfar[p][i] = rfar[p-1][i]
			} else {
				parr[p][i] = parr[p-1][mid]
				cumcost[p][i] = cumcost[p-1][i] + cumcost[p-1][mid]
				rfar[p][i] = rfar[p-1][mid]
			}
		}
	}
	computeFar := func(start int, kk int64) int {
		cur_r := ne[start]
		if cur_r >= n-1 {
			return n - 1
		}
		cur_cum := int64(0)
		cur_pos := start
		for p := LOG - 1; p >= 0; p-- {
			if cur_pos >= n {
				return n - 1
			}
			next_pos := parr[p][cur_pos]
			if next_pos > cur_pos {
				new_cum := cur_cum + cumcost[p][cur_pos]
				if new_cum <= kk {
					rfar_this := rfar[p][cur_pos]
					cur_cum = new_cum
					cur_pos = next_pos
					cur_r = rfar_this
					if cur_r >= n-1 {
						return n - 1
					}
				}
			}
		}
		if cur_pos >= n {
			return n - 1
		}
		nextp := ng[cur_pos]
		endseg := n - 1
		if nextp < n {
			endseg = nextp - 1
		}
		numd64 := int64(endseg - cur_r)
		if numd64 <= 0 {
			return cur_r
		}
		l64 := int64(0)
		r64 := numd64
		for l64 < r64 {
			m64 := (l64 + r64 + 1) / 2
			tt := cur_r + int(m64)
			sum_pt := prefix[tt+1] - prefix[cur_r+1]
			pt_cost := m64 * int64(nums[cur_pos]) - sum_pt
			if cur_cum + pt_cost <= kk {
				l64 = m64
			} else {
				r64 = m64 - 1
			}
		}
		return cur_r + int(l64)
	}
	ans := int64(0)
	for st := 0; st < n; st++ {
		far := computeFar(st, int64(k))
		ans += int64(far - st + 1)
	}
	return ans
}
# @lc code=end