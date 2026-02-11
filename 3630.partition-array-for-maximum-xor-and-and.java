#
# @lc app=leetcode id=3630 lang=java
#
# [3630] Partition Array for Maximum XOR and AND
#

# @lc code=start
class Solution {
    public long maximizeXorAndXor(int[] nums) {
        int n = nums.length;
        int N = 1 << n;
        int full = N - 1;
        long[] sub_xor = new long[N];
        long[] sub_and = new long[N];
        for (int i = 0; i < N; i++) {
            sub_and[i] = -1L;
        }
        sub_and[0] = 0L;
        for (int i = 0; i < n; i++) {
            long val = nums[i];
            for (int sub = 0; sub < N; sub++) {
                if ((sub & (1 << i)) != 0) {
                    sub_xor[sub] ^= val;
                    sub_and[sub] &= val;
                }
            }
        }
        long ans = 0;
        long LOW32 = 0xFFFFFFFFL;
        for (int maskB = 0; maskB < N; maskB++) {
            long andB = sub_and[maskB];
            int maskS = full ^ maskB;
            long xorS = sub_xor[maskS];
            // Build basis
            long[] basis = new long[32];
            for (int j = 0; j < n; j++) {
                if ((maskS & (1 << j)) == 0) continue;
                long val = nums[j];
                for (int p = 31; p >= 0; p--) {
                    if ((val & (1L << p)) == 0) continue;
                    if (basis[p] != 0) {
                        val ^= basis[p];
                    } else {
                        basis[p] = val;
                        break;
                    }
                }
            }
            // Greedy max proj = max (x & m)
            long m = (~xorS) & LOW32;
            long proj = 0;
            for (int p = 31; p >= 0; p--) {
                if ((m & (1L << p)) == 0) continue;
                if (basis[p] == 0) continue;
                if ((proj & (1L << p)) == 0) {
                    proj ^= basis[p];
                }
            }
            proj &= m;
            long curr = andB + xorS + 2 * proj;
            if (curr > ans) ans = curr;
        }
        return ans;
    }
}
# @lc code=end