#
# @lc app=leetcode id=3327 lang=golang
#
# [3327] Check if DFS Strings Are Palindromes
#
# @lc code=start
func findAnswer(parent []int, s string) []bool {
    n := len(parent)
    
    // Build adjacency list
    children := make([][]int, n)
    for i := 1; i < n; i++ {
        p := parent[i]
        children[p] = append(children[p], i)
    }
    
    // Sort children for each node to ensure increasing order
    for i := 0; i < n; i++ {
        sort.Ints(children[i])
    }
    
    // Build global DFS string and track ranges
    var dfsStr []byte
    ranges := make([][2]int, n)
    
    var dfs func(int)
    dfs = func(node int) {
        start := len(dfsStr)
        // Visit all children in increasing order
        for _, child := range children[node] {
            dfs(child)
        }
        // Add current node's character
        dfsStr = append(dfsStr, s[node])
        // Record range for this subtree
        ranges[node] = [2]int{start, len(dfsStr)}
    }
    
    // Start DFS from root
    dfs(0)
    
    // Check palindrome for each node's range
    answer := make([]bool, n)
    for i := 0; i < n; i++ {
        start, end := ranges[i][0], ranges[i][1]
        answer[i] = isPalindrome(dfsStr[start:end])
    }
    
    return answer
}

func isPalindrome(s []byte) bool {
    left, right := 0, len(s)-1
    for left < right {
        if s[left] != s[right] {
            return false
        }
        left++
        right--
    }
    return true
}
# @lc code=end