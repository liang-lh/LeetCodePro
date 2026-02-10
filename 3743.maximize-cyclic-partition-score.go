#
# @lc app=leetcode id=3743 lang=golang
#
# [3743] Maximize Cyclic Partition Score
#

# @lc code=start
func maximumScore(nums []int, k int) int64 {
	n := len(nums)
	if n == 0 {
		return 0
	}
	const INF int64 = 1 << 60
	var globalMax, globalMin int64 = -INF, INF
	for _, v := range nums {
		iv := int64(v)
		if iv > globalMax {
			globalMax = iv
		}
		if iv < globalMin {
			globalMin = iv
		}
	}
	fullRange := globalMax - globalMin

	// prefix max/min [0..l-1]
	prefixMax := make([]int64, n+1)
	prefixMin := make([]int64, n+1)
	prefixMax[0] = -INF
	prefixMin[0] = INF
	cmx, cmn := int64(-INF), int64(INF)
	for l := 1; l <= n; l++ {
		cmx = max(cmx, int64(nums[l-1]))
		cmn = min(cmn, int64(nums[l-1]))
		prefixMax[l] = cmx
		prefixMin[l] = cmn
	}

	// suffix max/min last l elems nums[n-l : n]
	suffixMax := make([]int64, n+1)
	suffixMin := make([]int64, n+1)
	suffixMax[0] = -INF
	suffixMin[0] = INF
	cmx, cmn = int64(-INF), int64(INF)
	for l := 1; l <= n; l++ {
		idx := n - l
		cmx = max(cmx, int64(nums[idx]))
		cmn = min(cmn, int64(nums[idx]))
		suffixMax[l] = cmx
		suffixMin[l] = cmn
	}

	// range_max[start][leng] = max(nums[start:start+leng])
	rangeMaxs := make([][]int64, n)
	rangeMins := make([][]int64, n)
	for start := 0; start < n; start++ {
		maxLen := n - start
		rangeMaxs[start] = make([]int64, maxLen+1)
		rangeMins[start] = make([]int64, maxLen+1)
		cmx = int64(-INF)
		cmn = int64(INF)
		for leng := 1; leng <= maxLen; leng++ {
			cmx = max(cmx, int64(nums[start+leng-1]))
			cmn = min(cmn, int64(nums[start+leng-1]))
			rangeMaxs[start][leng] = cmx
			rangeMins[start][leng] = cmn
		}
	}

	dp := make([][]int64, k+1)
	for j := 0; j <= k; j++ {
		dp[j] = make([]int64, n)
		for ii := 0; ii < n; ii++ {
			dp[j][ii] = -INF
		}
	}
	for i := 0; i < n; i++ {
		dp[1][i] = fullRange
	}

	for j := 2; j <= k; j++ {
		for i := 0; i < n; i++ {
			dp[j][i] = dp[j-1][i]
			for lenf := 1; lenf < n; lenf++ {
				nexti := (i + lenf) % n
				var c int64
				if i+lenf <= n {
					c = rangeMaxs[i][lenf] - rangeMins[i][lenf]
				} else {
					suflen := n - i
					prelen := lenf - suflen
					pmx := prefixMax[prelen]
					pmn := prefixMin[prelen]
					smx := suffixMax[suflen]
					smn := suffixMin[suflen]
					c = max(pmx, smx) - min(pmn, smn)
				}
				if dp[j-1][nexti] != -INF {
					dp[j][i] = max(dp[j][i], c+dp[j-1][nexti])
				}
			}
		}
	}

	ans := int64(0)
	for j := 1; j <= k; j++ {
		for i := 0; i < n; i++ {
			ans = max(ans, dp[j][i])
		}
	}
	return ans
}

func max(a, b int64) int64 {
	if a > b {
		return a
	}
	return b
}

func min(a, b int64) int64 {
	if a < b {
		return a
	}
	return b
}
# @lc code=end