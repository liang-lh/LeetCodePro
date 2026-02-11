#
# @lc app=leetcode id=3510 lang=java
#
# [3510] Minimum Pair Removal to Sort Array II
#

# @lc code=start
class Solution {
    private static class Node {
        long val;
        Node prev;
        Node next;
        int startIdx;
        Node(long v, int idx) {
            this.val = v;
            this.startIdx = idx;
        }
    }

    private static class Candidate implements Comparable<Candidate> {
        long sum;
        int pos;
        Node left;
        Candidate(long s, int p, Node l) {
            sum = s;
            pos = p;
            left = l;
        }
        @Override
        public int compareTo(Candidate o) {
            int cmp = Long.compare(sum, o.sum);
            if (cmp != 0) return cmp;
            return Integer.compare(pos, o.pos);
        }
    }

    public int minimumPairRemoval(int[] nums) {
        int n = nums.length;
        if (n <= 1) return 0;
        Node head = new Node((long) nums[0], 0);
        Node tail = head;
        java.util.PriorityQueue<Candidate> pq = new java.util.PriorityQueue<>();
        for (int i = 1; i < n; ++i) {
            Node node = new Node((long) nums[i], i);
            tail.next = node;
            node.prev = tail;
            tail = node;
            long s = tail.prev.val + tail.val;
            pq.offer(new Candidate(s, tail.prev.startIdx, tail.prev));
        }
        // compute initial violations
        int violations = 0;
        for (Node cur = head; cur.next != null; cur = cur.next) {
            if (cur.val > cur.next.val) ++violations;
        }
        int ops = 0;
        while (violations > 0 && head.next != null) {
            Candidate cand = null;
            while (!pq.isEmpty() && cand == null) {
                Candidate temp = pq.poll();
                Node L = temp.left;
                if (L != null && L.next != null && L.next.prev == L && temp.sum == L.val + L.next.val) {
                    cand = temp;
                }
            }
            if (cand == null) {
                break;
            }
            Node L = cand.left;
            Node R = L.next;
            Node P = L.prev;
            Node N = R.next;
            // remove old violations
            if (P != null && P.val > L.val) --violations;
            if (L.val > R.val) --violations;
            if (N != null && R.val > N.val) --violations;
            // create new node S
            Node S = new Node(L.val + R.val, Math.min(L.startIdx, R.startIdx));
            S.prev = P;
            S.next = N;
            if (P != null) P.next = S;
            if (N != null) N.prev = S;
            // unlink old
            L.prev = null;
            L.next = null;
            R.prev = null;
            R.next = null;
            // update head
            if (P == null) head = S;
            // add new violations
            if (P != null && P.val > S.val) ++violations;
            if (N != null && S.val > N.val) ++violations;
            // add new candidates
            if (P != null) {
                long ns = P.val + S.val;
                pq.offer(new Candidate(ns, P.startIdx, P));
            }
            if (N != null) {
                long ns = S.val + N.val;
                pq.offer(new Candidate(ns, S.startIdx, S));
            }
            ++ops;
        }
        return ops;
    }
}
# @lc code=end