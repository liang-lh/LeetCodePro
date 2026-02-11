#
# @lc app=leetcode id=3331 lang=java
#
# [3331] Find Subtree Sizes After Changes
#

# @lc code=start
class Solution {
  public int[] findSubtreeSizes(int[] parent, String s) {
    int n = parent.length;
    java.util.List<java.lang.Integer>[] ch = new java.util.ArrayList[n];
    for (int i = 0; i < n; i++) {
      ch[i] = new java.util.ArrayList<>();
    }
    for (int i = 1; i < n; i++) {
      ch[parent[i]].add(i);
    }
    int[] np = new int[n];
    java.lang.System.arraycopy(parent, 0, np, 0, n);
    int[] lastn = new int[26];
    java.util.Arrays.fill(lastn, -1);
    class DfsState {
      int u, flag, cid, prv;
      DfsState(int u, int f, int c, int p) {
        this.u = u;
        this.flag = f;
        this.cid = c;
        this.prv = p;
      }
    }
    java.util.Stack<DfsState> stk = new java.util.Stack<>();
    stk.push(new DfsState(0, -1, 0, 0));
    while (!stk.isEmpty()) {
      DfsState cur = stk.pop();
      if (cur.flag == -1) { // enter
        char cc = s.charAt(cur.u);
        int ci = cc - 'a';
        int prvv = lastn[ci];
        if (prvv != -1) {
          np[cur.u] = prvv;
        }
        lastn[ci] = cur.u;
        stk.push(new DfsState(cur.u, -2, ci, prvv));
        for (int j = ch[cur.u].size() - 1; j >= 0; j--) {
          int v = ch[cur.u].get(j);
          stk.push(new DfsState(v, -1, 0, 0));
        }
      } else { // exit
        lastn[cur.cid] = cur.prv;
      }
    }
    java.util.List<java.lang.Integer>[] newch = new java.util.ArrayList[n];
    for (int i = 0; i < n; i++) {
      newch[i] = new java.util.ArrayList<>();
    }
    for (int i = 1; i < n; i++) {
      newch[np[i]].add(i);
    }
    int[] ans = new int[n];
    class SzState {
      int u, phase;
      SzState(int u, int p) {
        this.u = u;
        this.phase = p;
      }
    }
    java.util.Stack<SzState> stk2 = new java.util.Stack<>();
    stk2.push(new SzState(0, 0));
    while (!stk2.isEmpty()) {
      SzState cur = stk2.pop();
      if (cur.phase == 0) {
        stk2.push(new SzState(cur.u, 1));
        for (int j = newch[cur.u].size() - 1; j >= 0; j--) {
          stk2.push(new SzState(newch[cur.u].get(j), 0));
        }
      } else {
        ans[cur.u] = 1;
        for (int v : newch[cur.u]) {
          ans[cur.u] += ans[v];
        }
      }
    }
    return ans;
  }
}
# @lc code=end