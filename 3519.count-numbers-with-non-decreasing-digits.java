#
# @lc app=leetcode id=3519 lang=java
#
# [3519] Count Numbers with Non-Decreasing Digits
#

# @lc code=start
class Solution {
    private static final int MOD = 1000000007;
    private static final int MAXL = 355;
    private static final int MAXD = 12;

    private long[][][][] dp;
    private int[][][][] visits;
    private int vers;
    private int basee;
    private java.util.List<java.lang.Integer> ddigits;
    private int leng;

    public int countNumbers(String l, String r, int b) {
        dp = new long[MAXL][MAXD][2][2];
        visits = new int[MAXL][MAXD][2][2];
        vers = 0;
        long cntR = solve(r, b);
        String lm1 = decrement(l);
        long cntL = solve(lm1, b);
        return (int) ((cntR - cntL + MOD) % MOD);
    }

    private long solve(String s, int b) {
        if ("0".equals(s)) {
            return 0L;
        }
        java.math.BigInteger num = new java.math.BigInteger(s);
        ddigits = getBaseBDigits(num, b);
        leng = ddigits.size();
        basee = b;
        vers++;
        return dfs(0, 0, 1, 1);
    }

    private long dfs(int pos, int pre, int tig, int lzero) {
        if (pos == leng) {
            return lzero == 0 ? 1L : 0L;
        }
        if (visits[pos][pre][tig][lzero] == vers) {
            return dp[pos][pre][tig][lzero];
        }
        visits[pos][pre][tig][lzero] = vers;
        dp[pos][pre][tig][lzero] = 0L;
        int up = tig == 1 ? ddigits.get(pos) : basee - 1;
        for (int d = 0; d <= up; d++) {
            int new_lz = (lzero == 1 && d == 0) ? 1 : 0;
            int new_pre;
            if (new_lz == 1) {
                new_pre = 0;
            } else {
                if (lzero == 1) {
                    new_pre = d;
                } else {
                    if (d < pre) continue;
                    new_pre = d;
                }
            }
            int new_tig = (tig == 1 && d == up) ? 1 : 0;
            dp[pos][pre][tig][lzero] = (dp[pos][pre][tig][lzero] + dfs(pos + 1, new_pre, new_tig, new_lz)) % MOD;
        }
        return dp[pos][pre][tig][lzero];
    }

    private java.util.List<java.lang.Integer> getBaseBDigits(java.math.BigInteger n, int b) {
        java.util.List<java.lang.Integer> digs = new java.util.ArrayList<java.lang.Integer>();
        if (n.equals(java.math.BigInteger.ZERO)) {
            digs.add(0);
            return digs;
        }
        java.math.BigInteger bb = java.math.BigInteger.valueOf((long) b);
        while (n.compareTo(java.math.BigInteger.ZERO) > 0) {
            digs.add(n.mod(bb).intValue());
            n = n.divide(bb);
        }
        java.util.Collections.reverse(digs);
        return digs;
    }

    private String decrement(String s) {
        return new java.math.BigInteger(s).subtract(java.math.BigInteger.ONE).toString();
    }
}
# @lc code=end