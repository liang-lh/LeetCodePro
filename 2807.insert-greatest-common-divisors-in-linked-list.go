#
# @lc app=leetcode id=2807 lang=golang
#
# [2807] Insert Greatest Common Divisors in Linked List
#
# @lc code=start
/**
* Definition for singly-linked list.
* type ListNode struct {
*     Val int
*     Next *ListNode
* }
*/

func gcd(a, b int) int {
    for b != 0 {
        a, b = b, a % b
    }
    return a
}

func insertGreatestCommonDivisors(head *ListNode) *ListNode {
    if head == nil || head.Next == nil {
        return head
    }
    
    current := head
    for current != nil && current.Next != nil {
        // Calculate GCD of current and next node values
        gcdValue := gcd(current.Val, current.Next.Val)
        
        // Create new node with GCD value
        newNode := &ListNode{Val: gcdValue}
        
        // Insert new node between current and current.Next
        newNode.Next = current.Next
        current.Next = newNode
        
        // Move to the next original node (skip the inserted node)
        current = newNode.Next
    }
    
    return head
}
# @lc code=end