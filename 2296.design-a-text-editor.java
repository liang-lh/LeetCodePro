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
    
    public void addText(String text) {
        left.append(text);
    }
    
    public int deleteText(int k) {
        int deleted = Math.min(k, left.length());
        left.setLength(left.length() - deleted);
        return deleted;
    }
    
    public String cursorLeft(int k) {
        int moves = Math.min(k, left.length());
        for (int i = 0; i < moves; i++) {
            right.append(left.charAt(left.length() - 1));
            left.setLength(left.length() - 1);
        }
        return getLastChars();
    }
    
    public String cursorRight(int k) {
        int moves = Math.min(k, right.length());
        for (int i = 0; i < moves; i++) {
            left.append(right.charAt(right.length() - 1));
            right.setLength(right.length() - 1);
        }
        return getLastChars();
    }
    
    private String getLastChars() {
        int len = Math.min(10, left.length());
        return left.substring(left.length() - len);
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