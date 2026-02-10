#
# @lc app=leetcode id=3575 lang=golang
#
# [3575] Maximum Good Subtree Score
#

# @lc code=start
func goodSubtreeSum(vals []int, par []int) int {
    n := len(vals)
    adj := make([][]int, n)
    for i := 1; i < n; i++ {
        adj[par[i]] = append(adj[par[i]], i)
    }
    mask := make([]int, n)
    valid := make([]bool, n)
    for i := 0; i < n; i++ {
        temp := vals[i]
        seen := 0
        isValid := true
        for temp > 0 {
            d := temp % 10
            bit := 1 << d
            if (seen & bit) != 0 {
                isValid = false
                break
            }
            seen |= bit
            temp /= 10
        }
        valid[i] = isValid
        if isValid {
            mask[i] = seen
        }
    }
    const SZ = 1 << 10
    const MOD int64 = 1000000007
    maxScores := make([]int64, n)
    var dfs func(int) []int64
    dfs = func(u int) []int64 {
        descendants := make([]int64, SZ)
        for i := range descendants {
            descendants[i] = -1
        }
        descendants[0] = 0
        for _, v := range adj[u] {
            child := dfs(v)
            newDesc := make([]int64, SZ)
            for i := range newDesc {
                newDesc[i] = -1
            }
            currActive := make([]int, 0, SZ)
            for m := 0; m < SZ; m++ {
                if descendants[m] != -1 {
                    currActive = append(currActive, m)
                }
            }
            childActive := make([]int, 0, SZ)
            for m := 0; m < SZ; m++ {
                if child[m] != -1 {
                    childActive = append(childActive, m)
                }
            }
            for _, m1 := range currActive {
                s1 := descendants[m1]
                for _, m2 := range childActive {
                    s2 := child[m2]
                    if (m1 & m2) == 0 {
                        nm := m1 | m2
                        ns := s1 + s2
                        if newDesc[nm] < ns {
                            newDesc[nm] = ns
                        }
                    }
                }
            }
            descendants = newDesc
        }
        descActive := make([]int, 0, SZ)
        for m := 0; m < SZ; m++ {
            if descendants[m] != -1 {
                descActive = append(descActive, m)
            }
        }
        subtree := make([]int64, SZ)
        for i := range subtree {
            subtree[i] = -1
        }
        // without u
        for _, m := range descActive {
            subtree[m] = descendants[m]
        }
        // with u
        if valid[u] {
            mu := mask[u]
            vu := int64(vals[u])
            for _, m := range descActive {
                if (m & mu) == 0 {
                    nm := m | mu
                    ns := descendants[m] + vu
                    if subtree[nm] < ns {
                        subtree[nm] = ns
                    }
                }
            }
        }
        var mx int64 = 0
        for m := 0; m < SZ; m++ {
            if subtree[m] > mx {
                mx = subtree[m]
            }
        }
        maxScores[u] = mx
        return subtree
    }
    dfs(0)
    var ans int64 = 0
    for _, m := range maxScores {
        ans = (ans + m) % MOD
    }
    return int(ans)
}
# @lc code=end