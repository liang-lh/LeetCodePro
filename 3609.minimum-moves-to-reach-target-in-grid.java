#
# @lc app=leetcode id=3609 lang=java
#
# [3609] Minimum Moves to Reach Target in Grid
#

import java.util.*;

# @lc code=start
class Solution {
    public int minMoves(int sx, int sy, int tx, int ty) {
        if (sx == tx && sy == ty) {
            return 0;
        }
        Queue<int[]> q = new LinkedList<>();
        HashSet<Long> vis = new HashSet<>();
        long MOD = 2000000001L;
        long key = (long) sx * MOD + sy;
        vis.add(key);
        q.offer(new int[]{sx, sy, 0});
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int x = curr[0];
            int y = curr[1];
            int dist = curr[2];
            long m = Math.max((long) x, y);
            // add to x
            {
                long nx = (long) x + m;
                long ny = y;
                if (nx <= tx && ny <= ty) {
                    long k = nx * MOD + ny;
                    if (!vis.contains(k)) {
                        if (nx == tx && ny == ty) {
                            return dist + 1;
                        }
                        vis.add(k);
                        q.offer(new int[]{(int) nx, (int) ny, dist + 1});
                    }
                }
            }
            // add to y
            {
                long nx = x;
                long ny = (long) y + m;
                if (nx <= tx && ny <= ty) {
                    long k = nx * MOD + ny;
                    if (!vis.contains(k)) {
                        if (nx == tx && ny == ty) {
                            return dist + 1;
                        }
                        vis.add(k);
                        q.offer(new int[]{(int) nx, (int) ny, dist + 1});
                    }
                }
            }
        }
        return -1;
    }
}
# @lc code=end