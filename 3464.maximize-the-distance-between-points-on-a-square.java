#
# @lc app=leetcode id=3464 lang=java
#
# [3464] Maximize the Distance Between Points on a Square
#

# @lc code=start
class Solution {
    public int maxDistance(int side, int[][] points, int k) {
        int left = 0;
        int right = 2 * side;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            if (canSelect(points, side, k, mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    private boolean canSelect(int[][] points, int side, int k, int D) {
        int n = points.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        final long S = side;
        final int SD = side;
        Arrays.sort(order, (i1, i2) -> {
            return Long.compare(peri(points[i1], S, SD), peri(points[i2], S, SD));
        });
        int[] selected = new int[k];
        return dfs(0, 0, order, points, D, k, selected);
    }

    private static long peri(int[] pt, long S, int side) {
        int x = pt[0];
        int y = pt[1];
        if (y == 0) {
            return x;
        } else if (x == side) {
            return S + y;
        } else if (y == side) {
            return 2 * S + (S - x);
        } else {
            return 3 * S + (S - y);
        }
    }

    private static boolean dfs(int idx, int cnt, Integer[] order, int[][] points, int D, int k, int[] selected) {
        if (cnt == k) {
            return true;
        }
        int n = order.length;
        if (idx == n || n - idx < k - cnt) {
            return false;
        }
        // try pick
        int i = order[idx];
        boolean canPick = true;
        for (int j = 0; j < cnt; j++) {
            int p = selected[j];
            long dx = Math.abs((long) points[i][0] - points[p][0]);
            long dy = Math.abs((long) points[i][1] - points[p][1]);
            if (dx + dy < D) {
                canPick = false;
                break;
            }
        }
        boolean res = false;
        if (canPick) {
            selected[cnt] = i;
            res = dfs(idx + 1, cnt + 1, order, points, D, k, selected);
        }
        if (!res) {
            res = dfs(idx + 1, cnt, order, points, D, k, selected);
        }
        return res;
    }
}
# @lc code=end