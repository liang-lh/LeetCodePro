#
# @lc app=leetcode id=3382 lang=java
#
# [3382] Maximum Area Rectangle With Point Constraints II
#

# @lc code=start
import java.util.*;

class Solution {

    static class MergeSortTree {
        List<Integer>[] tree;
        int n;

        @SuppressWarnings("unchecked")
        MergeSortTree(List<List<Integer>> colYs) {
            n = colYs.size();
            tree = new ArrayList[4 * n];
            for (int i = 0; i < 4 * n; ++i) {
                tree[i] = new ArrayList<>();
            }
            build(1, 0, n - 1, colYs);
        }

        void build(int node, int start, int end, List<List<Integer>> colYs) {
            if (start == end) {
                tree[node].addAll(colYs.get(start));
                return;
            }
            int mid = (start + end) / 2;
            build(2 * node, start, mid, colYs);
            build(2 * node + 1, mid + 1, end, colYs);
            List<Integer> left = tree[2 * node];
            List<Integer> right = tree[2 * node + 1];
            List<Integer> merged = tree[node];
            int i = 0, j = 0;
            while (i < left.size() && j < right.size()) {
                if (left.get(i) <= right.get(j)) {
                    merged.add(left.get(i++));
                } else {
                    merged.add(right.get(j++));
                }
            }
            while (i < left.size()) merged.add(left.get(i++));
            while (j < right.size()) merged.add(right.get(j++));
        }

        boolean hasPointInRange(int ql, int qr, int yl, int yr) {
            if (ql > qr) return false;
            return query(1, 0, n - 1, ql, qr, yl, yr);
        }

        private boolean query(int node, int start, int end, int ql, int qr, int yl, int yr) {
            if (qr < start || ql > end) return false;
            if (ql <= start && end <= qr) {
                List<Integer> lst = tree[node];
                int idx = lowerBound(lst, yl);
                return idx < lst.size() && lst.get(idx) <= yr;
            }
            int mid = (start + end) / 2;
            return query(2 * node, start, mid, ql, qr, yl, yr) ||
                   query(2 * node + 1, mid + 1, end, ql, qr, yl, yr);
        }

        private int lowerBound(List<Integer> lst, int val) {
            int low = 0, high = lst.size();
            while (low < high) {
                int m = (low + high) / 2;
                if (lst.get(m) < val) {
                    low = m + 1;
                } else {
                    high = m;
                }
            }
            return low;
        }
    }

    public long maxRectangleArea(int[] xCoord, int[] yCoord) {
        int n = xCoord.length;
        Map<Integer, List<Integer>> xToY = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            xToY.computeIfAbsent(xCoord[i], k -> new ArrayList<>()).add(yCoord[i]);
        }
        List<Integer> xs = new ArrayList<>(xToY.keySet());
        Collections.sort(xs);
        int m = xs.size();
        if (m < 2) return -1;
        List<List<Integer>> colYs = new ArrayList<>(m);
        for (int i = 0; i < m; ++i) {
            List<Integer> ys = xToY.get(xs.get(i));
            Collections.sort(ys);
            colYs.add(ys);
        }
        Map<Long, List<Integer>> heightToCands = new HashMap<>();
        for (int i = 0; i < m; ++i) {
            List<Integer> ys = colYs.get(i);
            if (ys.size() < 2) continue;
            for (int j = 0; j < ys.size() - 1; ++j) {
                int y1 = ys.get(j);
                int y2 = ys.get(j + 1);
                long key = ((long) y1 << 32) | y2;
                heightToCands.computeIfAbsent(key, k -> new ArrayList<>()).add(i);
            }
        }
        MergeSortTree mst = new MergeSortTree(colYs);
        long maxArea = -1;
        for (Map.Entry<Long, List<Integer>> entry : heightToCands.entrySet()) {
            List<Integer> cands = entry.getValue();
            if (cands.size() < 2) continue;
            Collections.sort(cands);
            long key = entry.getKey();
            int y1 = (int) (key >> 32);
            int y2 = (int) key;
            for (int p = 0; p < cands.size() - 1; ++p) {
                int left = cands.get(p);
                int right = cands.get(p + 1);
                if (!mst.hasPointInRange(left + 1, right - 1, y1, y2)) {
                    long width = (long) xs.get(right) - xs.get(left);
                    long h = (long) y2 - y1;
                    long area = width * h;
                    if (area > maxArea) maxArea = area;
                }
            }
        }
        return maxArea;
    }
}
# @lc code=end