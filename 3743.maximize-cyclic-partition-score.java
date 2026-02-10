=== PROBLEM-SOLVING INSTRUCTIONS ===

ANALYSIS REQUIREMENTS:

Read the problem statement and ALL examples carefully.
Work through each example manually to understand the pattern.
Identify your specific algorithm/approach and state it clearly.
Explain why your approach solves this problem.
Trace your algorithm on one example with actual values: "Input [X] → Step 1 produces [Y] → Step 2 produces [Z] → Output [result]".

Calculate complexity explicitly:
- Enumerate each loop level: "Outer loop: N iterations → Inner loop: M per outer → Operation: O(1) → Total: O(N*M)"
- With maximum constraints, estimate total operations: "N=1000, M=1000 → 10^6 operations → feasible"

Ask optimization questions: Are there repeated calculations? Can results be cached? Can iterations be reduced?

CODE REQUIREMENTS:

Write complete, executable code in the target language.
Follow the provided template structure exactly.
Implement ALL logic fully (zero TODOs, zero placeholders, zero gaps).
Use correct syntax that would compile/run immediately.
Handle all test cases including edge cases.

=== CRITICAL ANTI-PATTERNS ===

Your output must NOT contain:
✗ Instruction phrases: "Perform analysis", "Implement code here", "Your solution"
✗ Placeholder syntax: "[analyze here]", "TODO", "implement algorithm"
✗ Step listings without work: "Step 1: Understand problem" (show actual understanding)

These are instructions TO you, not output FROM you.

=== VERIFICATION CHECKLIST ===

Before submitting, verify:
□ "reasoning" field: Contains MY SPECIFIC algorithm choice and WHY it works?
□ "reasoning" field: Shows traced example with ACTUAL values from problem?
□ "reasoning" field: Includes complexity calculation with ENUMERATED operations?
□ "result" field: Contains COMPLETE, RUNNABLE code (not placeholder text)?
□ "result" field: Would execute WITHOUT errors if copy-pasted to IDE?

=== OUTPUT FORMAT ===

Respond with JSON containing exactly two fields:
{"reasoning": "your analysis text here", "result": "your complete code here"}

Do NOT include any text before or after the JSON object.