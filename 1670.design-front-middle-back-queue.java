#
# @lc app=leetcode id=1670 lang=java
#
# [1670] Design Front Middle Back Queue
#

import java.util.LinkedList;

# @lc code=start
class FrontMiddleBackQueue {

    LinkedList<Integer> list;

    public FrontMiddleBackQueue() {
        list = new LinkedList<Integer>();
    }

    public void pushFront(int val) {
        list.addFirst(val);
    }

    public void pushMiddle(int val) {
        list.add(list.size() / 2, val);
    }

    public void pushBack(int val) {
        list.addLast(val);
    }

    public int popFront() {
        if (list.isEmpty()) {
            return -1;
        }
        return list.removeFirst();
    }

    public int popMiddle() {
        if (list.isEmpty()) {
            return -1;
        }
        int sz = list.size();
        return list.remove((sz - 1) / 2);
    }

    public int popBack() {
        if (list.isEmpty()) {
            return -1;
        }
        return list.removeLast();
    }
}

/**
 * Your FrontMiddleBackQueue object will be instantiated and called as such:
 * FrontMiddleBackQueue obj = new FrontMiddleBackQueue();
 * obj.pushFront(val);
 * obj.pushMiddle(val);
 * obj.pushBack(val);
 * int param_4 = obj.popFront();
 * int param_5 = obj.popMiddle();
 * int param_6 = obj.popBack();
 */
# @lc code=end