#
# @lc app=leetcode id=3585 lang=java
#
# [3585] Find Weighted Median Node in Tree
#

# @lc code=start
class Solution {
  public int[] findMedian(int n, int[][] edges, int[][] queries) {
    java.util.List<int[]>[] adj = new java.util.ArrayList[n];
    for (int i = 0; i < n; i++) {
      adj[i] = new java.util.ArrayList<>();
    }
    for (int[] edge : edges) {
      int u = edge[0], v = edge[1], w = edge[2];
      adj[u].add(new int[]{v, w});
      adj[v].add(new int[]{u, w});
    }
    // BFS to compute dist, dep, parent
    long[] dist = new long[n];
    int[] dep = new int[n];
    int[] parent = new int[n];
    java.util.Arrays.fill(parent, -1);
    boolean[] visited = new boolean[n];
    java.util.Queue<Integer> queue = new java.util.LinkedList<>();
    queue.offer(0);
    visited[0] = true;
    dist[0] = 0;
    dep[0] = 0;
    while (!queue.isEmpty()) {
      int u = queue.poll();
      for (int[] p : adj[u]) {
        int v = p[0];
        int w = p[1];
        if (!visited[v]) {
          visited[v] = true;
          parent[v] = u;
          dist[v] = dist[u] + w;
          dep[v] = dep[u] + 1;
          queue.offer(v);
        }
      }
    }
    // Binary lifting
    final int LOG = 20;
    int[][] up = new int[LOG][n];
    long[][] distup = new long[LOG][n];
    for (int i = 0; i < n; i++) {
      up[0][i] = parent[i];
      if (parent[i] != -1) {
        distup[0][i] = dist[i] - dist[parent[i]];
      }
    }
    for (int k = 1; k < LOG; k++) {
      for (int i = 0; i < n; i++) {
        up[k][i] = -1;
        distup[k][i] = 0;
        int mid = up[k - 1][i];
        if (mid != -1) {
          int p = up[k - 1][mid];
          if (p != -1) {
            up[k][i] = p;
            distup[k][i] = distup[k - 1][i] + distup[k - 1][mid];
          }
        }
      }
    }
    // Process queries
    int[] answer = new int[queries.length];
    for (int qi = 0; qi < queries.length; qi++) {
      int uu = queries[qi][0];
      int vv = queries[qi][1];
      if (uu == vv) {
        answer[qi] = uu;
        continue;
      }
      // Compute LCA
      int a = uu, b = vv;
      if (dep[a] > dep[b]) {
        int temp = a;
        a = b;
        b = temp;
      }
      int diff = dep[b] - dep[a];
      for (int k = 0; k < LOG; k++) {
        if ((diff & (1 << k)) != 0) {
          b = up[k][b];
        }
      }
      int lca;
      if (a == b) {
        lca = a;
      } else {
        for (int k = LOG - 1; k >= 0; k--) {
          if (up[k][a] != up[k][b] && up[k][a] != -1 && up[k][b] != -1) {
            a = up[k][a];
            b = up[k][b];
          }
        }
        lca = up[0][a];
      }
      long du = dist[uu] - dist[lca];
      long dv = dist[vv] - dist[lca];
      long tot = du + dv;
      long tar = (tot + 1L) / 2;
      int med;
      if (du >= tar) {
        // lift_first_ge from uu to lca
        int cur = uu;
        long cd = 0;
        for (int k = LOG - 1; k >= 0; k--) {
          int p = up[k][cur];
          if (p != -1 && dep[p] >= dep[lca] && cd + distup[k][cur] < tar) {
            cd += distup[k][cur];
            cur = p;
          }
        }
        int nxt = up[0][cur];
        if (nxt != -1 && dep[nxt] >= dep[lca] && cd + distup[0][cur] >= tar) {
          med = nxt;
        } else {
          med = lca;
        }
      } else {
        long rem = tar - du;
        long maxfv = dv - rem;
        // lift_max_le from vv to lca
        int cur = vv;
        long cd = 0;
        for (int k = LOG - 1; k >= 0; k--) {
          int p = up[k][cur];
          if (p != -1 && dep[p] >= dep[lca] && cd + distup[k][cur] <= maxfv) {
            cd += distup[k][cur];
            cur = p;
          }
        }
        med = cur;
      }
      answer[qi] = med;
    }
    return answer;
  }
}
# @lc code=end