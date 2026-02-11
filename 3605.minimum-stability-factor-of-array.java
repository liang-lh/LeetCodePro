#
# @lc app=leetcode id=3605 lang=java
#
# [3605] Minimum Stability Factor of Array
#

# @lc code=start
class Solution {
    private static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public int minStable(int[] nums, int maxC) {
        int n = nums.length;
        if (n == 0) return 0;
        int[] logg = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            logg[i] = (i == 1) ? 0 : logg[i / 2] + 1;
        }
        int LOG = logg[n] + 1;
        int[][] st = new int[LOG][n];
        for (int i = 0; i < n; i++) {
            st[0][i] = nums[i];
        }
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i + (1 << j) <= n; i++) {
                st[j][i] = gcd(st[j - 1][i], st[j - 1][i + (1 << (j - 1))]);
            }
        }
        int left = 0, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int W = mid + 1;
            int changes = 0;
            int last = -1;
            boolean canDo = true;
            if (W <= n) {
                int len = W;
                int k = logg[len];
                int pw = 1 << k;
                for (int i = 0; i <= n - W; i++) {
                    int g1 = st[k][i];
                    int g2 = st[k][i + W - pw];
                    int g = gcd(g1, g2);
                    if (g > 1) {
                        if (last < i) {
                            changes++;
                            last = i + W - 1;
                            if (changes > maxC) {
                                canDo = false;
                                break;
                            }
                        }
                    }
                }
            }
            if (canDo && changes <= maxC) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
# @lc code=end