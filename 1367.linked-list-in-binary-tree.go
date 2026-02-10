#
# @lc app=leetcode id=1367 lang=golang
#
# [1367] Linked List in Binary Tree
#

# @lc code=start
/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
/**
 * Definition for a binary tree node.
 * type TreeNode struct {
 *     Val int
 *     Left *TreeNode
 *     Right *TreeNode
 * }
 */
func isSubPath(head *ListNode, root *TreeNode) bool {
    var check func(*TreeNode, *ListNode) bool
    check = func(node *TreeNode, list *ListNode) bool {
        if list == nil {
            return true
        }
        if node == nil || node.Val != list.Val {
            return false
        }
        return check(node.Left, list.Next) || check(node.Right, list.Next)
    }

    var dfs func(*TreeNode) bool
    dfs = func(node *TreeNode) bool {
        if node == nil {
            return false
        }
        if check(node, head) {
            return true
        }
        return dfs(node.Left) || dfs(node.Right)
    }

    return dfs(root)
}
# @lc code=end