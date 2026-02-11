#
# @lc app=leetcode id=3509 lang=java
#
# [3509] Maximum Product of Subsequences With an Alternating Sum Equal to K
#

# @lc code=start
class Solution {
    public int maxProduct(int[] nums, int k, int limit) {
        final int OFFSET = 2000;
        final int SZ = 4001;
        int n = nums.length;
        int[][] dp = new int[SZ][2];
        // Initialize to exclude empty subsequence
        for (int j = 0; j < SZ; j++) {
            dp[j][0] = -1;
            dp[j][1] = -1;
        }
        for (int i = 0; i < n; i++) {
            int val = nums[i];
            int[][] newdp = new int[SZ][2];
            for (int j = 0; j < SZ; j++) {
                newdp[j][0] = -1;
                newdp[j][1] = -1;
            }
            // carry over (not take)
            for (int j = 0; j < SZ; j++) {
                if (dp[j][0] != -1) {
                    newdp[j][0] = Math.max(newdp[j][0], dp[j][0]);
                }
                if (dp[j][1] != -1) {
                    newdp[j][1] = Math.max(newdp[j][1], dp[j][1]);
                }
            }
            // append from par=0 (+val) -> par=1
            for (int j = 0; j < SZ; j++) {
                if (dp[j][0] != -1) {
                    int sumv = j - OFFSET;
                    int nsum = sumv + val;
                    int nj = nsum + OFFSET;
                    if (nj >= 0 && nj < SZ) {
                        long nprod = (long) dp[j][0] * val;
                        if (nprod <= limit) {
                            newdp[nj][1] = Math.max(newdp[nj][1], (int) nprod);
                        }
                    }
                }
            }
            // append from par=1 (-val) -> par=0
            for (int j = 0; j < SZ; j++) {
                if (dp[j][1] != -1) {
                    int sumv = j - OFFSET;
                    int nsum = sumv - val;
                    int nj = nsum + OFFSET;
                    if (nj >= 0 && nj < SZ) {
                        long nprod = (long) dp[j][1] * val;
                        if (nprod <= limit) {
                            newdp[nj][0] = Math.max(newdp[nj][0], (int) nprod);
                        }
                    }
                }
            }
            // start new subsequence
            if (val <= limit) {
                int nsum = val;
                int nj = nsum + OFFSET;
                if (nj >= 0 && nj < SZ) {
                    newdp[nj][1] = Math.max(newdp[nj][1], val);
                }
            }
            dp = newdp;
        }
        int target = k + OFFSET;
        int ans = -1;
        if (target >= 0 && target < SZ) {
            if (dp[target][0] != -1) {
                ans = Math.max(ans, dp[target][0]);
            }
            if (dp[target][1] != -1) {
                ans = Math.max(ans, dp[target][1]);
            }
        }
        return ans;
    }
}
# @lc code=end