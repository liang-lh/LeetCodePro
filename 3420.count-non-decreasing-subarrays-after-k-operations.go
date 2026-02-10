#
# @lc app=leetcode id=3420 lang=golang
#
# [3420] Count Non-Decreasing Subarrays After K Operations
#
# @lc code=start
func countNonDecreasingSubarrays(nums []int, k int) int64 {
    n := len(nums)
    count := int64(0)
    
    // For each starting position
    for i := 0; i < n; i++ {
        maxVal := nums[i]
        operations := int64(0)
        
        // Try to extend the subarray
        for j := i; j < n; j++ {
            // Calculate operations needed for nums[j]
            if nums[j] < maxVal {
                operations += int64(maxVal - nums[j])
            } else {
                maxVal = nums[j]
            }
            
            // If operations <= k, this subarray is valid
            if operations <= int64(k) {
                count++
            } else {
                break // Further extensions will only increase operations
            }
        }
    }
    
    return count
}
# @lc code=end