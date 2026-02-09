#
# @lc app=leetcode id=3458 lang=java
#
# [3458] Select K Disjoint Special Substrings
#
# @lc code=start
import java.util.*;

class Solution {
    public boolean maxSubstringLength(String s, int k) {
        int n = s.length();
        if (k == 0) return true;
        
        // Find first and last occurrence of each character
        int[] first = new int[26];
        int[] last = new int[26];
        Arrays.fill(first, n);
        Arrays.fill(last, -1);
        
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            first[c] = Math.min(first[c], i);
            last[c] = i;
        }
        
        // Find all special substrings
        List<int[]> intervals = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            int minFirst = i, maxLast = i;
            boolean canExtend = true;
            
            for (int j = i; j < n && canExtend; j++) {
                int c = s.charAt(j) - 'a';
                minFirst = Math.min(minFirst, first[c]);
                maxLast = Math.max(maxLast, last[c]);
                
                // Check if [i, j] is special
                if (minFirst == i && maxLast == j && (i != 0 || j != n - 1)) {
                    intervals.add(new int[]{i, j});
                }
                
                // If minFirst < i, can't form special substring starting from i
                if (minFirst < i) {
                    canExtend = false;
                }
            }
        }
        
        // Find maximum number of non-overlapping intervals using greedy algorithm
        intervals.sort((a, b) -> Integer.compare(a[1], b[1]));
        
        int count = 0;
        int lastEnd = -1;
        
        for (int[] interval : intervals) {
            if (interval[0] > lastEnd) {
                count++;
                lastEnd = interval[1];
            }
        }
        
        return count >= k;
    }
}
# @lc code=end