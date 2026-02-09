#
# @lc app=leetcode id=3455 lang=java
#
# [3455] Shortest Matching Substring
#
# @lc code=start
class Solution {
    public int shortestMatchingSubstring(String s, String p) {
        int star1 = p.indexOf('*');
        int star2 = p.lastIndexOf('*');
        
        String prefix = p.substring(0, star1);
        String middle = p.substring(star1 + 1, star2);
        String suffix = p.substring(star2 + 1);
        
        if (prefix.isEmpty() && middle.isEmpty() && suffix.isEmpty()) {
            return 0;
        }
        
        int n = s.length();
        int minLen = Integer.MAX_VALUE;
        
        int prefixEnd = prefix.isEmpty() ? 1 : n - prefix.length() + 1;
        for (int i = 0; i < prefixEnd; i++) {
            if (!prefix.isEmpty() && (i + prefix.length() > n || !s.substring(i, i + prefix.length()).equals(prefix))) {
                continue;
            }
            
            int afterPrefix = i + prefix.length();
            
            int middleEnd = middle.isEmpty() ? afterPrefix + 1 : n - middle.length() + 1;
            for (int j = afterPrefix; j < middleEnd; j++) {
                if (!middle.isEmpty() && (j + middle.length() > n || !s.substring(j, j + middle.length()).equals(middle))) {
                    continue;
                }
                
                int afterMiddle = j + middle.length();
                
                int suffixPos = -1;
                if (suffix.isEmpty()) {
                    suffixPos = afterMiddle;
                } else {
                    for (int k = afterMiddle; k <= n - suffix.length(); k++) {
                        if (s.substring(k, k + suffix.length()).equals(suffix)) {
                            suffixPos = k;
                            break;
                        }
                    }
                }
                
                if (suffixPos != -1) {
                    int start = prefix.isEmpty() ? (middle.isEmpty() ? suffixPos : j) : i;
                    int end = suffix.isEmpty() ? afterMiddle : suffixPos + suffix.length();
                    int len = end - start;
                    minLen = Math.min(minLen, len);
                }
                
                if (middle.isEmpty()) break;
            }
            
            if (prefix.isEmpty()) break;
        }
        
        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }
}
# @lc code=end