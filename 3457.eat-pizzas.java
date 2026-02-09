#
# @lc app=leetcode id=3457 lang=java
#
# [3457] Eat Pizzas!
#
# @lc code=start
import java.util.Arrays;

class Solution {
    public long maxWeight(int[] pizzas) {
        int n = pizzas.length;
        Arrays.sort(pizzas); // Sort in ascending order
        
        int totalDays = n / 4;
        long totalWeight = 0;
        int left = n - 1; // Pointer to largest pizzas
        int right = 0;    // Pointer to smallest pizzas
        
        for (int day = 1; day <= totalDays; day++) {
            if (day % 2 == 1) { // Odd day - gain Z (max of 4)
                totalWeight += pizzas[left]; // The largest pizza
                left--;        // Move to next largest
                right += 3;    // Consume 3 smallest
            } else { // Even day - gain Y (2nd max of 4)
                // Consuming [left, left-1] as two largest and [right, right+1] as two smallest
                // After sorting the 4: [right, right+1, left-1, left]
                // Y = left-1 (2nd largest)
                totalWeight += pizzas[left - 1];
                left -= 2;     // Consume top 2
                right += 2;    // Consume bottom 2
            }
        }
        
        return totalWeight;
    }
}
# @lc code=end