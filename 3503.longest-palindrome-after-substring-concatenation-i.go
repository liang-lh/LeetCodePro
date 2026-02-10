[INTERNAL REASONING GUIDANCE - DO NOT OUTPUT THESE STEPS DIRECTLY]

These are reasoning steps to GUIDE your problem-solving process. Your final JSON output must contain:
- 'reasoning' field: YOUR analysis of the SPECIFIC problem you're solving
- 'result' field: Actual executable CODE in the specified language

DO NOT copy these guidance steps into your output. APPLY them to generate your solution.

---

Step 1: Identify the SPECIFIC problem you are solving. State explicitly: What is problem #[insert_id]? What are the exact inputs, outputs, and constraints? What does a valid solution look like for THIS problem?

Step 2: Decompose THIS specific problem. Break down the actual problem you're solving (not generic problems) into concrete sub-problems. What are the key challenges in THIS problem?

Step 3: Design a CONCRETE solution approach for THIS problem. Specify:
- What specific algorithm, technique, or strategy will solve THIS problem?
- What specific data structures will you use and why?
- How will you handle THIS problem's constraints and edge cases?
- What is the time and space complexity for YOUR approach?

Checkpoint: Stop and verify - Have you described a SPECIFIC solution for the current problem, or are you speaking in generalities? Your approach must name concrete algorithms, data structures, or techniques applicable to THIS exact problem. If still abstract, return to Step 2.

Step 4: Trace through the provided examples using YOUR specific approach. Walk through each example showing how YOUR algorithm produces the expected output. Identify any flaws in YOUR reasoning.

Step 5: Refine YOUR solution based on the trace. Fix any issues discovered. Optimize if possible while maintaining correctness.

Step 6: Final reasoning check - Review what you've written. Does it explain how to solve the SPECIFIC problem at hand? Would someone be able to implement working code from your reasoning? If your reasoning contains generic advice instead of problem-specific logic, restart from Step 2.

Step 7: WRITE YOUR ACTUAL SOLUTION CODE. This is what goes in the 'result' field of your JSON output. The code must be:
- Actual executable code in the specified programming language (NOT guidance text, NOT pseudocode, NOT these steps)
- Complete implementation that solves the specific problem
- Properly formatted according to the provided template
- Syntactically correct and runnable

Step 8: Verify your FINAL JSON output (not these guidance steps). Your output must be:
- Valid JSON with 'reasoning' and 'result' fields
- 'reasoning' field contains YOUR step-by-step analysis of THIS specific problem (not these generic guidance steps)
- 'result' field contains actual working code in the correct language (not guidance, not instructions)
- Code solves the specific problem given
- Format matches the example provided in the system prompt