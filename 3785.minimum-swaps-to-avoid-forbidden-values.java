#
# @lc app=leetcode id=3785 lang=java
#
# [3785] Minimum Swaps to Avoid Forbidden Values
#

# @lc code=start
import java.util.*;

class Solution {
    public int minSwaps(int[] nums, int[] forbidden) {
        int n = nums.length;
        Map<Integer, Integer> num_freq = new HashMap<>();
        Map<Integer, Integer> forb_freq = new HashMap<>();
        Map<Integer, Integer> bad_freq = new HashMap<>();
        int num_bad = 0;
        for (int i = 0; i < n; i++) {
            num_freq.put(nums[i], num_freq.getOrDefault(nums[i], 0) + 1);
            forb_freq.put(forbidden[i], forb_freq.getOrDefault(forbidden[i], 0) + 1);
            if (nums[i] == forbidden[i]) {
                bad_freq.put(nums[i], bad_freq.getOrDefault(nums[i], 0) + 1);
                num_bad++;
            }
        }
        // Feasibility check: Hall's condition for bipartite matching
        Set<Integer> allValues = new HashSet<>(num_freq.keySet());
        allValues.addAll(forb_freq.keySet());
        for (int v : allValues) {
            int nf = num_freq.getOrDefault(v, 0);
            int ff = forb_freq.getOrDefault(v, 0);
            if (nf + ff > n) {
                return -1;
            }
        }
        if (num_bad == 0) {
            return 0;
        }
        int max_bad_freq = 0;
        int max_bad_v = 0;
        for (Map.Entry<Integer, Integer> e : bad_freq.entrySet()) {
            int f = e.getValue();
            if (f > max_bad_freq) {
                max_bad_freq = f;
                max_bad_v = e.getKey();
            }
        }
        int max_pairs = Math.min(num_bad / 2, num_bad - max_bad_freq);
        int leftover = num_bad - 2 * max_pairs;
        int ans = num_bad - max_pairs;
        if (leftover == 0) {
            return ans;
        }
        boolean possible = false;
        if (max_pairs == num_bad / 2) {
            // Small case: internal cycle cover possible
            possible = true;
        } else {
            // Large case: check suitable goods for max_bad_v
            int v = max_bad_v;
            int bf = bad_freq.get(v);
            int g_num = num_freq.getOrDefault(v, 0) - bf;
            int g_forb = forb_freq.getOrDefault(v, 0) - bf;
            int suit = (n - num_bad) - g_num - g_forb;
            if (suit >= leftover) {
                possible = true;
            }
        }
        return possible ? ans : -1;
    }
}
# @lc code=end