#
# @lc app=leetcode id=3470 lang=golang
#
# [3470] Permutations IV
#

# @lc code=start
func permute(n int, k int64) []int {
	used := make([]bool, n+1)
	remOddCnt := (n + 1) / 2
	remEvenCnt := n / 2

	fact := [21]uint64{}
	fact[0] = 1
	for i := 1; i <= 20; i++ {
		fact[i] = fact[i-1] * uint64(i)
	}

	currK := uint64(k)
	var needOdd bool
	res := make([]int, n)

	for pos := 0; pos < n; pos++ {
		found := false
		m := n - pos
		for cand := 1; cand <= n; cand++ {
			if used[cand] {
				continue
			}
			isOddCand := (cand % 2 == 1)
			if pos > 0 && isOddCand != needOdd {
				continue
			}
			do := 0
			if isOddCand {
				do = 1
			}
			de := 1 - do
			if remOddCnt < do || remEvenCnt < de {
				continue
			}
			ra := remOddCnt - do
			rb := remEvenCnt - de
			s := m - 1
			nextOdd := !isOddCand
			nos := numOddSlots(s, nextOdd)
			nes := s - nos
			if ra != nos || rb != nes {
				continue
			}

			large := ra > 20 || rb > 20
			if large {
				res[pos] = cand
				used[cand] = true
				if isOddCand {
					remOddCnt--
				} else {
					remEvenCnt--
				}
				needOdd = !isOddCand
				found = true
				break
			} else {
				fa := fact[ra]
				fb := fact[rb]
				if currKLeCnt(currK, fa, fb) {
					res[pos] = cand
					used[cand] = true
					if isOddCand {
						remOddCnt--
					} else {
						remEvenCnt--
					}
					needOdd = !isOddCand
					found = true
					break
				} else {
					fcnt := float64(fa) * float64(fb)
					ucnt := uint64(fcnt)
					currK -= ucnt
				}
			}
		}
		if !found {
			return []int{}
		}
	}

	return res
}

func numOddSlots(length int, startOdd bool) int {
	if startOdd {
		return (length + 1) / 2
	}
	return length / 2
}

func currKLeCnt(ck uint64, fa uint64, fb uint64) bool {
	if fb == 0 {
		return ck <= 1
	}
	ceilDiv := (ck + fb - 1) / fb
	return fa >= ceilDiv
}
# @lc code=end