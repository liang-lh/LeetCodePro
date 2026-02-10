#
# @lc app=leetcode id=3434 lang=golang
#
# [3434] Maximum Frequency After Subarray Operation
#

# @lc code=start
func maxFrequency(nums []int, k int) int {
    totalK := 0
    for _, num := range nums {
        if num == k {
            totalK++
        }
    }
    maxGain := 0
    const INF = 1000000001
    for v := 1; v <= 50; v++ {
        maxCurrent := 0
        maxGlobal := -INF
        for _, num := range nums {
            delta := 0
            if num == v {
                delta = 1
            }
            if num == k {
                delta -= 1
            }
            nextCurrent := maxCurrent + delta
            if nextCurrent < delta {
                nextCurrent = delta
            }
            maxCurrent = nextCurrent
            if maxCurrent > maxGlobal {
                maxGlobal = maxCurrent
            }
        }
        if maxGlobal > maxGain {
            maxGain = maxGlobal
        }
    }
    return totalK + maxGain
}
# @lc code=end