#
# @lc app=leetcode id=3625 lang=java
#
# [3625] Count Number of Trapezoids II
#

# @lc code=start
class Solution {
    public int countTrapezoids(int[][] points) {
        int n = points.length;
        java.util.Map<Long, Integer> midCount = new java.util.HashMap<>();
        java.util.Set<String> slopeSet = new java.util.HashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // midpoint for parallelograms
                long mx = (long) points[i][0] + points[j][0];
                long my = (long) points[i][1] + points[j][1];
                long mkey = (mx + 2001L) * 100000L + (my + 2001L);
                midCount.put(mkey, midCount.getOrDefault(mkey, 0) + 1);
                // slope
                int dx0 = points[j][0] - points[i][0];
                int dy0 = points[j][1] - points[i][1];
                int absdx = Math.abs(dx0);
                int absdy = Math.abs(dy0);
                int a = absdx;
                int b = absdy;
                while (b != 0) {
                    int t = b;
                    b = a % b;
                    a = t;
                }
                int g = a;
                if (g == 0) continue; // impossible
                int dx = dx0 / g;
                int dy = dy0 / g;
                if (dx < 0 || (dx == 0 && dy < 0)) {
                    dx = -dx;
                    dy = -dy;
                }
                String skey = dx + "," + dy;
                slopeSet.add(skey);
            }
        }
        long para = 0;
        for (int cnt : midCount.values()) {
            para += (long) cnt * (cnt - 1) / 2;
        }
        long S = 0;
        for (String sk : slopeSet) {
            String[] parts = sk.split(",");
            int dx = Integer.parseInt(parts[0]);
            int dy = Integer.parseInt(parts[1]);
            java.util.Map<Long, Integer> lineCount = new java.util.HashMap<>();
            for (int k = 0; k < n; k++) {
                long lid = (long) dx * points[k][1] - (long) dy * points[k][0];
                lineCount.put(lid, lineCount.getOrDefault(lid, 0) + 1);
            }
            long sumC = 0;
            long sumCsq = 0;
            for (int ct : lineCount.values()) {
                if (ct >= 2) {
                    long c = (long) ct * (ct - 1) / 2;
                    sumC += c;
                    sumCsq += c * c;
                }
            }
            long contrib = (sumC * sumC - sumCsq) / 2;
            S += contrib;
        }
        return (int) (S - para);
    }
}
# @lc code=end