#
# @lc app=leetcode id=3534 lang=java
#
# [3534] Path Existence Queries in a Graph II
#

# @lc code=start
class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        if (n == 0) return new int[0];
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        java.util.Arrays.sort(indices, (a, b) -> Integer.compare(nums[a], nums[b]));
        int[] position = new int[n];
        for (int k = 0; k < n; k++) {
            position[indices[k]] = k;
        }
        int[] comp = new int[n];
        comp[0] = 0;
        int cur_comp = 0;
        for (int k = 0; k < n - 1; k++) {
            if (nums[indices[k + 1]] - nums[indices[k]] > maxDiff) {
                cur_comp++;
            }
            comp[k + 1] = cur_comp;
        }
        int[] rght = new int[n];
        int jj = 0;
        for (int i = 0; i < n; i++) {
            while (jj < n && nums[indices[jj]] <= nums[indices[i]] + maxDiff) {
                jj++;
            }
            rght[i] = jj - 1;
        }
        final int LOG = 18;
        int[][] far = new int[LOG][n];
        for (int i = 0; i < n; i++) {
            far[0][i] = rght[i];
        }
        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                int mid = far[k - 1][i];
                far[k][i] = far[k - 1][mid];
            }
        }
        int[] answer = new int[queries.length];
        for (int qi = 0; qi < queries.length; qi++) {
            int u = queries[qi][0];
            int v = queries[qi][1];
            if (u == v) {
                answer[qi] = 0;
                continue;
            }
            int pa = position[u];
            int pb = position[v];
            if (pa > pb) {
                int tmp = pa;
                pa = pb;
                pb = tmp;
            }
            if (comp[pa] != comp[pb]) {
                answer[qi] = -1;
                continue;
            }
            int l = 1;
            int h = pb - pa;
            int res_dist = h;
            while (l <= h) {
                int md = l + (h - l) / 2;
                int cur = pa;
                for (int bk = 0; bk < LOG; bk++) {
                    if ((md & (1 << bk)) != 0) {
                        cur = far[bk][cur];
                    }
                }
                if (cur >= pb) {
                    res_dist = md;
                    h = md - 1;
                } else {
                    l = md + 1;
                }
            }
            answer[qi] = res_dist;
        }
        return answer;
    }
}
# @lc code=end