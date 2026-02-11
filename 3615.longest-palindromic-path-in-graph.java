#
# @lc app=leetcode id=3615 lang=java
#
# [3615] Longest Palindromic Path in Graph
#

# @lc code=start
class Solution {
    public int maxLen(int n, int[][] edges, String label) {
        boolean[][] graph = new boolean[n][n];
        for (int[] e : edges) {
            graph[e[0]][e[1]] = true;
            graph[e[1]][e[0]] = true;
        }
        final char[] chs = label.toCharArray();
        final int max_mask = 1 << n;
        int[] pop = new int[max_mask];
        for (int i = 0; i < max_mask; i++) {
            pop[i] = Integer.bitCount(i);
        }
        final int[] popc = pop;
        final boolean[][] fgraph = graph;
        final char[] fchs = chs;
        final int NODES = n;
        final int[] mem_temp = new int[16384 * 14 * 14];
        final int[] mem = mem_temp;

        class Helper {
            int dfs(int mask, int l, int r) {
                int idx = (mask * 14 + l) * 14 + r;
                if (mem[idx] > 0) {
                    return mem[idx];
                }
                int res = popc[mask];
                for (int nl = 0; nl < NODES; nl++) {
                    if (!fgraph[l][nl] || (mask & (1 << nl)) != 0) continue;
                    char cl = fchs[nl];
                    for (int nr = 0; nr < NODES; nr++) {
                        if (!fgraph[r][nr] || (mask & (1 << nr)) != 0 || nl == nr || fchs[nr] != cl) continue;
                        int nmask = mask | (1 << nl) | (1 << nr);
                        res = Math.max(res, dfs(nmask, nl, nr));
                    }
                }
                mem[idx] = res;
                return res;
            }
        }
        Helper helper = new Helper();
        int ans = 1;
        // odd centers
        for (int c = 0; c < n; c++) {
            ans = Math.max(ans, helper.dfs(1 << c, c, c));
        }
        // even centers
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (fgraph[i][j] && fchs[i] == fchs[j]) {
                    int msk = (1 << i) | (1 << j);
                    ans = Math.max(ans, helper.dfs(msk, i, j));
                }
            }
        }
        return ans;
    }
}
# @lc code=end