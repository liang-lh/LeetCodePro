#
# @lc app=leetcode id=2816 lang=golang
#
# [2816] Double a Number Represented as a Linked List
#
# @lc code=start
/**
* Definition for singly-linked list.
* type ListNode struct {
*     Val int
*     Next *ListNode
* }
*/
func doubleIt(head *ListNode) *ListNode {
    carry := helper(head)
    if carry > 0 {
        newHead := &ListNode{Val: carry, Next: head}
        return newHead
    }
    return head
}

func helper(node *ListNode) int {
    if node == nil {
        return 0
    }
    
    // Process the rest of the list first
    carry := helper(node.Next)
    
    // Double current digit and add carry from right
    doubled := node.Val * 2 + carry
    node.Val = doubled % 10
    return doubled / 10
}
# @lc code=end