#
# @lc app=leetcode id=3670 lang=java
#
# [3670] Maximum Product of Two Integers With No Common Bits
#
# @lc code=start
class Solution {
    private static class TrieNode {
        TrieNode[] children = new TrieNode[2];
    }

    public long maxProduct(int[] nums) {
        TrieNode root = new TrieNode();
        for (int num : nums) {
            insert(root, num);
        }
        long ans = 0;
        for (int num : nums) {
            int cand = getMaxCompatible(root, num);
            ans = Math.max(ans, (long) num * cand);
        }
        return ans;
    }

    private void insert(TrieNode root, int num) {
        TrieNode curr = root;
        for (int i = 19; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if (curr.children[bit] == null) {
                curr.children[bit] = new TrieNode();
            }
            curr = curr.children[bit];
        }
    }

    private int getMaxCompatible(TrieNode root, int x) {
        TrieNode curr = root;
        int y = 0;
        for (int i = 19; i >= 0; i--) {
            int bitX = (x >> i) & 1;
            int bitY = 0;
            TrieNode next = null;
            if (bitX == 1) {
                next = curr.children[0];
            } else {
                if (curr.children[1] != null) {
                    bitY = 1;
                    next = curr.children[1];
                } else if (curr.children[0] != null) {
                    next = curr.children[0];
                }
            }
            if (next == null) {
                return 0;
            }
            y |= (bitY << i);
            curr = next;
        }
        return y;
    }
}
# @lc code=end