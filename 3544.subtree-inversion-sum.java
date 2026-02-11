#
# @lc app=leetcode id=3544 lang=java
#
# [3544] Subtree Inversion Sum
#

# @lc code=start
import java.util.*;

class Solution {
    private long[][] dfs(int u, int par, List<Integer>[] adj, int[] nums, int k) {
        List<Integer> children = new ArrayList<>();
        for (int v : adj[u]) {
            if (v != par) {
                children.add(v);
            }
        }
        long[][][] childDps = new long[children.size()][];
        for (int i = 0; i < children.size(); i++) {
            childDps[i] = dfs(children.get(i), u, adj, nums, k);
        }
        long[][] dp = new long[2][k];
        for (int pin = 0; pin < 2; pin++) {
            for (int din = 0; din < k; din++) {
                // inv=0
                {
                    int inv = 0;
                    int total_parity = (pin + inv) % 2;
                    long contrib = (long) nums[u] * (total_parity == 0 ? 1L : -1L);
                    int p_ch = (pin + inv) % 2;
                    int potential_d = (din == 0 ? k : din + 1);
                    int d_ch = (potential_d >= k ? 0 : potential_d);
                    long current = contrib;
                    for (int j = 0; j < children.size(); j++) {
                        current += childDps[j][p_ch][d_ch];
                    }
                    dp[pin][din] = current;
                }
                // inv=1 if din==0
                if (din == 0) {
                    int inv = 1;
                    int total_parity = (pin + inv) % 2;
                    long contrib = (long) nums[u] * (total_parity == 0 ? 1L : -1L);
                    int p_ch = (pin + inv) % 2;
                    int potential_d = 1;
                    int d_ch = (potential_d >= k ? 0 : potential_d);
                    long current = contrib;
                    for (int j = 0; j < children.size(); j++) {
                        current += childDps[j][p_ch][d_ch];
                    }
                    dp[pin][din] = Math.max(dp[pin][din], current);
                }
            }
        }
        return dp;
    }

    public long subtreeInversionSum(int[][] edges, int[] nums, int k) {
        int n = nums.length;
        @SuppressWarnings("unchecked")
        List<Integer>[] adj = new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }
        long[][] rootDp = dfs(0, -1, adj, nums, k);
        return rootDp[0][0];
    }
}
# @lc code=end