#
# @lc app=leetcode id=3630 lang=golang
#
# [3630] Partition Array for Maximum XOR and AND
#

# @lc code=start
type State struct {
	xa uint32
	xc uint32
	ab uint32
}

func generate(half []int) []State {
	res := make([]State, 0, 1 << 16)
	var dfs func(int, uint32, uint32, uint32)
	dfs = func(i int, x1 uint32, x2 uint32, andb uint32) {
		if i == len(half) {
			res = append(res, State{x1, x2, andb})
			return
		}
		nu := uint32(half[i])
		dfs(i+1, x1^nu, x2, andb)
		dfs(i+1, x1, x2^nu, andb)
		dfs(i+1, x1, x2, andb & nu)
	}
	dfs(0, 0, 0, ^uint32(0))
	return res
}

func maximizeXorAndXor(nums []int) int64 {
	n := len(nums)
	mid := n / 2
	lefts := generate(nums[:mid])
	rights := generate(nums[mid:])
	maxV := int64(0)
	ones := ^uint32(0)
	for _, l := range lefts {
		for _, r := range rights {
			abT := l.ab & r.ab
			if abT == ones {
				abT = 0
			}
			xaT := l.xa ^ r.xa
			xcT := l.xc ^ r.xc
			v := int64(xaT) + int64(xcT) + int64(abT)
			if v > maxV {
				maxV = v
			}
		}
	}
	return maxV
}
# @lc code=end