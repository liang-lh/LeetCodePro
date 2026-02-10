#
# @lc app=leetcode id=3710 lang=java
#
# [3710] Maximum Partition Factor
#
# @lc code=start
import java.util.*;

class Solution {
    public int maxPartitionFactor(int[][] points) {
        int n = points.length;
        
        // Special case
        if (n == 2) return 0;
        
        // Collect all unique distances
        Set<Integer> distSet = new TreeSet<>();
        distSet.add(0);
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int dist = manhattanDist(points[i], points[j]);
                distSet.add(dist);
            }
        }
        
        List<Integer> distances = new ArrayList<>(distSet);
        
        // Binary search
        int answer = 0;
        int left = 0, right = distances.size() - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int k = distances.get(mid);
            
            if (canPartition(points, k)) {
                answer = k;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return answer;
    }
    
    private int manhattanDist(int[] p1, int[] p2) {
        return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
    }
    
    private boolean canPartition(int[][] points, int k) {
        int n = points.length;
        
        // Build graph: edge if distance < k
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (manhattanDist(points[i], points[j]) < k) {
                    adj.get(i).add(j);
                    adj.get(j).add(i);
                }
            }
        }
        
        // Check bipartiteness
        int[] color = new int[n];
        Arrays.fill(color, -1);
        
        for (int start = 0; start < n; start++) {
            if (color[start] == -1) {
                if (!bfs(start, adj, color)) {
                    return false;
                }
            }
        }
        
        // Check both groups non-empty
        boolean has0 = false, has1 = false;
        for (int c : color) {
            if (c == 0) has0 = true;
            if (c == 1) has1 = true;
        }
        
        return has0 && has1;
    }
    
    private boolean bfs(int start, List<List<Integer>> adj, int[] color) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        color[start] = 0;
        
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj.get(u)) {
                if (color[v] == -1) {
                    color[v] = 1 - color[u];
                    q.offer(v);
                } else if (color[v] == color[u]) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
# @lc code=end