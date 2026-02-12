#
# @lc app=leetcode id=3501 lang=cpp
#
# [3501] Maximize Active Section with Trade II
#

# @lc code=start
class Solution {
public:
    vector<int> maxActiveSectionsAfterTrade(string s, vector<vector<int>>& queries) {
        vector<int> answer;
        for (const auto& q : queries) {
            int l = q[0], r = q[1];
            string t = '1' + s.substr(l, r - l + 1) + '1';
            int n = t.size();
            // Find all blocks
            vector<pair<int,int>> blocks; // (start, end) continuous same char
            int i = 0;
            while (i < n) {
                int j = i;
                while (j < n && t[j] == t[i]) ++j;
                blocks.push_back({i, j-1});
                i = j;
            }
            // Initial count of '1' blocks, excluding augmented ends
            int init_blocks = 0;
            for (auto& b : blocks) {
                if (t[b.first] == '1' && b.first != 0 && b.second != n-1)
                    init_blocks++;
            }
            int max_blocks = init_blocks;
            // Gather eligible '1' blocks for trading
            vector<int> eligible_one_block_indices;
            for (int idx = 0; idx < (int)blocks.size(); ++idx) {
                auto& b = blocks[idx];
                if (t[b.first] == '1' && b.first > 0 && b.second < n-1 && t[b.first-1]=='0' && t[b.second+1]=='0')
                    eligible_one_block_indices.push_back(idx);
            }
            // Gather eligible '0' blocks for flipping
            vector<int> eligible_zero_block_indices;
            for (int idx = 0; idx < (int)blocks.size(); ++idx) {
                auto& b = blocks[idx];
                if (t[b.first] == '0' && b.first > 0 && b.second < n-1 && t[b.first-1]=='1' && t[b.second+1]=='1')
                    eligible_zero_block_indices.push_back(idx);
            }
            // For each eligible '1' block, consider one atomic trade (remove it, and flip at most one eligible '0' block)
            for (int one_idx : eligible_one_block_indices) {
                // Remove '1' block at one_idx: temporarily mark it as '0'
                vector<pair<int,int>> new_blocks = blocks;
                new_blocks[one_idx].first = new_blocks[one_idx].first;
                new_blocks[one_idx].second = new_blocks[one_idx].second;
                // We'll simulate by marking this block as '0'
                string t_mod = t;
                for (int k = new_blocks[one_idx].first; k <= new_blocks[one_idx].second; ++k)
                    t_mod[k] = '0';
                // After removing, recompute blocks
                vector<pair<int,int>> blocks2;
                int x = 0, m = t_mod.size();
                while (x < m) {
                    int y = x;
                    while (y < m && t_mod[y] == t_mod[x]) ++y;
                    blocks2.push_back({x, y-1});
                    x = y;
                }
                // Find eligible '0' blocks in t_mod
                vector<int> eligible_zero_indices2;
                for (int idx2 = 0; idx2 < (int)blocks2.size(); ++idx2) {
                    auto& b2 = blocks2[idx2];
                    if (t_mod[b2.first] == '0' && b2.first > 0 && b2.second < m-1 && t_mod[b2.first-1]=='1' && t_mod[b2.second+1]=='1')
                        eligible_zero_indices2.push_back(idx2);
                }
                // Case 1: Don't flip any '0' block
                // Count '1' blocks in t_mod, excluding augmented ends
                int cnt1 = 0;
                for (auto& b : blocks2) {
                    if (t_mod[b.first] == '1' && b.first != 0 && b.second != m-1) cnt1++;
                }
                max_blocks = max(max_blocks, cnt1);
                // Case 2: Try flipping each eligible '0' block (only one, as part of a single trade)
                for (int zero_idx2 : eligible_zero_indices2) {
                    string t_mod2 = t_mod;
                    for (int k = blocks2[zero_idx2].first; k <= blocks2[zero_idx2].second; ++k)
                        t_mod2[k] = '1';
                    // Count '1' blocks in t_mod2, excluding augmented ends
                    int cnt2 = 0;
                    int p = 0;
                    while (p < m) {
                        if (t_mod2[p] == '1') {
                            int q = p;
                            while (q < m && t_mod2[q] == '1') ++q;
                            if (p != 0 && q-1 != m-1) cnt2++;
                            p = q;
                        } else {
                            ++p;
                        }
                    }
                    max_blocks = max(max_blocks, cnt2);
                }
            }
            answer.push_back(max_blocks);
        }
        return answer;
    }
};
# @lc code=end