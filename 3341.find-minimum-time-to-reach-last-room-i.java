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
        
        // Priority queue: [time, row, col]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0, 0});
        
        // Track minimum time to reach each cell
        int[][] minTime = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(minTime[i], Integer.MAX_VALUE);
        }
        minTime[0][0] = 0;
        
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int time = curr[0];
            int row = curr[1];
            int col = curr[2];
            
            // If we reached the destination
            if (row == n - 1 && col == m - 1) {
                return time;
            }
            
            // Skip if we've already found a better path
            if (time > minTime[row][col]) {
                continue;
            }
            
            // Try all 4 directions
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < m) {
                    // Calculate arrival time: wait for room to open + 1 second to move
                    int arrivalTime = Math.max(time, moveTime[newRow][newCol]) + 1;
                    
                    // Only process if this is a better path
                    if (arrivalTime < minTime[newRow][newCol]) {
                        minTime[newRow][newCol] = arrivalTime;
                        pq.offer(new int[]{arrivalTime, newRow, newCol});
                    }
                }
            }
        }
        
        return -1; // Should never reach here given the constraints
    }
}
# @lc code=end