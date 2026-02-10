#
# @lc app=leetcode id=3677 lang=golang
#
# [3677] Count Binary Palindromic Numbers
#

# @lc code=start
func countBinaryPalindromes(n int64) int {
	if n == 0 {
		return 1
	}

	un := uint64(n)
	L := 0
	tmp := un
	for tmp > 0 {
		tmp >>= 1
		L++
	}

	ans := int64(1)
	for l := 1; l < L; l++ {
		h := (l + 1) / 2
		ans += int64(1 << (h - 1))
	}

	h := (L + 1) / 2
	m := L / 2
	leftMin := uint64(1) << uint(h - 1)
	for left := leftMin; left < (uint64(1) << uint(h)); left++ {
		num := left << uint(L - h)
		rev := uint64(0)
		for i := 0; i < m; i++ {
			if left & (uint64(1) << uint(h - 1 - i)) != 0 {
				rev |= uint64(1) << uint(i)
			}
		}
		num |= rev
		if num <= un {
			ans++
		} else {
			break
		}
	}

	return int(ans)
}
# @lc code=end