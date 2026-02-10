#
# @lc app=leetcode id=3398 lang=golang
#
# [3398] Smallest Substring With Identical Characters I
#
# @lc code=start
func minLength(s string, numOps int) int {
    n := len(s)
    
    // Check if we can create an alternating pattern with available operations
    flips1 := 0  // flips needed for pattern 010101...
    flips2 := 0  // flips needed for pattern 101010...
    for i := 0; i < n; i++ {
        if i % 2 == 0 {
            if s[i] == '1' {
                flips1++
            } else {
                flips2++
            }
        } else {
            if s[i] == '0' {
                flips1++
            } else {
                flips2++
            }
        }
    }
    
    if flips1 <= numOps || flips2 <= numOps {
        return 1
    }
    
    // Binary search for the minimum achievable max length
    left, right := 2, n
    for left < right {
        mid := (left + right) / 2
        if canAchieve(s, mid, numOps) {
            right = mid
        } else {
            left = mid + 1
        }
    }
    return left
}

func canAchieve(s string, maxLen int, numOps int) bool {
    flipsNeeded := 0
    i := 0
    
    // Process each run of identical characters
    for i < len(s) {
        j := i
        // Find the end of the current run
        for j < len(s) && s[j] == s[i] {
            j++
        }
        runLen := j - i
        
        // Calculate flips needed to break this run into segments of at most maxLen
        if runLen > maxLen {
            flipsNeeded += runLen / (maxLen + 1)
        }
        
        i = j
    }
    
    return flipsNeeded <= numOps
}
# @lc code=end