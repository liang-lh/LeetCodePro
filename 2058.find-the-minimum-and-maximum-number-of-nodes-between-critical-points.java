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
        List<Integer> positions = new ArrayList<>();
        int idx = 1;
        ListNode prev = head, curr = head.next;
        while (curr != null && curr.next != null) {
            int val = curr.val;
            if ((val > prev.val && val > curr.next.val) || (val < prev.val && val < curr.next.val)) {
                positions.add(idx);
            }
            prev = curr;
            curr = curr.next;
            idx++;
        }
        if (positions.size() < 2) return new int[]{-1, -1};
        int minDist = Integer.MAX_VALUE;
        for (int i = 1; i < positions.size(); i++) {
            minDist = Math.min(minDist, positions.get(i) - positions.get(i - 1));
        }
        int maxDist = positions.get(positions.size() - 1) - positions.get(0);
        return new int[]{minDist, maxDist};
    }
}
# @lc code=end