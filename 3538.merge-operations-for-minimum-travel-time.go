#
# @lc app=leetcode id=3538 lang=golang
#
# [3538] Merge Operations for Minimum Travel Time
#

# @lc code=start
import "math"

func minTravelTime(l int, n int, k int, position []int, time []int) int {
	prefix := make([]int, n+1)
	for i := 0; i < n; i++ {
		prefix[i+1] = prefix[i] + time[i]
	}

	const INF int64 = 1e18

	dp := make([][][][]int64, n)
	for i := range dp {
		dp[i] = make([][][]int64, n)
		for j := range dp[i] {
			dp[i][j] = make([][]int64, k+1)
			for m := range dp[i][j] {
				dp[i][j][m] = make([]int64, 101)
				for ad := range dp[i][j][m] {
					dp[i][j][m][ad] = INF
				}
			}
		}
	}

	for i := 0; i < n; i++ {
		dp[i][i][0][0] = 0
	}

	for length := 2; length <= n; length++ {
		for L := 0; L <= n-length; L++ {
			R := L + length - 1
			maxMerges := k
			if R - L - 1 < maxMerges {
				maxMerges = R - L - 1
			}

			for m := 0; m <= maxMerges; m++ {
				for Q := L; Q < R; Q++ {
					rightMerges := R - Q - 1
					if rightMerges > m {
						continue
					}
					leftMerges := m - rightMerges
					dist := int64(position[R] - position[Q])
					var minCost int64 = INF
					for addedQ := 0; addedQ <= 100; addedQ++ {
						leftCost := dp[L][Q][leftMerges][addedQ]
						if leftCost == INF {
							continue
						}
						effTimeQ := int64(time[Q]) + int64(addedQ)
						cost := leftCost + effTimeQ * dist
						if cost < minCost {
							minCost = cost
						}
					}
					if minCost == INF {
						continue
					}
					addedR := prefix[R] - prefix[Q+1]
					if addedR > 100 {
						continue
					}
					if minCost < dp[L][R][m][addedR] {
						dp[L][R][m][addedR] = minCost
					}
				}
			}
		}
	}

	ans := INF
	for added := 0; added <= 100; added++ {
		if dp[0][n-1][k][added] < ans {
			ans = dp[0][n-1][k][added]
		}
	}

	return int(ans)
}
# @lc code=end