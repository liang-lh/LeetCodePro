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
        // Step 2: Reverse the linked list
        head = reverseList(head);
        // Step 3: Double each digit and handle carry
        ListNode curr = head;
        int carry = 0;
        ListNode prev = null;
        while (curr != null) {
            int sum = curr.val * 2 + carry;
            curr.val = sum % 10;
            carry = sum / 10;
            prev = curr;
            curr = curr.next;
        }
        // Step 4: If there is a carry left, add a new node
        if (carry > 0) {
            prev.next = new ListNode(carry);
        }
        // Step 5: Reverse back to restore original order
        return reverseList(head);
    }
    // Helper function to reverse a singly-linked list
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
}
# @lc code=end