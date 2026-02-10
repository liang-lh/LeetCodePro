#
# @lc app=leetcode id=2296 lang=golang
#
# [2296] Design a Text Editor
#
# @lc code=start
type TextEditor struct {
    left  []rune  // characters to the left of cursor
    right []rune  // characters to the right of cursor (reversed)
}


func Constructor() TextEditor {
    return TextEditor{
        left:  []rune{},
        right: []rune{},
    }
}


func (this *TextEditor) AddText(text string)  {
    for _, ch := range text {
        this.left = append(this.left, ch)
    }
}


func (this *TextEditor) DeleteText(k int) int {
    deleted := min(k, len(this.left))
    this.left = this.left[:len(this.left)-deleted]
    return deleted
}


func (this *TextEditor) CursorLeft(k int) string {
    moves := min(k, len(this.left))
    for i := 0; i < moves; i++ {
        ch := this.left[len(this.left)-1]
        this.left = this.left[:len(this.left)-1]
        this.right = append(this.right, ch)
    }
    return this.getLastTenLeft()
}


func (this *TextEditor) CursorRight(k int) string {
    moves := min(k, len(this.right))
    for i := 0; i < moves; i++ {
        ch := this.right[len(this.right)-1]
        this.right = this.right[:len(this.right)-1]
        this.left = append(this.left, ch)
    }
    return this.getLastTenLeft()
}

func (this *TextEditor) getLastTenLeft() string {
    start := max(0, len(this.left)-10)
    return string(this.left[start:])
}

func min(a, b int) int {
    if a < b {
        return a
    }
    return b
}

func max(a, b int) int {
    if a > b {
        return a
    }
    return b
}

/**
* Your TextEditor object will be instantiated and called as such:
* obj := Constructor();
* obj.AddText(text);
* param_2 := obj.DeleteText(k);
* param_3 := obj.CursorLeft(k);
* param_4 := obj.CursorRight(k);
*/
# @lc code=end