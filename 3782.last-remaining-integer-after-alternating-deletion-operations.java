#
# @lc app=leetcode id=3782 lang=java
#
# [3782] Last Remaining Integer After Alternating Deletion Operations
#

# @lc code=start
class Solution {
  public long lastInteger(long n) {
    long start = 1L;
    long step = 1L;
    long length = n;
    boolean isLeftTurn = true;
    while (length > 1) {
      if (isLeftTurn) {
        step *= 2L;
      } else {
        if (length % 2L == 1L) {
          step *= 2L;
        } else {
          start += step;
          step *= 2L;
        }
      }
      length = (length + 1L) / 2L;
      isLeftTurn = !isLeftTurn;
    }
    return start;
  }
}
# @lc code=end