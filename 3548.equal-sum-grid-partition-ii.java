#
# @lc app=leetcode id=3548 lang=java
#
# [3548] Equal Sum Grid Partition II
#
# @lc code=start
import java.util.*;

class Solution {
    public boolean canPartitionGrid(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        // Try horizontal cuts
        for (int cut = 0; cut < m - 1; cut++) {
            if (tryHorizontalCut(grid, cut)) {
                return true;
            }
        }
        
        // Try vertical cuts
        for (int cut = 0; cut < n - 1; cut++) {
            if (tryVerticalCut(grid, cut)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean tryHorizontalCut(int[][] grid, int cut) {
        int m = grid.length;
        int n = grid[0].length;
        
        long topSum = 0, bottomSum = 0;
        
        for (int i = 0; i <= cut; i++) {
            for (int j = 0; j < n; j++) {
                topSum += grid[i][j];
            }
        }
        
        for (int i = cut + 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                bottomSum += grid[i][j];
            }
        }
        
        if (topSum == bottomSum) {
            return true;
        }
        
        if (canDiscountFromSection(grid, 0, cut, 0, n - 1, topSum - bottomSum)) {
            return true;
        }
        
        if (canDiscountFromSection(grid, cut + 1, m - 1, 0, n - 1, bottomSum - topSum)) {
            return true;
        }
        
        return false;
    }
    
    private boolean tryVerticalCut(int[][] grid, int cut) {
        int m = grid.length;
        int n = grid[0].length;
        
        long leftSum = 0, rightSum = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <= cut; j++) {
                leftSum += grid[i][j];
            }
        }
        
        for (int i = 0; i < m; i++) {
            for (int j = cut + 1; j < n; j++) {
                rightSum += grid[i][j];
            }
        }
        
        if (leftSum == rightSum) {
            return true;
        }
        
        if (canDiscountFromSection(grid, 0, m - 1, 0, cut, leftSum - rightSum)) {
            return true;
        }
        
        if (canDiscountFromSection(grid, 0, m - 1, cut + 1, n - 1, rightSum - leftSum)) {
            return true;
        }
        
        return false;
    }
    
    private boolean canDiscountFromSection(int[][] grid, int r1, int r2, int c1, int c2, long diff) {
        for (int i = r1; i <= r2; i++) {
            for (int j = c1; j <= c2; j++) {
                if (grid[i][j] == diff) {
                    if (isConnectedAfterRemoval(grid, r1, r2, c1, c2, i, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean isConnectedAfterRemoval(int[][] grid, int r1, int r2, int c1, int c2, int removeR, int removeC) {
        int totalCells = (r2 - r1 + 1) * (c2 - c1 + 1) - 1;
        
        if (totalCells == 0) {
            return true;
        }
        
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<int[]> queue = new LinkedList<>();
        
        boolean found = false;
        for (int i = r1; i <= r2 && !found; i++) {
            for (int j = c1; j <= c2 && !found; j++) {
                if (i != removeR || j != removeC) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                    found = true;
                }
            }
        }
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int count = 0;
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            count++;
            
            for (int[] dir : dirs) {
                int ni = curr[0] + dir[0];
                int nj = curr[1] + dir[1];
                
                if (ni >= r1 && ni <= r2 && nj >= c1 && nj <= c2 && 
                    (ni != removeR || nj != removeC) && !visited[ni][nj]) {
                    visited[ni][nj] = true;
                    queue.offer(new int[]{ni, nj});
                }
            }
        }
        
        return count == totalCells;
    }
}
# @lc code=end