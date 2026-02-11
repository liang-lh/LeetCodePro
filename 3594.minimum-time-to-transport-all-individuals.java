#
# @lc app=leetcode id=3594 lang=java
#
# [3594] Minimum Time to Transport All Individuals
#

# @lc code=start
class Solution {
    private static class State implements Comparable<State> {
        double time;
        int mask, stage, boat;
        State(double time, int mask, int stage, int boat) {
            this.time = time;
            this.mask = mask;
            this.stage = stage;
            this.boat = boat;
        }
        @Override
        public int compareTo(State other) {
            return Double.compare(this.time, other.time);
        }
    }

    public double minTime(int n, int k, int m, int[] time, double[] mul) {
        final double INF = 1e100;
        int maxmask = 1 << n;
        double[] max_time = new double[maxmask];
        for (int mk = 0; mk < maxmask; ++mk) {
            double mx = 0.0;
            for (int i = 0; i < n; ++i) {
                if ((mk & (1 << i)) != 0) {
                    mx = Math.max(mx, (double)time[i]);
                }
            }
            max_time[mk] = mx;
        }
        double[][][] dist = new double[maxmask][m][2];
        for (int i = 0; i < maxmask; ++i) {
            for (int j = 0; j < m; ++j) {
                dist[i][j][0] = INF;
                dist[i][j][1] = INF;
            }
        }
        java.util.PriorityQueue<State> pq = new java.util.PriorityQueue<>();
        int full = (1 << n) - 1;
        dist[full][0][0] = 0.0;
        pq.offer(new State(0.0, full, 0, 0));
        while (!pq.isEmpty()) {
            State curr = pq.poll();
            if (curr.time > dist[curr.mask][curr.stage][curr.boat]) {
                continue;
            }
            int mask = curr.mask;
            int stage = curr.stage;
            int boat = curr.boat;
            double ctime = curr.time;
            if (boat == 0) {
                for (int sub = mask; sub > 0; sub = (sub - 1) & mask) {
                    if (Integer.bitCount(sub) > k) {
                        continue;
                    }
                    double maxt = max_time[sub];
                    double cost = maxt * mul[stage];
                    int adv = (int) Math.floor(cost) % m;
                    int nstage = (stage + adv) % m;
                    int nmask = mask ^ sub;
                    double ntime = ctime + cost;
                    if (ntime < dist[nmask][nstage][1]) {
                        dist[nmask][nstage][1] = ntime;
                        pq.offer(new State(ntime, nmask, nstage, 1));
                    }
                }
            } else {
                for (int r = 0; r < n; ++r) {
                    if ((mask & (1 << r)) == 0) {
                        double cost = (double) time[r] * mul[stage];
                        int adv = (int) Math.floor(cost) % m;
                        int nstage = (stage + adv) % m;
                        int nmask = mask | (1 << r);
                        double ntime = ctime + cost;
                        if (ntime < dist[nmask][nstage][0]) {
                            dist[nmask][nstage][0] = ntime;
                            pq.offer(new State(ntime, nmask, nstage, 0));
                        }
                    }
                }
            }
        }
        double ans = INF;
        for (int j = 0; j < m; ++j) {
            ans = Math.min(ans, dist[0][j][1]);
        }
        return ans < INF / 2 ? ans : -1.0;
    }
}
# @lc code=end