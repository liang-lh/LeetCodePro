#
# @lc app=leetcode id=3753 lang=golang
#
# [3753] Total Waviness of Numbers in Range II
#

# @lc code=start
func totalWaviness(num1 int64, num2 int64) int64 {
	calc := func(n int64) int64 {
		if n < 1 {
			return 0
		}

		// Extract digits LSB first
		var digs []int
		tmp := n
		for tmp > 0 {
			digs = append(digs, int(tmp%10))
			tmp /= 10
		}
		// Reverse to MSB first
		for i, j := 0, len(digs)-1; i < j; i, j = i+1, j-1 {
			digs[i], digs[j] = digs[j], digs[i]
		}
		L := len(digs)

		const MAXS = 70010
		memo_wav := make([]int64, MAXS)
		memo_cnt := make([]int64, MAXS)
		for i := 0; i < MAXS; i++ {
			memo_wav[i] = -1
			memo_cnt[i] = -1
		}

		stride_slen := 1
		stride_p1 := 17
		stride_p2 := 11 * stride_p1 // 187
		stride_tight := 11 * stride_p2 // 2057
		stride_pos := 2 * stride_tight // 4114

		getIndex := func(pos, tight, p2, p1, slen int) int {
			return pos*stride_pos + tight*stride_tight + p2*stride_p2 + p1*stride_p1 + slen
		}

		var dfs func(pos, tight, p2, p1, slen int) (int64, int64)
		dfs = func(pos, tight, p2, p1, slen int) (int64, int64) {
			if pos == L {
				return 0, 1
			}

			idx := getIndex(pos, tight, p2, p1, slen)
			if memo_wav[idx] != -1 {
				return memo_wav[idx], memo_cnt[idx]
			}

			var aw, ac int64 = 0, 0
			up := 9
			if tight == 1 {
				up = digs[pos]
			}

			for dd := 0; dd <= up; dd++ {
				nt := 0
				if tight == 1 && dd == up {
					nt = 1
				}

				contrib := int64(0)
				np2 := p2
				np1 := p1
				nslen := slen

				if !(slen == 0 && dd == 0) {
					oldslen := slen
					if oldslen >= 2 {
						if (p1 > p2 && p1 > dd) || (p1 < p2 && p1 < dd) {
							contrib = 1
						}
					}
					nslen = oldslen + 1
					np1 = dd
					if oldslen >= 1 {
						np2 = p1
					} else {
						np2 = 10
					}
				}

				sw, sc := dfs(pos+1, nt, np2, np1, nslen)
				aw += contrib * sc + sw
				ac += sc
			}

			memo_wav[idx] = aw
			memo_cnt[idx] = ac
			return aw, ac
		}

		res, _ := dfs(0, 1, 10, 10, 0)
		return res
	}

	return calc(num2) - calc(num1-1)
}
# @lc code=end