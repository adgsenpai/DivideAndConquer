# Divide and Conquer - Practice Questions Solutions

This repository contains solutions to divide and conquer algorithm practice questions, each with both theoretical explanations (`.md` files) and Java implementations (`.java` files).

## Questions and Solutions

### Q1: Insertion Sort (Decrease by 1)
- **Q1.md**: Trace, loop invariant proof, and complexity analysis
- **Q1.java**: Implementation with insertion sort algorithm

### Q2: Binary Search vs Interpolation Search
- **Q2.md**: Analysis of search algorithms and complexity
- **Q2.java**: Implementations of both binary and interpolation search with tracing

### Q3: Fake Coin on a Balance Scale (Decrease by Constant Factor)
- **Q3.md**: 2-pile and 3-pile algorithms with recurrence analysis
- **Q3.java**: Simulation of both methods with explicit weighing plan for n=26

### Q4: Euclid's GCD (Variable-Size Decrease)
- **Q4.md**: Correctness proof and O(log n) time complexity using Fibonacci connection
- **Q4.java**: Recursive and iterative implementations with Lamé's theorem verification

### Q5: Quickselect (k-th Order Statistic)
- **Q5.md**: Randomized quickselect pseudocode and O(n) expected time analysis
- **Q5.java**: Implementation with median finding example

### Q6: Generating Powerset and Gray Code
- **Q6.md**: Recursive powerset generation and Binary Reflected Gray Code
- **Q6.java**: Implementation showing one-bit-flip property

### Q7: Johnson-Trotter Permutations
- **Q7.md**: Mobile element definition and minimal-change permutation generation
- **Q7.java**: Implementation demonstrating Θ(n! × n) optimality

### Q8: Merge Sort vs Quick Sort
- **Q8.md**: Recurrence analysis, stability, and hybrid quicksort with median-of-three
- **Q8.java**: Both algorithms with performance comparison and cutoff optimization

### Q9: Closest Pair of Points in the Plane
- **Q9.md**: Divide-and-conquer algorithm with packing lemma proof
- **Q9.java**: O(n log n) implementation with strip checking

### Q10: Strassen's Matrix Multiplication
- **Q10.md**: Recurrence analysis, Θ(n^2.807) complexity, and practical considerations
- **Q10.java**: Implementation with classical comparison and operation counting

## Running the Code

Compile any solution:
```bash
javac Q1.java
```

Run the compiled program:
```bash
java Q1
```

Compile all solutions:
```bash
javac Q*.java
```

## Structure

Each question follows the same pattern:
- **Qn.md**: Theoretical analysis, proofs, and explanations
- **Qn.java**: Working Java implementation with examples and test cases

## Topics Covered

- **Decrease and Conquer**: Q1 (Insertion Sort), Q2 (Binary/Interpolation Search), Q3 (Fake Coin), Q4 (GCD)
- **Divide and Conquer**: Q5 (Quickselect), Q8 (Merge/Quick Sort), Q9 (Closest Pair), Q10 (Strassen)
- **Combinatorial Generation**: Q6 (Powerset), Q7 (Permutations)

## Algorithm Complexities

| Question | Algorithm | Time Complexity |
|----------|-----------|-----------------|
| Q1 | Insertion Sort | O(n²) worst, O(n) best |
| Q2 | Binary Search | O(log n) |
| Q2 | Interpolation Search | O(log log n) expected |
| Q3 | Fake Coin (3-pile) | O(log₃ n) |
| Q4 | Euclid's GCD | O(log min{m,n}) |
| Q5 | Quickselect | O(n) expected |
| Q6 | Powerset | O(n × 2ⁿ) |
| Q7 | Johnson-Trotter | Θ(n! × n) |
| Q8 | Merge Sort | Θ(n log n) |
| Q8 | Quick Sort | O(n log n) expected |
| Q9 | Closest Pair | O(n log n) |
| Q10 | Strassen | Θ(n^log₂7) ≈ Θ(n^2.807) |
