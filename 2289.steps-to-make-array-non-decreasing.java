#
# @lc app=leetcode id=2289 lang=java
#
# [2289] Steps to Make Array Non-decreasing
#

import java.util.*;

# @lc code=start
class Solution {
    public int totalSteps(int[] nums) {
        Deque<int[]> stack = new ArrayDeque<>();
        int ans = 0;
        for (int num : nums) {
            int cur = 0;
            while (!stack.isEmpty() && num >= stack.peek()[1]) {
                cur = Math.max(cur, stack.pop()[0]);
            }
            if (!stack.isEmpty()) {
                cur++;
            }
            ans = Math.max(ans, cur);
            stack.push(new int[]{cur, num});
        }
        return ans;
    }
}
# @lc code=end