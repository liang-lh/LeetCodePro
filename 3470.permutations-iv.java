#
# @lc app=leetcode id=3470 lang=java
#
# [3470] Permutations IV
#

# @lc code=start
class Solution {
    public int[] permute(int n, long k) {
        if (n == 0) return new int[0];
        int numOdds = (n + 1) / 2;
        int numEvens = n / 2;
        long c1 = countWays(numOdds, numEvens, true, k);
        long c2 = countWays(numOdds, numEvens, false, k);
        if (c1 < k && c2 < k && c1 + c2 < k) {
            return new int[0];
        }
        boolean[] used = new boolean[n + 1];
        int[] result = new int[n];
        int remOdds = numOdds;
        int remEvens = numEvens;
        long remK = k;
        boolean lastOdd = false;
        boolean hasPrev = false;
        for (int pos = 0; pos < n; ++pos) {
            boolean needOdd = !lastOdd;
            boolean chosen = false;
            for (int cand = 1; cand <= n; ++cand) {
                if (used[cand]) continue;
                boolean isOdd = (cand % 2 == 1);
                if (hasPrev && isOdd != needOdd) continue;
                int nrO = remOdds - (isOdd ? 1 : 0);
                int nrE = remEvens - (isOdd ? 0 : 1);
                if (nrO < 0 || nrE < 0) continue;
                boolean nextNeedOdd = !isOdd;
                long ways = countWays(nrO, nrE, nextNeedOdd, remK);
                if (ways >= remK) {
                    result[pos] = cand;
                    used[cand] = true;
                    lastOdd = isOdd;
                    hasPrev = true;
                    if (isOdd) --remOdds;
                    else --remEvens;
                    chosen = true;
                    break;
                } else {
                    remK -= ways;
                }
            }
            if (!chosen) {
                return new int[0];
            }
        }
        return result;
    }

    private static long countWays(int odds, int evens, boolean startOdd, long cap) {
        long K = cap;
        int total = odds + evens;
        if (total == 0) return 1L;
        int oddSlots = startOdd ? (total + 1) / 2 : total / 2;
        int evenSlots = total - oddSlots;
        if (oddSlots != odds || evenSlots != evens) return 0L;
        long factO = 1L;
        for (int i = 2; i <= odds; ++i) {
            if (factO > K / i) return K + 1;
            factO *= i;
        }
        long factE = 1L;
        for (int i = 2; i <= evens; ++i) {
            if (factE > K / i) return K + 1;
            factE *= i;
        }
        if (factE > 0 && factO > K / factE) {
            return K + 1;
        }
        return factO * factE;
    }
}
# @lc code=end