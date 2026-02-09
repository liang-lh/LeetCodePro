#
# @lc app=leetcode id=1670 lang=java
#
# [1670] Design Front Middle Back Queue
#
# @lc code=start
import java.util.LinkedList;

class FrontMiddleBackQueue {
    LinkedList<Integer> left;
    LinkedList<Integer> right;

    public FrontMiddleBackQueue() {
        left = new LinkedList<>();
        right = new LinkedList<>();
    }

    private void balance() {
        // Maintain the invariant: left.size() == right.size() or left.size() == right.size() + 1
        while (left.size() > right.size() + 1) {
            right.addFirst(left.removeLast());
        }
        while (left.size() < right.size()) {
            left.addLast(right.removeFirst());
        }
    }

    public void pushFront(int val) {
        left.addFirst(val);
        balance();
    }

    public void pushMiddle(int val) {
        // Ensure the new element becomes the frontmost middle in even-sized queues
        if (left.size() > right.size()) {
            right.addFirst(left.removeLast());
        }
        left.addLast(val);
        balance();
    }

    public void pushBack(int val) {
        right.addLast(val);
        balance();
    }

    public int popFront() {
        if (left.isEmpty() && right.isEmpty()) return -1;
        int res;
        if (!left.isEmpty()) {
            res = left.removeFirst();
        } else {
            res = right.removeFirst();
        }
        balance();
        return res;
    }

    public int popMiddle() {
        if (left.isEmpty() && right.isEmpty()) return -1;
        // Always remove the end of left (frontmost middle in even-sized queue)
        int res = left.removeLast();
        balance();
        return res;
    }

    public int popBack() {
        if (left.isEmpty() && right.isEmpty()) return -1;
        int res;
        if (!right.isEmpty()) {
            res = right.removeLast();
        } else {
            res = left.removeLast();
        }
        balance();
        return res;
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