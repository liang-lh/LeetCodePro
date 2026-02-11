#
# @lc app=leetcode id=3630 lang=java
#
# [3630] Partition Array for Maximum XOR and AND
#

# @lc code=start
class Solution {
  public long maximizeXorAndXor(int[] nums) {
    int n = nums.length;
    int mid = (n + 1) / 2;
    int lenL = mid;
    int lenR = n - mid;
    int numL = 1;
    for (int i = 0; i < lenL; i++) numL *= 3;
    int numR = 1;
    for (int i = 0; i < lenR; i++) numR *= 3;
    int[] xorAL = new int[numL];
    int[] xorCL = new int[numL];
    int[] andBL = new int[numL];
    boolean[] hasBL = new boolean[numL];
    for (int state = 0; state < numL; state++) {
      int temp = state;
      int xa = 0, xc = 0, ab = ~0;
      boolean hb = false;
      for (int k = 0; k < lenL; k++) {
        int ass = temp % 3;
        temp /= 3;
        int val = nums[k];
        if (ass == 0) {
          xa ^= val;
        } else if (ass == 2) {
          xc ^= val;
        } else {
          ab &= val;
          hb = true;
        }
      }
      xorAL[state] = xa;
      xorCL[state] = xc;
      andBL[state] = ab;
      hasBL[state] = hb;
    }
    int[] xorAR = new int[numR];
    int[] xorCR = new int[numR];
    int[] andBR = new int[numR];
    boolean[] hasBR = new boolean[numR];
    for (int state = 0; state < numR; state++) {
      int temp = state;
      int xa = 0, xc = 0, ab = ~0;
      boolean hb = false;
      for (int k = 0; k < lenR; k++) {
        int ass = temp % 3;
        temp /= 3;
        int val = nums[mid + k];
        if (ass == 0) {
          xa ^= val;
        } else if (ass == 2) {
          xc ^= val;
        } else {
          ab &= val;
          hb = true;
        }
      }
      xorAR[state] = xa;
      xorCR[state] = xc;
      andBR[state] = ab;
      hasBR[state] = hb;
    }
    long ans = 0;
    for (int i = 0; i < numL; i++) {
      int xal = xorAL[i];
      int xcl = xorCL[i];
      int abl = andBL[i];
      boolean hbl = hasBL[i];
      for (int j = 0; j < numR; j++) {
        int xar = xorAR[j];
        int xcr = xorCR[j];
        int abr = andBR[j];
        boolean hbr = hasBR[j];
        long abv;
        if (hbl && hbr) {
          abv = (long) abl & abr;
        } else if (hbl) {
          abv = abl;
        } else if (hbr) {
          abv = abr;
        } else {
          abv = 0;
        }
        long xat = (long) (xal ^ xar);
        long xct = (long) (xcl ^ xcr);
        long val = xat + xct + abv;
        if (val > ans) {
          ans = val;
        }
      }
    }
    return ans;
  }
}
# @lc code=end