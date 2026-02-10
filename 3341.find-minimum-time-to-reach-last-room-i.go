//
// @lc app=leetcode id=3341 lang=golang
//
// [3341] Find Minimum Time to Reach Last Room I
//

import "container/heap"

// @lc code=start
func minTimeToReach(moveTime [][]int) int {
    n := len(moveTime)
    m := len(moveTime[0])
    
    dist := make([][]int, n)
    for i := range dist {
        dist[i] = make([]int, m)
        for j := range dist[i] {
            dist[i][j] = int(1e9)
        }
    }
    dist[0][0] = 0
    
    pq := &PriorityQueue{}
    heap.Init(pq)
    heap.Push(pq, &State{time: 0, row: 0, col: 0})
    
    dirs := [][]int{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}
    
    for pq.Len() > 0 {
        curr := heap.Pop(pq).(*State)
        currTime, r, c := curr.time, curr.row, curr.col
        
        if r == n-1 && c == m-1 {
            return currTime
        }
        
        if currTime > dist[r][c] {
            continue
        }
        
        for _, dir := range dirs {
            nr, nc := r+dir[0], c+dir[1]
            
            if nr < 0 || nr >= n || nc < 0 || nc >= m {
                continue
            }
            
            arrivalTime := currTime
            if moveTime[nr][nc] > currTime {
                arrivalTime = moveTime[nr][nc]
            }
            arrivalTime++
            
            if arrivalTime < dist[nr][nc] {
                dist[nr][nc] = arrivalTime
                heap.Push(pq, &State{time: arrivalTime, row: nr, col: nc})
            }
        }
    }
    
    return dist[n-1][m-1]
}

type State struct {
    time int
    row  int
    col  int
}

type PriorityQueue []*State

func (pq PriorityQueue) Len() int { return len(pq) }
func (pq PriorityQueue) Less(i, j int) bool { return pq[i].time < pq[j].time }
func (pq PriorityQueue) Swap(i, j int) { pq[i], pq[j] = pq[j], pq[i] }
func (pq *PriorityQueue) Push(x interface{}) { *pq = append(*pq, x.(*State)) }
func (pq *PriorityQueue) Pop() interface{} {
    old := *pq
    n := len(old)
    item := old[n-1]
    *pq = old[0 : n-1]
    return item
}
// @lc code=end