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
        ListNode revHead = reverseList(head);
        ListNode curr = revHead;
        int carry = 0;
        ListNode tail = null;
        while (curr != null) {
            int prod = curr.val * 2 + carry;
            curr.val = prod % 10;
            carry = prod / 10;
            tail = curr;
            curr = curr.next;
        }
        if (carry != 0) {
            tail.next = new ListNode(carry);
        }
        return reverseList(revHead);
    }
    
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }
}
# @lc code=end