#
# @lc app=leetcode id=3525 lang=java
#
# [3525] Find X Value of Array II
#
# @lc code=start
class Solution {
    public int[] resultArray(int[] nums, int k, int[][] queries) {
        int n = nums.length;
        int q = queries.length;
        int[] result = new int[q];
        
        for (int qi = 0; qi < q; qi++) {
            int index = queries[qi][0];
            int value = queries[qi][1];
            int start = queries[qi][2];
            int x = queries[qi][3];
            
            // Update nums (persists for future queries)
            nums[index] = value;
            
            // Count the number of ways to remove suffix such that product % k == x
            int count = 0;
            int product = 1;
            
            for (int i = start; i < n; i++) {
                product = (product * (nums[i] % k)) % k;
                if (product == x) {
                    count++;
                }
            }
            
            result[qi] = count;
        }
        
        return result;
    }
}
# @lc code=end