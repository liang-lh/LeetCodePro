#
# @lc app=leetcode id=3762 lang=java
#
# [3762] Minimum Operations to Equalize Subarrays
#

# @lc code=start
import java.util.*;
class Solution {
    static class Fenwick {
        long[] tree;
        int n;
        Fenwick(int _n) {
            n = _n;
            tree = new long[n + 2];
        }
        void update(int idx, long val) {
            while (idx <= n) {
                tree[idx] += val;
                idx += idx & -idx;
            }
        }
        long query(int idx) {
            long sum = 0;
            while (idx > 0) {
                sum += tree[idx];
                idx -= idx & -idx;
            }
            return sum;
        }
    }

    static class MoQuery {
        int l, r, idx;
        MoQuery(int l, int r, int idx) {
            this.l = l;
            this.r = r;
            this.idx = idx;
        }
    }

    public long[] minOperations(int[] nums, int k, int[][] queries) {
        int n = nums.length;
        int qnum = queries.length;
        int[] mods = new int[n];
        int[] qvals = new int[n];
        for (int i = 0; i < n; i++) {
            mods[i] = nums[i] % k;
            qvals[i] = nums[i] / k;
        }
        // Sparse Table for min/max mods
        int LOG = 16;
        int[][] stmin = new int[LOG][n];
        int[][] stmax = new int[LOG][n];
        for (int i = 0; i < n; i++) {
            stmin[0][i] = stmax[0][i] = mods[i];
        }
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i + (1 << j) <= n; i++) {
                stmin[j][i] = Math.min(stmin[j - 1][i], stmin[j - 1][i + (1 << (j - 1))]);
                stmax[j][i] = Math.max(stmax[j - 1][i], stmax[j - 1][i + (1 << (j - 1))]);
            }
        }
        // Identify valid queries
        List<MoQuery> validQueries = new ArrayList<>();
        long[] answer = new long[qnum];
        Arrays.fill(answer, -1L);
        for (int qi = 0; qi < qnum; qi++) {
            int L = queries[qi][0];
            int R = queries[qi][1];
            int lenq = R - L + 1;
            int lg = 31 - Integer.numberOfLeadingZeros(lenq);
            int min_mod = Math.min(stmin[lg][L], stmin[lg][R - (1 << lg) + 1]);
            int max_mod = Math.max(stmax[lg][L], stmax[lg][R - (1 << lg) + 1]);
            if (min_mod == max_mod) {
                validQueries.add(new MoQuery(L, R, qi));
            }
        }
        if (validQueries.isEmpty()) {
            return answer;
        }
        // Discretization
        Integer[] qarr = new Integer[n];
        for (int i = 0; i < n; i++) qarr[i] = qvals[i];
        Arrays.sort(qarr);
        List<Integer> uniqueVals = new ArrayList<>();
        if (n > 0) {
            uniqueVals.add(qarr[0]);
            for (int i = 1; i < n; i++) {
                if (!qarr[i].equals(qarr[i - 1])) {
                    uniqueVals.add(qarr[i]);
                }
            }
        }
        int numUnique = uniqueVals.size();
        long[] valOfRank = new long[numUnique + 1];
        Map<Integer, Integer> rankMap = new HashMap<>();
        for (int i = 0; i < numUnique; i++) {
            int v = uniqueVals.get(i);
            int rnk = i + 1;
            rankMap.put(v, rnk);
            valOfRank[rnk] = v;
        }
        int[] ranks = new int[n];
        for (int i = 0; i < n; i++) {
            ranks[i] = rankMap.get(qvals[i]);
        }
        // Mo's setup
        int blockSz = (int) Math.sqrt(n) + 1;
        validQueries.sort((a, b) -> {
            int ba = a.l / blockSz;
            int bb = b.l / blockSz;
            if (ba != bb) return Integer.compare(ba, bb);
            return Integer.compare(a.r, b.r);
        });
        Fenwick countFT = new Fenwick(numUnique + 1);
        Fenwick sumFT = new Fenwick(numUnique + 1);
        int currL = 0;
        int currR = -1;
        for (MoQuery mq : validQueries) {
            int targetL = mq.l;
            int targetR = mq.r;
            while (currL > targetL) {
                currL--;
                int rk = ranks[currL];
                long v = qvals[currL];
                countFT.update(rk, 1);
                sumFT.update(rk, v);
            }
            while (currR < targetR) {
                currR++;
                int rk = ranks[currR];
                long v = qvals[currR];
                countFT.update(rk, 1);
                sumFT.update(rk, v);
            }
            while (currL < targetL) {
                int rk = ranks[currL];
                long v = qvals[currL];
                countFT.update(rk, -1);
                sumFT.update(rk, -v);
                currL++;
            }
            while (currR > targetR) {
                int rk = ranks[currR];
                long v = qvals[currR];
                countFT.update(rk, -1);
                sumFT.update(rk, -v);
                currR--;
            }
            // Compute ops
            int length = targetR - targetL + 1;
            int kth = (length + 1) / 2;
            int leftb = 1, rightb = numUnique;
            int medRank = rightb + 1;
            while (leftb <= rightb) {
                int midR = leftb + (rightb - leftb) / 2;
                if (countFT.query(midR) >= kth) {
                    medRank = midR;
                    rightb = midR - 1;
                } else {
                    leftb = midR + 1;
                }
            }
            long medVal = valOfRank[medRank];
            long countLeft = countFT.query(medRank - 1);
            long sumLeft = sumFT.query(medRank - 1);
            long countLeq = countFT.query(medRank);
            long sumLeq = sumFT.query(medRank);
            long countRight = (long) length - countLeq;
            long sumRight = sumFT.query(numUnique) - sumLeq;
            long ops = countLeft * medVal - sumLeft + sumRight - countRight * medVal;
            answer[mq.idx] = ops;
        }
        return answer;
    }
}
# @lc code=end