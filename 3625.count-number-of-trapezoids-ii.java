#
# @lc app=leetcode id=3625 lang=java
#
# [3625] Count Number of Trapezoids II
#
# @lc code=start
class Solution {
    public int countTrapezoids(int[][] points) {
        int n = points.length;
        int count = 0;
        
        // Enumerate all combinations of 4 points
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        if (formsTrapezoid(points[i], points[j], points[k], points[l])) {
                            count++;
                        }
                    }
                }
            }
        }
        
        return count;
    }
    
    private boolean formsTrapezoid(int[] p1, int[] p2, int[] p3, int[] p4) {
        int[][] pts = {p1, p2, p3, p4};
        
        // Try all 3 ways to form a quadrilateral from 4 points
        return isTrapezoidConfig(pts, 0, 1, 2, 3) ||
               isTrapezoidConfig(pts, 0, 1, 3, 2) ||
               isTrapezoidConfig(pts, 0, 2, 1, 3);
    }
    
    private boolean isTrapezoidConfig(int[][] pts, int a, int b, int c, int d) {
        // Check quadrilateral a-b-c-d
        // Sides: a-b, b-c, c-d, d-a
        // Opposite sides: (a-b, c-d) and (b-c, d-a)
        
        if (!isConvexQuad(pts[a], pts[b], pts[c], pts[d])) {
            return false;
        }
        
        boolean parallel1 = isParallel(pts[a], pts[b], pts[c], pts[d]);
        boolean parallel2 = isParallel(pts[b], pts[c], pts[d], pts[a]);
        
        return parallel1 || parallel2;
    }
    
    private boolean isConvexQuad(int[] p1, int[] p2, int[] p3, int[] p4) {
        // Check convexity using cross products
        long c1 = cross(p1, p2, p3);
        long c2 = cross(p2, p3, p4);
        long c3 = cross(p3, p4, p1);
        long c4 = cross(p4, p1, p2);
        
        return (c1 > 0 && c2 > 0 && c3 > 0 && c4 > 0) ||
               (c1 < 0 && c2 < 0 && c3 < 0 && c4 < 0);
    }
    
    private long cross(int[] o, int[] a, int[] b) {
        // Cross product of vectors (a-o) and (b-o)
        return (long)(a[0] - o[0]) * (b[1] - o[1]) - 
               (long)(a[1] - o[1]) * (b[0] - o[0]);
    }
    
    private boolean isParallel(int[] p1, int[] p2, int[] p3, int[] p4) {
        // Check if line p1-p2 is parallel to line p3-p4
        long dx1 = p2[0] - p1[0];
        long dy1 = p2[1] - p1[1];
        long dx2 = p4[0] - p3[0];
        long dy2 = p4[1] - p3[1];
        
        // Parallel if slopes are equal: dy1/dx1 == dy2/dx2
        // Which means: dx1*dy2 == dy1*dx2
        return dx1 * dy2 == dy1 * dx2;
    }
}
# @lc code=end