#
# @lc app=leetcode id=3704 lang=golang
#
# [3704] Count No-Zero Pairs That Sum to N
#

# @lc code=start
func countNoZeroPairs(n int64) int64 {
	dig := []int{}
	tmp := n
	for tmp > 0 {
		dig = append(dig, int(tmp%10))
		tmp /= 10
	}
	L := len(dig)
	// reverse to MSD first
	for i, j := 0, L-1; i < j; i, j = i+1, j-1 {
		dig[i], dig[j] = dig[j], dig[i]
	}
	dp := make([][][][]int64, L+1)
	for i := range dp {
		dp[i] = make([][][]int64, 2)
		for j := range dp[i] {
			dp[i][j] = make([][]int64, 2)
			for k := range dp[i][j] {
				dp[i][j][k] = make([]int64, 3)
			}
		}
	}
	// base case
	for sa := 0; sa < 2; sa++ {
		for sb := 0; sb < 2; sb++ {
			if sa == 1 && sb == 1 {
				dp[L][sa][sb][0] = 1
			}
		}
	}
	for p := L - 1; p >= 0; p-- {
		for sa := 0; sa < 2; sa++ {
			for sb := 0; sb < 2; sb++ {
				for co := 0; co < 3; co++ {
					dp[p][sa][sb][co] = 0
				}
				minDa := 0
				if sa == 1 {
					minDa = 1
				}
				minDb := 0
				if sb == 1 {
					minDb = 1
				}
				for da := minDa; da <= 9; da++ {
					for db := minDb; db <= 9; db++ {
						nsa := sa
						if da != 0 {
							nsa = 1
						}
						nsb := sb
						if db != 0 {
							nsb = 1
						}
						for cin := 0; cin <= 2; cin++ {
							temp := da + db + cin
							if temp%10 == dig[p] {
								co := temp / 10
								dp[p][sa][sb][co] += dp[p+1][nsa][nsb][cin]
							}
						}
					}
				}
			}
		}
	}
	return dp[0][0][0][0]
}
# @lc code=end