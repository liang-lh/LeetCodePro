import java.util.*;

class Solution {
    public long maxAlternatingSum(int[] nums, int[][] swaps) {
        int n = nums.length;
        
        // Union-Find initialization
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        
        // Build connected components
        for (int[] swap : swaps) {
            union(parent, swap[0], swap[1]);
        }
        
        // Group indices by component
        Map<Integer, List<Integer>> components = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = find(parent, i);
            components.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
        }
        
        // Optimize value assignment per component
        int[] result = new int[n];
        for (List<Integer> indices : components.values()) {
            List<Integer> values = new ArrayList<>();
            for (int idx : indices) {
                values.add(nums[idx]);
            }
            
            Collections.sort(values, Collections.reverseOrder());
            
            Collections.sort(indices, (a, b) -> {
                boolean aEven = a % 2 == 0;
                boolean bEven = b % 2 == 0;
                if (aEven && !bEven) return -1;
                if (!aEven && bEven) return 1;
                return a - b;
            });
            
            for (int i = 0; i < indices.size(); i++) {
                result[indices.get(i)] = values.get(i);
            }
        }
        
        // Calculate alternating sum
        long sum = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                sum += result[i];
            } else {
                sum -= result[i];
            }
        }
        
        return sum;
    }
    
    private int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]);
        }
        return parent[x];
    }
    
    private void union(int[] parent, int x, int y) {
        int px = find(parent, x);
        int py = find(parent, y);
        if (px != py) {
            parent[px] = py;
        }
    }
}