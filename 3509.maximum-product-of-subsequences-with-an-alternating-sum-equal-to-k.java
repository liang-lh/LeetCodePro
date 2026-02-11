#
# @lc app=leetcode id=3509 lang=java
#
# [3509] Maximum Product of Subsequences With an Alternating Sum Equal to K
#

# @lc code=start
class Solution {
    public int maxProduct(int[] nums, int k, int limit) {
        final int OFFSET = 1800;
        final int MAXS = 3600;
        int[][] prev = new int[MAXS + 1][2];
        int[][] curr = new int[MAXS + 1][2];
        // Initialize prev to -1
        for (int si = 0; si <= MAXS; si++) {
            prev[si][0] = -1;
            prev[si][1] = -1;
        }
        for (int i = 0; i < nums.length; i++) {
            int val = nums[i];
            // Reset curr to -1
            for (int si = 0; si <= MAXS; si++) {
                curr[si][0] = -1;
                curr[si][1] = -1;
            }
            // Skip: carry over prev states
            for (int si = 0; si <= MAXS; si++) {
                for (int p = 0; p < 2; p++) {
                    if (prev[si][p] != -1) {
                        curr[si][p] = Math.max(curr[si][p], prev[si][p]);
                    }
                }
            }
            // Start new subsequence with val
            if (val <= limit) {
                int nsi = OFFSET + val;
                if (nsi >= 0 && nsi <= MAXS) {
                    curr[nsi][1] = Math.max(curr[nsi][1], val);
                }
            }
            // Append val to prev states
            for (int si = 0; si <= MAXS; si++) {
                for (int p = 0; p < 2; p++) {
                    if (prev[si][p] == -1) continue;
                    long nprod = (long) prev[si][p] * val;
                    if (nprod > limit) continue;
                    int delta = (p == 0 ? val : -val);
                    int newsi = si + delta;
                    if (newsi < 0 || newsi > MAXS) continue;
                    int np = 1 - p;
                    curr[newsi][np] = Math.max(curr[newsi][np], (int) nprod);
                }
            }
            // Swap: copy curr to prev
            for (int si = 0; si <= MAXS; si++) {
                for (int p = 0; p < 2; p++) {
                    prev[si][p] = curr[si][p];
                }
            }
        }
        // Find max at target sum
        int target = OFFSET + k;
        int ans = -1;
        if (target >= 0 && target <= MAXS) {
            if (prev[target][0] != -1) {
                ans = Math.max(ans, prev[target][0]);
            }
            if (prev[target][1] != -1) {
                ans = Math.max(ans, prev[target][1]);
            }
        }
        return ans;
    }
}
# @lc code=end