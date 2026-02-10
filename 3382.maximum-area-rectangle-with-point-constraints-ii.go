#
# @lc app=leetcode id=3382 lang=golang
#
# [3382] Maximum Area Rectangle With Point Constraints II
#

# @lc code=start
import ("sort")

func maxRectangleArea(xCoord []int, yCoord []int) int64 {
	n := len(xCoord)
	if n < 4 {
		return -1
	}

	x_to_ys := make(map[int][]int)
	for i := 0; i < n; i++ {
		x_to_ys[xCoord[i]] = append(x_to_ys[xCoord[i]], yCoord[i])
	}

	for _, ys := range x_to_ys {
		sort.Ints(ys)
	}

	xlist := make([]int, 0, len(x_to_ys))
	for x := range x_to_ys {
		xlist = append(xlist, x)
	}
	sort.Ints(xlist)
	m := len(xlist)

	x_to_rank := make(map[int]int, m)
	for i := 0; i < m; i++ {
		x_to_rank[xlist[i]] = i
	}

	x_ys := make([][]int, m)
	for i := 0; i < m; i++ {
		x_ys[i] = make([]int, len(x_to_ys[xlist[i]]))
		copy(x_ys[i], x_to_ys[xlist[i]])
	}

	const MAX_Y int64 = 80000001
	height_groups := make(map[int64][]int)
	for x, ys := range x_to_ys {
		if len(ys) < 2 {
			continue
		}
		for k := 0; k < len(ys)-1; k++ {
			y1 := ys[k]
			y2 := ys[k+1]
			key := int64(y1) * MAX_Y + int64(y2)
			height_groups[key] = append(height_groups[key], x)
		}
	}

	tree := make([][]int, 4*m+10)

	var build func(int, int, int)
	build = func(node, start, end int) {
		if start == end {
			tree[node] = make([]int, len(x_ys[start]))
			copy(tree[node], x_ys[start])
			return
		}
		mid := (start + end) / 2
		build(2*node, start, mid)
		build(2*node+1, mid+1, end)
		left := tree[2*node]
		right := tree[2*node+1]
		merged := make([]int, len(left)+len(right))
		i, j, k := 0, 0, 0
		for i < len(left) && j < len(right) {
			if left[i] <= right[j] {
				merged[k] = left[i]
				i++
			} else {
				merged[k] = right[j]
				j++
			}
			k++
		}
		for i < len(left) {
			merged[k] = left[i]
			i++
			k++
		}
		for j < len(right) {
			merged[k] = right[j]
			j++
			k++
		}
		tree[node] = merged
	}

	if m > 0 {
		build(1, 0, m-1)
	}

	var has_point func(int, int, int, int, int, int, int) bool
	has_point = func(node, ns, ne, qs, qe, ylo, yhi int) bool {
		if qs > ne || qe < ns {
			return false
		}
		if qs <= ns && ne <= qe {
			v := tree[node]
			idx := sort.SearchInts(v, ylo)
			return idx < len(v) && v[idx] <= yhi
		}
		mid := (ns + ne) / 2
		if has_point(2*node, ns, mid, qs, qe, ylo, yhi) {
			return true
		}
		return has_point(2*node+1, mid+1, ne, qs, qe, ylo, yhi)
	}

	ans := int64(-1)
	for key, cx_ := range height_groups {
		cx := make([]int, len(cx_))
		copy(cx, cx_)
		sort.Ints(cx)
		ucx := make([]int, 0, len(cx))
		for _, val := range cx {
			if len(ucx) == 0 || ucx[len(ucx)-1] != val {
				ucx = append(ucx, val)
			}
		}
		if len(ucx) < 2 {
			continue
		}

		y1 := int(key / MAX_Y)
		y2 := int(key % MAX_Y)
		h := int64(y2 - y1)

		for p := 0; p < len(ucx)-1; p++ {
			xl := ucx[p]
			xr := ucx[p+1]
			rl := x_to_rank[xl]
			rr := x_to_rank[xr]
			area := int64(xr-xl) * h
			if rl+1 >= rr || !has_point(1, 0, m-1, rl+1, rr-1, y1, y2) {
				if area > ans {
					ans = area
				}
			}
		}
	}

	return ans
}

# @lc code=end