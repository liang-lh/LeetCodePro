#
# @lc app=leetcode id=3462 lang=java
#
# [3462] Maximum Sum With at Most K Elements
#

# @lc code=start
import java.util.*;

class Solution {
    public long maxSum(int[][] grid, int[] limits, int k) {
        List<Integer> candidates = new ArrayList<>();
        int n = grid.length;
        for (int i = 0; i < n; i++) {
            int len = grid[i].length;
            Integer[] row = new Integer[len];
            for (int j = 0; j < len; j++) {
                row[j] = grid[i][j];
            }
            Arrays.sort(row, (a, b) -> b.compareTo(a));
            for (int t = 0; t < limits[i]; t++) {
                candidates.add(row[t]);
            }
        }
        Collections.sort(candidates, (a, b) -> b.compareTo(a));
        long sum = 0;
        int take = Math.min(k, candidates.size());
        for (int i = 0; i < take; i++) {
            sum += candidates.get(i);
        }
        return sum;
    }
}
# @lc code=end