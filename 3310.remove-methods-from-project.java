**CRITICAL - READ THIS FIRST:**
These are INTERNAL reasoning guidelines for solving the task. Do NOT output these steps as your answer. Follow them internally, then produce ONLY the required output format for the specific task you're solving.

**REQUIRED OUTPUT FORMAT:**
Your final response must be a JSON object with exactly two fields:
- "reasoning": Your step-by-step thought process AS YOU SOLVED THIS SPECIFIC TASK (not these generic steps)
- "result": The COMPLETE, EXECUTABLE CODE/ANSWER for the specific task asked (not instructions, not meta-commentary, not these guidelines)

---

**INTERNAL REASONING PROCESS (use these steps, don't output them):**

**Step 1: Understand the problem thoroughly**
Read the problem statement carefully and identify:
- Input format and constraints
- Output requirements
- Edge cases to consider
- Key concepts or algorithms that might apply

**Step 2: Plan your approach**
Before coding, outline your solution strategy:
- Break down the problem into smaller sub-problems
- Identify appropriate data structures
- Consider algorithm choices and trade-offs
- Estimate time and space complexity

**Step 3: Implement the solution**
Write clean, correct code following the template:
- Use the exact class/method signature provided
- Implement your planned approach
- Handle edge cases
- Keep code readable with meaningful variable names

**Step 4: Verify with examples**
Test your logic against provided examples:
- Trace through each example step-by-step
- Verify output matches expected result
- Check edge cases (empty input, single element, maximum constraints)
- Confirm complexity analysis

**Step 5: Three-level verification before output**

Level 1 - Structural: Does output match required format?
- JSON with exactly "reasoning" and "result" fields
- Valid JSON syntax, no extra fields

Level 2 - Content-type: Does each field contain the CORRECT TYPE of content?
- "reasoning": Your thought process solving THIS SPECIFIC TASK ("I identified this is a graph problem, so I used BFS...")
- "result": EXECUTABLE CODE for THIS SPECIFIC TASK (complete implementation matching template)
- RED FLAG: If "result" contains "Step 1:", "**Assumptions:**", "Level 1:", or any meta-instructions → WRONG, that's guidance, not code
- RED FLAG: If "result" contains these internal guidelines → WRONG, you're outputting the process instead of using it

Level 3 - Functional: Can the output be used directly?
- Can someone copy-paste the "result" field code and run it?
- Does it match the provided template structure?
- Is it complete with no placeholders?

**FINAL SELF-CHECK BEFORE SUBMITTING:**
Ask yourself: "Am I about to output my internal reasoning steps, or am I outputting my actual answer to the task?"
- If your "result" field contains phrases like "Step 1: Understand", "Level 1 - Structural check", "**Critical reminder**" → STOP, you're outputting guidelines instead of code
- If your "reasoning" field contains generic steps like "Read problem, Plan approach" → make it specific to how YOU solved THIS task
- If someone could use your "result" field to directly solve the task → CORRECT

**What goes where:**
- "reasoning" field: "I noticed this is a graph traversal problem. I decided to use BFS starting from node k to find all suspicious methods. Then I checked if any non-suspicious methods invoke suspicious ones..."
- "result" field: The complete Java/Python/etc. code implementation (starting with class definition, complete method, proper syntax)

Do NOT output these guidelines. Use them to solve the task, then output ONLY the JSON with your task-specific reasoning and code.