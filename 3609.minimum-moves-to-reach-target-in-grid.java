#
# @lc app=leetcode id=3609 lang=java
#
# [3609] Minimum Moves to Reach Target in Grid
#

# @lc code=start
import java.util.*;

class Solution {
    public int minMoves(int sx, int sy, int tx, int ty) {
        if (sx == tx && sy == ty) {
            return 0;
        }
        long SX = sx;
        long SY = sy;
        long TX = tx;
        long TY = ty;
        Set<Long> visited = new HashSet<>();
        Queue<long[]> queue = new LinkedList<>();
        long initKey = (TX << 32) | TY;
        visited.add(initKey);
        queue.offer(new long[]{TX, TY, 0L});
        while (!queue.isEmpty()) {
            long[] curr = queue.poll();
            long x = curr[0];
            long y = curr[1];
            long dist = curr[2];
            List<long[]> prevs = new ArrayList<>();
            // Undo add to x
            if (x >= y && x < 2L * y) {
                prevs.add(new long[]{x - y, y});
            }
            if (x % 2 == 0) {
                long half = x / 2;
                if (half >= y) {
                    prevs.add(new long[]{half, y});
                }
            }
            // Undo add to y
            if (y >= x && y < 2L * x) {
                prevs.add(new long[]{x, y - x});
            }
            if (y % 2 == 0) {
                long half = y / 2;
                if (half >= x) {
                    prevs.add(new long[]{x, half});
                }
            }
            for (long[] p : prevs) {
                long px = p[0];
                long py = p[1];
                long key = (px << 32) | py;
                if (visited.contains(key)) {
                    continue;
                }
                if (px == SX && py == SY) {
                    return (int)(dist + 1);
                }
                visited.add(key);
                queue.offer(new long[]{px, py, dist + 1});
            }
        }
        return -1;
    }
}
# @lc code=end