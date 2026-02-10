#
# @lc app=leetcode id=3474 lang=golang
#
# [3474] Lexicographically Smallest Generated String
#
# @lc code=start
func generateString(str1 string, str2 string) string {
	n := len(str1)
	m := len(str2)
	L := n + m - 1
	word := make([]byte, L)
	forced := make([]bool, L)
	conflict := false
	
	// Step 1: Propagate 'T' constraints, force positions and detect conflicts on overlaps
	for i := 0; i < n; i++ {
		if str1[i] != 'T' {
			continue
		}
		for k := 0; k < m; k++ {
			p := i + k
			c := str2[k]
			if forced[p] && word[p] != c {
				conflict = true
			}
			word[p] = c
			forced[p] = true
		}
	}
	if conflict {
		return ""
	}
	
	// Step 2: Find violating 'F's - would match str2 under 'a' fill for frees (forced match + free str2[k]=='a'), hasFree
	violating := []int{}
	for i := 0; i < n; i++ {
		if str1[i] != 'F' {
			continue
		}
		match := true
		hasFree := false
		for k := 0; k < m; k++ {
			p := i + k
			c := str2[k]
			if !forced[p] {
				hasFree = true
				if 'a' != c {
					match = false
				}
			} else {
				if word[p] != c {
					match = false
				}
			}
		}
		if match {
			if !hasFree {
				return ""
			}
			violating = append(violating, i)
		}
	}
	
	// Step 3: Group violating by rightmost free pos max_p for greedy postponement
	endAt := make([][]int, L)
	for _, v := range violating {
		maxP := -1
		for k := 0; k < m; k++ {
			p := v + k
			if !forced[p] && p > maxP {
				maxP = p
			}
		}
		if maxP != -1 {
			endAt[maxP] = append(endAt[maxP], v)
		}
	}
	
	// Step 4: Track unsatisfied violating 'F's
	unsat := make([]bool, n)
	for _, v := range violating {
		unsat[v] = true
	}
	
	// Step 5: Assign frees left->right: 'a' default, 'b' if needed to satisfy ending unsat violating,
	// then satisfy all covering unsat violating (since 'b' != 'a' == their str2[rel])
	for j := 0; j < L; j++ {
		if forced[j] {
			continue
		}
		must := false
		for _, v := range endAt[j] {
			if unsat[v] {
				must = true
				break
			}
		}
		if must {
			word[j] = 'b'
			// Mark satisfied all covering unsat F's windows
			leftV := j - m + 1
			if leftV < 0 {
				leftV = 0
			}
			for vv := leftV; vv <= j && vv < n; vv++ {
				if unsat[vv] {
					unsat[vv] = false
				}
			}
		} else {
			word[j] = 'a'
		}
	}
	
	// Step 6: Verify all violating satisfied
	for i := 0; i < n; i++ {
		if unsat[i] {
			return ""
		}
	}
	
	// Verify no 'F' matches str2 exactly (catches non-violating affected adversely)
	for i := 0; i < n; i++ {
		if str1[i] == 'F' {
			match := true
			for k := 0; k < m; k++ {
				p := i + k
				if word[p] != str2[k] {
					match = false
					break
				}
			}
			if match {
				return ""
			}
		}
	}
	return string(word)
}
# @lc code=end