#
# @lc app=leetcode id=3413 lang=java
#
# [3413] Maximum Coins From K Consecutive Bags
#

# @lc code=start
class Solution {
    public long maximumCoins(int[][] coins, int k) {
        int n = coins.length;
        long K = k;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        java.util.Arrays.sort(order, (a, b) -> java.lang.Integer.compare(coins[a][0], coins[b][0]));
        // Sorted by li: posL increasing, posR non-decreasing due to non-overlap
        long[] posL = new long[n];
        long[] posR = new long[n];
        long[] col = new long[n];
        long[] pre = new long[n + 1];
        java.util.List<java.lang.Long> candidates = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            int id = order[i];
            posL[i] = coins[id][0];
            posR[i] = coins[id][1];
            col[i] = coins[id][2];
            long leng = posR[i] - posL[i] + 1L;
            long valu = col[i] * leng;
            pre[i + 1] = pre[i] + valu;
            // Candidates: window start alignments (left at li/ri+1), right alignments (end+1 at li/ri+1)
            candidates.add(posL[i]);
            candidates.add(posR[i] + 1L);
            candidates.add(posL[i] - K);
            candidates.add(posR[i] + 1L - K);
        }
        long ans = 0L;
        for (java.lang.Long stObj : candidates) {
            long st = stObj;
            long en = st + K - 1L;
            long cu = getSum(st, en, posL, posR, col, pre, n);
            if (cu > ans) {
                ans = cu;
            }
        }
        return ans;
    }

    private static long getSum(long left, long right, long[] posL, long[] posR, long[] col, long[] pre, int n) {
        if (left > right) {
            return 0L;
        }
        // leftIdx: first seg with ri >= left (leftmost potential intersect)
        int leftIdx = lowerBound(posR, left, n);
        // rightIdx: last seg with li <= right (rightmost potential intersect)
        int rightIdx = upperBound(posL, right, n) - 1;
        if (leftIdx > rightIdx || leftIdx >= n || rightIdx < 0) {
            return 0L;
        }
        long sumv = 0L;
        // Partial overlap for leftmost intersecting segment
        long ovl = java.lang.Math.max(posL[leftIdx], left);
        long ovr = java.lang.Math.min(posR[leftIdx], right);
        if (ovl <= ovr) {
            sumv += col[leftIdx] * (ovr - ovl + 1L);
        }
        if (rightIdx > leftIdx) {
            // Partial overlap for rightmost intersecting segment (if distinct)
            ovl = java.lang.Math.max(posL[rightIdx], left);
            ovr = java.lang.Math.min(posR[rightIdx], right);
            if (ovl <= ovr) {
                sumv += col[rightIdx] * (ovr - ovl + 1L);
            }
            // Full sum for interior segments [leftIdx+1, rightIdx-1] using prefix
            // Valid because interiors fully covered by non-overlap/sorted properties
            if (rightIdx >= leftIdx + 2) {
                sumv += pre[rightIdx] - pre[leftIdx + 1];
            }
        }
        return sumv;
    }

    // Standard lower_bound: smallest index where arr[mid] >= val
    private static int lowerBound(long[] arr, long val, int n) {
        int low = 0;
        int high = n;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= val) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    // Standard upper_bound: smallest index where arr[mid] > val
    private static int upperBound(long[] arr, long val, int n) {
        int low = 0;
        int high = n;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > val) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
}
# @lc code=end