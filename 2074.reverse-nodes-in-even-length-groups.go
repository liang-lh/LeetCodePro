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
    dummy := &ListNode{Next: head}
    prevGroupEnd := dummy
    groupSize := 1
    for prevGroupEnd.Next != nil {
        groupStart := prevGroupEnd.Next
        groupEnd := groupStart
        groupLength := 1
        for groupLength < groupSize && groupEnd.Next != nil {
            groupEnd = groupEnd.Next
            groupLength++
        }
        nextStart := groupEnd.Next
        if groupLength % 2 == 0 {
            revPrev := (*ListNode)(nil)
            curr := groupStart
            for curr != nextStart {
                nextNode := curr.Next
                curr.Next = revPrev
                revPrev = curr
                curr = nextNode
            }
            groupStart.Next = nextStart
            prevGroupEnd.Next = revPrev
            prevGroupEnd = groupStart
        } else {
            prevGroupEnd = groupEnd
        }
        groupSize++
    }
    return dummy.Next
}
# @lc code=end