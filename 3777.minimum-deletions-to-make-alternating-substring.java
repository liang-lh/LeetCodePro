#
# @lc app=leetcode id=3777 lang=java
#
# [3777] Minimum Deletions to Make Alternating Substring
#

# @lc code=start
class Solution {
    static class FenwickTree {
        private int[] tree;
        private int n;
        
        public FenwickTree(int nn) {
            n = nn;
            tree = new int[nn + 1];
        }
        
        public void update(int idx, int delta) {
            while (idx <= n) {
                tree[idx] += delta;
                idx += idx & -idx;
            }
        }
        
        public int query(int idx) {
            int sum = 0;
            while (idx > 0) {
                sum += tree[idx];
                idx -= idx & -idx;
            }
            return sum;
        }
        
        public int query(int left, int right) {
            if (left > right) return 0;
            return query(right) - query(left - 1);
        }
    }
    
    public int[] minDeletions(String s, int[][] queries) {
        int n = s.length();
        char[] sc = s.toCharArray();
        FenwickTree ft = new FenwickTree(n - 1);
        for (int i = 0; i < n - 1; i++) {
            int val = (sc[i] == sc[i + 1] ? 1 : 0);
            ft.update(i + 1, val);
        }
        java.util.List<Integer> answer = new java.util.ArrayList<>();
        for (int[] query : queries) {
            if (query.length == 2) { // flip
                int j = query[1];
                int old_eq_prev = 0;
                boolean has_prev = j > 0;
                if (has_prev) {
                    old_eq_prev = (sc[j - 1] == sc[j] ? 1 : 0);
                }
                int old_eq_next = 0;
                boolean has_next = j < n - 1;
                if (has_next) {
                    old_eq_next = (sc[j] == sc[j + 1] ? 1 : 0);
                }
                // flip
                sc[j] = (sc[j] == 'A' ? 'B' : 'A');
                // update prev pair eq[j-1]
                if (has_prev) {
                    int new_eq_prev = (sc[j - 1] == sc[j] ? 1 : 0);
                    ft.update(j, new_eq_prev - old_eq_prev);
                }
                // update next pair eq[j]
                if (has_next) {
                    int new_eq_next = (sc[j] == sc[j + 1] ? 1 : 0);
                    ft.update(j + 1, new_eq_next - old_eq_next);
                }
            } else { // query [2, l, r]
                int l = query[1];
                int r = query[2];
                int dels = (l == r) ? 0 : ft.query(l + 1, r);
                answer.add(dels);
            }
        }
        int[] res = new int[answer.size()];
        for (int i = 0; i < answer.size(); i++) {
            res[i] = answer.get(i);
        }
        return res;
    }
}
# @lc code=end