#
# @lc app=leetcode id=3725 lang=golang
#
# [3725] Count Ways to Choose Coprime Integers from Rows
#

# @lc code=start
func countCoprime(mat [][]int) int {
	const MOD int64 = 1000000007
	m := len(mat)
	const MAX int = 150
	mu := make([]int, MAX+1)
	vis := make([]bool, MAX+1)
	var primes []int
	mu[1] = 1
	for i := 2; i <= MAX; i++ {
		if !vis[i] {
			primes = append(primes, i)
			mu[i] = -1
		}
		for j := 0; j < len(primes); j++ {
			p := primes[j]
			if int64(i)*int64(p) > MAX {
				break
			}
			vis[i*p] = true
			if i%p == 0 {
				mu[i*p] = 0
				break
			} else {
				mu[i*p] = -mu[i]
			}
		}
	}
	freq := make([][]int, m)
	for i := 0; i < m; i++ {
		freq[i] = make([]int, MAX+1)
		for _, num := range mat[i] {
			if num <= MAX {
				freq[i][num]++
			}
		}
	}
	var ans int64 = 0
	for d := 1; d <= MAX; d++ {
		if mu[d] == 0 {
			continue
		}
		var prod int64 = 1
		for r := 0; r < m; r++ {
			var cnt int
			for k := d; k <= MAX; k += d {
				cnt += freq[r][k]
			}
			prod = prod * int64(cnt) % MOD
			if cnt == 0 {
				break
			}
		}
		if mu[d] == 1 {
			ans = (ans + prod) % MOD
		} else {
			ans = (ans - prod + MOD) % MOD
		}
	}
	return int(ans)
}
# @lc code=end