#
# @lc app=leetcode id=3538 lang=java
#
# [3538] Merge Operations for Minimum Travel Time
#
# @lc code=start
import java.util.*;

class Solution {
    int[] position;
    int[] time;
    Map<String, Integer> memo = new HashMap<>();
    
    public int minTravelTime(int l, int n, int k, int[] position, int[] time) {
        this.position = position;
        this.time = time;
        
        List<Integer> activePos = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            activePos.add(i);
        }
        
        List<Integer> activeTime = new ArrayList<>();
        for (int t : time) {
            activeTime.add(t);
        }
        
        return solve(activePos, activeTime, k);
    }
    
    String getKey(List<Integer> activePos, List<Integer> activeTime, int k) {
        return activePos.toString() + "|" + activeTime.toString() + "|" + k;
    }
    
    int solve(List<Integer> activePos, List<Integer> activeTime, int k) {
        if (k == 0) {
            return calculateCost(activePos, activeTime);
        }
        
        String key = getKey(activePos, activeTime, k);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        int minCost = Integer.MAX_VALUE;
        
        for (int i = 1; i < activePos.size() - 1; i++) {
            List<Integer> newPos = new ArrayList<>(activePos);
            List<Integer> newTime = new ArrayList<>(activeTime);
            
            newPos.remove(i);
            int combined = newTime.get(i) + newTime.get(i + 1);
            newTime.set(i + 1, combined);
            newTime.remove(i);
            
            minCost = Math.min(minCost, solve(newPos, newTime, k - 1));
        }
        
        memo.put(key, minCost);
        return minCost;
    }
    
    int calculateCost(List<Integer> activePos, List<Integer> activeTime) {
        int cost = 0;
        for (int i = 0; i < activePos.size() - 1; i++) {
            int pos1 = activePos.get(i);
            int pos2 = activePos.get(i + 1);
            int dist = position[pos2] - position[pos1];
            cost += dist * activeTime.get(i);
        }
        return cost;
    }
}
# @lc code=end