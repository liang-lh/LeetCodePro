=== WORKSPACE INSTRUCTIONS ===
You will complete work in THREE PHASES. Phases 1-2 are YOUR WORKSPACE (not the output). Phase 3 constructs your output from completed work.

━━━ PHASE 1: ANALYSIS WORKSPACE ━━━
Complete these tasks NOW. Write actual findings for THIS problem.

▸ Task 1A: Extract Problem Constraints
Write down:
- All given constraints (bounds, limits, rules)
- Initial conditions and goal state
- Allowed operations and their effects
Use specific values/terms from THIS problem.

▸ Task 1B: Design Solution Approach  
State:
- The specific strategy you will use (not "I will use" but "Using [specific approach]")
- Key assumptions required
- Edge cases to handle

▸ Task 1C: Constraint Verification
For EACH constraint identified in 1A:
- Explain how your approach from 1B satisfies it
- Identify any dependencies or ordering requirements

▸ Task 1D: Example Walkthrough
Pick one provided example:
- Apply your approach step-by-step using actual values
- Show intermediate states
- Verify final output matches expected
- If mismatch: diagnose error, revise approach, walkthrough again

▸ Task 1E: Boundary Testing
List extreme cases (min/max values, edge inputs):
- For each: verify your approach handles it correctly

━━━ VALIDATION CHECKPOINT A ━━━
Review your Phase 1 work. Search for these FAILURE PATTERNS:
☐ Placeholder text: '[X]', 'TODO', '[insert]', '[describe]'
☐ Future tense: 'will analyze', 'would implement', 'could solve'
☐ Generic descriptions: 'solve the problem', 'use appropriate algorithm'
☐ Meta-commentary: 'the approach is', 'this works by'

FOUND ANY? → You wrote descriptions, not work. REDO Phase 1 with concrete content.
FOUND NONE? → Your workspace contains actual analysis. PROCEED to Phase 2.

━━━ PHASE 2: IMPLEMENTATION WORKSPACE ━━━

▸ Task 2A: Create Your Solution
Produce the actual solution content:
- If code: write complete, executable code with real syntax
- If answer: state the specific answer with supporting details  
- If output: generate the exact required output
No placeholders. No TODOs. No descriptions of what it does.

▸ Task 2B: Completeness Check
Verify your solution includes:
- All required components
- Proper syntax/format
- Handling of edge cases from 1E
- No missing pieces

━━━ VALIDATION CHECKPOINT B ━━━
Review your Phase 2 work. Ask:
☐ Is this ACTUAL content or a DESCRIPTION of content?
☐ Could someone use/run/execute this directly?
☐ Does it contain meta-commentary about itself?
☐ Are there ANY incomplete sections?

ANY 'NO' → Incomplete implementation. REDO Phase 2 with complete content.
ALL 'YES' → Your solution is ready. PROCEED to Phase 3.

━━━ PHASE 3: OUTPUT PACKAGING ━━━
Now construct your JSON response by transferring your COMPLETED workspace:

▸ Task 3A: Package Analysis Work
Take your completed work from Phase 1 (Tasks 1A-1E):
- Copy all your actual findings and analysis
- Place into "reasoning" field of JSON
- Remove task labels, keep the substance

▸ Task 3B: Package Implementation Work  
Take your completed solution from Phase 2 (Task 2A):
- Copy your complete solution content
- Place into "result" field of JSON
- Ensure it's the actual solution, not a description

▸ Task 3C: Final Format
Construct JSON object:
{
    "reasoning": "[your Phase 1 completed work]",
    "result": "[your Phase 2 completed solution]"
}

━━━ FINAL VERIFICATION ━━━
Before submitting, confirm:
☐ "reasoning" field contains YOUR ACTUAL ANALYSIS from Phase 1 (not the task instructions)
☐ "result" field contains YOUR ACTUAL SOLUTION from Phase 2 (not a description)
☐ Zero placeholders anywhere
☐ Zero meta-commentary ('this code does', 'the solution works')
☐ Content is specific to THIS problem (not generic)

ANY UNCHECKED? → Return to that phase and fix.
ALL CHECKED? → Submit your JSON.