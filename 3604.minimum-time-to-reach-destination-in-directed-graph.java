❌ WRONG OUTPUT EXAMPLE (Never do this):
{
    "reasoning": "Step 1: Understand Output Requirements - I need to identify what deliverables...",
    "result": "CRITICAL: These steps are your INTERNAL reasoning framework. Step 1: Understand Output Requirements..."
}
This is WRONG because you copied the process steps themselves into your output.

✅ CORRECT OUTPUT EXAMPLE (Always do this):
{
    "reasoning": "This is a graph traversal problem with time-constrained edges. I need to use modified Dijkstra's algorithm to handle waiting times. The key insight is...",
    "result": "class Solution { public int minTime(int n, int[][] edges) { /* actual working implementation */ } }"
}
This is CORRECT because you provided actual problem-solving reasoning and concrete deliverables.

========================================
PRE-EXECUTION CHECKLIST (Answer internally before proceeding):
1. What is the expected output format for THIS task? (Check the task prompt's "Output format" section)
2. What specific deliverables does THIS task request? (e.g., code, analysis, answers)
3. Am I about to output the steps below, or use them as internal guidance?
   → Your answer MUST be: "Use them as internal guidance to produce actual work"
========================================

CRITICAL: The steps below are your INTERNAL reasoning framework. Use them to guide your thinking process. After completing these steps internally, OUTPUT the requested deliverables based on what the task asks for. DO NOT output these process steps themselves in your final response.

Step 1: Understand Output Requirements
- Identify what deliverables the task explicitly requests (e.g., analysis, code, answers, explanations)
- Note the specified output format (e.g., JSON structure, specific fields required)
- Determine if the task requires: (a) describing methodology, or (b) producing concrete execution results
- Key indicator: If specific inputs/examples are provided, concrete execution is required
- Decision: Your output should contain the actual work products, not descriptions of how to produce them

Step 2: Deep Problem Analysis (Execute Concretely)
- Read and understand all specifics: given inputs, constraints, examples, expected outputs
- Identify the core challenge and requirements of THIS specific task
- Extract key information that affects your approach
- Trace through provided examples to validate your understanding
- Note: Produce specific observations about THIS task, not generic problem-type observations

Step 3: Solution Strategy Development
- Develop your concrete approach to address the specific problem
- Consider: What data structures, algorithms, or reasoning patterns apply here?
- Explain WHY your approach works given the constraints
- Identify edge cases and how to handle them
- Validate approach against examples provided
- Note: This is concrete strategy for THIS problem, not abstract methodology

Step 4: Work Product Creation
- Execute your approach: write code, perform analysis, generate answers - whatever the task requests
- Translate your strategy into the required deliverable format
- Ensure completeness: handle all cases, follow templates if provided, address all requirements
- Produce concrete, specific work tied to the given inputs
- Critical: Create actual deliverables (working code, specific analysis, concrete answers), not procedural outlines

Step 5: Solution Verification
- Test your work product: trace through examples, check edge cases, verify correctness
- Ensure your solution fully addresses the problem requirements
- Confirm output follows any specified format or template

Step 6: Pre-Submission Sanity Check (INTERNAL ONLY - DO NOT OUTPUT THIS CHECK)
GLANCE at your draft output and ask:
→ Does it contain words like "Step 1", "Step 2", "methodology", "framework", "approach", "process"?
  • If YES: You FAILED. You output the process instead of executing it. DELETE everything and start over.
  • If NO: Proceed to next check.
→ Does it contain actual work products specific to this task (working code, concrete analysis, specific answers)?
  • If NO: You FAILED. Revise to provide the requested deliverables.
  • If YES: Proceed to next check.
→ If I removed all task-specific details (numbers, constraints, examples), would my output still make sense?
  • If YES: You FAILED. You provided generic guidance instead of specific solution.
  • If NO: Good, your output is concrete and task-specific.
This is an internal checkpoint only - never include this validation process in your output.

Step 7: Finalize Output
- Structure your response according to the specified format
- Include only the requested deliverables: your specific work products
- Ensure output is complete, correct, and directly addresses the task
- Remember: Output the solution itself, not commentary about solving