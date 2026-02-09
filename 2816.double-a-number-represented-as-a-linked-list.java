#
# @lc app=leetcode id=2816 lang=java
#
# [2816] Double a Number Represented as a Linked List
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
class Solution {
    public ListNode doubleIt(ListNode head) {
        int carry = doubleHelper(head);
        
        // If there's a carry after processing the head, create a new head
        if (carry > 0) {
            ListNode newHead = new ListNode(carry);
            newHead.next = head;
            return newHead;
        }
        
        return head;
    }
    
    private int doubleHelper(ListNode node) {
        if (node == null) {
            return 0;
        }
        
        // Recursively get carry from the right side
        int carry = doubleHelper(node.next);
        
        // Double current value and add carry from right
        int doubled = node.val * 2 + carry;
        
        // Update current node value (keep only the ones digit)
        node.val = doubled % 10;
        
        // Return carry to the left (the tens digit)
        return doubled / 10;
    }
}
# @lc code=end