#
# @lc app=leetcode id=3630 lang=java
#
# [3630] Partition Array for Maximum XOR and AND
#
# @lc code=start
class Solution {
    public long maximizeXorAndXor(int[] nums) {
        int n = nums.length;
        long maxValue = 0;
        
        // Total number of partitions: 3^n
        int totalPartitions = (int) Math.pow(3, n);
        
        for (int partition = 0; partition < totalPartitions; partition++) {
            int xorA = 0;
            int andB = 0;
            int xorC = 0;
            boolean hasBElements = false;
            int tempAndB = -1; // All bits set initially
            
            int temp = partition;
            for (int i = 0; i < n; i++) {
                int assignment = temp % 3;
                temp /= 3;
                
                if (assignment == 0) { // Assign to A
                    xorA ^= nums[i];
                } else if (assignment == 1) { // Assign to B
                    if (!hasBElements) {
                        tempAndB = nums[i];
                        hasBElements = true;
                    } else {
                        tempAndB &= nums[i];
                    }
                } else { // Assign to C (assignment == 2)
                    xorC ^= nums[i];
                }
            }
            
            if (hasBElements) {
                andB = tempAndB;
            }
            
            long value = (long)xorA + (long)andB + (long)xorC;
            maxValue = Math.max(maxValue, value);
        }
        
        return maxValue;
    }
}
# @lc code=end