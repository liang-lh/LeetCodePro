#
# @lc app=leetcode id=3605 lang=java
#
# [3605] Minimum Stability Factor of Array
#
# @lc code=start
class Solution {
    public int minStable(int[] nums, int maxC) {
        int n = nums.length;
        
        // Binary search on the answer
        int left = 0, right = n;
        int answer = n;
        
        while (left <= right) {
            int mid = (left + right) / 2;
            if (canAchieve(nums, maxC, mid)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return answer;
    }
    
    boolean canAchieve(int[] nums, int maxC, int k) {
        int n = nums.length;
        
        if (k >= n) return true;
        
        if (k == 0) {
            int count = 0;
            for (int num : nums) {
                if (num >= 2) count++;
            }
            return count <= maxC;
        }
        
        int changes = 0;
        int i = 0;
        
        while (i < n) {
            if (nums[i] < 2) {
                i++;
                continue;
            }
            
            // Find maximal stable subarray starting at i
            int j = i;
            int gcd = nums[i];
            while (j < n && gcd >= 2) {
                if (j > i) {
                    gcd = gcd(gcd, nums[j]);
                }
                if (gcd < 2) break;
                j++;
            }
            j--;
            
            int len = j - i + 1;
            if (len > k) {
                changes += len / (k + 1);
            }
            
            i = j + 1;
        }
        
        return changes <= maxC;
    }
    
    int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
# @lc code=end