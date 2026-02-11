#
# @lc app=leetcode id=3414 lang=java
#
# [3414] Maximum Score of Non-overlapping Intervals
#

# @lc code=start
class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        List<int[]> ints = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> row = intervals.get(i);
            ints.add(new int[]{row.get(0), row.get(1), row.get(2), i});
        }
        ints.sort((a, b) -> Integer.compare(a[1], b[1]));
        int[] ends = new int[n];
        for (int i = 0; i < n; i++) ends[i] = ints.get(i)[1];
        long[][] scores = new long[n + 1][5];
        List<Integer>[][] paths = new List[n + 1][5];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < 5; j++) {
                paths[i][j] = new ArrayList<Integer>();
                scores[i][j] = (j == 0 ? 0L : -1L);
            }
        }
        Comparator<List<Integer>> lexComp = (List<Integer> a, List<Integer> b) -> {
            int lena = a.size(), lenb = b.size();
            int minl = Math.min(lena, lenb);
            for (int k = 0; k < minl; k++) {
                int cmp = Integer.compare(a.get(k), b.get(k));
                if (cmp != 0) return cmp;
            }
            return Integer.compare(lena, lenb);
        };
        for (int i = 0; i < n; i++) {
            int L = ints.get(i)[0];
            int w = ints.get(i)[2];
            int idx = ints.get(i)[3];
            // binary search largest q < i with ends[q] < L
            int q = -1;
            int lo = 0, hi = i - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (ends[mid] < L) {
                    q = mid;
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            for (int j = 1; j <= 4; j++) {
                // not take
                scores[i + 1][j] = scores[i][j];
                paths[i + 1][j].clear();
                paths[i + 1][j].addAll(paths[i][j]);
                // take?
                long prev_s = -1L;
                List<Integer> prev_p = new ArrayList<>();
                boolean can_take = false;
                if (q == -1) {
                    if (j == 1) {
                        prev_s = 0L;
                        can_take = true;
                    }
                } else {
                    long ps = scores[q + 1][j - 1];
                    if (ps != -1L) {
                        prev_s = ps;
                        prev_p.addAll(paths[q + 1][j - 1]);
                        can_take = true;
                    }
                }
                if (can_take) {
                    long new_s = prev_s + (long) w;
                    List<Integer> new_p = new ArrayList<>(prev_p);
                    new_p.add(idx);
                    Collections.sort(new_p);
                    long cur_s = scores[i + 1][j];
                    boolean upd = (new_s > cur_s) || (new_s == cur_s && lexComp.compare(new_p, paths[i + 1][j]) < 0);
                    if (upd) {
                        scores[i + 1][j] = new_s;
                        paths[i + 1][j].clear();
                        paths[i + 1][j].addAll(new_p);
                    }
                }
            }
        }
        long max_score = -1L;
        List<Integer> best_path = new ArrayList<>();
        for (int j = 0; j <= 4; j++) {
            long ts = scores[n][j];
            if (ts == -1L && j > 0) continue;
            List<Integer> tp = paths[n][j];
            boolean is_better = (ts > max_score) || (ts == max_score && lexComp.compare(tp, best_path) < 0);
            if (is_better) {
                max_score = ts;
                best_path.clear();
                best_path.addAll(tp);
            }
        }
        int[] res = new int[best_path.size()];
        for (int k = 0; k < best_path.size(); k++) {
            res[k] = best_path.get(k);
        }
        return res;
    }
}
# @lc code=end