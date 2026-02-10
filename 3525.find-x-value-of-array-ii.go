#
# @lc app=leetcode id=3525 lang=golang
#
# [3525] Find X Value of Array II
#
# @lc code=start
func resultArray(nums []int, k int, queries [][]int) []int {
    result := make([]int, len(queries))
    
    for qi, query := range queries {
        index, value, start, x := query[0], query[1], query[2], query[3]
        
        // Update nums[index] to value (persistent)
        nums[index] = value
        
        // Count ways by incrementally computing products
        count := 0
        product := 1
        n := len(nums)
        
        // Incrementally build product from start to each endIdx
        for endIdx := start; endIdx < n; endIdx++ {
            product = (product * (nums[endIdx] % k)) % k
            
            if product == x {
                count++
            }
        }
        
        result[qi] = count
    }
    
    return result
}
# @lc code=end