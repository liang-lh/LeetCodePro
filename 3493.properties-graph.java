#
# @lc app=leetcode id=3493 lang=java
#
# [3493] Properties Graph
#
# @lc code=start
import java.util.*;

class Solution {
    public int numberOfComponents(int[][] properties, int k) {
        int n = properties.length;
        
        // Initialize Union-Find structure
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        
        // Check all pairs and union if intersection >= k
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (countIntersection(properties[i], properties[j]) >= k) {
                    union(parent, i, j);
                }
            }
        }
        
        // Count connected components
        int components = 0;
        for (int i = 0; i < n; i++) {
            if (find(parent, i) == i) {
                components++;
            }
        }
        
        return components;
    }
    
    private int countIntersection(int[] a, int[] b) {
        Set<Integer> setA = new HashSet<>();
        for (int num : a) {
            setA.add(num);
        }
        
        Set<Integer> intersection = new HashSet<>();
        for (int num : b) {
            if (setA.contains(num)) {
                intersection.add(num);
            }
        }
        
        return intersection.size();
    }
    
    private int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]); // Path compression
        }
        return parent[x];
    }
    
    private void union(int[] parent, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);
        if (rootX != rootY) {
            parent[rootX] = rootY;
        }
    }
}
# @lc code=end