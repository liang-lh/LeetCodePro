import java.util.*;
#
# @lc app=leetcode id=3786 lang=java
#
# [3786] Total Sum of Interaction Cost in Tree Groups
#

# @lc code=start
class Solution {
    public long interactionCosts(int n, int[][] edges, int[] group) {
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        List<Integer>[] ch = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            ch[i] = new ArrayList<Integer>();
        }
        int[] parent = new int[n];
        boolean[] vis = new boolean[n];
        Queue<Integer> q = new LinkedList<Integer>();
        Arrays.fill(parent, -1);
        Arrays.fill(vis, false);
        q.offer(0);
        vis[0] = true;
        parent[0] = -1;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj[u]) {
                if (!vis[v]) {
                    vis[v] = true;
                    parent[v] = u;
                    ch[u].add(v);
                    q.offer(v);
                }
            }
        }
        int[] gsz = new int[21];
        for (int i = 0; i < n; i++) {
            gsz[group[i]]++;
        }
        long res = 0;
        for (int g = 1; g <= 20; g++) {
            int ts = gsz[g];
            if (ts < 2) continue;
            int[] sub = new int[n];
            for (int i = 0; i < n; i++) {
                sub[i] = (group[i] == g ? 1 : 0);
            }
            int[] remn = new int[n];
            for (int i = 0; i < n; i++) {
                remn[i] = ch[i].size();
            }
            Queue<Integer> rq = new LinkedList<Integer>();
            for (int i = 0; i < n; i++) {
                if (remn[i] == 0) rq.offer(i);
            }
            while (!rq.isEmpty()) {
                int u = rq.poll();
                if (parent[u] != -1) {
                    int p = parent[u];
                    sub[p] += sub[u];
                    if (--remn[p] == 0) {
                        rq.offer(p);
                    }
                }
            }
            for (int u = 0; u < n; u++) {
                if (parent[u] != -1) {
                    long s1 = sub[u];
                    res += s1 * (ts - s1);
                }
            }
        }
        return res;
    }
}
# @lc code=end