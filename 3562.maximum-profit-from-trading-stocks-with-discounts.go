#
# @lc app=leetcode id=3562 lang=golang
#
# [3562] Maximum Profit from Trading Stocks with Discounts
#

# @lc code=start
func maxProfit(n int, present []int, future []int, hierarchy [][]int, budget int) int {
	children := make([][]int, n+1)
	for _, h := range hierarchy {
		children[h[0]] = append(children[h[0]], h[1])
	}

	var dfs func(int) ([]int, []int)
	dfs = func(u int) ([]int, []int) {
		combinedNo := make([]int, 161)
		combinedYes := make([]int, 161)
		for _, v := range children[u] {
			childF, childT := dfs(v)

			newNo := make([]int, 161)
			for j := 0; j <= 160; j++ {
				newNo[j] = combinedNo[j]
				for k := 1; k <= j; k++ {
					cand := combinedNo[j-k] + childF[k]
					if cand > newNo[j] {
						newNo[j] = cand
					}
				}
			}
			combinedNo = newNo

			newYes := make([]int, 161)
			for j := 0; j <= 160; j++ {
				newYes[j] = combinedYes[j]
				for k := 1; k <= j; k++ {
					cand := combinedYes[j-k] + childT[k]
					if cand > newYes[j] {
						newYes[j] = cand
					}
				}
			}
			combinedYes = newYes
		}

		pidx := u - 1
		costFull := present[pidx]
		profitFull := future[pidx] - costFull
		costDisc := costFull / 2
		profitDisc := future[pidx] - costDisc

		dpF := make([]int, 161)
		dpT := make([]int, 161)
		for j := 0; j <= 160; j++ {
			dpF[j] = combinedNo[j]
			if j >= costFull {
				cand := profitFull + combinedYes[j-costFull]
				if cand > dpF[j] {
					dpF[j] = cand
				}
			}

			dpT[j] = combinedNo[j]
			if j >= costDisc {
				cand := profitDisc + combinedYes[j-costDisc]
				if cand > dpT[j] {
					dpT[j] = cand
				}
			}
		}

		return dpF, dpT
	}

	dpRootF, _ := dfs(1)
	return dpRootF[budget]
}

# @lc code=end