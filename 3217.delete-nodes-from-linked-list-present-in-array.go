#
# @lc app=leetcode id=3217 lang=golang
#
# [3217] Delete Nodes From Linked List Present in Array
#

# @lc code=start
/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func modifiedList(nums []int, head *ListNode) *ListNode {
    numsSet := make(map[int]bool)
    for _, num := range nums {
        numsSet[num] = true
    }
    dummy := &ListNode{Val: 0, Next: head}
    prev := dummy
    for curr := head; curr != nil; curr = curr.Next {
        if numsSet[curr.Val] {
            prev.Next = curr.Next
        } else {
            prev = curr
        }
    }
    return dummy.Next
}
# @lc code=end