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
    if head == nil {
        return nil
    }
    
    dummy := &ListNode{Next: head}
    prev := dummy
    groupSize := 1
    
    for prev.Next != nil {
        // Count actual nodes in this group
        curr := prev.Next
        count := 0
        for curr != nil && count < groupSize {
            curr = curr.Next
            count++
        }
        
        // If group has even length, reverse it
        if count % 2 == 0 {
            prev.Next = reverseGroup(prev.Next, count)
        }
        
        // Move prev to the end of current group
        for i := 0; i < count; i++ {
            prev = prev.Next
        }
        
        groupSize++
    }
    
    return dummy.Next
}

func reverseGroup(head *ListNode, k int) *ListNode {
    var prev *ListNode
    curr := head
    
    for i := 0; i < k && curr != nil; i++ {
        next := curr.Next
        curr.Next = prev
        prev = curr
        curr = next
    }
    
    head.Next = curr
    return prev
}
# @lc code=end