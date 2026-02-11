#
# @lc app=leetcode id=2296 lang=java
#
# [2296] Design a Text Editor
#

# @lc code=start
class TextEditor {

    private StringBuilder left;
    private StringBuilder right;

    public TextEditor() {
        left = new StringBuilder();
        right = new StringBuilder();
    }

    private String getLeft10() {
        int sz = left.length();
        if (sz == 0) {
            return "";
        }
        return sz <= 10 ? left.toString() : left.substring(sz - 10);
    }

    public void addText(String text) {
        left.append(text);
    }

    public int deleteText(int k) {
        int del = Math.min(k, left.length());
        left.delete(left.length() - del, left.length());
        return del;
    }

    public String cursorLeft(int k) {
        for (int i = 0; i < k; ++i) {
            if (left.length() == 0) {
                break;
            }
            char c = left.charAt(left.length() - 1);
            left.deleteCharAt(left.length() - 1);
            right.append(c);
        }
        return getLeft10();
    }

    public String cursorRight(int k) {
        for (int i = 0; i < k; ++i) {
            if (right.length() == 0) {
                break;
            }
            char c = right.charAt(right.length() - 1);
            right.deleteCharAt(right.length() - 1);
            left.append(c);
        }
        return getLeft10();
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