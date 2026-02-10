#
# @lc app=leetcode id=3748 lang=golang
#
# [3748] Count Stable Subarrays
#

# @lc code=start
func countStableSubarrays(nums []int, queries [][]int) []int64 {
	n := len(nums)
	var runs [][2]int
	i := 0
	for i < n {
		j := i
		for j+1 < n && nums[j] <= nums[j+1] {
			j += 1
		}
		runs = append(runs, [2]int{i, j})
		i = j + 1
	}

	m := len(runs)
	belong := make([]int, n)
	for k := 0; k < m; k++ {
		for p := runs[k][0]; p <= runs[k][1]; p++ {
			belong[p] = k
		}
	}

	pref := make([]int64, m+1)
	for k := 0; k < m; k++ {
		s := runs[k][0]
		e := runs[k][1]
		ln := int64(e - s + 1)
		pref[k+1] = pref[k] + ln*(ln+1)/2
	}

	ans := make([]int64, len(queries))
	for qi, query := range queries {
		l := query[0]
		r := query[1]
		runL := belong[l]
		runR := belong[r]
		var total int64
		if runL == runR {
			ln := int64(r - l + 1)
			total = ln * (ln + 1) / 2
		} else {
			eL := runs[runL][1]
			lnL := int64(eL - l + 1)
			cl := lnL * (lnL + 1) / 2
			sR := runs[runR][0]
			lnR := int64(r - sR + 1)
			cr := lnR * (lnR + 1) / 2
			full := pref[runR] - pref[runL+1]
			total = cl + cr + full
		}
		ans[qi] = total
	}

	return ans
}
# @lc code=end