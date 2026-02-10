#
# @lc app=leetcode id=3670 lang=golang
#
# [3670] Maximum Product of Two Integers With No Common Bits
#

# @lc code=start
func maxProduct(nums []int) int64 {
	type Node struct {
		children [2]*Node
	}

	root := &Node{}

	insert := func(nd *Node, val int) {
		node := nd
		for i := 19; i >= 0; i-- {
			bit := (val >> i) & 1
			if node.children[bit] == nil {
				node.children[bit] = &Node{}
			}
			node = node.children[bit]
		}
	}

	getMax := func(nd *Node, val int) int {
		node := nd
		res := 0
		for i := 19; i >= 0; i-- {
			bit := (val >> i) & 1
			if bit == 1 {
				if node.children[0] == nil {
					return 0
				}
				node = node.children[0]
			} else {
				if node.children[1] != nil {
					res |= 1 << i
					node = node.children[1]
				} else if node.children[0] != nil {
					node = node.children[0]
				} else {
					return 0
				}
			}
		}
		return res
	}

	ans := int64(0)
	for _, num := range nums {
		partner := getMax(root, num)
		curr := int64(num) * int64(partner)
		if curr > ans {
			ans = curr
		}
		insert(root, num)
	}
	return ans
}
# @lc code=end