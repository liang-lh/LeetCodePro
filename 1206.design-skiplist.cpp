#
# @lc app=leetcode id=1206 lang=cpp
#
# [1206] Design Skiplist
#

# @lc code=start
#include <vector>
#include <cstdlib>
#include <ctime>
using namespace std;

class Skiplist {
    struct Node {
        int val;
        vector<Node*> forward;
        Node(int val, int level): val(val), forward(level, nullptr) {}
    };
    const float P = 0.5;
    const int MAX_LEVEL = 16;
    Node* head;
    int level;

    int randomLevel() {
        int lvl = 1;
        while (((float)rand() / RAND_MAX) < P && lvl < MAX_LEVEL) {
            lvl++;
        }
        return lvl;
    }
public:
    Skiplist() {
        srand(time(nullptr));
        level = 1;
        head = new Node(-1, MAX_LEVEL);
    }
    
    bool search(int target) {
        Node* curr = head;
        for (int i = level - 1; i >= 0; --i) {
            while (curr->forward[i] && curr->forward[i]->val < target) {
                curr = curr->forward[i];
            }
        }
        curr = curr->forward[0];
        return curr && curr->val == target;
    }
    
    void add(int num) {
        vector<Node*> update(MAX_LEVEL, nullptr);
        Node* curr = head;
        for (int i = level - 1; i >= 0; --i) {
            while (curr->forward[i] && curr->forward[i]->val < num) {
                curr = curr->forward[i];
            }
            update[i] = curr;
        }
        int lvl = randomLevel();
        if (lvl > level) {
            for (int i = level; i < lvl; ++i) {
                update[i] = head;
            }
            level = lvl;
        }
        Node* newNode = new Node(num, lvl);
        for (int i = 0; i < lvl; ++i) {
            newNode->forward[i] = update[i]->forward[i];
            update[i]->forward[i] = newNode;
        }
    }
    
    bool erase(int num) {
        vector<Node*> update(MAX_LEVEL, nullptr);
        Node* curr = head;
        for (int i = level - 1; i >= 0; --i) {
            while (curr->forward[i] && curr->forward[i]->val < num) {
                curr = curr->forward[i];
            }
            update[i] = curr;
        }
        curr = curr->forward[0];
        if (!curr || curr->val != num) return false;
        for (int i = 0; i < level; ++i) {
            if (update[i]->forward[i] != curr) break;
            update[i]->forward[i] = curr->forward[i];
        }
        delete curr;
        while (level > 1 && head->forward[level - 1] == nullptr) {
            level--;
        }
        return true;
    }
};

/**
* Your Skiplist object will be instantiated and called as such:
* Skiplist* obj = new Skiplist();
* bool param_1 = obj->search(target);
* obj->add(num);
* bool param_3 = obj->erase(num);
*/
# @lc code=end