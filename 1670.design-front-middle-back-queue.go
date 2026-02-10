//
// @lc app=leetcode id=1670 lang=golang
//
// [1670] Design Front Middle Back Queue
//
// @lc code=start
type FrontMiddleBackQueue struct {
    queue []int
}

func Constructor() FrontMiddleBackQueue {
    return FrontMiddleBackQueue{queue: []int{}}
}

func (this *FrontMiddleBackQueue) PushFront(val int) {
    this.queue = append([]int{val}, this.queue...)
}

func (this *FrontMiddleBackQueue) PushMiddle(val int) {
    n := len(this.queue)
    mid := n / 2
    this.queue = append(this.queue[:mid], append([]int{val}, this.queue[mid:]...)...)
}

func (this *FrontMiddleBackQueue) PushBack(val int) {
    this.queue = append(this.queue, val)
}

func (this *FrontMiddleBackQueue) PopFront() int {
    if len(this.queue) == 0 {
        return -1
    }
    val := this.queue[0]
    this.queue = this.queue[1:]
    return val
}

func (this *FrontMiddleBackQueue) PopMiddle() int {
    n := len(this.queue)
    if n == 0 {
        return -1
    }
    mid := (n - 1) / 2
    val := this.queue[mid]
    this.queue = append(this.queue[:mid], this.queue[mid+1:]...)
    return val
}

func (this *FrontMiddleBackQueue) PopBack() int {
    n := len(this.queue)
    if n == 0 {
        return -1
    }
    val := this.queue[n-1]
    this.queue = this.queue[:n-1]
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
// @lc code=end