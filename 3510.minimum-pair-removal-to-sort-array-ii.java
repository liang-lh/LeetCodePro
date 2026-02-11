#
# @lc app=leetcode id=3510 lang=java
#
# [3510] Minimum Pair Removal to Sort Array II
#

# @lc code=start
class Solution {
    public int minimumPairRemoval(int[] nums) {
        int n = nums.length;
        if (n < 2) return 0;
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node((long) nums[i], i);
        }
        for (int i = 0; i < n - 1; i++) {
            nodes[i].next = nodes[i + 1];
            nodes[i + 1].prev = nodes[i];
        }
        java.util.PriorityQueue<Candidate> pq = new java.util.PriorityQueue<>();
        for (int i = 0; i < n - 1; i++) {
            pq.offer(new Candidate(nodes[i], nodes[i].val + nodes[i].next.val));
        }
        int violations = 0;
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] > nums[i + 1]) violations++;
        }
        int ops = 0;
        while (violations > 0 && !pq.isEmpty()) {
            Candidate cand = pq.poll();
            Node left = cand.left;
            if (!left.active || left.next == null || !left.next.active) continue;
            long currSum = left.val + left.next.val;
            if (currSum != cand.sum) continue;
            Node prevv = left.prev;
            Node right = left.next;
            Node nextt = right.next;
            boolean hadA = prevv != null && prevv.val > left.val;
            boolean hadB = left.val > right.val;
            boolean hadC = nextt != null && right.val > nextt.val;
            violations -= (hadA ? 1 : 0) + (hadB ? 1 : 0) + (hadC ? 1 : 0);
            left.val += right.val;
            left.startIdx = Math.min(left.startIdx, right.startIdx);
            left.next = nextt;
            if (nextt != null) nextt.prev = left;
            right.active = false;
            boolean newA = prevv != null && prevv.val > left.val;
            boolean newC = nextt != null && left.val > nextt.val;
            violations += (newA ? 1 : 0) + (newC ? 1 : 0);
            ops++;
            if (prevv != null) {
                pq.offer(new Candidate(prevv, prevv.val + left.val));
            }
            if (left.next != null) {
                pq.offer(new Candidate(left, left.val + left.next.val));
            }
        }
        return ops;
    }

    private static class Node {
        long val;
        Node prev, next;
        int startIdx;
        boolean active = true;
        Node(long v, int idx) {
            val = v;
            startIdx = idx;
        }
    }

    private static class Candidate implements Comparable<Candidate> {
        long sum;
        Node left;
        Candidate(Node l, long s) {
            left = l;
            sum = s;
        }
        public int compareTo(Candidate o) {
            if (sum != o.sum) {
                return Long.compare(sum, o.sum);
            }
            return Integer.compare(left.startIdx, o.left.startIdx);
        }
    }
}
# @lc code=end