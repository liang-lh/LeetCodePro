//
// @lc app=leetcode id=3414 lang=java
//
// [3414] Maximum Score of Non-overlapping Intervals
//
// @lc code=start
import java.util.*;

class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        
        // Create indexed intervals
        Interval[] arr = new Interval[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new Interval(
                intervals.get(i).get(0),
                intervals.get(i).get(1),
                intervals.get(i).get(2),
                i
            );
        }
        
        // Sort by end time
        Arrays.sort(arr, (a, b) -> Integer.compare(a.end, b.end));
        
        // DP state: dp[i][k] = {weight, indices}
        State[][] dp = new State[n + 1][5];
        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                dp[i][k] = new State(0, new ArrayList<>());
            }
        }
        
        for (int i = 1; i <= n; i++) {
            Interval curr = arr[i - 1];
            
            // Option 1: Skip current interval
            for (int k = 0; k <= 4; k++) {
                dp[i][k] = new State(dp[i-1][k]);
            }
            
            // Option 2: Take current interval
            int prev = binarySearch(arr, i - 1, curr.start);
            
            for (int k = 1; k <= 4; k++) {
                State prevState = prev == -1 ? new State(0, new ArrayList<>()) : dp[prev + 1][k - 1];
                long newWeight = prevState.weight + curr.weight;
                
                if (newWeight > dp[i][k].weight) {
                    List<Integer> newIndices = new ArrayList<>(prevState.indices);
                    newIndices.add(curr.originalIndex);
                    dp[i][k] = new State(newWeight, newIndices);
                } else if (newWeight == dp[i][k].weight) {
                    List<Integer> newIndices = new ArrayList<>(prevState.indices);
                    newIndices.add(curr.originalIndex);
                    if (isLexSmaller(newIndices, dp[i][k].indices)) {
                        dp[i][k] = new State(newWeight, newIndices);
                    }
                }
            }
        }
        
        // Find best solution
        State best = new State(0, new ArrayList<>());
        for (int k = 0; k <= 4; k++) {
            if (dp[n][k].weight > best.weight) {
                best = new State(dp[n][k]);
            } else if (dp[n][k].weight == best.weight && isLexSmaller(dp[n][k].indices, best.indices)) {
                best = new State(dp[n][k]);
            }
        }
        
        Collections.sort(best.indices);
        return best.indices.stream().mapToInt(i -> i).toArray();
    }
    
    private int binarySearch(Interval[] arr, int right, int targetStart) {
        int left = 0, result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid].end < targetStart) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }
    
    private boolean isLexSmaller(List<Integer> a, List<Integer> b) {
        List<Integer> sortedA = new ArrayList<>(a);
        List<Integer> sortedB = new ArrayList<>(b);
        Collections.sort(sortedA);
        Collections.sort(sortedB);
        
        for (int i = 0; i < Math.min(sortedA.size(), sortedB.size()); i++) {
            if (sortedA.get(i) < sortedB.get(i)) return true;
            if (sortedA.get(i) > sortedB.get(i)) return false;
        }
        return sortedA.size() < sortedB.size();
    }
    
    static class Interval {
        int start, end, weight, originalIndex;
        Interval(int s, int e, int w, int idx) {
            start = s; end = e; weight = w; originalIndex = idx;
        }
    }
    
    static class State {
        long weight;
        List<Integer> indices;
        State(long w, List<Integer> idx) {
            weight = w; indices = new ArrayList<>(idx);
        }
        State(State other) {
            weight = other.weight; indices = new ArrayList<>(other.indices);
        }
    }
}
// @lc code=end