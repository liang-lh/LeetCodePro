#
# @lc app=leetcode id=2296 lang=java
#
# [2296] Design a Text Editor
#

# @lc code=start
import java.util.*;

class TextEditor {
    private Deque<Character> leftStack;
    private Deque<Character> rightStack;

    public TextEditor() {
        leftStack = new ArrayDeque<>();
        rightStack = new ArrayDeque<>();
    }

    public void addText(String text) {
        for (char c : text.toCharArray()) {
            leftStack.push(c);
        }
    }

    public int deleteText(int k) {
        int count = 0;
        while (k > 0 && !leftStack.isEmpty()) {
            leftStack.pop();
            count++;
            k--;
        }
        return count;
    }

    public String cursorLeft(int k) {
        while (k > 0 && !leftStack.isEmpty()) {
            rightStack.push(leftStack.pop());
            k--;
        }
        return getLast10();
    }

    public String cursorRight(int k) {
        while (k > 0 && !rightStack.isEmpty()) {
            leftStack.push(rightStack.pop());
            k--;
        }
        return getLast10();
    }

    private String getLast10() {
        StringBuilder sb = new StringBuilder();
        Iterator<Character> it = leftStack.iterator();
        ArrayList<Character> temp = new ArrayList<>();
        while (it.hasNext()) {
            temp.add(it.next());
        }
        int start = Math.max(0, temp.size() - 10);
        for (int i = start; i < temp.size(); i++) {
            sb.append(temp.get(i));
        }
        return sb.toString();
    }
}

/**
* Your TextEditor object will be instantiated and called as such:
* TextEditor obj = new TextEditor();
* obj.addText(text);
* int param_2 = obj.deleteText(k);
* String param_3 = obj.cursorLeft(k);
* String param_4 = obj.cursorRight(k);
*/
# @lc code=end