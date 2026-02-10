#
# @lc app=leetcode id=3455 lang=golang
#
# [3455] Shortest Matching Substring
#

# @lc code=start
func shortestMatchingSubstring(s string, p string) int {
	star1 := -1
	star2 := -1
	for i := 0; i < len(p); i++ {
		if p[i] == '*' {
			if star1 == -1 {
				star1 = i
			} else {
				star2 = i
				break
			}
		}
	}

	a := p[:star1]
	b := p[star1+1:star2]
	c := p[star2+1:]
	la, lb, lc := len(a), len(b), len(c)

	computeLPS := func(pat string) []int {
		m := len(pat)
		if m == 0 {
			return []int{}
		}
		pi := make([]int, m)
		j := 0
		i := 1
		for i < m {
			if pat[i] == pat[j] {
				j++
				pi[i] = j
				i++
			} else {
				if j != 0 {
					j = pi[j-1]
				} else {
					pi[i] = 0
					i++
				}
			}
		}
		return pi
	}

	findAllMatches := func(text, pattern string) []int {
		n := len(text)
		m := len(pattern)
		if m == 0 {
			res := make([]int, n+1)
			for i := 0; i <= n; i++ {
				res[i] = i
			}
			return res
		}
		pi := computeLPS(pattern)
		res := []int{}
		j := 0
		for i := 0; i < n; i++ {
			for j > 0 && text[i] != pattern[j] {
				j = pi[j-1]
			}
			if text[i] == pattern[j] {
				j++
			}
			if j == m {
				res = append(res, i-m+1)
				j = pi[j-1]
			}
		}
		return res
	}

	largestLE := func(pos []int, val int) int {
		lo := 0
		hi := len(pos) - 1
		res := -1
		for lo <= hi {
			mid := lo + (hi-lo)/2
			if pos[mid] <= val {
				res = mid
				lo = mid + 1
			} else {
				hi = mid - 1
			}
		}
		return res
	}

	smallestGE := func(pos []int, val int) int {
		lo := 0
		hi := len(pos) - 1
		res := -1
		for lo <= hi {
			mid := lo + (hi-lo)/2
			if pos[mid] >= val {
				res = mid
				hi = mid - 1
			} else {
				lo = mid + 1
			}
		}
		return res
	}

	posa := findAllMatches(s, a)
	posb := findAllMatches(s, b)
	posc := findAllMatches(s, c)

	inf := 1 << 30
	minlen := inf
	for j := 0; j < len(posb); j++ {
		startb := posb[j]
		limitl := startb - la
		idxl := largestLE(posa, limitl)
		if idxl == -1 {
			continue
		}
		startr := startb + lb
		idxr := smallestGE(posc, startr)
		if idxr == -1 {
			continue
		}
		cand := posc[idxr] + lc - posa[idxl]
		if cand < minlen {
			minlen = cand
		}
	}
	if minlen == inf {
		return -1
	}
	return minlen
}
# @lc code=end