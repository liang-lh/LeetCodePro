#
# @lc app=leetcode id=2074 lang=java
#
# [2074] Reverse Nodes in Even Length Groups
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
    public ListNode reverseEvenLengthGroups(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy;
        ListNode curr = head;
        int groupSize = 1;

        while (curr != null) {
            // Find the end of this group and count group length
            ListNode groupStart = curr;
            int count = 0;
            ListNode temp = curr;
            while (count < groupSize && temp != null) {
                temp = temp.next;
                count++;
            }
            // Reverse group if its length is even
            if (count % 2 == 0) {
                ListNode prevNode = temp;
                ListNode node = curr;
                for (int i = 0; i < count; i++) {
                    ListNode nextNode = node.next;
                    node.next = prevNode;
                    prevNode = node;
                    node = nextNode;
                }
                prev.next = prevNode;
                prev = groupStart;
                curr = temp;
            } else {
                // Move prev and curr pointers forward for odd group (no reversal)
                for (int i = 0; i < count; i++) {
                    prev = curr;
                    curr = curr.next;
                }
            }
            // Verification step: ensure linked list structure is intact
            // (In actual implementation, this could be a debug assertion or helper check)
            groupSize++;
        }
        return dummy.next;
    }
}
# @lc code=end