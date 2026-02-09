#
# @lc app=leetcode id=3519 lang=java
#
# [3519] Count Numbers with Non-Decreasing Digits
#
# @lc code=start
import java.util.*;

class Solution {
    private static final int MOD = 1000000007;
    private Long[][][][] memo;
    private int base;
    
    public int countNumbers(String l, String r, int b) {
        this.base = b;
        
        // Count from 0 to r
        long countR = countUpTo(r);
        
        // Count from 0 to l-1
        long countL = countUpTo(subtract(l));
        
        // Result is count(r) - count(l-1)
        long result = (countR - countL + MOD) % MOD;
        return (int) result;
    }
    
    private long countUpTo(String num) {
        if (num.equals("-1")) return 0;
        
        // Convert decimal string to base b
        List<Integer> digits = convertToBase(num, base);
        
        int n = digits.size();
        memo = new Long[n][base][2][2];
        
        return dp(0, 0, 1, 0, digits);
    }
    
    private long dp(int pos, int lastDigit, int tight, int started, List<Integer> digits) {
        if (pos == digits.size()) {
            return started;
        }
        
        if (memo[pos][lastDigit][tight][started] != null) {
            return memo[pos][lastDigit][tight][started];
        }
        
        int limit = tight == 1 ? digits.get(pos) : (base - 1);
        long count = 0;
        
        for (int digit = 0; digit <= limit; digit++) {
            if (started == 1 && digit < lastDigit) continue;
            
            int newTight = (tight == 1 && digit == limit) ? 1 : 0;
            int newStarted = (started == 1 || digit > 0) ? 1 : 0;
            int newLastDigit = newStarted == 1 ? digit : 0;
            
            count = (count + dp(pos + 1, newLastDigit, newTight, newStarted, digits)) % MOD;
        }
        
        memo[pos][lastDigit][tight][started] = count;
        return count;
    }
    
    private List<Integer> convertToBase(String decimal, int base) {
        List<Integer> result = new ArrayList<>();
        
        if (decimal.equals("0")) {
            result.add(0);
            return result;
        }
        
        List<Integer> num = new ArrayList<>();
        for (char c : decimal.toCharArray()) {
            num.add(c - '0');
        }
        
        while (!isZero(num)) {
            int remainder = divideByBase(num, base);
            result.add(remainder);
        }
        
        Collections.reverse(result);
        return result;
    }
    
    private boolean isZero(List<Integer> num) {
        for (int d : num) {
            if (d != 0) return false;
        }
        return true;
    }
    
    private int divideByBase(List<Integer> num, int base) {
        int carry = 0;
        List<Integer> quotient = new ArrayList<>();
        
        for (int i = 0; i < num.size(); i++) {
            int current = carry * 10 + num.get(i);
            quotient.add(current / base);
            carry = current % base;
        }
        
        // Remove leading zeros and update num
        int startIdx = 0;
        while (startIdx < quotient.size() && quotient.get(startIdx) == 0) {
            startIdx++;
        }
        
        num.clear();
        if (startIdx < quotient.size()) {
            num.addAll(quotient.subList(startIdx, quotient.size()));
        } else {
            num.add(0);
        }
        
        return carry;
    }
    
    private String subtract(String num) {
        if (num.equals("0")) return "-1";
        
        char[] digits = num.toCharArray();
        int i = digits.length - 1;
        
        while (i >= 0 && digits[i] == '0') {
            digits[i] = '9';
            i--;
        }
        
        if (i < 0) return "-1";
        
        digits[i]--;
        
        // Remove leading zeros
        int startIdx = 0;
        while (startIdx < digits.length - 1 && digits[startIdx] == '0') {
            startIdx++;
        }
        
        return new String(digits, startIdx, digits.length - startIdx);
    }
}
# @lc code=end