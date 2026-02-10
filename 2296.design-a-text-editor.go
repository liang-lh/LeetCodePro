#
# @lc app=leetcode id=2296 lang=golang
#
# [2296] Design a Text Editor
#

# @lc code=start
type TextEditor struct {
	left  []byte
	right []byte
}


func Constructor() TextEditor {
	return TextEditor{}
}


func (this *TextEditor) AddText(text string)  {
	this.left = append(this.left, []byte(text)...)
}


func (this *TextEditor) DeleteText(k int) int {
	n := len(this.left)
	del := k
	if del > n {
		del = n
	}
	this.left = this.left[:n-del]
	return del
}


func (this *TextEditor) CursorLeft(k int) string {
	n := len(this.left)
	moves := k
	if moves > n {
		moves = n
	}
	for i := 0; i < moves; i++ {
		ch := this.left[n-1]
		this.left = this.left[:n-1]
		this.right = append(this.right, ch)
		n--
	}
	l := len(this.left)
	start := 0
	if l > 10 {
		start = l - 10
	}
	return string(this.left[start:])
}


func (this *TextEditor) CursorRight(k int) string {
	n := len(this.right)
	moves := k
	if moves > n {
		moves = n
	}
	for i := 0; i < moves; i++ {
		ch := this.right[n-1]
		this.right = this.right[:n-1]
		this.left = append(this.left, ch)
		n--
	}
	l := len(this.left)
	start := 0
	if l > 10 {
		start = l - 10
	}
	return string(this.left[start:])
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