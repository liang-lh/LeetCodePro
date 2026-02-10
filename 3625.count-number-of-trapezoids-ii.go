#
# @lc app=leetcode id=3625 lang=golang
#
# [3625] Count Number of Trapezoids II
#

# @lc code=start
import "fmt"

func countTrapezoids(points [][]int) int {
	n := len(points)
	if n < 4 {
		return 0
	}

	const OFF = 3000
	const MS = 6001
	type Key uint32

	indexmap := make(map[Key]int)
	for i := 0; i < n; i++ {
		x := points[i][0]
		y := points[i][1]
		k := Key(uint32(x + OFF) + MS * uint32(y + OFF))
		indexmap[k] = i
	}

	// Compute number of parallelograms * 8
	var para int64 = 0
	for i := 0; i < n; i++ {
		pix := points[i][0]
		piy := points[i][1]
		for j := 0; j < n; j++ {
			if j == i {
				continue
			}
			pjx := points[j][0]
			pjy := points[j][1]
			dx1 := pjx - pix
			dy1 := pjy - piy
			for k := 0; k < n; k++ {
				if k == i || k == j {
					continue
				}
				pkx := points[k][0]
				pky := points[k][1]
				dx2 := pkx - pix
				dy2 := pky - piy
				cross := int64(dx1) * int64(dy2) - int64(dy1) * int64(dx2)
				if cross == 0 {
					continue
				}
				px := pjx + pkx - pix
				py := pjy + pky - piy
				ptk := Key(uint32(px + OFF) + MS * uint32(py + OFF))
				idx, ok := indexmap[ptk]
				if !ok || idx == i || idx == j || idx == k {
					continue
				}
				para++
			}
		}
	}

	P := para / 8

	// Slope constants
	const DYOFF = 2000
	const DYMAX = 4001
	type SlopeKey int64

	numEdges := make(map[SlopeKey]int)

	abs := func(a int) int {
		if a < 0 {
			return -a
		}
		return a
	}

	gcd := func(a, b int) int {
		a = abs(a)
		b = abs(b)
		for b != 0 {
			a, b = b, a % b
		}
		return a
	}

	normalize := func(dx, dy int) (int, int) {
		g := gcd(dx, dy)
		if g == 0 {
			return 0, 0
		}
		dx /= g
		dy /= g
		if dx < 0 || (dx == 0 && dy < 0) {
			dx = -dx
			dy = -dy
		}
		return dx, dy
	}

	// Compute numEdges per slope
	for i := 0; i < n; i++ {
		for j := i + 1; j < n; j++ {
			dx := points[j][0] - points[i][0]
			dy := points[j][1] - points[i][1]
			ndx, ndy := normalize(dx, dy)
			sk := SlopeKey(int64(ndx) * int64(DYMAX) + int64(ndy + DYOFF))
			numEdges[sk]++
		}
	}

	// Compute S
	var S int64 = 0
	for sk, totseg := range numEdges {
		if totseg < 2 {
			continue
		}
		ndx := int(sk / DYMAX)
		ndy := int(sk % DYMAX) - DYOFF
		linecnt := make(map[int64]int)
		for _, p := range points {
			c := int64(ndy) * int64(p[0]) - int64(ndx) * int64(p[1])
			linecnt[c]++
		}
		var sumsq int64 = 0
		for _, cnt := range linecnt {
			if cnt < 2 {
				continue
			}
			segs := int64(cnt) * int64(cnt-1) / 2
			sumsq += segs * segs
		}
		tot64 := int64(totseg)
		contrib := (tot64 * tot64 - sumsq) / 2
		S += contrib
	}

	ans := S - P
	if ans < 0 {
		return 0 // safety
	}
	return int(ans)
}

# @lc code=end