#
# @lc app=leetcode id=3493 lang=java
#
# [3493] Properties Graph
#

# @lc code=start
class Solution {
    private int find(int x, int[] parent) {
        int root = x;
        while (parent[root] != root) {
            root = parent[root];
        }
        int cur = x;
        while (cur != root) {
            int nxt = parent[cur];
            parent[cur] = root;
            cur = nxt;
        }
        return root;
    }

    public int numberOfComponents(int[][] properties, int k) {
        int n = properties.length;
        boolean[][] pres = new boolean[n][101];
        for (int i = 0; i < n; i++) {
            for (int val : properties[i]) {
                pres[i][val] = true;
            }
        }
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int common = 0;
                for (int v = 1; v <= 100; v++) {
                    if (pres[i][v] && pres[j][v]) {
                        common++;
                    }
                }
                if (common >= k) {
                    int pi = find(i, parent);
                    int pj = find(j, parent);
                    if (pi != pj) {
                        parent[pi] = pj;
                    }
                }
            }
        }
        int comp = 0;
        for (int i = 0; i < n; i++) {
            if (find(i, parent) == i) {
                comp++;
            }
        }
        return comp;
    }
}
# @lc code=end