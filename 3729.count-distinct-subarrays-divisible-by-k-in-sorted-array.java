#
# @lc app=leetcode id=3729 lang=java
#
# [3729] Count Distinct Subarrays Divisible by K in Sorted Array
#

# @lc code=start
class Solution {
    public long numGoodSubarrays(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) return 0;
        long kk = k;
        long[] modpref = new long[n + 1];
        modpref[0] = 0;
        long cur = 0;
        for (int i = 0; i < n; i++) {
            cur = (cur + (long) nums[i]) % kk;
            modpref[i + 1] = cur;
        }
        int[] run_starts_arr = new int[n];
        int num_runs = 0;
        run_starts_arr[num_runs++] = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[i - 1]) {
                run_starts_arr[num_runs++] = i;
            }
        }
        java.util.HashMap<Long, Long> prev_count = new java.util.HashMap<>();
        long multi = 0;
        for (int g = 0; g < num_runs; g++) {
            int st = run_starts_arr[g];
            int next_st = (g + 1 < num_runs) ? run_starts_arr[g + 1] : n;
            int en = next_st - 1;
            for (int j = st; j <= en; j++) {
                long needed = modpref[j + 1];
                Long cntt = prev_count.get(needed);
                if (cntt != null) {
                    multi += cntt;
                }
            }
            for (int i = st; i <= en; i++) {
                long md = modpref[i];
                Long val = prev_count.get(md);
                long newc = (val == null ? 1L : val + 1L);
                prev_count.put(md, newc);
            }
        }
        long singles = 0;
        for (int g = 0; g < num_runs; g++) {
            int st = run_starts_arr[g];
            int next_st = (g + 1 < num_runs) ? run_starts_arr[g + 1] : n;
            long cv = (long) (next_st - st);
            long v = nums[st];
            long modv = v % kk;
            long addd = 0;
            if (modv == 0) {
                addd = cv;
            } else {
                long aa = modv;
                long bb = kk;
                while (bb != 0) {
                    long temp = bb;
                    bb = aa % bb;
                    aa = temp;
                }
                long dd = aa;
                long pp = kk / dd;
                addd = cv / pp;
            }
            singles += addd;
        }
        return multi + singles;
    }
}
# @lc code=end