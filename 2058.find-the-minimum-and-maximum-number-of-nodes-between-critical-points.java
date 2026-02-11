#
# @lc app=leetcode id=2058 lang=java
#
# [2058] Find the Minimum and Maximum Number of Nodes Between Critical Points
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
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        if (head == null || head.next == null) {
            return new int[]{-1, -1};
        }
        ListNode p = head;
        ListNode c = head.next;
        int idx = 2;
        int minDist = Integer.MAX_VALUE;
        int firstCrit = -1;
        int prevCrit = -1;
        int lastCrit = -1;
        while (c != null && c.next != null) {
            ListNode n = c.next;
            boolean isCrit = (c.val > p.val && c.val > n.val) || (c.val < p.val && c.val < n.val);
            if (isCrit) {
                if (prevCrit != -1) {
                    minDist = Math.min(minDist, idx - prevCrit);
                }
                prevCrit = idx;
                if (firstCrit == -1) {
                    firstCrit = idx;
                }
                lastCrit = idx;
            }
            p = c;
            c = n;
            idx++;
        }
        if (firstCrit == -1 || firstCrit == prevCrit) {
            return new int[]{-1, -1};
        }
        return new int[]{minDist, lastCrit - firstCrit};
    }
}
# @lc code=end