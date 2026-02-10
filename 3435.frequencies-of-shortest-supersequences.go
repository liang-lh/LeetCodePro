#
# @lc app=leetcode id=3435 lang=golang
#
# [3435] Frequencies of Shortest Supersequences
#
# @lc code=start
func supersequences(words []string) [][]int {
    // Extract unique characters
    charSet := make(map[rune]bool)
    for _, word := range words {
        for _, ch := range word {
            charSet[ch] = true
        }
    }
    chars := make([]rune, 0, len(charSet))
    for ch := range charSet {
        chars = append(chars, ch)
    }
    
    // BFS state: (progress array, sequence string)
    type State struct {
        progress []int
        seq string
    }
    
    queue := []State{{make([]int, len(words)), ""}}
    
    for len(queue) > 0 {
        nextQueue := []State{}
        seen := make(map[string]bool)
        
        for _, state := range queue {
            // Check if complete
            complete := true
            for i := range words {
                if state.progress[i] < 2 {
                    complete = false
                    break
                }
            }
            if complete {
                // Found shortest, collect all at this level
                results := []string{state.seq}
                for _, s := range queue {
                    allDone := true
                    for i := range words {
                        if s.progress[i] < 2 {
                            allDone = false
                            break
                        }
                    }
                    if allDone && s.seq != state.seq {
                        results = append(results, s.seq)
                    }
                }
                
                // Convert to frequency arrays and deduplicate
                freqSet := make(map[string][]int)
                for _, seq := range results {
                    freq := make([]int, 26)
                    for _, ch := range seq {
                        freq[ch-'a']++
                    }
                    key := ""
                    for _, f := range freq {
                        key += string(rune(f))
                    }
                    freqSet[key] = freq
                }
                
                result := make([][]int, 0, len(freqSet))
                for _, freq := range freqSet {
                    result = append(result, freq)
                }
                return result
            }
            
            // Try each character
            for _, ch := range chars {
                newProgress := make([]int, len(words))
                copy(newProgress, state.progress)
                
                for i, word := range words {
                    if newProgress[i] < len(word) && rune(word[newProgress[i]]) == ch {
                        newProgress[i]++
                    }
                }
                
                newSeq := state.seq + string(ch)
                
                // State deduplication
                key := ""
                for _, p := range newProgress {
                    key += string(rune(p))
                }
                key += newSeq
                
                if !seen[key] {
                    seen[key] = true
                    nextQueue = append(nextQueue, State{newProgress, newSeq})
                }
            }
        }
        
        queue = nextQueue
    }
    
    return [][]int{}
}
# @lc code=end