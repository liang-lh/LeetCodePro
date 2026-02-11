#
# @lc app=leetcode id=3777 lang=java
#
# [3777] Minimum Deletions to Make Alternating Substring
#

# @lc code=start
class Solution {
    private static class FenwickTree {
        private int[] tree;
        private int n;

        public FenwickTree(int size) {
            this.n = size;
            this.tree = new int[size + 1];
        }

        public void update(int index, int delta) {
            while (index <= this.n) {
                this.tree[index] += delta;
                index += index & -index;
            }
        }

        public int query(int index) {
            int sum = 0;
            while (index > 0) {
                sum += this.tree[index];
                index -= index & -index;
            }
            return sum;
        }

        public int query(int left, int right) {
            return this.query(right) - this.query(left - 1);
        }
    }

    public int[] minDeletions(String s, int[][] queries) {
        int n = s.length();
        char[] ch = s.toCharArray();
        FenwickTree ft = new FenwickTree(n - 1);
        for (int i = 0; i < n - 1; ++i) {
            int d = (ch[i] != ch[i + 1]) ? 1 : 0;
            ft.update(i + 1, d);
        }
        int[] answer = new int[queries.length];
        int ansIdx = 0;
        for (int[] q : queries) {
            if (q[0] == 1) {
                int j = q[1];
                int old_left = (j > 0) ? ((ch[j - 1] != ch[j]) ? 1 : 0) : 0;
                int old_right = (j < n - 1) ? ((ch[j] != ch[j + 1]) ? 1 : 0) : 0;
                ch[j] = (ch[j] == 'A') ? 'B' : 'A';
                int new_left = (j > 0) ? ((ch[j - 1] != ch[j]) ? 1 : 0) : 0;
                int new_right = (j < n - 1) ? ((ch[j] != ch[j + 1]) ? 1 : 0) : 0;
                if (j > 0) {
                    ft.update(j, new_left - old_left);
                }
                if (j < n - 1) {
                    ft.update(j + 1, new_right - old_right);
                }
            } else {
                int l = q[1];
                int r = q[2];
                int sum_diff = (l < r) ? ft.query(l + 1, r) : 0;
                int del = (r - l) - sum_diff;
                answer[ansIdx++] = del;
            }
        }
        return java.util.Arrays.copyOf(answer, ansIdx);
    }
}
# @lc code=end