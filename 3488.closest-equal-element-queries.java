#
# @lc app=leetcode id=3488 lang=java
#
# [3488] Closest Equal Element Queries
#
# @lc code=start
class Solution {
    public List<Integer> solveQueries(int[] nums, int[] queries) {
        // Build a map from value to list of indices
        Map<Integer, List<Integer>> valueToIndices = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            valueToIndices.putIfAbsent(nums[i], new ArrayList<>());
            valueToIndices.get(nums[i]).add(i);
        }
        
        List<Integer> result = new ArrayList<>();
        int n = nums.length;
        
        for (int queryIdx : queries) {
            int value = nums[queryIdx];
            List<Integer> indices = valueToIndices.get(value);
            
            // If only one occurrence (the query index itself)
            if (indices.size() == 1) {
                result.add(-1);
                continue;
            }
            
            // Find minimum circular distance
            int minDist = Integer.MAX_VALUE;
            for (int idx : indices) {
                if (idx == queryIdx) continue;
                
                // Calculate circular distance
                int dist1 = (idx - queryIdx + n) % n;
                int dist2 = (queryIdx - idx + n) % n;
                int circularDist = Math.min(dist1, dist2);
                
                minDist = Math.min(minDist, circularDist);
            }
            
            result.add(minDist);
        }
        
        return result;
    }
}
# @lc code=end