#
# @lc app=leetcode id=3387 lang=java
#
# [3387] Maximize Amount After Two Days of Conversions
#

# @lc code=start
class Solution {
    public double maxAmount(String initialCurrency, List<List<String>> pairs1, double[] rates1, List<List<String>> pairs2, double[] rates2) {
        Map<String, Map<String, Double>> graph1 = new HashMap<>();
        for (int i = 0; i < pairs1.size(); i++) {
            String a = pairs1.get(i).get(0);
            String b = pairs1.get(i).get(1);
            double r = rates1[i];
            graph1.computeIfAbsent(a, k -> new HashMap<>()).put(b, r);
            graph1.computeIfAbsent(b, k -> new HashMap<>()).put(a, 1.0 / r);
        }
        Map<String, Map<String, Double>> graph2 = new HashMap<>();
        for (int i = 0; i < pairs2.size(); i++) {
            String a = pairs2.get(i).get(0);
            String b = pairs2.get(i).get(1);
            double r = rates2[i];
            graph2.computeIfAbsent(a, k -> new HashMap<>()).put(b, r);
            graph2.computeIfAbsent(b, k -> new HashMap<>()).put(a, 1.0 / r);
        }
        // Compute mult1: initial -> C on day1
        Map<String, Double> mult1 = new HashMap<>();
        mult1.put(initialCurrency, 1.0);
        Set<String> visited1 = new HashSet<>();
        visited1.add(initialCurrency);
        Queue<String> q1 = new LinkedList<>();
        q1.offer(initialCurrency);
        while (!q1.isEmpty()) {
            String curr = q1.poll();
            Map<String, Double> neis = graph1.getOrDefault(curr, new HashMap<>());
            for (Map.Entry<String, Double> entry : neis.entrySet()) {
                String nei = entry.getKey();
                double rate = entry.getValue();
                if (!visited1.contains(nei)) {
                    visited1.add(nei);
                    mult1.put(nei, mult1.get(curr) * rate);
                    q1.offer(nei);
                }
            }
        }
        // Build revGraph2 for day2
        Map<String, Map<String, Double>> revGraph2 = new HashMap<>();
        for (Map.Entry<String, Map<String, Double>> outer : graph2.entrySet()) {
            String a = outer.getKey();
            for (Map.Entry<String, Double> inner : outer.getValue().entrySet()) {
                String b = inner.getKey();
                double r_ab = inner.getValue();
                revGraph2.computeIfAbsent(b, k -> new HashMap<>()).put(a, 1.0 / r_ab);
            }
        }
        // Compute mult_rev: initial -> C in revGraph2 (1 / (C -> initial in graph2))
        Map<String, Double> mult_rev = new HashMap<>();
        mult_rev.put(initialCurrency, 1.0);
        Set<String> visited_rev = new HashSet<>();
        visited_rev.add(initialCurrency);
        Queue<String> q_rev = new LinkedList<>();
        q_rev.offer(initialCurrency);
        while (!q_rev.isEmpty()) {
            String curr = q_rev.poll();
            Map<String, Double> neis = revGraph2.getOrDefault(curr, new HashMap<>());
            for (Map.Entry<String, Double> entry : neis.entrySet()) {
                String nei = entry.getKey();
                double rate = entry.getValue();
                if (!visited_rev.contains(nei)) {
                    visited_rev.add(nei);
                    mult_rev.put(nei, mult_rev.get(curr) * rate);
                    q_rev.offer(nei);
                }
            }
        }
        // mult2: C -> initial on day2
        Map<String, Double> mult2 = new HashMap<>();
        for (Map.Entry<String, Double> e : mult_rev.entrySet()) {
            mult2.put(e.getKey(), 1.0 / e.getValue());
        }
        // Max over intermediate C
        double ans = 1.0;
        for (Map.Entry<String, Double> e : mult1.entrySet()) {
            String c = e.getKey();
            double m1v = e.getValue();
            Double m2v = mult2.get(c);
            if (m2v != null) {
                ans = Math.max(ans, m1v * m2v);
            }
        }
        return ans;
    }
}
# @lc code=end