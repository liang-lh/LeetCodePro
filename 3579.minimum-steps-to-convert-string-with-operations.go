#
# @lc app=leetcode id=3579 lang=golang
#
# [3579] Minimum Steps to Convert String with Operations
#

# @lc code=start
func minOperations(word1 string, word2 string) int {
	n := len(word1)
	dp := make([]int, n+1)
	const INF = 1 << 30
	for i := range dp {
		dp[i] = INF
	}
	dp[0] = 0

	rev_string := func(s string) string {
		b := []byte(s)
		i, j := 0, len(b)-1
		for i < j {
			b[i], b[j] = b[j], b[i]
			i++
			j--
		}
		return string(b)
	}

	match_cost := func(a, b string) int {
		k := len(a)
		cnt := [26][26]int{}
		d := 0
		for p := 0; p < k; p++ {
			if a[p] != b[p] {
				x := int(a[p] - 'a')
				y := int(b[p] - 'a')
				cnt[x][y]++
				d++
			}
		}
		m := 0
		for x := 0; x < 26; x++ {
			for y := x+1; y < 26; y++ {
				times := cnt[x][y]
				if times > cnt[y][x] {
					times = cnt[y][x]
				}
				m += times
			}
		}
		return d - m
	}

	calc_cost := func(l, r int) int {
		k := r - l + 1
		s1 := word1[l : l+k]
		s2 := word2[l : l+k]
		c1 := match_cost(s1, s2)
		r1 := rev_string(s1)
		c2 := match_cost(r1, s2) + 1
		r2 := rev_string(s2)
		c3 := match_cost(s1, r2) + 1
		minC := c1
		if c2 < minC {
			minC = c2
		}
		if c3 < minC {
			minC = c3
		}
		return minC
	}

	for i := 1; i <= n; i++ {
		for j := 0; j < i; j++ {
			if dp[j] != INF {
				c := calc_cost(j, i-1)
				if dp[j] + c < dp[i] {
					dp[i] = dp[j] + c
				}
			}
		}
	}

	return dp[n]
}
# @lc code=end