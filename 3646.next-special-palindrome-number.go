#
# @lc app=leetcode id=3646 lang=golang
#
# [3646] Next Special Palindrome Number
#

# @lc code=start
func specialPalindrome(n int64) int64 {
	// toStr: int64 to string
	var toStr func(int64) string = func(nn int64) string {
		if nn == 0 {
			return "0"
		}
		var digs [20]byte
		idx := 0
		for nn > 0 {
			digs[idx] = byte('0' + byte(nn % 10))
			idx++
			nn /= 10
		}
		var s = make([]byte, idx)
		for i := 0; i < idx; i++ {
			s[i] = digs[idx-1-i]
		}
		return string(s)
	}

	// parseStr: string to int64
	var parseStr func(string) int64 = func(s string) int64 {
		res := int64(0)
		for _, ch := range s {
			res = res * 10 + int64(ch - '0')
		}
		return res
	}

	str_n := toStr(n)
	L_n := len(str_n)

	// Try same length L_n
	var min_same string = ""
	for mask := 1; mask < (1 << 9); mask++ {
		var cnt [10]int
		l := 0
		old_cnt := 0
		for i := 0; i < 9; i++ {
			if (mask & (1 << i)) != 0 {
				d := i + 1
				cnt[d] = d
				l += d
				if d % 2 == 1 {
					old_cnt++
				}
			}
		}
		if old_cnt > 1 || l != L_n {
			continue
		}
		// dfs to generate all pals for this cnt
		H := (L_n + 1) / 2
		P := L_n / 2
		init_remain := [10]int{}
		copy(init_remain[:], cnt[:])
		var dfs func(pos int, rmain [10]int, frst []byte)
		dfs = func(pos int, rmain [10]int, frst []byte) {
			if pos == H {
				full := make([]byte, L_n)
				for i := 0; i < H; i++ {
					full[i] = frst[i]
					full[L_n-1-i] = frst[i]
				}
				p := string(full)
				if p > str_n && (min_same == "" || p < min_same) {
					min_same = p
				}
				return
			}
			consume := 2
			if L_n%2 == 1 && pos == H-1 {
				consume = 1
			}
			for cand := 1; cand <= 9; cand++ {
				if rmain[cand] >= consume {
					nr := rmain
					nr[cand] -= consume
					nfrst := append(frst, byte('0'+byte(cand)))
					dfs(pos+1, nr, nfrst)
				}
			}
		}
		dfs(0, init_remain, []byte{})
	}
	if min_same != "" {
		return parseStr(min_same)
	}

	// Longer lengths
	for ll := L_n + 1; ll <= 55; ll++ {
		var min_ll string = ""
		for mask := 1; mask < (1 << 9); mask++ {
			var cnt [10]int
			l := 0
			old_cnt := 0
			for i := 0; i < 9; i++ {
				if (mask & (1 << i)) != 0 {
					d := i + 1
					cnt[d] = d
					l += d
					if d % 2 == 1 { old_cnt++ }
				}
			}
			if old_cnt > 1 || l != ll { continue }
			// get smallest pal
			sp := get_smallest_pal(cnt)
			if sp != "" && (min_ll == "" || sp < min_ll) {
				min_ll = sp
			}
		}
		if min_ll != "" {
			return parseStr(min_ll)
		}
	}

	// Should not reach
	return 0
}

func get_smallest_pal(counts [10]int) string {
	L := 0
	for i := 1; i <= 9; i++ {
		L += counts[i]
	}
	if L == 0 { return "" }
	P := L / 2
	H := P + (L % 2)
	first := make([]byte, H)
	var remain [10]int
	copy(remain[:], counts[:])
	for pos := 0; pos < H; pos++ {
		consume := 2
		is_middle := (L % 2 == 1) && (pos == H - 1)
		if is_middle {
			consume = 1
		}
		found := false
		for cand := 1; cand <= 9; cand++ {
			if remain[cand] < consume { continue }
			remain[cand] -= consume
			// compute feasible
			sum_half := 0
			num_odd := 0
			for d := 1; d <= 9; d++ {
				sum_half += remain[d] / 2
				if remain[d] % 2 == 1 {
					num_odd++
				}
			}
			assigned_pairs := pos + 1
			if assigned_pairs > P { assigned_pairs = P }
			rem_pairs := P - assigned_pairs
			middle_pending := (L % 2 == 1) && (pos < P)
			feasible := false
			if middle_pending {
				if num_odd == 1 && sum_half == rem_pairs {
					feasible = true
				}
			} else {
				if num_odd == 0 && sum_half == rem_pairs {
					feasible = true
				}
			}
			if feasible {
				first[pos] = byte('0' + byte(cand))
				found = true
				break
			}
			remain[cand] += consume // revert
		}
		}
		if !found {
			return ""
		}
	}
	// build full
	full := make([]byte, L)
	for i := 0; i < H; i++ {
		full[i] = first[i]
		full[L-1-i] = first[i]
	}
	return string(full)
}
# @lc code=end