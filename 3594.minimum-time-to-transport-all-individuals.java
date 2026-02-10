#
# @lc app=leetcode id=3594 lang=java
#
# [3594] Minimum Time to Transport All Individuals
#
# @lc code=start
class Solution {
    public double minTime(int n, int k, int m, int[] time, double[] mul) {
        // Special case: if k >= n, send everyone at once
        if (k >= n) {
            int maxTime = 0;
            for (int t : time) {
                maxTime = Math.max(maxTime, t);
            }
            return maxTime * mul[0];
        }
        
        // Special case: if k = 1 and n > 1, impossible
        if (k == 1 && n > 1) {
            return -1.0;
        }
        
        // BFS with priority queue (Dijkstra-like)
        java.util.Map<String, Double> minTimeMap = new java.util.HashMap<>();
        java.util.PriorityQueue<State> pq = new java.util.PriorityQueue<>((a, b) -> Double.compare(a.totalTime, b.totalTime));
        
        // Initial state: no one at destination, stage 0, time 0
        pq.offer(new State(0, 0, 0.0));
        minTimeMap.put(getKey(0, 0), 0.0);
        
        int allMask = (1 << n) - 1;
        
        while (!pq.isEmpty()) {
            State curr = pq.poll();
            
            // If all people at destination, return time
            if (curr.atDest == allMask) {
                return curr.totalTime;
            }
            
            String key = getKey(curr.atDest, curr.stage);
            if (minTimeMap.get(key) < curr.totalTime) {
                continue;
            }
            
            // Get people at base camp
            int atBase = allMask ^ curr.atDest;
            java.util.List<Integer> baseList = new java.util.ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((atBase & (1 << i)) != 0) {
                    baseList.add(i);
                }
            }
            
            // Try all subsets of size <= k from base camp
            for (int mask = 1; mask <= (1 << baseList.size()) - 1; mask++) {
                if (Integer.bitCount(mask) > k) continue;
                
                java.util.List<Integer> group = new java.util.ArrayList<>();
                int groupMask = 0;
                for (int i = 0; i < baseList.size(); i++) {
                    if ((mask & (1 << i)) != 0) {
                        group.add(baseList.get(i));
                        groupMask |= (1 << baseList.get(i));
                    }
                }
                
                // Calculate crossing time
                int maxTime = 0;
                for (int person : group) {
                    maxTime = Math.max(maxTime, time[person]);
                }
                double crossTime = maxTime * mul[curr.stage];
                int newStage = (curr.stage + (int)Math.floor(crossTime)) % m;
                
                int newAtDest = curr.atDest | groupMask;
                
                // Check if we need someone to return
                if (newAtDest == allMask) {
                    // All people at destination
                    double newTotalTime = curr.totalTime + crossTime;
                    String newKey = getKey(newAtDest, newStage);
                    if (!minTimeMap.containsKey(newKey) || minTimeMap.get(newKey) > newTotalTime) {
                        minTimeMap.put(newKey, newTotalTime);
                        pq.offer(new State(newAtDest, newStage, newTotalTime));
                    }
                } else {
                    // Someone must return
                    java.util.List<Integer> destList = new java.util.ArrayList<>();
                    for (int i = 0; i < n; i++) {
                        if ((newAtDest & (1 << i)) != 0) {
                            destList.add(i);
                        }
                    }
                    
                    for (int returnPerson : destList) {
                        double returnTime = time[returnPerson] * mul[newStage];
                        int finalStage = (newStage + (int)Math.floor(returnTime)) % m;
                        int finalAtDest = newAtDest ^ (1 << returnPerson);
                        
                        double newTotalTime = curr.totalTime + crossTime + returnTime;
                        String newKey = getKey(finalAtDest, finalStage);
                        if (!minTimeMap.containsKey(newKey) || minTimeMap.get(newKey) > newTotalTime) {
                            minTimeMap.put(newKey, newTotalTime);
                            pq.offer(new State(finalAtDest, finalStage, newTotalTime));
                        }
                    }
                }
            }
        }
        
        return -1.0;
    }
    
    private String getKey(int atDest, int stage) {
        return atDest + "," + stage;
    }
    
    static class State {
        int atDest;  // bitmask of people at destination
        int stage;   // current stage
        double totalTime;
        
        State(int atDest, int stage, double totalTime) {
            this.atDest = atDest;
            this.stage = stage;
            this.totalTime = totalTime;
        }
    }
}
# @lc code=end