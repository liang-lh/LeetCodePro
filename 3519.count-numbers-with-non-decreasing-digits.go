#
# @lc app=leetcode id=3519 lang=golang
#
# [3519] Count Numbers with Non-Decreasing Digits
#

# @lc code=start
import (
    "math/big"
)

func countNumbers(l string, r string, b int) int {
    const MOD int64 = 1000000007

    getDigits := func(n *big.Int, base int) []int {
        if n.Cmp(big.NewInt(0)) == 0 {
            return []int{0}
        }
        digits := []int{}
        bb := big.NewInt(int64(base))
        tmp := new(big.Int).Set(n)
        for tmp.Cmp(big.NewInt(0)) > 0 {
            rem := new(big.Int).Mod(tmp, bb)
            digits = append(digits, int(rem.Int64()))
            tmp.Div(tmp, bb)
        }
        // reverse to MSD first
        for i, j := 0, len(digits)-1; i < j; i, j = i+1, j-1 {
            digits[i], digits[j] = digits[j], digits[i]
        }
        return digits
    }

    countLeq := func(s string, base int) int {
        N := new(big.Int)
        N.SetString(s, 10)
        D := getDigits(N, base)
        L := len(D)
        memo := make([][][]int64, L+1)
        for i := 0; i <= L; i++ {
            memo[i] = make([][]int64, 2)
            for j := 0; j < 2; j++ {
                memo[i][j] = make([]int64, base)
                for k := 0; k < base; k++ {
                    memo[i][j][k] = -1
                }
            }
        }
        var dfs func(pos, tight, prev int) int64
        dfs = func(pos, tight1, prev int) int64 {
            if pos == L {
                if prev == 0 {
                    return 0
                }
                return 1
            }
            if memo[pos][tight1][prev] != -1 {
                return memo[pos][tight1][prev]
            }
            ans := int64(0)
            up := base - 1
            if tight1 == 1 {
                up = D[pos]
            }
            for cur := 0; cur <= up; cur++ {
                newTight := 0
                if tight1 == 1 && cur == up {
                    newTight = 1
                }
                newPrev := prev
                cont := true
                if prev == 0 {
                    if cur != 0 {
                        newPrev = cur
                    }
                } else {
                    if cur < prev {
                        cont = false
                    } else {
                        newPrev = cur
                    }
                }
                if cont {
                    ans = (ans + dfs(pos+1, newTight, newPrev)) % MOD
                }
            }
            memo[pos][tight1][prev] = ans
            return ans
        }
        return int(dfs(0, 1, 0))
    }

    countR := countLeq(r, b)

    nl := new(big.Int)
    nl.SetString(l, 10)
    nl.Sub(nl, big.NewInt(1))
    l1 := nl.String()
    countL := countLeq(l1, b)

    res := (int64(countR) - int64(countL) + MOD) % MOD
    return int(res)
}
# @lc code=end