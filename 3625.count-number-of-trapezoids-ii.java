⚠️ BEFORE ANYTHING ELSE - READ YOUR TASK ⚠️

What problem are you solving?
What output format is required?
What inputs do you have?

This guidance helps you THINK. You must OUTPUT task-specific results:
- "reasoning": Your solution approach for THIS problem
- "result": Executable code solving THIS problem

═══════════════════════════════════════════════
INTERNAL THINKING GUIDE (use mentally, don't output):
═══════════════════════════════════════════════

1. UNDERSTAND THE TASK
   - Extract: what to compute, input constraints, output format, special conditions

2. ANALYZE CONSTRAINTS
   - n ≤ 100 → O(n⁴) OK | n ≤ 500 → O(n³) OK, O(n⁴) suspect | n ≤ 5000 → O(n²) OK | n ≤ 10⁵ → O(n log n) OK
   - RED FLAG: Borderline complexity? Find better approach or strong justification. Don't proceed with uncertainty.

3. EXPLORE & SELECT
   - Generate 2-3 different approaches
   - Compare: strategy, complexity, pros/cons
   - Select best fit for constraints

4. VERIFY FEASIBILITY
   - Calculate complexity with constant factors: iterations × work per iteration < 10⁸-10⁹ operations
   - Identify edge cases: boundaries, special configs, corner cases
   - Confirm algorithm correctness

5. IMPLEMENT
   - Write complete executable code in target language
   - Follow template structure exactly
   - Handle edge cases

═══════════════════════════════════════════════
OUTPUT VERIFICATION - CHECK BEFORE SUBMITTING:
═══════════════════════════════════════════════

□ My "reasoning" explains my solution to THIS specific problem (not generic methodology)
□ My "result" contains actual executable code in target language
□ My "result" can be copy-pasted and run without errors
□ My "result" starts with code syntax (e.g., "class Solution" for Java)

If ANY box is unchecked, you haven't completed the task.

Wrong: "Step 1: Understand... Step 2: Design..." → methodology, not code
Wrong: "Use a hashmap to count..." → description, not code
Correct: "class Solution {\n    public int method(...) {\n        // actual code\n    }\n}" → executable code

═══════════════════════════════════════════════
FINAL REMINDER:
═══════════════════════════════════════════════

Ask yourself: "If someone reads ONLY my output, will they get the solution to THIS task?"
If NO, you're outputting guidance/methodology instead of the actual solution.