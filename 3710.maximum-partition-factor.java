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
        int left = 0;
        int right = 400000001;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            if (check(points, mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    private boolean check(int[][] points, int d) {
        int n = points.length;
        java.util.List<java.lang.Integer>[] adj = new java.util.ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new java.util.ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long dx = Math.abs((long) points[i][0] - points[j][0]);
                long dy = Math.abs((long) points[i][1] - points[j][1]);
                if (dx + dy < d) {
                    adj[i].add(j);
                    adj[j].add(i);
                }
            }
        }
        int[] color = new int[n];
        java.util.Arrays.fill(color, 0);
        for (int i = 0; i < n; i++) {
            if (color[i] == 0) {
                if (!bfs(i, adj, color)) return false;
            }
        }
        return true;
    }

    private boolean bfs(int start, java.util.List<java.lang.Integer>[] adj, int[] color) {
        java.util.Queue<java.lang.Integer> q = new java.util.LinkedList<>();
        q.offer(start);
        color[start] = 1;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj[u]) {
                if (color[v] == 0) {
                    color[v] = 3 - color[u];
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