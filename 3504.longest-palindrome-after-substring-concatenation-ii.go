#
# @lc app=leetcode id=3504 lang=golang
#
# [3504] Longest Palindrome After Substring Concatenation II
#

# @lc code=start
func longestPalindrome(s string, t string) int {

	type Hpair struct {
		F1 uint64
		F2 uint64
	}

	const (
		BASE1 uint64 = 131
		BASE2 uint64 = 137
	)

	max := func(a, b int) int {
		if a > b {
			return a
		}
		return b
	}

	var reverse func(string) string = func(str string) string {
		bs := []byte(str)
		i, j := 0, len(bs)-1
		for i < j {
			bs[i], bs[j] = bs[j], bs[i]
			i++
			j--
		}
		return string(bs)
	}

	compute_prefix := func(str string, base uint64) ([]uint64, []uint64) {
		n := len(str)
		pre := make([]uint64, n+1)
		pwr := make([]uint64, n+1)
		pwr[0] = 1
		for i := 0; i < n; i++ {
			pre[i+1] = pre[i]*base + uint64(str[i]-'a'+1)
			pwr[i+1] = pwr[i] * base
		}
		return pre, pwr
	}

	geth := func(pre, pwr []uint64, l, r int) uint64 {
		return pre[r+1] - pre[l]*pwr[r-l+1]
	}

	palDP := func(str string) ([]int, []int, int) {
		n := len(str)
		dp := make([][]bool, n)
		for i := range dp {
			dp[i] = make([]bool, n)
		}
		maxlps := 0
		for i := 0; i < n; i++ {
			dp[i][i] = true
			maxlps = 1
		}
		for i := 0; i < n-1; i++ {
			if str[i] == str[i+1] {
				dp[i][i+1] = true
				maxlps = 2
			}
		}
		for leng := 3; leng <= n; leng++ {
			for i := 0; i <= n-leng; i++ {
				j := i + leng - 1
				if str[i] == str[j] && dp[i+1][j-1] {
					dp[i][j] = true
					maxlps = leng
				}
			}
		}

		startm := make([]int, n)
		endm := make([]int, n)
		for p := 0; p < n; p++ {
			startm[p] = 1
			for q := p; q < n; q++ {
				if dp[p][q] {
					startm[p] = q - p + 1
				}
			}
		}
		for j := 0; j < n; j++ {
			endm[j] = 1
			for ii := j; ii >= 0; ii-- {
				if dp[ii][j] {
					endm[j] = j - ii + 1
				}
			}
		}

		return startm, endm, maxlps
	}

	n, m := len(s), len(t)
	if n == 0 && m == 0 {
		return 0
	}

	start_s, end_s, lps_s := palDP(s)
	start_t, end_t, lps_t := palDP(t)

	ans := max(lps_s, lps_t)

	// hashes for s
	pre_s1, pwr_s1 := compute_prefix(s, BASE1)
	pre_s2, pwr_s2 := compute_prefix(s, BASE2)

	// hashes for t
	pre_t1, pwr_t1 := compute_prefix(t, BASE1)
	pre_t2, pwr_t2 := compute_prefix(t, BASE2)

	s_subs := make(map[int]map[Hpair]struct{})
	t_subs := make(map[int]map[Hpair]struct{})

	for kk := 1; kk <= n; kk++ {
		s_subs[kk] = make(map[Hpair]struct{})
		for st := 0; st <= n-kk; st++ {
			h1 := geth(pre_s1, pwr_s1, st, st+kk-1)
			h2 := geth(pre_s2, pwr_s2, st, st+kk-1)
			s_subs[kk][Hpair{h1, h2}] = struct{}{}
		}
	}

	for kk := 1; kk <= m; kk++ {
		t_subs[kk] = make(map[Hpair]struct{})
		for st := 0; st <= m-kk; st++ {
			h1 := geth(pre_t1, pwr_t1, st, st+kk-1)
			h2 := geth(pre_t2, pwr_t2, st, st+kk-1)
			t_subs[kk][Hpair{h1, h2}] = struct{}{}
		}
	}

	rs := reverse(s)
	rt := reverse(t)

	pre_rs1, pwr_rs1 := compute_prefix(rs, BASE1)
	pre_rs2, pwr_rs2 := compute_prefix(rs, BASE2)

	pre_rt1, pwr_rt1 := compute_prefix(rt, BASE1)
	pre_rt2, pwr_rt2 := compute_prefix(rt, BASE2)

	// Case 1: extend in s, lenL >= k = lenR
	for a := 0; a < n; a++ {
		for kk := 1; kk <= n-a; kk++ {
			st := n - a - kk
			h1 := geth(pre_rs1, pwr_rs1, st, st+kk-1)
			h2 := geth(pre_rs2, pwr_rs2, st, st+kk-1)
			hp := Hpair{h1, h2}
			if tsub, ok := t_subs[kk]; ok {
				if _, has := tsub[hp]; has {
					p := a + kk
					var len_pal int
					if p < n {
						len_pal = start_s[p]
					} // else 0
					ans = max(ans, 2*kk + len_pal)
				}
			}
		}
	}

	// Case 2: extend in t, lenR >= k = lenL
	for e := 0; e < m; e++ {
		for kk := 1; kk <= e+1; kk++ {
			st_rt := m - 1 - e
			h1 := geth(pre_rt1, pwr_rt1, st_rt, st_rt+kk-1)
			h2 := geth(pre_rt2, pwr_rt2, st_rt, st_rt+kk-1)
			hp := Hpair{h1, h2}
			if ssub, ok := s_subs[kk]; ok {
				if _, has := ssub[hp]; has {
					pos := e - kk
					var len_pal int
					if pos >= 0 {
						len_pal = end_t[pos]
					} // else 0
					ans = max(ans, 2*kk + len_pal)
				}
			}
		}
	}

	return ans
}
# @lc code=end