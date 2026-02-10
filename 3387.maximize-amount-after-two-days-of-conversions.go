#
# @lc app=leetcode id=3387 lang=golang
#
# [3387] Maximize Amount After Two Days of Conversions
#
# @lc code=start
func maxAmount(initialCurrency string, pairs1 [][]string, rates1 []float64, pairs2 [][]string, rates2 []float64) float64 {
    // Helper function to find max conversion rates from a starting currency to all others
    findMaxRates := func(startCurrency string, pairs [][]string, rates []float64) map[string]float64 {
        // Build bidirectional graph
        graph := make(map[string]map[string]float64)
        for i, pair := range pairs {
            from, to := pair[0], pair[1]
            rate := rates[i]
            
            if graph[from] == nil {
                graph[from] = make(map[string]float64)
            }
            if graph[to] == nil {
                graph[to] = make(map[string]float64)
            }
            
            // Add both forward and reverse edges
            graph[from][to] = rate
            graph[to][from] = 1.0 / rate
        }
        
        // Find max rates using Bellman-Ford style relaxation
        maxRates := make(map[string]float64)
        maxRates[startCurrency] = 1.0
        
        // Relax edges until no improvement
        changed := true
        for changed {
            changed = false
            for curr := range maxRates {
                if graph[curr] != nil {
                    currRate := maxRates[curr]
                    for next, edgeRate := range graph[curr] {
                        newRate := currRate * edgeRate
                        if newRate > maxRates[next] {
                            maxRates[next] = newRate
                            changed = true
                        }
                    }
                }
            }
        }
        
        return maxRates
    }
    
    // Find max rates from initialCurrency on day 1
    day1Rates := findMaxRates(initialCurrency, pairs1, rates1)
    
    // Find max rates from initialCurrency on day 2
    day2Rates := findMaxRates(initialCurrency, pairs2, rates2)
    
    // Find the maximum product
    // day1Rates[C] = rate from initial TO C
    // day2Rates[C] = rate from initial TO C, so rate from C TO initial = 1/day2Rates[C]
    // Total: day1Rates[C] / day2Rates[C]
    maxResult := 1.0
    for currency := range day1Rates {
        if day2Rates[currency] > 0 {
            result := day1Rates[currency] / day2Rates[currency]
            if result > maxResult {
                maxResult = result
            }
        }
    }
    
    return maxResult
}
# @lc code=end