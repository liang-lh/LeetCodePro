#
# @lc app=leetcode id=2289 lang=java
#
# [2289] Steps to Make Array Non-decreasing
#
# @lc code=start
class Solution {
    public int totalSteps(int[] nums) {
        int n = nums.length;
        int[] steps = new int[n];
        java.util.Deque<Integer> stack = new java.util.ArrayDeque<>();
        int maxStep = 0;
        for (int i = 0; i < n; ++i) {
            int currStep = 0;
            while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
                currStep = Math.max(currStep, steps[stack.pop()]);
            }
            if (!stack.isEmpty()) {
                steps[i] = currStep + 1;
                maxStep = Math.max(maxStep, steps[i]);
            }
            stack.push(i);
        }
        return maxStep;
    }
}
# @lc code=end