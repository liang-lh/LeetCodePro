#
# @lc app=leetcode id=3748 lang=java
#
# [3748] Count Stable Subarrays
#

# @lc code=start
class Solution {
    public long[] countStableSubarrays(int[] nums, int[][] queries) {
        int n = nums.length;
        int q = queries.length;
        long[] ans = new long[q];

        // Single pass: collect segments using lists
        java.util.List<Integer> segStartList = new java.util.ArrayList<>();
        java.util.List<Integer> segEndList = new java.util.ArrayList<>();
        for (int i = 0; i < n; ) {
            int j = i;
            while (j + 1 < n && nums[j + 1] >= nums[j]) ++j;
            segStartList.add(i);
            segEndList.add(j);
            i = j + 1;
        }
        int K = segStartList.size();
        int[] segStart = new int[K];
        int[] segEnd = new int[K];
        for (int k = 0; k < K; ++k) {
            segStart[k] = segStartList.get(k);
            segEnd[k] = segEndList.get(k);
        }

        // Precompute triangular numbers and prefix sums
        long[] segTotal = new long[K];
        long[] pref = new long[K + 1];
        for (int j = 0; j < K; ++j) {
            int len_ = segEnd[j] - segStart[j] + 1;
            segTotal[j] = (long) len_ * (len_ + 1) / 2;
            pref[j + 1] = pref[j] + segTotal[j];
        }

        // Process queries
        for (int qi = 0; qi < q; ++qi) {
            int L = queries[qi][0];
            int R = queries[qi][1];

            // Binary search for jL: largest j where segStart[j] <= L
            int low = 0, high = K - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (segStart[mid] <= L) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            int jL = high;

            // Binary search for jR: largest j where segStart[j] <= R
            low = 0;
            high = K - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (segStart[mid] <= R) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            int jR = high;

            long res;
            if (jL == jR) {
                int m = R - L + 1;
                res = (long) m * (m + 1) / 2;
            } else {
                // Left partial: L to segEnd[jL]
                int eL = segEnd[jL];
                long mL = (long) eL - L + 1;
                long numL = mL * (mL + 1) / 2;
                // Right partial: segStart[jR] to R
                int sR = segStart[jR];
                long mR = (long) R - sR + 1;
                long numR = mR * (mR + 1) / 2;
                // Middle full segments: jL+1 to jR-1
                long numMid = pref[jR] - pref[jL + 1];
                res = numL + numR + numMid;
            }
            ans[qi] = res;
        }

        return ans;
    }
}
# @lc code=end