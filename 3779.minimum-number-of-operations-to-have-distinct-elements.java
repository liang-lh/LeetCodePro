#
# @lc app=leetcode id=3779 lang=java
#
# [3779] Minimum Number of Operations to Have Distinct Elements
#

# @lc code=start
class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int[] freq = new int[100001];
        int dupCnt = 0;
        for (int num : nums) {
            freq[num]++;
            if (freq[num] == 2) {
                dupCnt++;
            }
        }
        int ops = 0;
        int pos = 0;
        while (true) {
            if (pos >= n || dupCnt == 0) {
                return ops;
            }
            int removeCnt = Math.min(3, n - pos);
            for (int j = 0; j < removeCnt; j++) {
                int val = nums[pos + j];
                int before = freq[val];
                freq[val]--;
                if (before == 2) {
                    dupCnt--;
                }
            }
            pos += removeCnt;
            ops++;
        }
    }
}
# @lc code=end