#
# @lc app=leetcode id=3510 lang=java
#
# [3510] Minimum Pair Removal to Sort Array II
#
# @lc code=start
import java.util.ArrayList;
import java.util.List;

class Solution {
    public int minimumPairRemoval(int[] nums) {
        // Convert array to ArrayList for easier manipulation
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        
        int operations = 0;
        
        // Keep merging pairs until array becomes non-decreasing
        while (!isNonDecreasing(list)) {
            // Find the adjacent pair with minimum sum (leftmost if tied)
            int minSum = Integer.MAX_VALUE;
            int minIndex = -1;
            
            for (int i = 0; i < list.size() - 1; i++) {
                int sum = list.get(i) + list.get(i + 1);
                if (sum < minSum) {
                    minSum = sum;
                    minIndex = i;
                }
            }
            
            // Replace the pair with their sum
            list.set(minIndex, minSum);
            list.remove(minIndex + 1);
            operations++;
        }
        
        return operations;
    }
    
    // Helper function to check if array is non-decreasing
    private boolean isNonDecreasing(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < list.get(i - 1)) {
                return false;
            }
        }
        return true;
    }
}
# @lc code=end