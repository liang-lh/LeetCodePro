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
        ListNode prevTail = dummy;
        int groupSize = 1;
        while (prevTail.next != null) {
            ListNode groupStart = prevTail.next;
            ListNode groupTail = null;
            ListNode temp = groupStart;
            int length = 0;
            while (temp != null && length < groupSize) {
                length++;
                groupTail = temp;
                temp = temp.next;
            }
            ListNode nextGroupHead = temp;
            if (length % 2 == 0) {
                // Iterative reverse
                ListNode revPrev = nextGroupHead;
                ListNode revCurr = groupStart;
                while (revCurr != nextGroupHead) {
                    ListNode revNextTemp = revCurr.next;
                    revCurr.next = revPrev;
                    revPrev = revCurr;
                    revCurr = revNextTemp;
                }
                prevTail.next = revPrev;
                prevTail = groupStart;
            } else {
                prevTail = groupTail;
            }
            groupSize++;
        }
        return dummy.next;
    }
}
# @lc code=end