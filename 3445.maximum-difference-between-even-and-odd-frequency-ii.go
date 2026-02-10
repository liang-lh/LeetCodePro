#
# @lc app=leetcode id=3445 lang=golang
#
# [3445] Maximum Difference Between Even and Odd Frequency II
#

# @lc code=start
func maxDifference(s string, k int) int {
	const (
		INF  = 30005
		MINF = -30005
	)
	n := len(s)
	ans := MINF
	freq := [5]int{}
	for l := 0; l < n; l++ {
		for i := 0; i < 5; i++ {
			freq[i] = 0
		}
		for r := l; r < n; r++ {
			c := int(s[r] - '0')
			freq[c]++
			if r - l + 1 >= k {
				mxo := 0
				mne := INF
				for cc := 0; cc < 5; cc++ {
					f := freq[cc]
					if f&1 == 1 {
						if f > mxo {
							mxo = f
						}
					}
					if f&1 == 0 && f >= 2 {
						if f < mne {
							mne = f
						}
					}
				}
				if mxo > 0 && mne < INF {
					if mxo - mne > ans {
						ans = mxo - mne
					}
				}
			}
		}
	}
	if ans == MINF {
		return -1
	}
	return ans
}
# @lc code=end