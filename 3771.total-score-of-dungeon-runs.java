#
# @lc app=leetcode id=3771 lang=java
#
# [3771] Total Score of Dungeon Runs
#
# @lc code=start
class Solution {
    public long totalScore(int hp, int[] damage, int[] requirement) {
        int n = damage.length;
        long[] prefix = new long[n + 1];
        
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + damage[i];
        }
        
        long totalScore = 0;
        java.util.List<Long> sortedPrefixes = new java.util.ArrayList<>();
        sortedPrefixes.add(prefix[0]);
        
        for (int i = 0; i < n; i++) {
            long threshold = requirement[i] + prefix[i + 1] - hp;
            int count = countGreaterOrEqual(sortedPrefixes, threshold);
            totalScore += count;
            insertSorted(sortedPrefixes, prefix[i + 1]);
        }
        
        return totalScore;
    }
    
    private int countGreaterOrEqual(java.util.List<Long> list, long threshold) {
        int left = 0, right = list.size();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) >= threshold) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return list.size() - left;
    }
    
    private void insertSorted(java.util.List<Long> list, long value) {
        int left = 0, right = list.size();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < value) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        list.add(left, value);
    }
}
# @lc code=end