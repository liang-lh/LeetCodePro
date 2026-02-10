#
# @lc app=leetcode id=3474 lang=golang
#
# [3474] Lexicographically Smallest Generated String
#
# @lc code=start
func generateString(str1 string, str2 string) string {
    n := len(str1)
    m := len(str2)
    length := n + m - 1
    
    word := make([]byte, length)
    
    // Build the word character by character
    for pos := 0; pos < length; pos++ {
        found := false
        // Try each character from 'a' to 'z'
        for ch := byte('a'); ch <= byte('z'); ch++ {
            word[pos] = ch
            valid := true
            
            // Check only constraints that overlap with this position
            // Constraint i has substring [i, i+m-1], so it overlaps pos if:
            // max(0, pos-m+1) <= i <= min(pos, n-1)
            startI := pos - m + 1
            if startI < 0 {
                startI = 0
            }
            endI := pos
            if endI >= n {
                endI = n - 1
            }
            
            for i := startI; i <= endI; i++ {
                start := i
                end := i + m - 1
                
                // Skip if constraint is out of bounds
                if end >= length {
                    continue
                }
                
                if pos == end {
                    // Full constraint can be checked
                    substr := string(word[start:end+1])
                    if str1[i] == 'T' {
                        if substr != str2 {
                            valid = false
                            break
                        }
                    } else { // str1[i] == 'F'
                        if substr == str2 {
                            valid = false
                            break
                        }
                    }
                } else if str1[i] == 'T' {
                    // Partial constraint for 'T' - must match prefix
                    prefixLen := pos - start + 1
                    if string(word[start:pos+1]) != str2[:prefixLen] {
                        valid = false
                        break
                    }
                }
                // For 'F' with partial constraint, no check needed yet
            }
            
            if valid {
                found = true
                break
            }
        }
        
        if !found {
            return ""
        }
    }
    
    return string(word)
}
# @lc code=end