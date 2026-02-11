#
# @lc app=leetcode id=3525 lang=java
#
# [3525] Find X Value of Array II
#

# @lc code=start
class Solution {
    public int[] resultArray(int[] nums, int k, int[][] queries) {
        final int B = 400;
        final int MAXK = 6;
        int n = nums.length;
        int num_blocks = (n + B - 1) / B;
        int[][][] blockCounts = new int[num_blocks][MAXK][MAXK];
        int[] blockTotalProd = new int[num_blocks];
        int[] arr = nums.clone();

        // Initial build
        for (int b = 0; b < num_blocks; b++) {
            for (int em = 0; em < k; em++) {
                for (int tm = 0; tm < k; tm++) {
                    blockCounts[b][em][tm] = 0;
                }
            }
            int left = b * B;
            int right = Math.min(n - 1, left + B - 1);
            int sz = right - left + 1;
            if (sz <= 0) continue;
            long[] prefix = new long[sz + 1];
            prefix[1] = arr[left] % k;
            for (int t = 2; t <= sz; t++) {
                long mul = arr[left + t - 1] % k;
                prefix[t] = (prefix[t - 1] * mul) % k;
            }
            for (int t = 1; t <= sz; t++) {
                long pre = prefix[t];
                for (int enter = 0; enter < k; enter++) {
                    int fullm = (int) (((long) enter * pre) % k);
                    blockCounts[b][enter][fullm]++;
                }
            }
            blockTotalProd[b] = (int) prefix[sz];
        }

        int qlen = queries.length;
        int[] ans = new int[qlen];
        for (int qi = 0; qi < qlen; qi++) {
            int[] qu = queries[qi];
            int idx = qu[0];
            int val = qu[1];
            int start = qu[2];
            int x = qu[3];
            arr[idx] = val;
            // Rebuild affected block
            int ub = idx / B;
            {
                int b = ub;
                for (int em = 0; em < k; em++) {
                    for (int tm = 0; tm < k; tm++) {
                        blockCounts[b][em][tm] = 0;
                    }
                }
                int left = b * B;
                int right = Math.min(n - 1, left + B - 1);
                int sz = right - left + 1;
                if (sz > 0) {
                    long[] prefix = new long[sz + 1];
                    prefix[1] = arr[left] % k;
                    for (int t = 2; t <= sz; t++) {
                        long mul = arr[left + t - 1] % k;
                        prefix[t] = (prefix[t - 1] * mul) % k;
                    }
                    for (int t = 1; t <= sz; t++) {
                        long pre = prefix[t];
                        for (int enter = 0; enter < k; enter++) {
                            int fullm = (int) (((long) enter * pre) % k);
                            blockCounts[b][enter][fullm]++;
                        }
                    }
                    blockTotalProd[b] = (int) prefix[sz];
                }
            }
            // Compute answer for suffix starting at 'start'
            int res = 0;
            long cur_mod = 1L % k;
            int pos = start;
            int cb = pos / B;
            int blew = cb * B;
            int bright = Math.min(n - 1, blew + B - 1);
            // First partial block
            for (int p = pos; p <= bright && p < n; p++) {
                long mul = arr[p] % k;
                cur_mod = (cur_mod * mul) % k;
                if ((int) cur_mod == x) res++;
            }
            if (bright < n - 1) {
                int lb = (n - 1) / B;
                // Full blocks: cb+1 to lb-1
                for (int bb = cb + 1; bb < lb; bb++) {
                    res += blockCounts[bb][(int) cur_mod][x];
                    cur_mod = (cur_mod * (long) blockTotalProd[bb]) % k;
                }
                // Last partial block
                if (lb > cb) {
                    int lleft = lb * B;
                    for (int p = lleft; p < n; p++) {
                        long mul = arr[p] % k;
                        cur_mod = (cur_mod * mul) % k;
                        if ((int) cur_mod == x) res++;
                    }
                }
            }
            ans[qi] = res;
        }
        return ans;
    }
}
# @lc code=end