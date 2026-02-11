#
# @lc app=leetcode id=3486 lang=java
#
# [3486] Longest Special Path II
#

# @lc code=start
import java.util.*;
class Solution {
    private int[] nums;
    private List<List<int[]>> children;
    private int[] res;

    public int[] longestSpecialPath(int[][] edges, int[] nums) {
        int n = nums.length;
        List<List<int[]>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>()) ;
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            adj.get(u).add(new int[]{v, w});
            adj.get(v).add(new int[]{u, w});
        }
        children = new ArrayList<>(n);
        for (int i = 0; i < n; i++) children.add(new ArrayList<>());
        boolean[] vis = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        vis[0] = true;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int[] pr : adj.get(u)) {
                int v = pr[0];
                if (!vis[v]) {
                    vis[v] = true;
                    children.get(u).add(pr);
                    q.offer(v);
                }
            }
        }
        this.nums = nums;
        res = new int[]{0, Integer.MAX_VALUE / 2};
        dfs(0, -1, -1, -1);
        if (res[1] == Integer.MAX_VALUE / 2) res[1] = 1;
        return res;
    }

    private static class State {
        int len = -1;
        int nodes = Integer.MAX_VALUE / 2;
        boolean valid() { return len != -1; }
        void update(int l, int nd) {
            if (l > len) {
                len = l;
                nodes = nd;
            } else if (l == len) {
                nodes = Math.min(nodes, nd);
            }
        }
    }

    private void updateGlobal(int l, int nd) {
        if (l > res[0]) {
            res[0] = l;
            res[1] = nd;
        } else if (l == res[0]) {
            res[1] = Math.min(res[1], nd);
        }
    }

    private State[][] dfs(int u, int p, int local_par, int tracked) {
        State[][] st = new State[5][3];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                st[i][j] = new State();
            }
        }
        int vu = nums[u];
        int cnt0_local = (local_par != -1 && vu == local_par) ? 1 : 0;
        int cnt0_tracked = (tracked != -1 && vu == tracked) ? 1 : 0;
        int local_k = cnt0_local;
        st[local_k][cnt0_tracked].update(0, 1);
        updateGlobal(0, 1);
        for (int[] pr : children.get(u)) {
            int v = pr[0];
            int w = pr[1];
            State[][] ch = dfs(v, u, vu, local_par);
            for (int lk = 0; lk < 5; lk++) {
                for (int tk = 0; tk < 3; tk++) {
                    if (!ch[lk][tk].valid()) continue;
                    if (!isCompatible(lk)) continue;
                    int newl = ch[lk][tk].len + w;
                    int newn = ch[lk][tk].nodes + 1;
                    updateGlobal(newl, newn);
                    int cnt_par_child = tk;
                    int new_cnt_par = cnt0_local + cnt_par_child;
                    if (new_cnt_par > 2) continue;
                    int cnt_ch_local = getCntLocal(lk);
                    boolean intro = (cnt_ch_local == 1);
                    boolean ch_dupe = (lk == 3);
                    boolean has_dupe = intro || ch_dupe;
                    int newk;
                    if (!has_dupe) {
                        newk = new_cnt_par;
                    } else {
                        if (intro) {
                            newk = (vu == local_par ? 2 : 3);
                        } else {
                            newk = 3;
                        }
                        if (new_cnt_par == 1) newk = 4;
                    }
                    int new_cnt_tracked = cnt0_tracked + tk;
                    if (new_cnt_tracked > 2) continue;
                    st[newk][new_cnt_tracked].update(newl, newn);
                }
            }
        }
        return st;
    }

    private boolean isCompatible(int lk) {
        return lk == 0 || lk == 1 || lk == 3;
    }

    private int getCntLocal(int lk) {
        if (lk == 2) return 2;
        if (lk == 0 || lk == 3) return 0;
        return 1;
    }
}
# @lc code=end