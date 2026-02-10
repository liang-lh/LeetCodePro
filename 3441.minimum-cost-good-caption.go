#
# @lc app=leetcode id=3441 lang=golang
#
# [3441] Minimum Cost Good Caption
#

# @lc code=start
func minCostGoodCaption(caption string) string {
	n := len(caption)
	if n == 0 {
		return ""
	}

	const INF int64 = 1000000000000000000

	dp := make([][][]int64, n+1)
	for i := 0; i <= n; i++ {
		dp[i] = make([][]int64, 26)
		for j := 0; j < 26; j++ {
			dp[i][j] = make([]int64, 4)
		}
	}

	// Initialize dp_back[n]
	for c := 0; c < 26; c++ {
		for l := 1; l <= 3; l++ {
			if l == 3 {
				dp[n][c][l] = 0
			} else {
				dp[n][c][l] = INF
			}
		}
	}

	abs := func(a int) int {
		if a < 0 {
			return -a
		}
		return a
	}

	// Fill dp_back backward
	for i := n-1; i >= 0; i-- {
		orig := int(caption[i] - 'a')
		for c := 0; c < 26; c++ {
			for l := 1; l <= 3; l++ {
				minVal := INF
				for d := 0; d < 26; d++ {
					ccost := abs(orig - d)
					if d == c {
						newL := l + 1
						if newL > 3 {
							newL = 3
						}
						newC := c
						thisCost := int64(ccost) + dp[i+1][newC][newL]
						if thisCost < minVal {
							minVal = thisCost
						}
					} else {
						if l != 3 {
							continue
						}
						newC := d
						newL := 1
						thisCost := int64(ccost) + dp[i+1][newC][newL]
						if thisCost < minVal {
							minVal = thisCost
						}
					}
				}
				dp[i][c][l] = minVal
			}
		}
	}

	// Compute minCost
	var minCost int64 = INF
	orig0 := int(caption[0] - 'a')
	for d := 0; d < 26; d++ {
		ccost := abs(orig0 - d)
		tot := int64(ccost) + dp[1][d][1]
		if tot < minCost {
			minCost = tot
		}
	}

	if minCost == INF {
		return ""
	}

	// Reconstruct lex smallest
	var res []byte
	var currCost int64
	var currC, currL int

	// First character
	for d := 0; d < 26; d++ {
		ccost := abs(orig0 - d)
		newC := d
		newL := 1
		tot := currCost + int64(ccost) + dp[1][newC][newL]
		if tot == minCost {
			res = append(res, byte('a'+d))
			currCost += int64(ccost)
			currC = newC
			currL = newL
			break
		}
	}

	// Remaining characters
	for i := 1; i < n; i++ {
		orig := int(caption[i] - 'a')
		var found bool
		for d := 0; d < 26; d++ {
			ccost := abs(orig - d)
			var newC2, newL2 int
			valid := false
			if d == currC {
				newL2 = currL + 1
				if newL2 > 3 {
					newL2 = 3
				}
				newC2 = currC
				valid = true
			} else if currL == 3 {
				newC2 = d
				newL2 = 1
				valid = true
			}
			if valid {
				tot := currCost + int64(ccost) + dp[i+1][newC2][newL2]
				if tot == minCost {
					res = append(res, byte('a'+d))
					currCost += int64(ccost)
					currC = newC2
					currL = newL2
					found = true
					break
				}
			}
		}
		if !found {
			return "" // should not happen
		}
	}

	return string(res)
}
# @lc code=end