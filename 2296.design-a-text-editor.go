#
# @lc app=leetcode id=2296 lang=golang
#
# [2296] Design a Text Editor
#
import (
    "container/list"
)

# @lc code=start
type TextEditor struct {
    left  *list.List
    right *list.List
}


func Constructor() TextEditor {
    return TextEditor{list.New(), list.New()}
}


func (this *TextEditor) AddText(text string)  {
    for _, c := range text {
        this.left.PushBack(c)
    }
}


func (this *TextEditor) DeleteText(k int) int {
    deleted := 0
    for k > 0 && this.left.Len() > 0 {
        this.left.Remove(this.left.Back())
        deleted++
        k--
    }
    return deleted
}


func (this *TextEditor) getLeft10() string {
    res := []rune{}
    cur := this.left.Back()
    for i := 0; i < 10 && cur != nil; i++ {
        res = append(res, cur.Value.(rune))
        cur = cur.Prev()
    }
    for i, j := 0, len(res)-1; i < j; i, j = i+1, j-1 {
        res[i], res[j] = res[j], res[i]
    }
    return string(res)
}


func (this *TextEditor) CursorLeft(k int) string {
    for k > 0 && this.left.Len() > 0 {
        c := this.left.Back().Value.(rune)
        this.left.Remove(this.left.Back())
        this.right.PushFront(c)
        k--
    }
    return this.getLeft10()
}


func (this *TextEditor) CursorRight(k int) string {
    for k > 0 && this.right.Len() > 0 {
        c := this.right.Front().Value.(rune)
        this.right.Remove(this.right.Front())
        this.left.PushBack(c)
        k--
    }
    return this.getLeft10()
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