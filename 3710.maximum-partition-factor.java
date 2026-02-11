#
# @lc app=leetcode id=3710 lang=java
#
# [3710] Maximum Partition Factor
#

# @lc code=start
class Solution {
    public int maxPartitionFactor(int[][] points) {
        int n = points.length;
        if (n == 2) return 0;
        long maxDist = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                long dx = Math.abs((long) points[i][0] - points[j][0]);
                long dy = Math.abs((long) points[i][1] - points[j][1]);
                maxDist = Math.max(maxDist, dx + dy);
            }
        }
        int left = 0;
        int right = (int) maxDist + 1;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            if (check(points, mid, n)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    private boolean check(int[][] points, int d, int n) {
        java.util.List<java.util.List<Integer>> adj = new java.util.ArrayList<>();
        for (int i = 0; i < n; ++i) {
            adj.add(new java.util.ArrayList<>());
        }
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                long dx = Math.abs((long) points[i][0] - points[j][0]);
                long dy = Math.abs((long) points[i][1] - points[j][1]);
                if (dx + dy < d) {
                    adj.get(i).add(j);
                    adj.get(j).add(i);
                }
            }
        }
        int[] color = new int[n];
        java.util.Arrays.fill(color, -1);
        for (int i = 0; i < n; ++i) {
            if (color[i] == -1) {
                if (!bfs(i, adj, color)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean bfs(int start, java.util.List<java.util.List<Integer>> adj, int[] color) {
        java.util.Queue<Integer> q = new java.util.LinkedList<>();
        q.offer(start);
        color[start] = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj.get(u)) {
                if (color[v] == -1) {
                    color[v] = 1 - color[u];
                    q.offer(v);
                } else if (color[v] == color[u]) {
                    return false;
                }
            }
        }
        return true;
    }
}
# @lc code=end