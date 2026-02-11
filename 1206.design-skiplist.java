#
# @lc app=leetcode id=1206 lang=java
#
# [1206] Design Skiplist
#

# @lc code=start
class Skiplist {

    private static final int MAX_LEVEL = 16;

    private Node head;
    private int maxLevel;

    private static class Node {
        int val;
        Node[] forward;
        Node(int val, int level) {
            this.val = val;
            forward = new Node[level];
        }
    }

    private int randomLevel() {
        int level = 1;
        while (Math.random() < 0.25 && level < MAX_LEVEL) {
            ++level;
        }
        return level;
    }

    public Skiplist() {
        head = new Node(-1, MAX_LEVEL);
        maxLevel = 1;
    }

    public boolean search(int target) {
        Node cur = head;
        for (int i = maxLevel - 1; i >= 0; --i) {
            while (cur.forward[i] != null && cur.forward[i].val < target) {
                cur = cur.forward[i];
            }
        }
        cur = cur.forward[0];
        return cur != null && cur.val == target;
    }

    public void add(int num) {
        Node[] update = new Node[MAX_LEVEL];
        Node cur = head;
        for (int i = maxLevel - 1; i >= 0; --i) {
            while (cur.forward[i] != null && cur.forward[i].val < num) {
                cur = cur.forward[i];
            }
            update[i] = cur;
        }
        int level = randomLevel();
        if (level > maxLevel) {
            for (int i = maxLevel; i < level; ++i) {
                update[i] = head;
            }
            maxLevel = level;
        }
        Node newNode = new Node(num, level);
        for (int i = 0; i < level; ++i) {
            newNode.forward[i] = update[i].forward[i];
            update[i].forward[i] = newNode;
        }
    }

    public boolean erase(int num) {
        Node[] update = new Node[MAX_LEVEL];
        Node cur = head;
        for (int i = maxLevel - 1; i >= 0; --i) {
            while (cur.forward[i] != null && cur.forward[i].val < num) {
                cur = cur.forward[i];
            }
            update[i] = cur;
        }
        Node cand = update[0].forward[0];
        if (cand == null || cand.val != num) {
            return false;
        }
        for (int i = 0; i < maxLevel; ++i) {
            if (update[i].forward[i] == cand) {
                update[i].forward[i] = cand.forward[i];
            }
        }
        return true;
    }
}

/**
 * Your Skiplist object will be instantiated and called as such:
 * Skiplist obj = new Skiplist();
 * boolean param_1 = obj.search(target);
 * obj.add(num);
 * boolean param_3 = obj.erase(num);
 */
# @lc code=end