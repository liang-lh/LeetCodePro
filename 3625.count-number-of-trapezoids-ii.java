#
# @lc app=leetcode id=3625 lang=java
#
# [3625] Count Number of Trapezoids II
#

# @lc code=start
class Solution {
    private static class Slope {
        int dx, dy;

        Slope(int ddx, int ddy) {
            int adx = Math.abs(ddx);
            int ady = Math.abs(ddy);
            int g = gcd(adx, ady);
            dx = ddx / g;
            dy = ddy / g;
            if (dx < 0 || (dx == 0 && dy < 0)) {
                dx = -dx;
                dy = -dy;
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Slope other = (Slope) obj;
            return dx == other.dx && dy == other.dy;
        }

        @Override
        public int hashCode() {
            return 31 * dx + dy;
        }
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    private static long getKey(int x, int y) {
        final int OFFSET = 1005;
        final long MUL = 2005L;
        return ((long) x + OFFSET) * MUL + (y + OFFSET);
    }

    public int countTrapezoids(int[][] points) {
        int n = points.length;
        java.util.Set<Slope> slopeSet = new java.util.HashSet<>();
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                int dxp = points[j][0] - points[i][0];
                int dyp = points[j][1] - points[i][1];
                slopeSet.add(new Slope(dxp, dyp));
            }
        }

        long total = 0;
        for (Slope s : slopeSet) {
            int dx = s.dx;
            int dy = s.dy;
            java.util.Map<Long, Integer> lineCnt = new java.util.HashMap<>();
            for (int p = 0; p < n; ++p) {
                long lkey = (long) dx * points[p][1] - (long) dy * points[p][0];
                lineCnt.merge(lkey, 1, Integer::sum);
            }
            long sumC = 0;
            long sumCsq = 0;
            for (int c : lineCnt.values()) {
                if (c < 2) continue;
                long cc = (long) c * (c - 1) / 2;
                sumC += cc;
                sumCsq += cc * cc;
            }
            long contrib = (sumC * sumC - sumCsq) / 2;
            total += contrib;
        }

        // Count parallelograms
        java.util.Map<Long, Integer> ptIdx = new java.util.HashMap<>();
        for (int i = 0; i < n; ++i) {
            long key = getKey(points[i][0], points[i][1]);
            ptIdx.put(key, i);
        }

        long paraTimes = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (j == i) continue;
                long vx = points[j][0] - points[i][0];
                long vy = points[j][1] - points[i][1];
                for (int k = 0; k < n; ++k) {
                    if (k == i || k == j) continue;
                    long wx = points[k][0] - points[i][0];
                    long wy = points[k][1] - points[i][1];
                    long cross = vx * wy - vy * wx;
                    if (cross == 0) continue;
                    int dx = points[j][0] + points[k][0] - points[i][0];
                    int dy_ = points[j][1] + points[k][1] - points[i][1];
                    long dkey = getKey(dx, dy_);
                    Integer lobj = ptIdx.get(dkey);
                    if (lobj != null) {
                        int l = lobj;
                        if (l != i && l != j && l != k) {
                            ++paraTimes;
                        }
                    }
                }
            }
        }
        long numPara = paraTimes / 8;

        return (int) (total - numPara);
    }
}
# @lc code=end