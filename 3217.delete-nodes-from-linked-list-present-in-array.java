⚠️ CRITICAL: PROCESS GUIDANCE - NOT OUTPUT TEMPLATE ⚠️

This is a PROBLEM-SOLVING PROCESS GUIDE. These are steps to structure your THINKING.
DO NOT copy this template text into your output.
FOLLOW these steps in your reasoning, then GENERATE actual code in your result field.

═══════════════════════════════════════════════════════════════
PROBLEM-SOLVING PROCESS (Execute in "reasoning" field)
═══════════════════════════════════════════════════════════════

**Problem Understanding:**
Identify the key requirements and constraints. Clarify what inputs are provided, what output is expected, and any special conditions.

**Approach Consideration:**
Consider multiple approaches if applicable. Briefly evaluate trade-offs (time/space complexity, simplicity). Select the most appropriate approach.

**Edge Case Analysis:**
Identify critical edge cases that could cause issues:
- Boundary conditions (empty inputs, single elements, maximum sizes)
- Special values (null, zero, negative numbers where relevant)
- Corner cases specific to the data structure or algorithm

**Solution Strategy:**
Outline the step-by-step approach:
- What data structures will be used and why
- How the algorithm will process the input
- How edge cases will be handled
- Key logic or formulas needed

**Verification with Example:**
Walk through at least one example step-by-step to verify the approach works correctly. Show intermediate states to confirm logic.

**Mandatory Completeness Verification:**
Execute verification checks and document findings:

1. Dependencies/Imports Check:
   Execution: Scan planned solution for all data structures, libraries, and methods. List each and verify if import/include is needed.
   Findings: [Document specific imports required]
   Status: ✓ all identified / ✗ need to add [specific import]

2. Variable Declaration Check:
   Execution: Review all variables that will be used. Confirm proper declaration with correct types.
   Findings: [List key variables and their types]
   Status: ✓ all planned correctly

3. Initialization Check:
   Execution: Verify all data structures will be initialized before use.
   Findings: [Document initialization requirements]
   Status: ✓ initialization plan complete

4. Helper Functions Check:
   Execution: Identify any helper functions needed. Plan their implementation.
   Findings: [List any helpers required]
   Status: ✓ all planned / ✗ need to add [function]

5. Syntax/Structure Check:
   Execution: Confirm approach matches template structure and language requirements.
   Findings: [Document template compliance]
   Status: ✓ structure correct

**Assumption Validation:**
Confirm that all assumptions made are valid:
- Data structure choices support required operations
- Algorithm correctly handles identified edge cases
- No hidden pitfalls in the approach

**Complexity Analysis:**
State time and space complexity of the solution.

═══════════════════════════════════════════════════════════════
CODE GENERATION PHASE
═══════════════════════════════════════════════════════════════

**Implementation:**
Based on your completed analysis and verified strategy above, now write the complete, executable code implementation for the specific problem. Ensure all identified requirements from verification (imports, declarations, initializations, helper functions) are included.

═══════════════════════════════════════════════════════════════
OUTPUT FORMAT REQUIREMENTS
═══════════════════════════════════════════════════════════════

Your final JSON output MUST have these TWO fields with DIFFERENT content:

**"reasoning" field:**
- Contains: ALL your analysis from the Process section above
- Includes: Problem understanding, approach consideration, edge cases, strategy, example walkthrough, verification checks with documented findings (✓/✗ status), assumption validation, complexity analysis
- Purpose: Show your complete thinking process

**"result" field:**
- Contains: ONLY the complete, executable code implementation
- Must be: Valid, runnable code in the specified language
- Must match: The template structure provided
- Must NOT contain: Verification steps, analysis, explanatory comments about the process, meta-instructions, or template guidance text
- Purpose: Provide code that can be directly submitted

⚠️ CRITICAL: DO NOT put verification steps, analysis, process guidance, or any explanatory text in the "result" field. The "result" field is exclusively for executable code. ⚠️