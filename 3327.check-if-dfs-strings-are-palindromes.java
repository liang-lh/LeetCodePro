#
# @lc app=leetcode id=3327 lang=java
#
# [3327] Check if DFS Strings Are Palindromes
#
# @lc code=start
class Solution {
    private List<List<Integer>> children;
    private StringBuilder dfsStr;
    private int[] start, end;
    private int timer = 0;
    
    public boolean[] findAnswer(int[] parent, String s) {
        int n = parent.length;
        children = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            children.add(new ArrayList<>());
        }
        
        // Build adjacency list
        for (int i = 1; i < n; i++) {
            children.get(parent[i]).add(i);
        }
        
        // Sort children to ensure increasing order
        for (int i = 0; i < n; i++) {
            Collections.sort(children.get(i));
        }
        
        // Build DFS string from root
        dfsStr = new StringBuilder();
        start = new int[n];
        end = new int[n];
        timer = 0;
        buildDfsString(0, s);
        
        String fullStr = dfsStr.toString();
        int len = fullStr.length();
        
        // Polynomial hashing for efficient palindrome check
        long MOD = 1000000007L;
        long BASE = 31L;
        
        long[] hashForward = new long[len + 1];
        long[] hashBackward = new long[len + 1];
        long[] pow = new long[len + 1];
        
        pow[0] = 1;
        for (int i = 1; i <= len; i++) {
            pow[i] = (pow[i-1] * BASE) % MOD;
        }
        
        // Forward hash
        for (int i = 0; i < len; i++) {
            hashForward[i + 1] = (hashForward[i] * BASE + (fullStr.charAt(i) - 'a' + 1)) % MOD;
        }
        
        // Backward hash
        for (int i = len - 1; i >= 0; i--) {
            hashBackward[len - i] = (hashBackward[len - i - 1] * BASE + (fullStr.charAt(i) - 'a' + 1)) % MOD;
        }
        
        boolean[] answer = new boolean[n];
        for (int i = 0; i < n; i++) {
            int l = start[i];
            int r = end[i];
            
            // Get hash of substring [l, r)
            long forwardHash = (hashForward[r] - hashForward[l] * pow[r - l] % MOD + MOD) % MOD;
            
            // Get hash of reversed substring
            int revL = len - r;
            int revR = len - l;
            long backwardHash = (hashBackward[revR] - hashBackward[revL] * pow[r - l] % MOD + MOD) % MOD;
            
            answer[i] = (forwardHash == backwardHash);
        }
        
        return answer;
    }
    
    private void buildDfsString(int node, String s) {
        start[node] = timer;
        
        // Visit children in increasing order
        for (int child : children.get(node)) {
            buildDfsString(child, s);
        }
        
        // Add current node's character
        dfsStr.append(s.charAt(node));
        timer++;
        
        end[node] = timer;
    }
}
# @lc code=end