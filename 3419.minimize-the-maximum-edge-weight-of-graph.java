#
# @lc app=leetcode id=3419 lang=java
#
# [3419] Minimize the Maximum Edge Weight of Graph
#

# @lc code=start
class Solution {
    public int minMaxWeight(int n, int[][] edges, int threshold) {
        int maxW = 0;
        for (int[] e : edges) {
            maxW = Math.max(maxW, e[2]);
        }
        int left = 0, right = maxW, ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            List<Integer>[] revAdj = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                revAdj[i] = new ArrayList<>();
            }
            for (int[] e : edges) {
                if (e[2] <= mid) {
                    revAdj[e[1]].add(e[0]);
                }
            }
            boolean[] visited = new boolean[n];
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(0);
            visited[0] = true;
            int count = 1;
            while (!queue.isEmpty()) {
                int node = queue.poll();
                for (int nei : revAdj[node]) {
                    if (!visited[nei]) {
                        visited[nei] = true;
                        count++;
                        queue.offer(nei);
                    }
                }
            }
            if (count == n) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }
}
# @lc code=end