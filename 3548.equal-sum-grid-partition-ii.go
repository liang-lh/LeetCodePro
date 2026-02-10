#
# @lc app=leetcode id=3548 lang=golang
#
# [3548] Equal Sum Grid Partition II
#

# @lc code=start
func canPartitionGrid(grid [][]int) bool {
	m := len(grid)
	if m == 0 {
		return false
	}
	n := len(grid[0])
	if m*n < 2 {
		return false
	}
	rowSum := make([]int64, m)
	colSum := make([]int64, n)
	min_row := make(map[int]int)
	max_row := make(map[int]int)
	min_col := make(map[int]int)
	max_col := make(map[int]int)
	for i := 0; i < m; i++ {
		for j := 0; j < n; j++ {
			v := grid[i][j]
			rowSum[i] += int64(v)
			colSum[j] += int64(v)
			if cur, ok := min_row[v]; !ok || i < cur {
				min_row[v] = i
			}
			if cur, ok := max_row[v]; !ok || i > cur {
				max_row[v] = i
			}
			if cur, ok := min_col[v]; !ok || j < cur {
				min_col[v] = j
			}
			if cur, ok := max_col[v]; !ok || j > cur {
				max_col[v] = j
			}
		}
	}
	prefix_row := make([]int64, m+1)
	for i := 0; i < m; i++ {
		prefix_row[i+1] = prefix_row[i] + rowSum[i]
	}
	prefix_col := make([]int64, n+1)
	for j := 0; j < n; j++ {
		prefix_col[j+1] = prefix_col[j] + colSum[j]
	}
	total := prefix_row[m]
	// horizontal cuts
	for k := 1; k < m; k++ {
		sum1 := prefix_row[k]
		sum2 := total - sum1
		if sum1 == sum2 {
			return true
		}
		if sum1 > sum2 {
			diff := sum1 - sum2
			if diff > 100000 || diff < 1 {
				continue
			}
			d := int(diff)
			ht := k
			wt := n
			sz := int64(ht) * int64(wt)
			if sz == 1 {
				continue
			}
			minhw := ht
			if wt < minhw {
				minhw = wt
			}
			if minhw >= 2 {
				if mr, has := min_row[d]; has && mr < k {
					return true
				}
			} else {
				if wt == 1 {
					if grid[0][0] == d || grid[k-1][0] == d {
						return true
					}
				} else {
					if grid[0][0] == d || grid[0][n-1] == d {
						return true
					}
				}
			}
		}
		if sum2 > sum1 {
			diff := sum2 - sum1
			if diff > 100000 || diff < 1 {
				continue
			}
			d := int(diff)
			hb := m - k
			wb := n
			sz := int64(hb) * int64(wb)
			if sz == 1 {
				continue
			}
			minhw := hb
			if wb < minhw {
				minhw = wb
			}
			if minhw >= 2 {
				if mr, has := max_row[d]; has && mr >= k {
					return true
				}
			} else {
				if wb == 1 {
					if grid[k][0] == d || grid[m-1][0] == d {
						return true
					}
				} else {
					if grid[m-1][0] == d || grid[m-1][n-1] == d {
						return true
					}
				}
			}
		}
	}
	// vertical cuts
	for k := 1; k < n; k++ {
		sum1 := prefix_col[k]
		sum2 := total - sum1
		if sum1 == sum2 {
			return true
		}
		if sum1 > sum2 {
			diff := sum1 - sum2
			if diff > 100000 || diff < 1 {
				continue
			}
			d := int(diff)
			hl := m
			wl := k
			sz := int64(hl) * int64(wl)
			if sz == 1 {
				continue
			}
			minhw := hl
			if wl < minhw {
				minhw = wl
			}
			if minhw >= 2 {
				if mc, has := min_col[d]; has && mc < k {
					return true
				}
			} else {
				if hl == 1 {
					if grid[0][0] == d || grid[0][k-1] == d {
						return true
					}
				} else {
					if grid[0][0] == d || grid[m-1][0] == d {
						return true
					}
				}
			}
		}
		if sum2 > sum1 {
			diff := sum2 - sum1
			if diff > 100000 || diff < 1 {
				continue
			}
			d := int(diff)
			hr := m
			wr := n - k
			sz := int64(hr) * int64(wr)
			if sz == 1 {
				continue
			}
			minhw := hr
			if wr < minhw {
				minhw = wr
			}
			if minhw >= 2 {
				if mc, has := max_col[d]; has && mc >= k {
					return true
				}
			} else {
				if hr == 1 {
					if grid[0][k] == d || grid[0][n-1] == d {
						return true
					}
				} else {
					if grid[0][k] == d || grid[m-1][k] == d {
						return true
					}
				}
			}
		}
	}
	return false
}
# @lc code=end