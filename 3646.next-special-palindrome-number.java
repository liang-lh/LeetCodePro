#
# @lc app=leetcode id=3646 lang=java
#
# [3646] Next Special Palindrome Number
#
# @lc code=start
class Solution {
    public long specialPalindrome(long n) {
        List<Long> specials = new ArrayList<>();
        generateSpecials(specials);
        Collections.sort(specials);
        
        for (long special : specials) {
            if (special > n) {
                return special;
            }
        }
        
        return -1;
    }
    
    private void generateSpecials(List<Long> specials) {
        int[] evenDigits = {2, 4, 6, 8};
        int[] oddDigits = {1, 3, 5, 7, 9};
        
        for (int mask = 1; mask < (1 << 4); mask++) {
            List<Integer> digits = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                if ((mask & (1 << i)) != 0) {
                    digits.add(evenDigits[i]);
                }
            }
            long palindrome = buildEvenPalindrome(digits);
            if (palindrome > 0) {
                specials.add(palindrome);
            }
        }
        
        for (int oddDigit : oddDigits) {
            for (int mask = 0; mask < (1 << 4); mask++) {
                List<Integer> evenDigs = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    if ((mask & (1 << i)) != 0) {
                        evenDigs.add(evenDigits[i]);
                    }
                }
                long palindrome = buildOddPalindrome(evenDigs, oddDigit);
                if (palindrome > 0) {
                    specials.add(palindrome);
                }
            }
        }
    }
    
    private long buildEvenPalindrome(List<Integer> digits) {
        try {
            List<Integer> halfDigits = new ArrayList<>();
            for (int digit : digits) {
                for (int i = 0; i < digit / 2; i++) {
                    halfDigits.add(digit);
                }
            }
            Collections.sort(halfDigits);
            
            StringBuilder sb = new StringBuilder();
            for (int d : halfDigits) {
                sb.append(d);
            }
            String half = sb.toString();
            String full = half + new StringBuilder(half).reverse().toString();
            
            if (full.length() > 18) return -1;
            return Long.parseLong(full);
        } catch (Exception e) {
            return -1;
        }
    }
    
    private long buildOddPalindrome(List<Integer> evenDigits, int oddDigit) {
        try {
            List<Integer> halfDigits = new ArrayList<>();
            for (int digit : evenDigits) {
                for (int i = 0; i < digit / 2; i++) {
                    halfDigits.add(digit);
                }
            }
            for (int i = 0; i < (oddDigit - 1) / 2; i++) {
                halfDigits.add(oddDigit);
            }
            Collections.sort(halfDigits);
            
            StringBuilder sb = new StringBuilder();
            for (int d : halfDigits) {
                sb.append(d);
            }
            String half = sb.toString();
            String full = half + oddDigit + new StringBuilder(half).reverse().toString();
            
            if (full.length() > 18) return -1;
            return Long.parseLong(full);
        } catch (Exception e) {
            return -1;
        }
    }
}
# @lc code=end