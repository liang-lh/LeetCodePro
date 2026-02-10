#
# @lc app=leetcode id=3459 lang=golang
#
# [3459] Length of Longest V-Shaped Diagonal Segment
#

# @lc code=start
func lenOfVDiagonal(grid [][]int) int {
	n := len(grid)
	if n == 0 {
		return 0
	}
	m := len(grid[0])
	dirs := [4][2]int{{1, 1}, {1, -1}, {-1, -1}, {-1, 1}}
	maxExtA := make([][][]int, 4)
	maxExtB := make([][][]int, 4)
	for i := 0; i < 4; i++ {
		maxExtA[i] = make([][]int, n)
		maxExtB[i] = make([][]int, n)
		for j := 0; j < n; j++ {
			maxExtA[i][j] = make([]int, m)
			maxExtB[i][j] = make([]int, m)
		}
	}
	for d := 0; d < 4; d++ {
		dx := dirs[d][0]
		dy := dirs[d][1]
		if dx > 0 {
			for r := n-1; r >= 0; r-- {
				for c := 0; c < m; c++ {
					nr := r + dx
					nc := c + dy
					if nr >= 0 && nr < n && nc >= 0 && nc < m {
						if grid[nr][nc] == 2 {
							maxExtA[d][r][c] = 1 + maxExtB[d][nr][nc]
						}
						if grid[nr][nc] == 0 {
							maxExtB[d][r][c] = 1 + maxExtA[d][nr][nc]
						}
					}
				}
			}
		} else {
			for r := 0; r < n; r++ {
				for c := 0; c < m; c++ {
					nr := r + dx
					nc := c + dy
					if nr >= 0 && nr < n && nc >= 0 && nc < m {
						if grid[nr][nc] == 2 {
							maxExtA[d][r][c] = 1 + maxExtB[d][nr][nc]
						}
						if grid[nr][nc] == 0 {
							maxExtB[d][r][c] = 1 + maxExtA[d][nr][nc]
						}
					}
				}
			}
		}
	}
	max := func(a, b int) int {
		if a > b {
			return a
		}
		return b
	}
	ans := 0
	for r := 0; r < n; r++ {
		for c := 0; c < m; c++ {
			if grid[r][c] != 1 {
				continue
			}
			for d := 0; d < 4; d++ {
				ext := maxExtA[d][r][c]
				ans = max(ans, 1+ext)
				dx := dirs[d][0]
				dy := dirs[d][1]
				newd := (d + 1) % 4
				for e1 := 1; e1 <= ext; e1++ {
					tr := r + e1*dx
					tc := c + e1*dy
					var ext2 int
					if e1%2 == 0 {
						ext2 = maxExtA[newd][tr][tc]
					} else {
						ext2 = maxExtB[newd][tr][tc]
					}
					ans = max(ans, 1+e1+ext2)
				}
			}
		}
	}
	return ans
}
# @lc code=end