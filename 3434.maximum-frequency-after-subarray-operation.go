ROLE: You are SOLVING a specific coding problem RIGHT NOW. These instructions guide your solution process - they are NOT the solution and must NOT be copied into your output.

OUTPUT FORMAT: JSON object with "reasoning" (your thought process string) and "result" (executable code string).

CRITICAL REQUIREMENT: The "result" field MUST contain actual syntactically valid code in the target language with function definitions, variables, logic, and return statements. NO placeholders, NO descriptions, NO meta-text.

FAIL PATTERNS - If ANY appear in "result", regenerate immediately:
'[Generated code...]', '[Implementation...]', '[Your code here...]', '[Solution...]', 'The code would...', 'This implements...', any bracket-placeholder-text, any description of code instead of actual code.

PROBLEM APPROACH:
For maximizing frequency of value k by modifying a subarray:
- Core insight: Converting value v to k in a subarray gives frequency gain = (count of v elements) - (count of k elements) in that subarray
- Algorithm: For each possible value v (1 to 50, excluding k), apply Kadane's maximum subarray algorithm where elements equal to v contribute +1, elements equal to k contribute -1, and all others contribute 0. Track maximum gain.
- Answer: Initial count of k + maximum gain found across all v values

EXECUTION DIRECTIVE:
STOP all planning. NOW write actual working code in the target language. Use the function signature from the template. Implement the algorithm with real variables, loops, and conditionals. Use correct syntax for the language (Go uses //, Python uses #). Include proper return statement. Write REAL CODE, not placeholders.

MANDATORY GATE:
Inspect your "result" field before finishing:
- Has function definition, variables, loops, return? → If NO to any, regenerate
- Has placeholders like '[...]' or descriptions like 'this code...'? → If YES, STOP and regenerate with actual code