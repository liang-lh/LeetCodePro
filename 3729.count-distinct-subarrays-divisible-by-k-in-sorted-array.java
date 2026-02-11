#
# @lc app=leetcode id=3729 lang=java
#
# [3729] Count Distinct Subarrays Divisible by K in Sorted Array
#

# @lc code=start
class Solution {
    public long numGoodSubarrays(int[] nums, int k) {
        long K = k;
        int n = nums.length;
        java.util.List<java.lang.Long> vals = new java.util.ArrayList<>();
        java.util.List<java.lang.Integer> cnts = new java.util.ArrayList<>();
        for (int i = 0; i < n; ) {
            long v = nums[i];
            int j = i;
            while (j < n && nums[j] == nums[i]) ++j;
            vals.add(v);
            cnts.add(j - i);
            i = j;
        }
        int m = cnts.size();
        long[] prefixMod = new long[m + 1];
        prefixMod[0] = 0;
        for (int g = 0; g < m; ++g) {
            long v = vals.get(g);
            int c = cnts.get(g);
            long groupSumMod = ((long) c * v) % K;
            prefixMod[g + 1] = (prefixMod[g] + groupSumMod + K) % K;
        }
        long ans = 0;
        // intra-group
        for (int g = 0; g < m; ++g) {
            long vv = vals.get(g) % K;
            long gg = gcd(vv, K);
            long period = K / gg;
            ans += (long) cnts.get(g) / period;
        }
        // inter-group
        java.util.Map<java.lang.Long, java.lang.Long> futureRight = new java.util.HashMap<>();
        for (int s = m - 1; s >= 0; --s) {
            // left_freq
            java.util.Map<java.lang.Long, java.lang.Long> leftF = new java.util.HashMap<>();
            long Vs = vals.get(s) % K;
            long Ms1 = prefixMod[s + 1];
            int cs = cnts.get(s);
            for (int a = 1; a <= cs; ++a) {
                long ra = ((long) a * Vs) % K;
                long lmod = (ra - Ms1 + 2 * K) % K;
                leftF.put(lmod, leftF.getOrDefault(lmod, 0L) + 1L);
            }
            // contributions
            for (java.util.Map.Entry<java.lang.Long, java.lang.Long> e : leftF.entrySet()) {
                long r = e.getKey();
                long cl = e.getValue();
                long need = (K - r + K) % K;
                long cr = futureRight.getOrDefault(need, 0L);
                ans += cl * cr;
            }
            // right_freq
            java.util.Map<java.lang.Long, java.lang.Long> rightF = new java.util.HashMap<>();
            long Vt = vals.get(s) % K;
            long Mt = prefixMod[s];
            int ct = cnts.get(s);
            for (int b = 1; b <= ct; ++b) {
                long rb = ((long) b * Vt) % K;
                long rmod = (rb + Mt + K) % K;
                rightF.put(rmod, rightF.getOrDefault(rmod, 0L) + 1L);
            }
            // add to futureRight
            for (java.util.Map.Entry<java.lang.Long, java.lang.Long> e : rightF.entrySet()) {
                long rmod = e.getKey();
                long cc = e.getValue();
                futureRight.put(rmod, futureRight.getOrDefault(rmod, 0L) + cc);
            }
        }
        return ans;
    }
    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
# @lc code=end