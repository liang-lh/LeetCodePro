#
# @lc app=leetcode id=1670 lang=golang
#
# [1670] Design Front Middle Back Queue
#

# @lc code=start
import "container/list"

type FrontMiddleBackQueue struct {
	q *list.List
}


func Constructor() FrontMiddleBackQueue {
	return FrontMiddleBackQueue{list.New()}
}


func (this *FrontMiddleBackQueue) PushFront(val int)  {
	this.q.PushFront(val)
}


func (this *FrontMiddleBackQueue) PushMiddle(val int)  {
	if this.q.Len() == 0 {
		this.q.PushFront(val)
		return
	}
	n := this.q.Len()
	pos := n / 2
	it := this.q.Front()
	for i := 0; i < pos; i++ {
		it = it.Next()
	}
	this.q.InsertBefore(val, it)
}


func (this *FrontMiddleBackQueue) PushBack(val int)  {
	this.q.PushBack(val)
}


func (this *FrontMiddleBackQueue) PopFront() int {
	if this.q.Len() == 0 {
		return -1
	}
	e := this.q.Front()
	val := e.Value.(int)
	this.q.Remove(e)
	return val
}


func (this *FrontMiddleBackQueue) PopMiddle() int {
	n := this.q.Len()
	if n == 0 {
		return -1
	}
	pos := (n - 1) / 2
	it := this.q.Front()
	for i := 0; i < pos; i++ {
		it = it.Next()
	}
	val := it.Value.(int)
	this.q.Remove(it)
	return val
}


func (this *FrontMiddleBackQueue) PopBack() int {
	if this.q.Len() == 0 {
		return -1
	}
	e := this.q.Back()
	val := e.Value.(int)
	this.q.Remove(e)
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