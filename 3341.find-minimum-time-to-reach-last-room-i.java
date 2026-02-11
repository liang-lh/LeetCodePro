#
# @lc app=leetcode id=3341 lang=java
#
# [3341] Find Minimum Time to Reach Last Room I
#

# @lc code=start
import java.util.*;
class Solution {
    public int minTimeToReach(int[][] moveTime) {
        int n = moveTime.length;
        int m = moveTime[0].length;
        long[][] dist = new long[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dist[i][j] = Long.MAX_VALUE / 2;
            }
        }
        dist[0][0] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, 0, 0});
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            long dtime = curr[0];
            int x = (int) curr[1];
            int y = (int) curr[2];
            if (dtime > dist[x][y]) continue;
            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < m) {
                    long depart = Math.max(dtime, (long) moveTime[nx][ny]);
                    long ndist = depart + 1;
                    if (ndist < dist[nx][ny]) {
                        dist[nx][ny] = ndist;
                        pq.offer(new long[]{ndist, nx, ny});
                    }
                }
            }
        }
        return (int) dist[n - 1][m - 1];
    }
}
# @lc code=end