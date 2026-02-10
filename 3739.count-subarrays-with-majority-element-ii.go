#
# @lc app=leetcode id=3739 lang=golang
#
# [3739] Count Subarrays With Majority Element II
#

# @lc code=start
func countMajoritySubarrays(nums []int, target int) int64 {
	n := len(nums)
	if n == 0 {
		return 0
	}
	offset := n + 1
	size := 2*n + 2
	ft := make([]int64, size+1)

	var update = func(mut_idx int, delta int64) {
		maxn := len(ft) - 1
		for mut_idx <= maxn {
			ft[mut_idx] += delta
			mut_idx += mut_idx & -mut_idx
		}
	}

	var prefixSum = func(mut_idx int) int64 {
		res := int64(0)
		for mut_idx > 0 {
			res += ft[mut_idx]
			mut_idx -= mut_idx & -mut_idx
		}
		return res
	}

	diff := make([]int, n)
	for i := 0; i < n; i++ {
		if nums[i] == target {
			diff[i] = 1
		} else {
			diff[i] = -1
		}
	}

	prefix := make([]int, n+1)
	for i := 1; i <= n; i++ {
		prefix[i] = prefix[i-1] + diff[i-1]
	}

	var ans int64
	update(0 + offset, 1)

	for k := 1; k <= n; k++ {
		qidx := prefix[k] - 1 + offset
		ans += prefixSum(qidx)
		update(prefix[k] + offset, 1)
	}

	return ans
}
# @lc code=end