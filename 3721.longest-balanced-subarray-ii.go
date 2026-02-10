#
# @lc app=leetcode id=3721 lang=golang
#
# [3721] Longest Balanced Subarray II
#

# @lc code=start
func longestBalanced(nums []int) int {
	n := len(nums)
	if n == 0 {
		return 0
	}
	first := make(map[int]int)
	last := make(map[int]int)
	for i := 0; i < n; i++ {
		v := nums[i]
		if _, ok := first[v]; !ok {
			first[v] = i
		}
		last[v] = i
	}
	le := make([]int, n+1)
	lo := make([]int, n+1)
	for l := 1; l <= n; l++ {
		le[l] = le[l-1]
		lo[l] = lo[l-1]
		p := l - 1
		if p < n {
			v := nums[p]
			if last[v] == p {
				if v%2 == 0 {
					le[l]++
				} else {
					lo[l]++
				}
			}
		}
	}
	numEvenFirstLeq := make([]int, n+1)
	numOddFirstLeq := make([]int, n+1)
	for rr := 1; rr <= n; rr++ {
		numEvenFirstLeq[rr] = numEvenFirstLeq[rr-1]
		numOddFirstLeq[rr] = numOddFirstLeq[rr-1]
		q := rr - 1
		v := nums[q]
		if first[v] == q {
			if v%2 == 0 {
				numEvenFirstLeq[rr]++
			} else {
				numOddFirstLeq[rr]++
			}
		}
	}
	totalE := numEvenFirstLeq[n]
	totalO := numOddFirstLeq[n]
	delta := totalE - totalO
	minL := make(map[int]int)
	for l := 0; l <= n; l++ {
		a := le[l] - lo[l]
		if val, ok := minL[a]; !ok || l < val {
			minL[a] = l
		}
	}
	ans := 0
	for r := 0; r < n; r++ {
		re := totalE - numEvenFirstLeq[r+1]
		ro := totalO - numOddFirstLeq[r+1]
		b := re - ro
		target := delta - b
		if minl, ok := minL[target]; ok && minl <= r {
			length := r - minl + 1
			if length > ans {
				ans = length
			}
		}
	}
	return ans
}
# @lc code=end