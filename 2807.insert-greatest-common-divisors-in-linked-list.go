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
	cur := head
	for cur != nil && cur.Next != nil {
		g := gcd(cur.Val, cur.Next.Val)
		newNode := &ListNode{Val: g, Next: cur.Next}
		cur.Next = newNode
		cur = newNode.Next
	}
	return head
}
# @lc code=end