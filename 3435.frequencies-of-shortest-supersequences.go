#
# @lc app=leetcode id=3435 lang=golang
#
# [3435] Frequencies of Shortest Supersequences
#

# @lc code=start
func supersequences(words []string) [][]int {
	charSet := make(map[byte]bool)
	for _, word := range words {
		charSet[word[0]] = true
		charSet[word[1]] = true
	}

	letterList := []int{}
	var idOf [26]int
	for i := range idOf {
		idOf[i] = -1
	}

	idIdx := 0
	for ch := range charSet {
		letIdx := int(ch - 'a')
		letterList = append(letterList, letIdx)
		idOf[letIdx] = idIdx
		idIdx++
	}

	k := len(letterList)

	mand := 0
	pairs := [][2]int{}
	for _, word := range words {
		u := idOf[int(word[0]-'a')]
		v := idOf[int(word[1]-'a')]
		pairs = append(pairs, [2]int{u, v})
		if u == v {
			mand |= 1 << u
		}
	}

	minL := 999
	var res [][]int

	for mask := 0; mask < (1 << k); mask++ {
		if (mask & mand) != mand {
			continue
		}

		var openID [16]int
		var closeID [16]int
		eid := 0
		for i := 0; i < k; i++ {
			openID[i] = eid
			eid++
			if (mask & (1 << i)) != 0 {
				closeID[i] = eid
				eid++
			} else {
				closeID[i] = openID[i]
			}
		}

		E := eid

		adjj := make([][]int, E)
		indegg := make([]int, E)

		// double edges
		for i := 0; i < k; i++ {
			if (mask & (1 << i)) != 0 {
				fr := openID[i]
				to := closeID[i]
				adjj[fr] = append(adjj[fr], to)
				indegg[to]++
			}
		}

		// pair edges
		for _, p := range pairs {
			u := p[0]
			v := p[1]
			fr := openID[u]
			to := closeID[v]
			adjj[fr] = append(adjj[fr], to)
			indegg[to]++
		}

		// Kahn's
		q := []int{}
		for i := 0; i < E; i++ {
			if indegg[i] == 0 {
				q = append(q, i)
			}
		}

		qi := 0
		cnt := 0
		for qi < len(q) {
			u := q[qi]
			qi++
			cnt++
			for _, nei := range adjj[u] {
				indegg[nei]--
				if indegg[nei] == 0 {
					q = append(q, nei)
				}
			}
		}

		ok := (cnt == E)

		if ok {
			thisL := E
			if thisL < minL {
				minL = thisL
				res = res[:0]
				freq := make([]int, 26)
				for i := 0; i < k; i++ {
					fcnt := 1
					if (mask & (1 << i)) != 0 {
						fcnt = 2
					}
					let := letterList[i]
					freq[let] = fcnt
				}
				res = append(res, freq)
			} else if thisL == minL {
				freq := make([]int, 26)
				for i := 0; i < k; i++ {
					fcnt := 1
					if (mask & (1 << i)) != 0 {
						fcnt = 2
					}
					let := letterList[i]
					freq[let] = fcnt
				}
				res = append(res, freq)
			}
		}
	}

	return res
}
# @lc code=end