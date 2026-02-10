#
# @lc app=leetcode id=3387 lang=golang
#
# [3387] Maximize Amount After Two Days of Conversions
#

# @lc code=start
func maxAmount(initialCurrency string, pairs1 [][]string, rates1 []float64, pairs2 [][]string, rates2 []float64) float64 {
		currencies := make(map[string]bool)
		currencies[initialCurrency] = true
		for _, p := range pairs1 {
			currencies[p[0]] = true
			currencies[p[1]] = true
		}
		for _, p := range pairs2 {
			currencies[p[0]] = true
			currencies[p[1]] = true
		}
		currlist := make([]string, 0, len(currencies))
		for c := range currencies {
			currlist = append(currlist, c)
		}
		N := len(currlist)
		idmap := make(map[string]int, N)
		for i, c := range currlist {
			idmap[c] = i
		}
		sid := idmap[initialCurrency]

		maxf := func(a, b float64) float64 {
			if a > b {
				return a
			}
			return b
		}

		// day1
		dist1 := make([][]float64, N)
		for i := 0; i < N; i++ {
			dist1[i] = make([]float64, N)
			for j := 0; j < N; j++ {
				if i == j {
					dist1[i][j] = 1.0
				} else {
					dist1[i][j] = 0.0
				}
			}
		}
		for idx := range pairs1 {
			a := idmap[pairs1[idx][0]]
			b := idmap[pairs1[idx][1]]
			r := rates1[idx]
			dist1[a][b] = maxf(dist1[a][b], r)
			dist1[b][a] = maxf(dist1[b][a], 1.0 / r)
		}
		// FW day1
		for k := 0; k < N; k++ {
			for i := 0; i < N; i++ {
				for j := 0; j < N; j++ {
					if dist1[i][k] > 0 && dist1[k][j] > 0 {
						dist1[i][j] = maxf(dist1[i][j], dist1[i][k] * dist1[k][j])
					}
				}
			}
		}

		// day2
		dist2 := make([][]float64, N)
		for i := 0; i < N; i++ {
			dist2[i] = make([]float64, N)
			for j := 0; j < N; j++ {
				if i == j {
					dist2[i][j] = 1.0
				} else {
					dist2[i][j] = 0.0
				}
			}
		}
		for idx := range pairs2 {
			a := idmap[pairs2[idx][0]]
			b := idmap[pairs2[idx][1]]
			r := rates2[idx]
			dist2[a][b] = maxf(dist2[a][b], r)
			dist2[b][a] = maxf(dist2[b][a], 1.0 / r)
		}
		// FW day2
		for k := 0; k < N; k++ {
			for i := 0; i < N; i++ {
				for j := 0; j < N; j++ {
					if dist2[i][k] > 0 && dist2[k][j] > 0 {
						dist2[i][j] = maxf(dist2[i][j], dist2[i][k] * dist2[k][j])
					}
				}
			}
		}

		maxamt := 1.0
		for i := 0; i < N; i++ {
			amt := dist1[sid][i] * dist2[i][sid]
			if amt > maxamt {
				maxamt = amt
			}
		}
		return maxamt
}
# @lc code=end