#
# @lc app=leetcode id=3640 lang=golang
#
# [3640] Trionic Array II
#

# @lc code=start
func maxSumTrionic(nums []int) int64 {
	const INF int64 = 1000000000000000000
	const NEGINF int64 = -INF * 2

	func max(a, b int64) int64 {
		if a > b {
			return a
		}
		return b
	}

	n := len(nums)

	// prefix sums
	prefix := make([]int64, n+1)
	for i := 0; i < n; i++ {
		prefix[i+1] = prefix[i] + int64(nums[i])
	}

	// dp_left[i]: max sum strict inc ending at i, length >=2 or NEGINF
	dp_left := make([]int64, n)
	for i := range dp_left {
		dp_left[i] = NEGINF
	}
	for i := 1; i < n; i++ {
		if nums[i-1] < nums[i] {
			cand1 := int64(nums[i-1]) + int64(nums[i])
			cand2 := NEGINF
			if dp_left[i-1] != NEGINF {
				cand2 = dp_left[i-1] + int64(nums[i])
			}
			dp_left[i] = max(cand1, cand2)
		}
	}

	// dp_right[i]: max sum strict inc starting at i, length >=2 or NEGINF
	dp_right := make([]int64, n)
	for i := range dp_right {
		dp_right[i] = NEGINF
	}
	for i := n-2; i >= 0; i-- {
		if nums[i] < nums[i+1] {
			cand1 := int64(nums[i]) + int64(nums[i+1])
			cand2 := NEGINF
			if dp_right[i+1] != NEGINF {
				cand2 = int64(nums[i]) + dp_right[i+1]
			}
			dp_right[i] = max(cand1, cand2)
		}
	}

	max_sum := NEGINF
	current_max := NEGINF

	for q := 1; q < n; q++ {
		if nums[q-1] > nums[q] {
			p_val := dp_left[q-1] - prefix[q]
			if dp_left[q-1] != NEGINF {
				current_max = max(current_max, p_val)
			}
			if dp_right[q] != NEGINF && current_max != NEGINF {
				total := current_max + prefix[q] + dp_right[q]
				if total > max_sum {
					max_sum = total
				}
			}
		} else {
			current_max = NEGINF
		}
	}

	return max_sum
}
# @lc code=end