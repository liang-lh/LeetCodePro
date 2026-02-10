#
# @lc app=leetcode id=3515 lang=golang
#
# [3515] Shortest Path in a Weighted Tree
#

# @lc code=start
type Pair struct {
    to int
    w  int
}

type Fenwick struct {
    tree []int
    n    int
}

func (f *Fenwick) update(idx int, val int) {
    for idx <= f.n {
        f.tree[idx] += val
        idx += idx & -idx
    }
}

func (f *Fenwick) prefix(idx int) int {
    res := 0
    for idx > 0 {
        res += f.tree[idx]
        idx -= idx & -idx
    }
    return res
}

type Frame struct {
    Node, Par, Wto, ChildIdx int
}

func treeQueries(n int, edges [][]int, queries [][]int) []int {
    adj := make([][]Pair, n+1)
    for _, e := range edges {
        u, v, w := e[0], e[1], e[2]
        adj[u] = append(adj[u], Pair{to: v, w: w})
        adj[v] = append(adj[v], Pair{to: u, w: w})
    }
    parent := make([]int, n+1)
    edgeToParent := make([]int, n+1)
    dfn := make([]int, n+1)
    sz := make([]int, n+1)
    timer := 1
    stack := []Frame{{Node: 1, Par: 0, Wto: 0, ChildIdx: 0}}
    for len(stack) > 0 {
        idx := len(stack) - 1
        f := &stack[idx]
        if f.ChildIdx == 0 {
            dfn[f.Node] = timer
            timer++
            parent[f.Node] = f.Par
            edgeToParent[f.Node] = f.Wto
        }
        found := false
        for f.ChildIdx < len(adj[f.Node]) {
            cand := adj[f.Node][f.ChildIdx]
            f.ChildIdx++
            if cand.to != f.Par {
                stack = append(stack, Frame{Node: cand.to, Par: f.Node, Wto: cand.w, ChildIdx: 0})
                found = true
                break
            }
        }
        if !found {
            sz[f.Node] = 1
            for _, p := range adj[f.Node] {
                if p.to != f.Par {
                    sz[f.Node] += sz[p.to]
                }
            }
            stack = stack[:len(stack)-1]
        }
    }
    ft := &Fenwick{
        tree: make([]int, n+3),
        n:    n + 1,
    }
    for i := 2; i <= n; i++ {
        l := dfn[i]
        r1 := l + sz[i]
        ft.update(l, edgeToParent[i])
        ft.update(r1, -edgeToParent[i])
    }
    ans := []int{}
    for _, q := range queries {
        if len(q) == 2 {
            x := q[1]
            if x == 1 {
                ans = append(ans, 0)
                continue
            }
            ans = append(ans, ft.prefix(dfn[x]))
        } else {
            u := q[1]
            v := q[2]
            wnew := q[3]
            child := v
            if parent[v] != u {
                child = u
            }
            wold := edgeToParent[child]
            delta := wnew - wold
            edgeToParent[child] = wnew
            l := dfn[child]
            r1 := l + sz[child]
            ft.update(l, delta)
            ft.update(r1, -delta)
        }
    }
    return ans
}
# @lc code=end