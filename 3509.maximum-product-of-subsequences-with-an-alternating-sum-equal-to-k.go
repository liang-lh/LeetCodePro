#
# @lc app=leetcode id=3509 lang=golang
#
# [3509] Maximum Product of Subsequences With an Alternating Sum Equal to K
#
# @lc code=start
func maxProduct(nums []int, k int, limit int) int {
    type State struct {
        sum    int
        parity int // 0 for even length, 1 for odd length
    }
    
    // dp[state] = set of achievable products
    dp := make(map[State]map[int]bool)
    
    for _, num := range nums {
        newDp := make(map[State]map[int]bool)
        
        // Copy existing states (skip current number)
        for state, products := range dp {
            if newDp[state] == nil {
                newDp[state] = make(map[int]bool)
            }
            for product := range products {
                newDp[state][product] = true
            }
        }
        
        // Start new subsequence with current number
        if num <= limit {
            state := State{sum: num, parity: 1}
            if newDp[state] == nil {
                newDp[state] = make(map[int]bool)
            }
            newDp[state][num] = true
        }
        
        // Extend existing subsequences
        for state, products := range dp {
            for product := range products {
                newProduct := product * num
                if newProduct > limit {
                    continue
                }
                
                var newSum int
                newParity := 1 - state.parity
                
                if state.parity == 1 {
                    // Odd length: next element at odd index (subtract)
                    newSum = state.sum - num
                } else {
                    // Even length: next element at even index (add)
                    newSum = state.sum + num
                }
                
                newState := State{sum: newSum, parity: newParity}
                if newDp[newState] == nil {
                    newDp[newState] = make(map[int]bool)
                }
                newDp[newState][newProduct] = true
            }
        }
        
        dp = newDp
    }
    
    // Find maximum product with sum = k
    maxProd := -1
    for state, products := range dp {
        if state.sum == k {
            for product := range products {
                if product > maxProd {
                    maxProd = product
                }
            }
        }
    }
    
    return maxProd
}
# @lc code=end