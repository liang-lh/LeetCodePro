#
# @lc app=leetcode id=3562 lang=java
#
# [3562] Maximum Profit from Trading Stocks with Discounts
#

# @lc code=start
class Solution {
    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
        java.util.List<java.util.List<Integer>> children = new java.util.ArrayList<java.util.List<Integer>>(n);
        for (int i = 0; i < n; i++) {
            children.add(new java.util.ArrayList<Integer>());
        }
        for (int[] h : hierarchy) {
            children.get(h[0] - 1).add(h[1] - 1);
        }
        int B = budget;
        final int NEG_INF = -1000000;
        int[][][] memo = new int[n][2][B + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                java.util.Arrays.fill(memo[i][j], NEG_INF);
            }
        }
        dfs(0, present, future, children, memo, B, NEG_INF);
        int ans = 0;
        for (int s = 0; s <= B; s++) {
            if (memo[0][0][s] > ans) {
                ans = memo[0][0][s];
            }
        }
        return ans;
    }

    private void dfs(int u, int[] present, int[] future, java.util.List<java.util.List<Integer>> children, int[][][] memo, int B, int NEG_INF) {
        for (int v : children.get(u)) {
            dfs(v, present, future, children, memo, B, NEG_INF);
        }
        // Compute child_no knapsack (no discount for children)
        int[] child_no = new int[B + 1];
        java.util.Arrays.fill(child_no, NEG_INF);
        child_no[0] = 0;
        for (int v : children.get(u)) {
            int[] dpv = memo[v][0];
            int[] temp = new int[B + 1];
            java.util.Arrays.fill(temp, NEG_INF);
            for (int s = 0; s <= B; s++) {
                if (child_no[s] == NEG_INF) continue;
                for (int t = 0; t <= B - s; t++) {
                    if (dpv[t] != NEG_INF) {
                        temp[s + t] = Math.max(temp[s + t], child_no[s] + dpv[t]);
                    }
                }
            }
            child_no = temp;
        }
        // Compute child_yes knapsack (discount for children)
        int[] child_yes = new int[B + 1];
        java.util.Arrays.fill(child_yes, NEG_INF);
        child_yes[0] = 0;
        for (int v : children.get(u)) {
            int[] dpv = memo[v][1];
            int[] temp = new int[B + 1];
            java.util.Arrays.fill(temp, NEG_INF);
            for (int s = 0; s <= B; s++) {
                if (child_yes[s] == NEG_INF) continue;
                for (int t = 0; t <= B - s; t++) {
                    if (dpv[t] != NEG_INF) {
                        temp[s + t] = Math.max(temp[s + t], child_yes[s] + dpv[t]);
                    }
                }
            }
            child_yes = temp;
        }
        // Compute for both bp
        for (int bp = 0; bp < 2; bp++) {
            int cost_buy = (bp == 1) ? present[u] / 2 : present[u];
            int profit_buy = future[u] - cost_buy;
            int[] res = new int[B + 1];
            java.util.Arrays.fill(res, NEG_INF);
            // Case 1: u does not buy
            for (int s = 0; s <= B; s++) {
                if (child_no[s] != NEG_INF) {
                    res[s] = Math.max(res[s], child_no[s]);
                }
            }
            // Case 2: u buys
            if (cost_buy <= B) {
                for (int s = 0; s <= B - cost_buy; s++) {
                    if (child_yes[s] != NEG_INF) {
                        int tot_s = cost_buy + s;
                        int tot_p = profit_buy + child_yes[s];
                        res[tot_s] = Math.max(res[tot_s], tot_p);
                    }
                }
            }
            System.arraycopy(res, 0, memo[u][bp], 0, B + 1);
        }
    }
}
# @lc code=end