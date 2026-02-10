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
	minD := 200000000
	maxD := 0
	first := 0
	prevCrit := 0
	hasFirst := false
	pos := 1
	var prevNode *ListNode
	cur := head
	for cur != nil {
		if prevNode != nil && cur.Next != nil {
			if (cur.Val > prevNode.Val && cur.Val > cur.Next.Val) ||
				(cur.Val < prevNode.Val && cur.Val < cur.Next.Val) {
				if !hasFirst {
					hasFirst = true
					first = pos
					prevCrit = pos
				} else {
					dist := pos - prevCrit
					if dist < minD {
						minD = dist
					}
					maxD = pos - first
					prevCrit = pos
				}
			}
		}
		prevNode = cur
		cur = cur.Next
		pos++
	}
	if !hasFirst || first == prevCrit {
		return []int{-1, -1}
	}
	return []int{minD, maxD}
}
# @lc code=end