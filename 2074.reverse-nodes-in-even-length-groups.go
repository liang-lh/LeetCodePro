#
# @lc app=leetcode id=2074 lang=golang
#
# [2074] Reverse Nodes in Even Length Groups
#

# @lc code=start
/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func reverseEvenLengthGroups(head *ListNode) *ListNode {
	dummy := &ListNode{0, head}
	prevEnd := dummy
	groupSize := 1
	for prevEnd.Next != nil {
		groupStart := prevEnd.Next
		cur := groupStart
		groupLen := 0
		for groupLen < groupSize && cur != nil {
			cur = cur.Next
			groupLen++
		}
		if groupLen%2 == 0 {
			newHead := (*ListNode)(nil)
			curNode := groupStart
			for i := 0; i < groupLen; i++ {
				next := curNode.Next
				curNode.Next = newHead
				newHead = curNode
				curNode = next
			}
			prevEnd.Next = newHead
			groupStart.Next = cur
			prevEnd = groupStart
		} else {
			groupEnd := groupStart
			for i := 1; i < groupLen; i++ {
				groupEnd = groupEnd.Next
			}
			prevEnd = groupEnd
		}
		groupSize++
	}
	return dummy.Next
}

# @lc code=end