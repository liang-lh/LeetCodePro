package main

import "sort"

// @lc code=start
func maxSubstringLength(s string, k int) bool {
    n := len(s)
    
    if k == 0 {
        return true
    }
    
    // Find first and last occurrence of each character
    first := make(map[byte]int)
    last := make(map[byte]int)
    
    for i := 0; i < n; i++ {
        c := s[i]
        if _, exists := first[c]; !exists {
            first[c] = i
        }
        last[c] = i
    }
    
    // Find all valid special substrings
    var intervals [][2]int
    
    for i := 0; i < n; {
        right := i
        valid := true
        
        // Expand window until it stabilizes
        for {
            newRight := right
            for j := i; j <= right; j++ {
                c := s[j]
                if first[c] < i {
                    valid = false
                    break
                }
                if last[c] > newRight {
                    newRight = last[c]
                }
            }
            if !valid || newRight == right {
                break
            }
            right = newRight
        }
        
        // Check if valid special substring (not entire string)
        if valid && !(i == 0 && right == n-1) && right < n {
            intervals = append(intervals, [2]int{i, right})
            i = right + 1  // Skip to avoid duplicates
        } else {
            i++
        }
    }
    
    // If no valid special substrings found
    if len(intervals) == 0 {
        return false
    }
    
    // Greedy interval scheduling
    sort.Slice(intervals, func(i, j int) bool {
        return intervals[i][1] < intervals[j][1]
    })
    
    count := 1
    lastEnd := intervals[0][1]
    
    for i := 1; i < len(intervals); i++ {
        if intervals[i][0] > lastEnd {
            count++
            lastEnd = intervals[i][1]
        }
    }
    
    return count >= k
}
// @lc code=end