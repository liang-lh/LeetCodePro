#
# @lc app=leetcode id=3373 lang=java
#
# [3373] Maximize the Number of Target Nodes After Connecting Trees II
#

# @lc code=start
import java.util.*;

class Solution {
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2) {
        int n = edges1.length + 1;
        int m = edges2.length + 1;

        // Build adj1 and compute colors and counts for tree1
        List<Integer>[] adj1 = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj1[i] = new ArrayList<>();
        }
        for (int[] e : edges1) {
            int a = e[0], b = e[1];
            adj1[a].add(b);
            adj1[b].add(a);
        }
        int[] color1 = new int[n];
        boolean[] vis1 = new boolean[n];
        Queue<Integer> q1 = new LinkedList<>();
        q1.offer(0);
        vis1[0] = true;
        color1[0] = 0;
        int cnt0_1 = 1;
        int cnt1_1 = 0;
        while (!q1.isEmpty()) {
            int u = q1.poll();
            for (int v : adj1[u]) {
                if (!vis1[v]) {
                    vis1[v] = true;
                    color1[v] = 1 - color1[u];
                    if (color1[v] == 0) cnt0_1++;
                    else cnt1_1++;
                    q1.offer(v);
                }
            }
        }

        // Build adj2 and compute counts for tree2
        List<Integer>[] adj2 = new ArrayList[m];
        for (int i = 0; i < m; i++) {
            adj2[i] = new ArrayList<>();
        }
        for (int[] e : edges2) {
            int a = e[0], b = e[1];
            adj2[a].add(b);
            adj2[b].add(a);
        }
        int[] color2 = new int[m];
        boolean[] vis2 = new boolean[m];
        Queue<Integer> q2 = new LinkedList<>();
        q2.offer(0);
        vis2[0] = true;
        color2[0] = 0;
        int cnt0_2 = 1;
        int cnt1_2 = 0;
        while (!q2.isEmpty()) {
            int u = q2.poll();
            for (int v : adj2[u]) {
                if (!vis2[v]) {
                    vis2[v] = true;
                    color2[v] = 1 - color2[u];
                    if (color2[v] == 0) cnt0_2++;
                    else cnt1_2++;
                    q2.offer(v);
                }
            }
        }
        int max2 = Math.max(cnt0_2, cnt1_2);

        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (color1[i] == 0 ? cnt0_1 : cnt1_1) + max2;
        }
        return ans;
    }
}
# @lc code=end