#
# @lc app=leetcode id=3445 lang=java
#
# [3445] Maximum Difference Between Even and Odd Frequency II
#
# @lc code=start
class Solution {
    public int maxDifference(String s, int k) {
        int n = s.length();
        int maxDiff = Integer.MIN_VALUE;
        
        // Try all possible substrings of length >= k
        for (int i = 0; i < n; i++) {
            int[] freq = new int[5]; // For digits 0-4
            
            for (int j = i; j < n; j++) {
                // Add current character to frequency map
                freq[s.charAt(j) - '0']++;
                
                // Check if substring length is at least k
                if (j - i + 1 >= k) {
                    // Find max odd frequency and min even frequency
                    int maxOdd = Integer.MIN_VALUE;
                    int minEven = Integer.MAX_VALUE;
                    
                    for (int f : freq) {
                        if (f % 2 == 1) { // Odd frequency
                            maxOdd = Math.max(maxOdd, f);
                        }
                        if (f > 0 && f % 2 == 0) { // Non-zero even frequency
                            minEven = Math.min(minEven, f);
                        }
                    }
                    
                    // If we have both odd and even frequencies, calculate difference
                    if (maxOdd != Integer.MIN_VALUE && minEven != Integer.MAX_VALUE) {
                        maxDiff = Math.max(maxDiff, maxOdd - minEven);
                    }
                }
            }
        }
        
        return maxDiff;
    }
}
# @lc code=end