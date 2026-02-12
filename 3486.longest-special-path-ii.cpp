#
# @lc app=leetcode id=3486 lang=cpp
#
# [3486] Longest Special Path II
#

# @lc code=start
class Solution {
public:
    vector<int> longestSpecialPath(vector<vector<int>>& edges, vector<int>& nums) {
        int n = nums.size();
        vector<vector<pair<int,int>>> tree(n);
        for (auto& e : edges) {
            int u = e[0], v = e[1], l = e[2];
            tree[u].emplace_back(v, l);
            tree[v].emplace_back(u, l);
        }
        int max_len = 0, min_nodes = INT_MAX;
        unordered_map<int, int> valueCount;
        function<void(int, int, int, int, int)> dfs = [&](int u, int parent, int cur_len, int cur_nodes, int dup) {
            valueCount[nums[u]]++;
            if (valueCount[nums[u]] == 2) dup++;
            // Explicitly verify constraints after visiting this node
            bool isValid = (dup <= 1);
            if (isValid) {
                // Update result if current path is valid
                if (cur_len > max_len) {
                    max_len = cur_len;
                    min_nodes = cur_nodes;
                } else if (cur_len == max_len) {
                    min_nodes = min(min_nodes, cur_nodes);
                }
                for (auto& [v, l] : tree[u]) {
                    if (v == parent) continue;
                    dfs(v, u, cur_len + l, cur_nodes + 1, dup);
                }
            }
            // Backtrack and verify state restoration
            if (valueCount[nums[u]] == 2) dup--;
            valueCount[nums[u]]--;
        };
        // Handle edge cases: tree with only two nodes
        dfs(0, -1, 0, 1, 0);
        return {max_len, min_nodes};
    }
};
# @lc code=end