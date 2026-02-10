#
# @lc app=leetcode id=3457 lang=golang
#
# [3457] Eat Pizzas!
#

# @lc code=start
import "sort"

func maxWeight(pizzas []int) int64 {
	sort.Slice(pizzas, func(i, j int) bool {
		return pizzas[i] > pizzas[j]
	})
	k := len(pizzas) / 4
	numOdd := (k + 1) / 2
	numEven := k / 2
	ans := int64(0)
	for i := 0; i < numOdd; i++ {
		ans += int64(pizzas[i])
	}
	pos := numOdd
	for i := 0; i < numEven; i++ {
		ans += int64(pizzas[pos + 1])
		pos += 2
	}
	return ans
}
# @lc code=end