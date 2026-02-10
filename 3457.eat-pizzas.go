#
# @lc app=leetcode id=3457 lang=golang
#
# [3457] Eat Pizzas!
#
# @lc code=start
import "sort"

func maxWeight(pizzas []int) int64 {
    n := len(pizzas)
    totalDays := n / 4
    oddDays := (totalDays + 1) / 2
    evenDays := totalDays / 2
    
    // Sort in descending order
    sort.Slice(pizzas, func(i, j int) bool {
        return pizzas[i] > pizzas[j]
    })
    
    var result int64
    
    // Sum first oddDays pizzas (Z on odd days)
    for i := 0; i < oddDays; i++ {
        result += int64(pizzas[i])
    }
    
    // Sum pizzas that become Y on even days
    // These are at indices [oddDays+evenDays, oddDays+2*evenDays)
    start := oddDays + evenDays
    end := oddDays + 2*evenDays
    for i := start; i < end && i < n; i++ {
        result += int64(pizzas[i])
    }
    
    return result
}
# @lc code=end