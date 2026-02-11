#
# @lc app=leetcode id=3310 lang=java
#
# [3310] Remove Methods From Project
#

import java.util.*;

# @lc code=start
class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] inv : invocations) {
            graph[inv[0]].add(inv[1]);
        }
        boolean[] susp = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        q.offer(k);
        susp[k] = true;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : graph[u]) {
                if (!susp[v]) {
                    susp[v] = true;
                    q.offer(v);
                }
            }
        }
        boolean canRemove = true;
        for (int[] inv : invocations) {
            if (!susp[inv[0]] && susp[inv[1]]) {
                canRemove = false;
                break;
            }
        }
        List<Integer> ans = new ArrayList<>();
        if (canRemove) {
            for (int i = 0; i < n; i++) {
                if (!susp[i]) {
                    ans.add(i);
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                ans.add(i);
            }
        }
        return ans;
    }
}
# @lc code=end