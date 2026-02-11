#
# @lc app=leetcode id=3488 lang=java
#
# [3488] Closest Equal Element Queries
#

import java.util.*;

# @lc code=start
class Solution {
    public List<Integer> solveQueries(int[] nums, int[] queries) {
        int n = nums.length;
        int[] minDist = new int[n];
        Arrays.fill(minDist, -1);
        Map<Integer, List<Integer>> mp = new HashMap<>();
        for (int i = 0; i < n; i++) {
            mp.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }
        for (Map.Entry<Integer, List<Integer>> entry : mp.entrySet()) {
            List<Integer> pos = entry.getValue();
            int k = pos.size();
            if (k < 2) continue;
            for (int i = 0; i < k; i++) {
                int p = pos.get(i);
                int lidx = (i - 1 + k) % k;
                int ridx = (i + 1) % k;
                int left = pos.get(lidx);
                int right = pos.get(ridx);
                int dl = Math.abs(p - left);
                dl = Math.min(dl, n - dl);
                int dr = Math.abs(p - right);
                dr = Math.min(dr, n - dr);
                minDist[p] = Math.min(dl, dr);
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int q : queries) {
            ans.add(minDist[q]);
        }
        return ans;
    }
}
# @lc code=end