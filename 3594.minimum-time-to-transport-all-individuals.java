#
# @lc app=leetcode id=3594 lang=java
#
# [3594] Minimum Time to Transport All Individuals
#

# @lc code=start
class Solution {
    private static class State implements Comparable<State> {
        double t;
        int mask, stage;
        State(double t, int mask, int stage) {
            this.t = t;
            this.mask = mask;
            this.stage = stage;
        }
        @Override
        public int compareTo(State o) {
            return Double.compare(this.t, o.t);
        }
    }

    public double minTime(int n, int k, int m, int[] time, double[] mul) {
        double INF = Double.POSITIVE_INFINITY;
        int N = 1 << n;
        double[][] dp = new double[N][m];
        for (int i = 0; i < N; i++) {
            java.util.Arrays.fill(dp[i], INF);
        }
        dp[0][0] = 0.0;
        java.util.PriorityQueue<State> pq = new java.util.PriorityQueue<>();
        pq.offer(new State(0.0, 0, 0));
        double ans = INF;
        int full = (1 << n) - 1;
        java.util.List<Integer> groups = new java.util.ArrayList<>();
        int[] gmax = new int[N];
        for (int mask = 1; mask < N; mask++) {
            if (Integer.bitCount(mask) > k) continue;
            int mx = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    mx = Math.max(mx, time[i]);
                }
            }
            groups.add(mask);
            gmax[mask] = mx;
        }
        while (!pq.isEmpty()) {
            State cur = pq.poll();
            double ct = cur.t;
            int cmask = cur.mask;
            int cstage = cur.stage;
            if (ct > dp[cmask][cstage]) continue;
            int smask = full ^ cmask;
            for (int sub : groups) {
                if ((sub & smask) != sub) continue;
                int maxt = gmax[sub];
                double d = maxt * mul[cstage];
                int adv = (int) d % m;
                int nstage = (cstage + adv) % m;
                int nmask = cmask | sub;
                double nt_fwd = ct + d;
                int cleft = Integer.bitCount(full ^ nmask);
                if (cleft == 0) {
                    ans = Math.min(ans, nt_fwd);
                    continue;
                }
                for (int r = 0; r < n; r++) {
                    if ((nmask & (1 << r)) == 0) continue;
                    double rt = time[r] * mul[nstage];
                    int adv2 = (int) rt % m;
                    int nstage2 = (nstage + adv2) % m;
                    int nmask2 = nmask ^ (1 << r);
                    double nt = nt_fwd + rt;
                    if (nt < dp[nmask2][nstage2]) {
                        dp[nmask2][nstage2] = nt;
                        pq.offer(new State(nt, nmask2, nstage2));
                    }
                }
            }
        }
        return ans == INF ? -1.0 : ans;
    }
}
# @lc code=end