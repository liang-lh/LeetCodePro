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
func insertGreatestCommonDivisors(head *ListNode) *ListNode {

	gcd := func(a, b int) int {
		for b != 0 {
			a, b = b, a % b
		}
		return a
	}

	if head == nil {
		return head
	}

	curr := head
	for curr.Next != nil {
		g := gcd(curr.Val, curr.Next.Val)
		newNode := &ListNode{Val: g}
		newNode.Next = curr.Next
		curr.Next = newNode
		curr = newNode.Next
	}

	return head
}
# @lc code=end