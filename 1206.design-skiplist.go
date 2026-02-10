#
# @lc app=leetcode id=1206 lang=golang
#
# [1206] Design Skiplist
#

import (
	"math/rand"
)

const MAX_LEVEL = 16

type Node struct {
	val     int
	forward []*Node
}

type Skiplist struct {
	head *Node
}


func Constructor() Skiplist {
	head := &Node{
		val:     -1,
		forward: make([]*Node, MAX_LEVEL+1),
	}
	return Skiplist{head: head}
}


func randomLevel() int {
	level := 1
	for level < MAX_LEVEL && rand.Float64() < 0.5 {
		level++
	}
	return level
}

func (this *Skiplist) Search(target int) bool {
	curr := this.head
	for i := MAX_LEVEL; i >= 0; i-- {
		for curr.forward[i] != nil && curr.forward[i].val < target {
			curr = curr.forward[i]
		}
	}
	next := curr.forward[0]
	return next != nil && next.val == target
}

func (this *Skiplist) Add(num int) {
	update := make([]*Node, MAX_LEVEL+1)
	curr := this.head
	for i := MAX_LEVEL; i >= 0; i-- {
		for curr.forward[i] != nil && curr.forward[i].val < num {
			curr = curr.forward[i]
		}
		update[i] = curr
	}
	level := randomLevel()
	newnode := &Node{
		val:     num,
		forward: make([]*Node, level+1),
	}
	for i := 0; i <= level; i++ {
		newnode.forward[i] = update[i].forward[i]
		update[i].forward[i] = newnode
	}
}

func (this *Skiplist) Erase(num int) bool {
	update := make([]*Node, MAX_LEVEL+1)
	curr := this.head
	for i := MAX_LEVEL; i >= 0; i-- {
		for curr.forward[i] != nil && curr.forward[i].val < num {
			curr = curr.forward[i]
		}
		update[i] = curr
	}
	if update[0].forward[0] == nil || update[0].forward[0].val != num {
		return false
	}
	for i := 0; i <= MAX_LEVEL; i++ {
		if update[i].forward[i] != nil && update[i].forward[i].val == num {
			update[i].forward[i] = update[i].forward[i].forward[i]
		}
	}
	return true
}


/**
 * Your Skiplist object will be instantiated and called as such:
 * obj := Constructor();
 * param_1 := obj.Search(target);
 * obj.Add(num);
 * param_3 := obj.Erase(num);
 */
# @lc code=end