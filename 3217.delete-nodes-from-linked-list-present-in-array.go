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
    // Create a map for O(1) lookup
    numSet := make(map[int]bool)
    for _, num := range nums {
        numSet[num] = true
    }
    
    // Create a dummy node to handle head deletion
    dummy := &ListNode{Next: head}
    prev := dummy
    current := head
    
    // Traverse the list
    for current != nil {
        if numSet[current.Val] {
            // Skip this node by removing it from the list
            prev.Next = current.Next
        } else {
            // Keep this node, move prev forward
            prev = current
        }
        current = current.Next
    }
    
    return dummy.Next
}
# @lc code=end