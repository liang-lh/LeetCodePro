#
# @lc app=leetcode id=3435 lang=java
#
# [3435] Frequencies of Shortest Supersequences
#

# @lc code=start
import java.util.*;
class Solution {
  public List<List<Integer>> supersequences(String[] words) {
    Set<Character> charSet = new HashSet<>();
    for (String w : words) {
      charSet.add(w.charAt(0));
      charSet.add(w.charAt(1));
    }
    List<Character> letters = new ArrayList<>(charSet);
    int k = letters.size();
    if (k == 0) return new ArrayList<>();
    Map<Character, Integer> idmap = new HashMap<>();
    for (int i = 0; i < k; i++) {
      idmap.put(letters.get(i), i);
    }
    List<int[]> edges = new ArrayList<>();
    for (String w : words) {
      int u = idmap.get(w.charAt(0));
      int v = idmap.get(w.charAt(1));
      edges.add(new int[]{u, v});
    }
    int min_r = Integer.MAX_VALUE;
    for (int dmask = 0; dmask < (1 << k); dmask++) {
      if (isValid(dmask, k, edges)) {
        min_r = Math.min(min_r, Integer.bitCount(dmask));
      }
    }
    List<List<Integer>> ans = new ArrayList<>();
    for (int dmask = 0; dmask < (1 << k); dmask++) {
      if (Integer.bitCount(dmask) == min_r && isValid(dmask, k, edges)) {
        List<Integer> freq = new ArrayList<>();
        for (int i = 0; i < 26; i++) freq.add(0);
        for (int x = 0; x < k; x++) {
          int cnt = ((dmask & (1 << x)) != 0) ? 2 : 1;
          char ch = letters.get(x);
          freq.set(ch - 'a', cnt);
        }
        ans.add(freq);
      }
    }
    return ans;
  }
  private boolean isValid(int dmask, int k, List<int[]> edges) {
    int[] node_first = new int[k];
    int[] node_last = new int[k];
    int node_cnt = 0;
    for (int x = 0; x < k; x++) {
      node_first[x] = node_cnt++;
      node_last[x] = ((dmask & (1 << x)) != 0) ? node_cnt++ : node_first[x];
    }
    List<List<Integer>> g = new ArrayList<>();
    for (int i = 0; i < node_cnt; i++) {
      g.add(new ArrayList<>());
    }
    int[] indegree = new int[node_cnt];
    for (int x = 0; x < k; x++) {
      if ((dmask & (1 << x)) != 0) {
        int fr = node_first[x];
        int to = node_last[x];
        g.get(fr).add(to);
        indegree[to]++;
      }
    }
    for (int[] e : edges) {
      int fr = node_first[e[0]];
      int to = node_last[e[1]];
      g.get(fr).add(to);
      indegree[to]++;
    }
    Queue<Integer> q = new LinkedList<>();
    int processed = 0;
    for (int i = 0; i < node_cnt; i++) {
      if (indegree[i] == 0) q.offer(i);
    }
    while (!q.isEmpty()) {
      int u = q.poll();
      processed++;
      for (int nei : g.get(u)) {
        indegree[nei]--;
        if (indegree[nei] == 0) {
          q.offer(nei);
        }
      }
    }
    return processed == node_cnt;
  }
}
# @lc code=end