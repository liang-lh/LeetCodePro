#
# @lc app=leetcode id=3387 lang=java
#
# [3387] Maximize Amount After Two Days of Conversions
#
# @lc code=start
import java.util.*;

class Solution {
    public double maxAmount(String initialCurrency, List<List<String>> pairs1, double[] rates1, List<List<String>> pairs2, double[] rates2) {
        // Build graphs for both days
        Map<String, List<Pair>> graph1 = buildGraph(pairs1, rates1);
        Map<String, List<Pair>> graph2 = buildGraph(pairs2, rates2);
        
        // Day 1: Find max amount for each currency starting from initialCurrency
        Map<String, Double> day1Max = getMaxAmounts(graph1, initialCurrency);
        
        // Day 2: For each currency we can reach on day 1, 
        // find max amount of initialCurrency we can get back
        double result = 1.0; // If we don't convert at all
        
        for (String currency : day1Max.keySet()) {
            double amountAfterDay1 = day1Max.get(currency);
            Map<String, Double> day2Max = getMaxAmounts(graph2, currency);
            
            if (day2Max.containsKey(initialCurrency)) {
                result = Math.max(result, amountAfterDay1 * day2Max.get(initialCurrency));
            }
        }
        
        return result;
    }
    
    private Map<String, List<Pair>> buildGraph(List<List<String>> pairs, double[] rates) {
        Map<String, List<Pair>> graph = new HashMap<>();
        
        for (int i = 0; i < pairs.size(); i++) {
            String from = pairs.get(i).get(0);
            String to = pairs.get(i).get(1);
            double rate = rates[i];
            
            graph.putIfAbsent(from, new ArrayList<>());
            graph.putIfAbsent(to, new ArrayList<>());
            
            graph.get(from).add(new Pair(to, rate));
            graph.get(to).add(new Pair(from, 1.0 / rate));
        }
        
        return graph;
    }
    
    private Map<String, Double> getMaxAmounts(Map<String, List<Pair>> graph, String start) {
        Map<String, Double> maxAmount = new HashMap<>();
        maxAmount.put(start, 1.0);
        
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            double currAmount = maxAmount.get(curr);
            
            if (!graph.containsKey(curr)) continue;
            
            for (Pair neighbor : graph.get(curr)) {
                String next = neighbor.currency;
                double newAmount = currAmount * neighbor.rate;
                
                if (!maxAmount.containsKey(next) || newAmount > maxAmount.get(next)) {
                    maxAmount.put(next, newAmount);
                    queue.offer(next);
                }
            }
        }
        
        return maxAmount;
    }
    
    private static class Pair {
        String currency;
        double rate;
        
        Pair(String currency, double rate) {
            this.currency = currency;
            this.rate = rate;
        }
    }
}
# @lc code=end