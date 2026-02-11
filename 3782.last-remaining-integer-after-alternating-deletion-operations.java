#
# @lc app=leetcode id=3782 lang=java
#
# [3782] Last Remaining Integer After Alternating Deletion Operations
#

# @lc code=start
class Solution {
  public long lastInteger(long n) {
    // Compute the sequence of lengths after each operation (forward)
    java.util.ArrayList<java.lang.Long> lengths = new java.util.ArrayList<>();
    long len = n;
    do {
      lengths.add(len);
      if (len == 1) {
        break;
      }
      len = (len + 1) / 2;
    } while (true);

    // Backward simulation: start from final survivor position 0 at len=1
    long pos = 0;
    for (int i = lengths.size() - 2; i >= 0; i--) {
      // Operation number i+1 (1=first Op1, 2=Op2, etc.)
      boolean isOp1 = ((i + 1) % 2 == 1);
      long oldLen = lengths.get(i);
      if (isOp1) {
        // Undo Op1 (kept even indices 0,2,...): reverse pos = 2 * pos
        pos *= 2;
      } else {
        // Undo Op2 (kept start+0, start+2,...): reverse pos = 2 * pos + start
        long start = (oldLen % 2 == 0L) ? 1L : 0L;
        pos = pos * 2 + start;
      }
    }
    return pos + 1;
  }
}
# @lc code=end