#
# @lc app=leetcode id=3621 lang=java
#
# [3621] Number of Integers With Popcount-Depth Equal to K I
#

# @lc code=start
class Solution {
    private static final int MAX_LOG = 60;
    private static final int MAX_POP = 64;
    private long[][][] memo;
    private int[] digits;
    private int targetCnt;

    private int getDepth(int x) {
        int d = 0;
        while (x != 1) {
            x = Integer.bitCount(x);
            d++;
        }
        return d;
    }

    private long dfs(int pos, int cnt, int tight) {
        if (pos == MAX_LOG) {
            return cnt == targetCnt ? 1L : 0L;
        }
        if (cnt > targetCnt) {
            return 0L;
        }
        if (tight == 0 && memo[pos][cnt][0] != -1L) {
            return memo[pos][cnt][0];
        }
        long res = 0L;
        int lim = tight == 1 ? digits[pos] : 1;
        for (int d = 0; d <= lim; d++) {
            int newCnt = cnt + d;
            if (newCnt > targetCnt) continue;
            int newTight = (tight == 1 && d == lim) ? 1 : 0;
            res += dfs(pos + 1, newCnt, newTight);
        }
        if (tight == 0) {
            memo[pos][cnt][0] = res;
        }
        return res;
    }

    private long countNumbers(long num, int pop) {
        if (num <= 0 || pop == 0) return 0L;
        targetCnt = pop;
        digits = new int[MAX_LOG];
        for (int i = 0; i < MAX_LOG; i++) {
            digits[i] = (int) ((num >> (MAX_LOG - 1 - i)) & 1L);
        }
        memo = new long[MAX_LOG][MAX_POP + 1][2];
        for (int i = 0; i < MAX_LOG; i++) {
            for (int j = 0; j <= MAX_POP; j++) {
                memo[i][j][0] = -1L;
                memo[i][j][1] = -1L;
            }
        }
        return dfs(0, 0, 1);
    }

    public long popcountDepth(long n, int k) {
        if (k == 0) {
            return 1L;
        }
        int[] depths = new int[MAX_POP + 1];
        for (int i = 1; i <= MAX_POP; i++) {
            depths[i] = getDepth(i);
        }
        long answer = 0L;
        for (int s = 1; s <= MAX_POP; s++) {
            if (depths[s] == k - 1) {
                answer += countNumbers(n, s);
            }
        }
        if (k == 1) {
            answer -= 1L;
        }
        return answer;
    }
}
# @lc code=end