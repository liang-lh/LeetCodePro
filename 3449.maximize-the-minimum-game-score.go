#
# @lc app=leetcode id=3449 lang=golang
#
# [3449] Maximize the Minimum Game Score
#

# @lc code=start
func maxScore(points []int, m int) int64 {
    n := len(points)
    minP := int64(points[0])
    for _, p := range points {
        pp := int64(p)
        if pp < minP {
            minP = pp
        }
    }
    left, right := int64(0), minP * int64(m)
    for left < right {
        mid := left + (right - left + 1) / 2
        if canAchieve(points, mid, int64(m), n) {
            left = mid
        } else {
            right = mid - 1
        }
    }
    return left
}

func canAchieve(points []int, mid, M int64, n int) bool {
    f := make([]int64, n)
    f[0] = (mid + int64(points[0]) - 1) / int64(points[0])
    for i := 1; i < n; i++ {
        need := (mid + int64(points[i]) - 1) / int64(points[i])
        f[i] = max(need, f[i-1] - 1)
    }
    total := int64(0)
    for i := 0; i < n; i++ {
        total += f[i]
        if total > M {
            return false
        }
    }
    // Backward pass with incremental update to catch extra costs
    currb := f[n-1]
    for i := n-2; i >= 0; i-- {
        need := (mid + int64(points[i]) - 1) / int64(points[i])
        currb = max(need, currb - 1)
        if currb > f[i] {
            total += currb - f[i]
            if total > M {
                return false
            }
            f[i] = currb
        }
    }
    return total <= M
}

func max(a, b int64) int64 {
    if a > b {
        return a
    }
    return b
}
# @lc code=end