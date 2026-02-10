#
# @lc app=leetcode id=3630 lang=java
#
# [3630] Partition Array for Maximum XOR and AND
#
# @lc code=start
import java.util.HashMap;

class Solution {
    private HashMap<String, Long> memo = new HashMap<>();
    
    public long maximizeXorAndXor(int[] nums) {
        return solve(nums, 0, 0, 0, 0, true);
    }
    
    private long solve(int[] nums, int idx, long xorA, long andB, long xorC, boolean isBEmpty) {
        if (idx == nums.length) {
            long finalAndB = isBEmpty ? 0 : andB;
            return xorA + finalAndB + xorC;
        }
        
        String key = idx + "_" + xorA + "_" + andB + "_" + xorC + "_" + isBEmpty;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        long num = nums[idx];
        
        // Option 1: Add to A
        long opt1 = solve(nums, idx + 1, xorA ^ num, andB, xorC, isBEmpty);
        
        // Option 2: Add to B
        long newAndB = isBEmpty ? num : (andB & num);
        long opt2 = solve(nums, idx + 1, xorA, newAndB, xorC, false);
        
        // Option 3: Add to C
        long opt3 = solve(nums, idx + 1, xorA, andB, xorC ^ num, isBEmpty);
        
        long result = Math.max(opt1, Math.max(opt2, opt3));
        memo.put(key, result);
        return result;
    }
}
# @lc code=end