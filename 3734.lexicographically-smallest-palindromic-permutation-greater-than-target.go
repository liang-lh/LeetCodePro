#
# @lc app=leetcode id=3734 lang=golang
#
# [3734] Lexicographically Smallest Palindromic Permutation Greater Than Target
#

# @lc code=start
func lexPalindromicPermutation(s string, target string) string {
	n := len(s)
	freq := [26]int{}
	for i := 0; i < n; i++ {
		freq[int(s[i]) - int('a')]++
	}
	odds := 0
	for i := 0; i < 26; i++ {
		if freq[i] % 2 == 1 {
			odds++
		}
	}
	if odds != n % 2 {
		return ""
	}
	halfLen := n / 2
	isOdd := n % 2 == 1
	m := halfLen + (n % 2)
	first := make([]byte, m)
	cfreq := [26]int{}
	copy(cfreq[:], freq[:])
	alreadyGreater := false
	for j := 0; j < m; j++ {
		isPair := j < halfLen
		need := 2
		if !isPair {
			need = 1
		}
		found := false
		for chb := byte('a'); chb <= byte('z'); chb++ {
			i := int(chb - 'a')
			if cfreq[i] < need {
				continue
			}
			cfreq[i] -= need
			// feasible?
			assignedPairs := j + 1
			if !isPair {
				assignedPairs = halfLen
			}
			remPairs := halfLen - assignedPairs
			remMiddle := 0
			if isOdd && j+1 < m {
				remMiddle = 1
			}
			expChars := 2 * remPairs + remMiddle
			sumRem := 0
			oddRem := 0
			for k := 0; k < 26; k++ {
				sumRem += cfreq[k]
				if cfreq[k] % 2 == 1 {
					oddRem++
				}
			}
			feasible := sumRem == expChars && oddRem == remMiddle
			if !feasible {
				cfreq[i] += need
				continue
			}
			// condition
			thisGT := alreadyGreater || (chb > target[j])
			if thisGT {
				found = true
				first[j] = chb
				alreadyGreater = alreadyGreater || (chb > target[j])
				break
			} else if chb < target[j] {
				cfreq[i] += need
				continue
			} else {
				// == case, check largest
				pMax := make([]byte, n)
				for p := 0; p < j; p++ {
					pMax[p] = first[p]
					pMax[n-1-p] = first[p]
				}
				pMax[j] = chb
				if j < halfLen {
					pMax[n-1-j] = chb
				}
				var tfreq [26]int
				copy(tfreq[:], cfreq[:])
				simSuccess := true
				for p := j + 1; p < m; p++ {
					pPair := p < halfLen
					pNeed := 2
					if !pPair {
						pNeed = 1
					}
					fMax := false
					for cc := byte('z'); cc >= byte('a'); cc-- {
						ii := int(cc - 'a')
						if tfreq[ii] >= pNeed {
							pMax[p] = cc
							if p < halfLen {
								pMax[n-1-p] = cc
							}
							tfreq[ii] -= pNeed
							fMax = true
							break
						}
					}
					if !fMax {
						simSuccess = false
						break
					}
				}
				if simSuccess && string(pMax) > target {
					found = true
					first[j] = chb
					alreadyGreater = alreadyGreater || (chb > target[j])
					break
				}
				cfreq[i] += need
			}
		}
		if !found {
			return ""
		}
	}
	res := make([]byte, n)
	for i := 0; i < halfLen; i++ {
		res[i] = first[i]
		res[n-1-i] = first[i]
	}
	if isOdd {
		res[halfLen] = first[halfLen]
	}
	return string(res)
}
# @lc code=end