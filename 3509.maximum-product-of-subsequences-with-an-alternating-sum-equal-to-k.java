#
# @lc app=leetcode id=3509 lang=java
#
# [3509] Maximum Product of Subsequences With an Alternating Sum Equal to K
#

# @lc code=start
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxProduct(int[] nums, int k, int limit) {
        // Map: "sum,lenMod" -> max_product
        Map<String, Integer> currentStates = new HashMap<>();
        
        for (int num : nums) {
            Map<String, Integer> newStates = new HashMap<>(currentStates);
            
            // Start a new subsequence with this element
            String key = num + ",1";
            newStates.put(key, Math.max(newStates.getOrDefault(key, 0), num));
            
            // Extend existing subsequences
            for (Map.Entry<String, Integer> entry : currentStates.entrySet()) {
                String[] parts = entry.getKey().split(",");
                int sum = Integer.parseInt(parts[0]);
                int lenMod = Integer.parseInt(parts[1]);
                int product = entry.getValue();
                
                int newSum, newLenMod;
                if (lenMod == 0) {  // even length, next is at even position (adds)
                    newSum = sum + num;
                    newLenMod = 1;
                } else {  // odd length, next is at odd position (subtracts)
                    newSum = sum - num;
                    newLenMod = 0;
                }
                
                int newProduct = product * num;
                if (newProduct <= limit) {
                    String newKey = newSum + "," + newLenMod;
                    newStates.put(newKey, Math.max(newStates.getOrDefault(newKey, 0), newProduct));
                }
            }
            
            currentStates = newStates;
        }
        
        // Find max product where sum == k
        int result = -1;
        for (Map.Entry<String, Integer> entry : currentStates.entrySet()) {
            String[] parts = entry.getKey().split(",");
            int sum = Integer.parseInt(parts[0]);
            int product = entry.getValue();
            
            if (sum == k && product > result) {
                result = product;
            }
        }
        
        return result;
    }
}
# @lc code=end