#
# @lc app=leetcode id=1670 lang=golang
#
# [1670] Design Front Middle Back Queue
#

# @lc code=start
type FrontMiddleBackQueue struct {

	q []int
}


func Constructor() FrontMiddleBackQueue {

	return FrontMiddleBackQueue{q: []int{}}
}


func (this *FrontMiddleBackQueue) PushFront(val int)  {

	this.q = append([]int{val}, this.q...)
}


func (this *FrontMiddleBackQueue) PushMiddle(val int)  {

	pos := len(this.q) / 2

	this.q = append(this.q[:pos], append([]int{val}, this.q[pos:]...)...)
}


func (this *FrontMiddleBackQueue) PushBack(val int)  {

	this.q = append(this.q, val)
}


func (this *FrontMiddleBackQueue) PopFront() int {

	if len(this.q) == 0 {

		return -1

	}

	val := this.q[0]

	this.q = this.q[1:]

	return val
}


func (this *FrontMiddleBackQueue) PopMiddle() int {

	if len(this.q) == 0 {

		return -1

	}

	mid := (len(this.q) - 1) / 2

	val := this.q[mid]

	this.q = append(this.q[:mid], this.q[mid+1:]...)

	return val
}


func (this *FrontMiddleBackQueue) PopBack() int {

	if len(this.q) == 0 {

		return -1

	}

	n := len(this.q)

	val := this.q[n-1]

	this.q = this.q[:n-1]

	return val
}


/**
 * Your FrontMiddleBackQueue object will be instantiated and called as such:
 * obj := Constructor();
 * obj.PushFront(val);
 * obj.PushMiddle(val);
 * obj.PushBack(val);
 * param_4 := obj.PopFront();
 * param_5 := obj.PopMiddle();
 * param_6 := obj.PopBack();
 */
# @lc code=end