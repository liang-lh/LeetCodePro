#
# @lc app=leetcode id=3414 lang=golang
#
# [3414] Maximum Score of Non-overlapping Intervals
#
# @lc code=start
func maximumWeight(intervals [][]int) []int {
    n := len(intervals)
    
    type IndexedInterval struct {
        left, right, weight, index int
    }
    
    indexed := make([]IndexedInterval, n)
    for i := 0; i < n; i++ {
        indexed[i] = IndexedInterval{
            left:   intervals[i][0],
            right:  intervals[i][1],
            weight: intervals[i][2],
            index:  i,
        }
    }
    
    // Sort by end time
    sort.Slice(indexed, func(i, j int) bool {
        return indexed[i].right < indexed[j].right
    })
    
    type State struct {
        weight  int
        indices []int
    }
    
    // dp[i][k] = best state when selecting exactly k intervals from first i intervals
    const NEG_INF = -1
    dp := make([][]State, n+1)
    for i := 0; i <= n; i++ {
        dp[i] = make([]State, 5)
        for k := 0; k <= 4; k++ {
            dp[i][k] = State{weight: NEG_INF, indices: nil}
        }
    }
    dp[0][0] = State{weight: 0, indices: []int{}}
    
    // Binary search for last non-overlapping interval
    findLastNonOverlap := func(idx int) int {
        target := indexed[idx].left
        result := 0
        left, right := 0, idx
        for left < right {
            mid := (left + right) / 2
            if indexed[mid].right < target {
                result = mid + 1
                left = mid + 1
            } else {
                right = mid
            }
        }
        return result
    }
    
    // Lexicographic comparison
    lexSmaller := func(a, b []int) bool {
        if b == nil {
            return true
        }
        if a == nil {
            return false
        }
        for i := 0; i < len(a) && i < len(b); i++ {
            if a[i] != b[i] {
                return a[i] < b[i]
            }
        }
        return len(a) < len(b)
    }
    
    // Merge and sort indices
    mergeSorted := func(a []int, val int) []int {
        result := make([]int, len(a)+1)
        copy(result, a)
        result[len(a)] = val
        sort.Ints(result)
        return result
    }
    
    // Update state if better
    updateState := func(target *State, newWeight int, newIndices []int) {
        if target.weight < 0 || newWeight > target.weight ||
            (newWeight == target.weight && lexSmaller(newIndices, target.indices)) {
            target.weight = newWeight
            target.indices = newIndices
        }
    }
    
    for i := 1; i <= n; i++ {
        curr := indexed[i-1]
        
        for k := 0; k <= 4; k++ {
            // Option 1: Don't take current interval
            if dp[i-1][k].weight >= 0 {
                updateState(&dp[i][k], dp[i-1][k].weight, dp[i-1][k].indices)
            }
            
            // Option 2: Take current interval
            if k > 0 {
                j := findLastNonOverlap(i - 1)
                if dp[j][k-1].weight >= 0 {
                    newWeight := dp[j][k-1].weight + curr.weight
                    newIndices := mergeSorted(dp[j][k-1].indices, curr.index)
                    updateState(&dp[i][k], newWeight, newIndices)
                }
            }
        }
    }
    
    // Find best result across all k values
    best := State{weight: NEG_INF, indices: nil}
    for k := 0; k <= 4; k++ {
        if dp[n][k].weight > best.weight ||
            (dp[n][k].weight == best.weight && lexSmaller(dp[n][k].indices, best.indices)) {
            best = dp[n][k]
        }
    }
    
    if best.indices == nil {
        return []int{}
    }
    return best.indices
}
# @lc code=end