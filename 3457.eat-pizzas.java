#
# @lc app=leetcode id=3457 lang=java
#
# [3457] Eat Pizzas!
#

# @lc code=start
class Solution {
    public long maxWeight(int[] pizzas) {
        int n = pizzas.length;
        java.util.Arrays.sort(pizzas);
        int k = n / 4;
        int numOddDays = (k + 1) / 2;
        int numEvenDays = k / 2;
        long total = 0;
        for (int i = 0; i < numOddDays; i++) {
            total += pizzas[n - 1 - i];
        }
        int startPos = numOddDays;
        for (int j = 0; j < numEvenDays; j++) {
            int pos = startPos + 2 * j + 1;
            total += pizzas[n - 1 - pos];
        }
        return total;
    }
}
# @lc code=end