#
# @lc app=leetcode id=3373 lang=java
#
# [3373] Maximize the Number of Target Nodes After Connecting Trees II
#
# @lc code=start
class Solution {
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2) {
        int n = edges1.length + 1;
        int m = edges2.length + 1;
        
        // Build adjacency lists
        List<Integer>[] graph1 = buildGraph(n, edges1);
        List<Integer>[] graph2 = buildGraph(m, edges2);
        
        // Color tree1 and count nodes of each color
        int[] color1 = new int[n];
        Arrays.fill(color1, -1);
        int[] count1 = new int[2];
        colorTree(graph1, 0, 0, color1, count1);
        
        // Color tree2 and count nodes of each color
        int[] color2 = new int[m];
        Arrays.fill(color2, -1);
        int[] count2 = new int[2];
        colorTree(graph2, 0, 0, color2, count2);
        
        // Maximum nodes we can reach from tree2
        int maxFromTree2 = Math.max(count2[0], count2[1]);
        
        // Calculate result for each node in tree1
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            // Nodes at even distance in tree1 + optimal nodes from tree2
            result[i] = count1[color1[i]] + maxFromTree2;
        }
        
        return result;
    }
    
    private List<Integer>[] buildGraph(int n, int[][] edges) {
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        return graph;
    }
    
    private void colorTree(List<Integer>[] graph, int node, int c, int[] color, int[] count) {
        color[node] = c;
        count[c]++;
        for (int neighbor : graph[node]) {
            if (color[neighbor] == -1) {
                colorTree(graph, neighbor, 1 - c, color, count);
            }
        }
    }
}
# @lc code=end