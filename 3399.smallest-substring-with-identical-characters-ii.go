#
# @lc app=leetcode id=3399 lang=golang
#
# [3399] Smallest Substring With Identical Characters II
#
# @lc code=start
func minLength(s string, numOps int) int {
    n := len(s)
    
    // Helper function to check if maxLen is achievable with at most numOps flips
    canAchieve := func(maxLen int) bool {
        if maxLen == 1 {
            // Special case: need alternating pattern
            mismatch0 := 0 // Pattern "0101..."
            mismatch1 := 0 // Pattern "1010..."
            for i := 0; i < n; i++ {
                expected0 := byte('0' + i%2)
                expected1 := byte('1' - i%2)
                if s[i] != expected0 {
                    mismatch0++
                }
                if s[i] != expected1 {
                    mismatch1++
                }
            }
            return min(mismatch0, mismatch1) <= numOps
        }
        
        // For maxLen >= 2, count flips needed to break runs
        opsNeeded := 0
        i := 0
        for i < n {
            j := i
            // Find the end of current run
            for j < n && s[j] == s[i] {
                j++
            }
            runLen := j - i
            if runLen > maxLen {
                // Number of flips needed to ensure each segment is at most maxLen
                opsNeeded += runLen / (maxLen + 1)
            }
            i = j
        }
        return opsNeeded <= numOps
    }
    
    // Binary search on the answer
    left, right := 1, n
    result := n
    
    for left <= right {
        mid := (left + right) / 2
        if canAchieve(mid) {
            result = mid
            right = mid - 1
        } else {
            left = mid + 1
        }
    }
    
    return result
}

func min(a, b int) int {
    if a < b {
        return a
    }
    return b
}
# @lc code=end