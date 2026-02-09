#
# @lc app=leetcode id=3459 lang=java
#
# [3459] Length of Longest V-Shaped Diagonal Segment
#
# @lc code=start
class Solution {
    public int lenOfVDiagonal(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int maxLen = 0;
        
        // Four diagonal directions: down-right, down-left, up-left, up-right
        int[][] dirs = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
        
        // For each cell with value 1, try starting a V-shaped segment
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    for (int dir = 0; dir < 4; dir++) {
                        maxLen = Math.max(maxLen, findMaxLength(grid, i, j, dir, dirs, n, m));
                    }
                }
            }
        }
        
        return maxLen;
    }
    
    private int findMaxLength(int[][] grid, int r, int c, int dir, int[][] dirs, int n, int m) {
        int maxLen = 1;
        int seqIdx = 0;
        int lenBeforeTurn = 1;
        
        while (true) {
            // Try making a clockwise turn from current position
            int newDir = (dir + 1) % 4;
            int nextR = r + dirs[newDir][0];
            int nextC = c + dirs[newDir][1];
            
            if (isValid(nextR, nextC, n, m)) {
                int expectedVal = getExpectedValue(seqIdx + 1);
                if (grid[nextR][nextC] == expectedVal) {
                    int lenAfterTurn = continueSegment(grid, nextR, nextC, seqIdx + 1, newDir, dirs, n, m);
                    maxLen = Math.max(maxLen, lenBeforeTurn + lenAfterTurn);
                }
            }
            
            // Continue in the current direction
            r += dirs[dir][0];
            c += dirs[dir][1];
            seqIdx++;
            
            if (!isValid(r, c, n, m)) break;
            
            int expectedVal = getExpectedValue(seqIdx);
            if (grid[r][c] != expectedVal) break;
            
            lenBeforeTurn++;
        }
        
        // Return max of path with turn or straight path without turn
        return Math.max(maxLen, lenBeforeTurn);
    }
    
    private int continueSegment(int[][] grid, int r, int c, int seqIdx, int dir, int[][] dirs, int n, int m) {
        int len = 1;
        
        while (true) {
            r += dirs[dir][0];
            c += dirs[dir][1];
            seqIdx++;
            
            if (!isValid(r, c, n, m)) break;
            
            int expectedVal = getExpectedValue(seqIdx);
            if (grid[r][c] != expectedVal) break;
            
            len++;
        }
        
        return len;
    }
    
    private int getExpectedValue(int seqIdx) {
        if (seqIdx == 0) return 1;
        return (seqIdx % 2 == 1) ? 2 : 0;
    }
    
    private boolean isValid(int r, int c, int n, int m) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}
# @lc code=end