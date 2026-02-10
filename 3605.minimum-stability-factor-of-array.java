#
# @lc app=leetcode id=3605 lang=java
#
# [3605] Minimum Stability Factor of Array
#
# @lc code=start
import java.util.*;

class Solution {
    public int minStable(int[] nums, int maxC) {
        int n = nums.length;
        
        // Binary search on the answer
        int left = 0, right = n;
        
        while (left < right) {
            int mid = (left + right) / 2;
            if (canAchieve(nums, maxC, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    private boolean canAchieve(int[] nums, int maxC, int target) {
        List<int[]> intervals = findMaximalStableIntervals(nums);
        int mods = 0;
        
        for (int[] interval : intervals) {
            int len = interval[1] - interval[0] + 1;
            if (len > target) {
                mods += len / (target + 1);
            }
        }
        
        return mods <= maxC;
    }
    
    private List<int[]> findMaximalStableIntervals(int[] nums) {
        List<int[]> intervals = new ArrayList<>();
        int n = nums.length;
        int i = 0;
        
        while (i < n) {
            if (nums[i] < 2) {
                i++;
                continue;
            }
            
            int j = i;
            int gcd = nums[i];
            
            while (j + 1 < n && gcd(gcd, nums[j + 1]) >= 2) {
                j++;
                gcd = gcd(gcd, nums[j]);
            }
            
            intervals.add(new int[]{i, j});
            i = j + 1;
        }
        
        return intervals;
    }
    
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
# @lc code=end