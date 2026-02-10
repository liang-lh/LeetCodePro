func maxRectangleArea(xCoord []int, yCoord []int) int64 {
    n := len(xCoord)
    if n < 4 {
        return -1
    }
    
    // Component A: Build point set for O(1) lookup
    pointSet := make(map[[2]int]bool)
    for i := 0; i < n; i++ {
        pointSet[[2]int{xCoord[i], yCoord[i]}] = true
    }
    
    // Component B: Group points by x-coordinate
    xGroups := make(map[int][]int)
    for i := 0; i < n; i++ {
        xGroups[xCoord[i]] = append(xGroups[xCoord[i]], yCoord[i])
    }
    
    // Sort y-coordinates within each x-group
    xCoords := make([]int, 0, len(xGroups))
    for x := range xGroups {
        xCoords = append(xCoords, x)
        sort.Ints(xGroups[x])
    }
    sort.Ints(xCoords)
    
    maxArea := int64(-1)
    
    // Component C: For each x-coordinate pair
    for i := 0; i < len(xCoords); i++ {
        x1 := xCoords[i]
        for j := i + 1; j < len(xCoords); j++ {
            x2 := xCoords[j]
            
            // For each y on x1 line
            for _, y1 := range xGroups[x1] {
                // For each y on x2 line
                for _, y2 := range xGroups[x2] {
                    if y1 == y2 {
                        continue
                    }
                    
                    yMin, yMax := y1, y2
                    if yMin > yMax {
                        yMin, yMax = yMax, yMin
                    }
                    
                    // Check if other two corners exist
                    if !pointSet[[2]int{x1, yMax}] || !pointSet[[2]int{x2, yMin}] {
                        continue
                    }
                    
                    // Validate no points inside or on borders (except corners)
                    valid := true
                    
                    // Check all x-coordinates between x1 and x2
                    for k := 0; k < len(xCoords) && valid; k++ {
                        x := xCoords[k]
                        if x <= x1 || x >= x2 {
                            continue
                        }
                        
                        // Binary search for points in y range [yMin, yMax]
                        for _, y := range xGroups[x] {
                            if y >= yMin && y <= yMax {
                                valid = false
                                break
                            }
                        }
                    }
                    
                    if !valid {
                        continue
                    }
                    
                    // Check x1 line for points strictly between yMin and yMax
                    for _, y := range xGroups[x1] {
                        if y > yMin && y < yMax {
                            valid = false
                            break
                        }
                    }
                    
                    if !valid {
                        continue
                    }
                    
                    // Check x2 line for points strictly between yMin and yMax
                    for _, y := range xGroups[x2] {
                        if y > yMin && y < yMax {
                            valid = false
                            break
                        }
                    }
                    
                    if valid {
                        area := int64(x2-x1) * int64(yMax-yMin)
                        if area > maxArea {
                            maxArea = area
                        }
                    }
                }
            }
        }
    }
    
    return maxArea
}