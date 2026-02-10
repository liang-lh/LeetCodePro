#
# @lc app=leetcode id=3729 lang=golang
#
# [3729] Count Distinct Subarrays Divisible by K in Sorted Array
#

# @lc code=start
func numGoodSubarrays(nums []int, k int) int64 {
    if k == 0 {
        return 0
    }
    k64 := int64(k)
    type Group struct {
        val int64
        cnt int64
    }
    var groups []Group
    i := 0
    n := len(nums)
    for i < n {
        vi := nums[i]
        v64 := int64(vi)
        j := i + 1
        for j < n && nums[j] == vi {
            j++
        }
        cnt64 := int64(j - i)
        groups = append(groups, Group{v64, cnt64})
        i = j
    }
    m := len(groups)
    prefix_mod := make([]int64, m+1)
    for gi := 0; gi < m; gi++ {
        valm := groups[gi].val % k64
        cnm := groups[gi].cnt % k64
        add := (cnm * valm) % k64
        prefix_mod[gi+1] = (prefix_mod[gi] + add) % k64
    }
    ans := int64(0)
    gcdFunc := func(a int64, b int64) int64 {
        for b != 0 {
            a, b = b, a%b
        }
        return a
    }
    // single groups
    for _, g := range groups {
        vmod := g.val % k64
        d := gcdFunc(vmod, k64)
        per := k64 / d
        ans += g.cnt / per
    }
    // multi groups
    freq := make(map[int64]int64)
    for gi := 0; gi < m; gi++ {
        vmod := groups[gi].val % k64
        cn := groups[gi].cnt
        pre := prefix_mod[gi]
        // process ends first
        for s := int64(1); s <= cn; s++ {
            contrib := (s * vmod) % k64
            emod := (pre + contrib) % k64
            ans += freq[emod]
        }
        // then add starts
        for p := int64(1); p <= cn; p++ {
            q := cn - p
            contrib := (q * vmod) % k64
            smod := (pre + contrib) % k64
            freq[smod]++
        }
    }
    return ans
}
# @lc code=end