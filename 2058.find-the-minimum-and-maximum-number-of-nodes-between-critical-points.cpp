#
# @lc app=leetcode id=2058 lang=cpp
#
# [2058] Find the Minimum and Maximum Number of Nodes Between Critical Points
#
# @lc code=start
/**
* Definition for singly-linked list.
* struct ListNode {
*     int val;
*     ListNode *next;
*     ListNode() : val(0), next(nullptr) {}
*     ListNode(int x) : val(x), next(nullptr) {}
*     ListNode(int x, ListNode *next) : val(x), next(next) {}
* };
*/
#include <vector>
#include <climits>
using namespace std;
class Solution {
public:
    vector<int> nodesBetweenCriticalPoints(ListNode* head) {
        vector<int> criticalIndices;
        int idx = 1; // start at 1 for the second node (1-based)
        ListNode* prev = head;
        ListNode* curr = head ? head->next : nullptr;
        while (curr && curr->next) {
            ++idx;
            int prevVal = prev->val;
            int currVal = curr->val;
            int nextVal = curr->next->val;
            if ((currVal > prevVal && currVal > nextVal) || (currVal < prevVal && currVal < nextVal)) {
                criticalIndices.push_back(idx - 1); // zero-based index for the current node
            }
            prev = curr;
            curr = curr->next;
        }
        // Verification: ensure at least two critical points
        if (criticalIndices.size() < 2) {
            return {-1, -1};
        }
        int minDist = INT_MAX;
        for (size_t i = 1; i < criticalIndices.size(); ++i) {
            minDist = min(minDist, criticalIndices[i] - criticalIndices[i-1]);
        }
        int maxDist = criticalIndices.back() - criticalIndices.front();
        return {minDist, maxDist};
    }
};
# @lc code=end