#
# @lc app=leetcode id=3217 lang=java
#
# [3217] Delete Nodes From Linked List Present in Array
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
import java.util.HashSet;
import java.util.Set;
class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {
        Set<Integer> toDelete = new HashSet<>();
        for (int num : nums) {
            toDelete.add(num);
        }
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy;
        ListNode curr = head;
        while (curr != null) {
            if (toDelete.contains(curr.val)) {
                prev.next = curr.next;
            } else {
                prev = curr;
            }
            curr = curr.next;
        }
        // At this point, all nodes with values in nums are removed.
        // The structure of the list is preserved.
        return dummy.next;
    }
}
# @lc code=end