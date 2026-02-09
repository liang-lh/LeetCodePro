#
# @lc app=leetcode id=3470 lang=java
#
# [3470] Permutations IV
#
# @lc code=start
import java.util.*;

class Solution {
    public int[] permute(int n, long k) {
        List<Integer> odds = new ArrayList<>();
        List<Integer> evens = new ArrayList<>();
        
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) odds.add(i);
            else evens.add(i);
        }
        
        int[] result = new int[n];
        if (!build(result, 0, odds, evens, k, -1)) {
            return new int[0];
        }
        return result;
    }
    
    private boolean build(int[] result, int pos, List<Integer> odds, List<Integer> evens, 
                          long k, int lastParity) {
        if (pos == result.length) {
            return k == 1;
        }
        
        List<Integer> candidates = new ArrayList<>();
        if (lastParity == -1) {
            candidates.addAll(odds);
            candidates.addAll(evens);
        } else if (lastParity == 1) {
            candidates.addAll(evens);
        } else {
            candidates.addAll(odds);
        }
        
        Collections.sort(candidates);
        
        long totalCount = 0;
        for (int candidate : candidates) {
            List<Integer> newOdds = new ArrayList<>(odds);
            List<Integer> newEvens = new ArrayList<>(evens);
            
            if (candidate % 2 == 1) {
                newOdds.remove(Integer.valueOf(candidate));
            } else {
                newEvens.remove(Integer.valueOf(candidate));
            }
            
            long count = countPermutations(newOdds, newEvens, candidate % 2);
            
            if (count >= k - totalCount) {
                result[pos] = candidate;
                return build(result, pos + 1, newOdds, newEvens, k - totalCount, candidate % 2);
            }
            
            totalCount += count;
        }
        
        return false;
    }
    
    private long countPermutations(List<Integer> odds, List<Integer> evens, int lastParity) {
        int remaining = odds.size() + evens.size();
        if (remaining == 0) return 1;
        
        int oddSlotsNeeded = 0, evenSlotsNeeded = 0;
        int currentParity = 1 - lastParity;
        
        for (int i = 0; i < remaining; i++) {
            if (currentParity == 1) {
                oddSlotsNeeded++;
            } else {
                evenSlotsNeeded++;
            }
            currentParity = 1 - currentParity;
        }
        
        if (oddSlotsNeeded != odds.size() || evenSlotsNeeded != evens.size()) {
            return 0;
        }
        
        return factorial(odds.size()) * factorial(evens.size());
    }
    
    private long factorial(int n) {
        if (n > 20) return Long.MAX_VALUE;
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
# @lc code=end