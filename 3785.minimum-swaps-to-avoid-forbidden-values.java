#
# @lc app=leetcode id=3785 lang=java
#
# [3785] Minimum Swaps to Avoid Forbidden Values
#

# @lc code=start
class Solution {
    public int minSwaps(int[] nums, int[] forbidden) {
        int n = nums.length;
        java.util.Map<Integer, Integer> numFreq = new java.util.HashMap<>();
        for (int num : nums) {
            numFreq.put(num, numFreq.getOrDefault(num, 0) + 1);
        }
        java.util.Map<Integer, Integer> forbFreq = new java.util.HashMap<>();
        for (int f : forbidden) {
            forbFreq.put(f, forbFreq.getOrDefault(f, 0) + 1);
        }
        for (int v : forbFreq.keySet()) {
            int p = forbFreq.get(v);
            int fnum = numFreq.getOrDefault(v, 0);
            if (p > n - fnum) {
                return -1;
            }
        }
        java.util.Map<Integer, Integer> badFreq = new java.util.HashMap<>();
        int totalBad = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == forbidden[i]) {
                int v = nums[i];
                badFreq.put(v, badFreq.getOrDefault(v, 0) + 1);
                totalBad++;
            }
        }
        if (totalBad == 0) {
            return 0;
        }
        int maxBad = 0;
        for (int cnt : badFreq.values()) {
            maxBad = Math.max(maxBad, cnt);
        }
        int pairs;
        if (maxBad <= totalBad - maxBad) {
            pairs = totalBad / 2;
        } else {
            pairs = totalBad - maxBad;
        }
        int ans = totalBad - pairs;
        return ans;
    }
}
# @lc code=end