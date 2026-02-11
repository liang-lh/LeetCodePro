#
# @lc app=leetcode id=3515 lang=java
#
# [3515] Shortest Path in a Weighted Tree
#

# @lc code=start
class Solution {
    static class FenwickTree {
        private long[] tree;
        private int n;
        
        FenwickTree(int nn) {
            n = nn;
            tree = new long[nn + 2];
        }
        
        void update(int idx, long delta) {
            while (idx <= n) {
                tree[idx] += delta;
                idx += idx & -idx;
            }
        }
        
        long query(int idx) {
            long sum = 0;
            while (idx > 0) {
                sum += tree[idx];
                idx -= idx & -idx;
            }
            return sum;
        }
    }
    
    public int[] treeQueries(int n, int[][] edges, int[][] queries) {
        java.util.List<int[]>[] adj = new java.util.ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new java.util.ArrayList<int[]>();
        }
        for (int[] e : edges) {
            adj[e[0]].add(new int[]{e[1], e[2]});
            adj[e[1]].add(new int[]{e[0], e[2]});
        }
        
        int[] dfn = new int[n + 1];
        int[] sz = new int[n + 1];
        int[] parent = new int[n + 1];
        int[] val = new int[n + 1];
        java.util.List<Integer>[] children = new java.util.ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            children[i] = new java.util.ArrayList<Integer>();
        }
        
        // BFS to compute parent, val, children
        java.util.Queue<Integer> q = new java.util.LinkedList<Integer>();
        parent[1] = 0;
        val[1] = 0;
        q.offer(1);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int[] ne : adj[u]) {
                int v = ne[0];
                int w = ne[1];
                if (v == parent[u]) continue;
                parent[v] = u;
                val[v] = w;
                children[u].add(v);
                q.offer(v);
            }
        }
        
        // Iterative sz bottom-up
        int[] remaining = new int[n + 1];
        java.util.Queue<Integer> qq = new java.util.LinkedList<Integer>();
        for (int u = 1; u <= n; u++) {
            sz[u] = 1;
            remaining[u] = children[u].size();
            if (remaining[u] == 0) {
                qq.offer(u);
            }
        }
        while (!qq.isEmpty()) {
            int u = qq.poll();
            int p = parent[u];
            if (p != 0) {
                sz[p] += sz[u];
                remaining[p]--;
                if (remaining[p] == 0) {
                    qq.offer(p);
                }
            }
        }
        
        // Iterative preorder dfn
        int timer = 0;
        java.util.Stack<Integer> st = new java.util.Stack<Integer>();
        st.push(1);
        while (!st.isEmpty()) {
            int u = st.pop();
            dfn[u] = ++timer;
            for (int i = children[u].size() - 1; i >= 0; i--) {
                st.push(children[u].get(i));
            }
        }
        
        FenwickTree ft = new FenwickTree(n + 1);
        for (int u = 2; u <= n; u++) {
            long v = val[u];
            ft.update(dfn[u], v);
            ft.update(dfn[u] + sz[u], -v);
        }
        
        int type2cnt = 0;
        for (int[] qu : queries) {
            if (qu[0] == 2) type2cnt++;
        }
        int[] answer = new int[type2cnt];
        int idx = 0;
        
        for (int[] qu : queries) {
            if (qu[0] == 1) {
                int u = qu[1], v = qu[2], w = qu[3];
                int c = (parent[u] == v ? u : v);
                long delta = (long) w - val[c];
                val[c] = w;
                ft.update(dfn[c], delta);
                ft.update(dfn[c] + sz[c], -delta);
            } else {
                int x = qu[1];
                answer[idx++] = (int) ft.query(dfn[x]);
            }
        }
        return answer;
    }
}
# @lc code=end