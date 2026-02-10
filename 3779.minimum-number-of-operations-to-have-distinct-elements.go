#
# @lc app=leetcode id=3779 lang=golang
#
# [3779] Minimum Number of Operations to Have Distinct Elements
#

# @lc code=start
func minOperations(nums []int) int {
    pos := make(map[int][]int)
    for i, num := range nums {
        pos[num] = append(pos[num], i)
    }
    spoil := -1
    for _, p := range pos {
        if len(p) >= 2 {
            if spoil < p[len(p)-2] {
                spoil = p[len(p)-2]
            }
        }
    }
    i := 0
    ops := 0
    n := len(nums)
    for i < n && i <= spoil {
        ops++
        rem := n - i
        if rem < 3 {
            i += rem
        } else {
            i += 3
        }
    }
    return ops
}
# @lc code=end