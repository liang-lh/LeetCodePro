#
# @lc app=leetcode id=2058 lang=golang
#
# [2058] Find the Minimum and Maximum Number of Nodes Between Critical Points
#

# @lc code=start
/**
* Definition for singly-linked list.
* type ListNode struct {
*     Val int
*     Next *ListNode
* }
*/
func nodesBetweenCriticalPoints(head *ListNode) []int {
	if head == nil || head.Next == nil {
		return []int{-1, -1}
	}
	crit := []int{}
	prevVal := head.Val
	cur := head.Next
	idx := 2
	for cur != nil {
		if cur.Next == nil {
			break
		}
		nextVal := cur.Next.Val
		if (cur.Val > prevVal && cur.Val > nextVal) || (cur.Val < prevVal && cur.Val < nextVal) {
			crit = append(crit, idx)
		}
		prevVal = cur.Val
		cur = cur.Next
		idx++
	}
	if len(crit) < 2 {
		return []int{-1, -1}
	}
	minDist := crit[1] - crit[0]
	for i := 2; i < len(crit); i++ {
		dist := crit[i] - crit[i-1]
		if dist < minDist {
			minDist = dist
		}
	}
	maxDist := crit[len(crit)-1] - crit[0]
	return []int{minDist, maxDist}
}
# @lc code=end