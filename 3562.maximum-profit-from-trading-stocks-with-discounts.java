#
# @lc app=leetcode id=3562 lang=java
#
# [3562] Maximum Profit from Trading Stocks with Discounts
#

# @lc code=start
class Solution {
    private static final int NEG_INF = Integer.MIN_VALUE / 2;

    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
        @SuppressWarnings("unchecked")
        java.util.ArrayList<java.lang.Integer>[] children = new java.util.ArrayList[n];
        for (int i = 0; i < n; i++) {
            children[i] = new java.util.ArrayList<>();
        }
        for (int[] h : hierarchy) {
            int u = h[0] - 1;
            int v = h[1] - 1;
            children[u].add(v);
        }
        int[][][] memo = new int[n][2][budget + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                java.util.Arrays.fill(memo[i][j], -1);
            }
        }
        int[] rootDp = getDP(0, 0, children, present, future, budget, memo);
        int ans = 0;
        for (int c = 0; c <= budget; c++) {
            if (rootDp[c] != NEG_INF) {
                ans = Math.max(ans, rootDp[c]);
            }
        }
        return ans;
    }

    private int[] getDP(int u, int hasDisc, java.util.ArrayList<java.lang.Integer>[] children, int[] present, int[] future, int maxb, int[][][] memo) {
        if (memo[u][hasDisc][0] != -1) {
            int[] res = new int[maxb + 1];
            for (int c = 0; c <= maxb; c++) {
                res[c] = memo[u][hasDisc][c];
            }
            return res;
        }
        int fullCost = present[u];
        int discCost = fullCost / 2;
        int costU = hasDisc == 1 ? discCost : fullCost;
        int profitU = future[u] - costU;
        int[] childNo = getCombined(children[u], 0, children, present, future, maxb, memo);
        int[] childYes = getCombined(children[u], 1, children, present, future, maxb, memo);
        int[] res = new int[maxb + 1];
        java.util.Arrays.fill(res, NEG_INF);
        // not buy u
        for (int c = 0; c <= maxb; c++) {
            if (childNo[c] != NEG_INF) {
                res[c] = Math.max(res[c], childNo[c]);
            }
        }
        // buy u
        for (int c2 = 0; c2 <= maxb - costU; c2++) {
            if (childYes[c2] != NEG_INF) {
                int totalC = costU + c2;
                int totalP = profitU + childYes[c2];
                res[totalC] = Math.max(res[totalC], totalP);
            }
        }
        // memoize
        for (int c = 0; c <= maxb; c++) {
            memo[u][hasDisc][c] = res[c];
        }
        return res;
    }

    private int[] getCombined(java.util.ArrayList<java.lang.Integer> childs, int chDisc, java.util.ArrayList<java.lang.Integer>[] children, int[] present, int[] future, int maxb, int[][][] memo) {
        int[] curr = new int[maxb + 1];
        java.util.Arrays.fill(curr, NEG_INF);
        curr[0] = 0;
        for (int v : childs) {
            int[] childDp = getDP(v, chDisc, children, present, future, maxb, memo);
            int[] nextDp = new int[maxb + 1];
            java.util.Arrays.fill(nextDp, NEG_INF);
            for (int c1 = 0; c1 <= maxb; c1++) {
                if (curr[c1] == NEG_INF) continue;
                for (int k = 0; k <= maxb - c1; k++) {
                    if (childDp[k] == NEG_INF) continue;
                    int nc = c1 + k;
                    nextDp[nc] = Math.max(nextDp[nc], curr[c1] + childDp[k]);
                }
            }
            curr = nextDp;
        }
        return curr;
    }
}
# @lc code=end