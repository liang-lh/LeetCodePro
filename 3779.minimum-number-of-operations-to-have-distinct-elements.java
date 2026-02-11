#
# @lc app=leetcode id=3779 lang=java
#
# [3779] Minimum Number of Operations to Have Distinct Elements
#

# @lc code=start
import java.util.*;

class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        Map<Integer, List<Integer>> posMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            posMap.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }
        int maxStart = 0;
        for (List<Integer> positions : posMap.values()) {
            if (positions.size() >= 2) {
                maxStart = Math.max(maxStart, positions.get(positions.size() - 2) + 1);
            }
        }
        return (maxStart + 2) / 3;
    }
}
# @lc code=end