#
# @lc app=leetcode id=3604 lang=java
#
# [3604] Minimum Time to Reach Destination in Directed Graph
#
# @lc code=start
class Solution {
    public int minTime(int n, int[][] edges) {
        // Build adjacency list
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], start = edge[2], end = edge[3];
            graph.get(u).add(new int[]{v, start, end});
        }
        
        // Dijkstra's algorithm
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        pq.offer(new int[]{0, 0}); // {time, node}
        dist[0] = 0;
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int time = curr[0];
            int node = curr[1];
            
            if (node == n - 1) {
                return time;
            }
            
            // If we've already found a better path to this node, skip
            if (time > dist[node]) {
                continue;
            }
            
            // Explore all outgoing edges
            for (int[] edge : graph.get(node)) {
                int nextNode = edge[0];
                int start = edge[1];
                int end = edge[2];
                
                // Can only use edge if we can depart within [start, end]
                if (time <= end) {
                    // Earliest we can depart is max(time, start)
                    int departTime = Math.max(time, start);
                    int arriveTime = departTime + 1;
                    
                    if (arriveTime < dist[nextNode]) {
                        dist[nextNode] = arriveTime;
                        pq.offer(new int[]{arriveTime, nextNode});
                    }
                }
            }
        }
        
        return dist[n - 1] == Integer.MAX_VALUE ? -1 : dist[n - 1];
    }
}
# @lc code=end