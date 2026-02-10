#
# @lc app=leetcode id=3538 lang=golang
#
# [3538] Merge Operations for Minimum Travel Time
#
# @lc code=start
import "fmt"

func minTravelTime(l int, n int, k int, position []int, time []int) int {
    memo := make(map[string]int)
    return solve(position, time, k, memo)
}

func solve(positions []int, times []int, remaining int, memo map[string]int) int {
    if remaining == 0 {
        return calculateCost(positions, times)
    }
    
    key := stateKey(positions, times, remaining)
    if val, ok := memo[key]; ok {
        return val
    }
    
    minCost := 1 << 30
    for i := 1; i < len(positions)-1; i++ {
        newPos, newTimes := merge(positions, times, i)
        cost := solve(newPos, newTimes, remaining-1, memo)
        if cost < minCost {
            minCost = cost
        }
    }
    
    memo[key] = minCost
    return minCost
}

func merge(positions []int, times []int, idx int) ([]int, []int) {
    n := len(positions)
    
    newPos := make([]int, n-1)
    copy(newPos, positions[:idx])
    copy(newPos[idx:], positions[idx+1:])
    
    newTimes := make([]int, n-1)
    copy(newTimes, times[:idx])
    newTimes[idx] = times[idx] + times[idx+1]
    if idx+2 < n {
        copy(newTimes[idx+1:], times[idx+2:])
    }
    
    return newPos, newTimes
}

func calculateCost(positions []int, times []int) int {
    cost := 0
    for i := 0; i < len(positions)-1; i++ {
        distance := positions[i+1] - positions[i]
        cost += distance * times[i]
    }
    return cost
}

func stateKey(positions []int, times []int, remaining int) string {
    return fmt.Sprintf("%v:%v:%d", positions, times, remaining)
}
# @lc code=end