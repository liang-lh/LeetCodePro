#
# @lc app=leetcode id=3605 lang=java
#
# [3605] Minimum Stability Factor of Array
#

# @lc code=start
class Solution {
    public int minStable(int[] nums, int maxC) {
        int n = nums.length;
        if (n == 0) return 0;
        int[] logg = new int[n + 1];
        logg[1] = 0;
        for (int i = 2; i <= n; i++) {
            logg[i] = logg[i / 2] + 1;
        }
        int LOG = logg[n] + 1;
        int[][] st = new int[LOG][n];
        java.util.function.IntBinaryOperator mygcd = (a, b) -> {
            a = Math.abs(a);
            b = Math.abs(b);
            while (b != 0) {
                int t = b;
                b = a % b;
                a = t;
            }
            return a;
        };
        for (int i = 0; i < n; i++) {
            st[0][i] = nums[i];
        }
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i + (1 << j) <= n; i++) {
                st[j][i] = mygcd.applyAsInt(st[j - 1][i], st[j - 1][i + (1 << (j - 1))]);
            }
        }
        int left = 0, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int K = mid + 1;
            boolean can = false;
            if (K > n) {
                can = true;
            } else {
                java.util.ArrayList<Integer> bad = new java.util.ArrayList<>();
                int kk = logg[K];
                int len_shift = (1 << kk);
                for (int i = 0; i <= n - K; i++) {
                    int g = mygcd.applyAsInt(st[kk][i], st[kk][i + K - len_shift]);
                    if (g >= 2) {
                        bad.add(i);
                    }
                }
                int m = bad.size();
                if (m == 0) {
                    can = true;
                } else {
                    int hits = 0;
                    int j = 0;
                    while (j < m) {
                        int pick_pos = bad.get(j) + K - 1;
                        hits++;
                        while (j < m && bad.get(j) <= pick_pos) {
                            j++;
                        }
                    }
                    can = hits <= maxC;
                }
            }
            if (can) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
# @lc code=end