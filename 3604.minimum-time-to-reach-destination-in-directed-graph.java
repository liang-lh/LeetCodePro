#
# @lc app=leetcode id=3604 lang=java
#
# [3604] Minimum Time to Reach Destination in Directed Graph
#

# @lc code=start
class Solution {
    public int minTime(int n, int[][] edges) {
        java.util.List<int[]>[] graph = new java.util.List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new java.util.ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], s = edge[2], e = edge[3];
            graph[u].add(new int[]{v, s, e});
        }
        long[] dist = new long[n];
        long INF = 1L << 60;
        java.util.Arrays.fill(dist, INF);
        dist[0] = 0;
        java.util.PriorityQueue<long[]> pq = new java.util.PriorityQueue<>((a, b) -> java.lang.Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, 0});
        while (!pq.isEmpty()) {
            long[] front = pq.poll();
            long time = front[0];
            int u = (int) front[1];
            if (time > dist[u]) continue;
            for (int[] ed : graph[u]) {
                int v = ed[0];
                int startt = ed[1];
                int endd = ed[2];
                long depart = Math.max(time, startt);
                if (depart <= endd) {
                    long ntime = depart + 1;
                    if (ntime < dist[v]) {
                        dist[v] = ntime;
                        pq.offer(new long[]{ntime, v});
                    }
                }
            }
        }
        return dist[n - 1] == INF ? -1 : (int) dist[n - 1];
    }
}
# @lc code=end