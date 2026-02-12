#
# @lc app=leetcode id=2074 lang=cpp
#
# [2074] Reverse Nodes in Even Length Groups
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
class Solution {
public:
    ListNode* reverseEvenLengthGroups(ListNode* head) {
        ListNode dummy(0, head);
        ListNode* prev = &dummy;
        ListNode* curr = head;
        int group = 1;
        while (curr) {
            // Find the length of the current group
            ListNode* temp = curr;
            int cnt = 0;
            for (int i = 0; i < group && temp; ++i) {
                temp = temp->next;
                cnt++;
            }
            // Now, curr points to the start of the group, temp points to the node after the group
            if (cnt % 2 == 0) {
                // Reverse this group
                ListNode* prev2 = temp;
                ListNode* node = curr;
                for (int i = 0; i < cnt; ++i) {
                    ListNode* next = node->next;
                    node->next = prev2;
                    prev2 = node;
                    node = next;
                }
                prev->next = prev2;
                prev = curr;
                curr = temp;
            } else {
                // Move prev and curr forward without reversing
                for (int i = 0; i < cnt; ++i) {
                    prev = curr;
                    curr = curr->next;
                }
            }
            group++;
        }
        return dummy.next;
    }
};
# @lc code=end