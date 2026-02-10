#
# @lc app=leetcode id=3609 lang=golang
#
# [3609] Minimum Moves to Reach Target in Grid
#

# @lc code=start
func minMoves(sx int, sy int, tx int, ty int) int {
	if sx == tx && sy == ty {
		return 0
	}

	mod := int64(1000000007)

	keyFunc := func(x, y int) int64 {
		return int64(x)*mod + int64(y)
	}

	visited := make(map[int64]bool)
	queue := [][]int{{tx, ty, 0}}

	visited[keyFunc(tx, ty)] = true

	getPrevs := func(x, y int) [][]int {
		res := [][]int{}
		// From x direction halve
		if x%2 == 0 {
			m := x / 2
			if m >= y {
				res = append(res, []int{m, y})
			}
		}
		// From x direction subtract
		if x >= y && x <= 2*y {
			res = append(res, []int{x - y, y})
		}
		// From y direction halve
		if y%2 == 0 {
			m := y / 2
			if m >= x {
				res = append(res, []int{x, m})
			}
		}
		// From y direction subtract
		if y >= x && y <= 2*x {
			res = append(res, []int{x, y - x})
		}
		return res
	}

	for len(queue) > 0 {
		curr := queue[0]
		queue = queue[1:]
		x, y, steps := curr[0], curr[1], curr[2]

		if x == sx && y == sy {
			return steps
		}

		prevs := getPrevs(x, y)
		for _, p := range prevs {
			px, py := p[0], p[1]
			if px >= 0 && py >= 0 {
				k := keyFunc(px, py)
				if !visited[k] {
					visited[k] = true
					queue = append(queue, []int{px, py, steps + 1})
				}
			}
		}
	}

	return -1
}
# @lc code=end