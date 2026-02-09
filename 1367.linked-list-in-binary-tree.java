#
# @lc app=leetcode id=1367 lang=java
#
# [1367] Linked List in Binary Tree
#
# @lc code=start
/**
* Definition for singly-linked list.
* public class ListNode {
*     int val;
*     ListNode next;
*     ListNode() {}
*     ListNode(int val) { this.val = val; }
*     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
* }
*/
/**
* Definition for a binary tree node.
* public class TreeNode {
*     int val;
*     TreeNode left;
*     TreeNode right;
*     TreeNode() {}
*     TreeNode(int val) { this.val = val; }
*     TreeNode(int val, TreeNode left, TreeNode right) {
*         this.val = val;
*         this.left = left;
*         this.right = right;
*     }
* }
*/
class Solution {
    public boolean isSubPath(ListNode head, TreeNode root) {
        if (root == null) return false;
        // Check if path starts from current node OR from left subtree OR from right subtree
        return dfs(head, root) || isSubPath(head, root.left) || isSubPath(head, root.right);
    }
    
    private boolean dfs(ListNode head, TreeNode root) {
        // If we've matched all nodes in the list, return true
        if (head == null) return true;
        // If tree node is null but list is not empty, no match
        if (root == null) return false;
        // Check if current values match AND either left or right path continues the match
        if (head.val == root.val) {
            return dfs(head.next, root.left) || dfs(head.next, root.right);
        }
        return false;
    }
}
# @lc code=end