#
# @lc app=leetcode id=2289 lang=java
#
# [2289] Steps to Make Array Non-decreasing
#
# @lc code=start
import java.util.Stack;

class Solution {
    public int totalSteps(int[] nums) {
        int n = nums.length;
        Stack<int[]> stack = new Stack<>(); // [value, steps]
        int maxSteps = 0;
        
        // Process from right to left
        for (int i = n - 1; i >= 0; i--) {
            int steps = 0;
            // Pop all smaller elements and calculate steps
            while (!stack.isEmpty() && nums[i] > stack.peek()[0]) {
                steps = Math.max(steps + 1, stack.pop()[1]);
            }
            stack.push(new int[]{nums[i], steps});
            maxSteps = Math.max(maxSteps, steps);
        }
        
        return maxSteps;
    }
}
# @lc code=end