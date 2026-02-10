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
    // Need at least 3 nodes to have a critical point
    if head == nil || head.Next == nil || head.Next.Next == nil {
        return []int{-1, -1}
    }
    
    criticalPoints := []int{}
    prev := head
    curr := head.Next
    position := 1  // Start counting from position 1 (second node)
    
    // Traverse the list to find all critical points
    for curr.Next != nil {
        // Check if current node is a critical point
        // Local maxima: curr.Val > prev.Val && curr.Val > curr.Next.Val
        // Local minima: curr.Val < prev.Val && curr.Val < curr.Next.Val
        if (curr.Val > prev.Val && curr.Val > curr.Next.Val) || 
           (curr.Val < prev.Val && curr.Val < curr.Next.Val) {
            criticalPoints = append(criticalPoints, position)
        }
        prev = curr
        curr = curr.Next
        position++
    }
    
    // If fewer than 2 critical points, return [-1, -1]
    if len(criticalPoints) < 2 {
        return []int{-1, -1}
    }
    
    // Calculate min and max distances
    minDistance := int(^uint(0) >> 1) // Max int value
    maxDistance := criticalPoints[len(criticalPoints)-1] - criticalPoints[0]
    
    // Find minimum distance between consecutive critical points
    for i := 1; i < len(criticalPoints); i++ {
        distance := criticalPoints[i] - criticalPoints[i-1]
        if distance < minDistance {
            minDistance = distance
        }
    }
    
    return []int{minDistance, maxDistance}
}
# @lc code=end