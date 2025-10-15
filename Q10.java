public class Q10 {
    private static final int STRASSEN_CUTOFF = 64;  // Switch to classical below this size

    // ================== CLASSICAL MATRIX MULTIPLICATION ==================

    public static int[][] classicalMultiply(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }

    // ================== STRASSEN'S ALGORITHM ==================

    public static int[][] strassenMultiply(int[][] A, int[][] B) {
        int n = A.length;
        
        // Base case: use classical multiplication for small matrices
        if (n <= STRASSEN_CUTOFF) {
            return classicalMultiply(A, B);
        }

        // Assume n is power of 2; in practice, pad to next power of 2
        int newSize = n / 2;
        
        // Divide matrices into quadrants
        int[][] A11 = new int[newSize][newSize];
        int[][] A12 = new int[newSize][newSize];
        int[][] A21 = new int[newSize][newSize];
        int[][] A22 = new int[newSize][newSize];
        int[][] B11 = new int[newSize][newSize];
        int[][] B12 = new int[newSize][newSize];
        int[][] B21 = new int[newSize][newSize];
        int[][] B22 = new int[newSize][newSize];

        split(A, A11, 0, 0);
        split(A, A12, 0, newSize);
        split(A, A21, newSize, 0);
        split(A, A22, newSize, newSize);
        split(B, B11, 0, 0);
        split(B, B12, 0, newSize);
        split(B, B21, newSize, 0);
        split(B, B22, newSize, newSize);

        // Calculate M1 to M7 (7 multiplications)
        int[][] M1 = strassenMultiply(add(A11, A22), add(B11, B22));
        int[][] M2 = strassenMultiply(add(A21, A22), B11);
        int[][] M3 = strassenMultiply(A11, subtract(B12, B22));
        int[][] M4 = strassenMultiply(A22, subtract(B21, B11));
        int[][] M5 = strassenMultiply(add(A11, A12), B22);
        int[][] M6 = strassenMultiply(subtract(A21, A11), add(B11, B12));
        int[][] M7 = strassenMultiply(subtract(A12, A22), add(B21, B22));

        // Calculate C quadrants
        int[][] C11 = add(subtract(add(M1, M4), M5), M7);
        int[][] C12 = add(M3, M5);
        int[][] C21 = add(M2, M4);
        int[][] C22 = add(subtract(add(M1, M3), M2), M6);

        // Combine quadrants into result
        int[][] C = new int[n][n];
        join(C11, C, 0, 0);
        join(C12, C, 0, newSize);
        join(C21, C, newSize, 0);
        join(C22, C, newSize, newSize);

        return C;
    }

    // ================== HELPER FUNCTIONS ==================

    private static void split(int[][] P, int[][] C, int iB, int jB) {
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                C[i][j] = P[i + iB][j + jB];
            }
        }
    }

    private static void join(int[][] C, int[][] P, int iB, int jB) {
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                P[i + iB][j + jB] = C[i][j];
            }
        }
    }

    private static int[][] add(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    private static int[][] subtract(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

    // ================== OPERATION COUNTERS ==================

    static class OpCounter {
        long multiplications = 0;
        long additions = 0;
    }

    public static int[][] classicalMultiplyCount(int[][] A, int[][] B, OpCounter counter) {
        int n = A.length;
        int[][] C = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                    counter.multiplications++;
                    if (k > 0) counter.additions++;
                }
            }
        }

        return C;
    }

    // Count operations for Strassen (simplified - counts recursive calls)
    public static long strassenMultCount(int n) {
        if (n <= 1) return 1;
        return 7 * strassenMultCount(n / 2);
    }

    // ================== UTILITIES ==================

    private static void printMatrix(int[][] matrix, String name) {
        System.out.println(name + ":");
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.printf("%4d ", val);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean matricesEqual(int[][] A, int[][] B) {
        if (A.length != B.length) return false;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (A[i][j] != B[i][j]) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("=== Q10: Strassen's Matrix Multiplication ===\n");

        // Test with n = 8
        System.out.println("--- Part (b): n = 8 computation ---");
        
        OpCounter classicalCounter = new OpCounter();
        int n = 8;
        int[][] A = new int[n][n];
        int[][] B = new int[n][n];

        // Fill with simple values
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = i + j;
                B[i][j] = i - j + 1;
            }
        }

        int[][] C1 = classicalMultiplyCount(A, B, classicalCounter);
        System.out.printf("Classical (n=%d):%n", n);
        System.out.printf("  Multiplications: %d (n³ = %d)%n", 
                          classicalCounter.multiplications, n * n * n);
        System.out.printf("  Additions: %d (≈ n²(n-1) = %d)%n", 
                          classicalCounter.additions, n * n * (n - 1));

        long strassenMults = strassenMultCount(n);
        System.out.printf("Strassen (n=%d):%n", n);
        System.out.printf("  Multiplications: %d (7³ = 343)%n", strassenMults);
        System.out.printf("  Reduction: %.1f%%%n", 
                          100.0 * (classicalCounter.multiplications - strassenMults) / classicalCounter.multiplications);
        System.out.println();

        // Verify correctness
        int[][] C2 = strassenMultiply(A, B);
        System.out.println("Verification: " + (matricesEqual(C1, C2) ? "✓ Results match" : "✗ Results differ"));
        System.out.println();

        // Performance comparison
        System.out.println("--- Performance Comparison ---");
        int[] sizes = {4, 8, 16, 32, 64, 128, 256};

        for (int size : sizes) {
            int[][] testA = new int[size][size];
            int[][] testB = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    testA[i][j] = i + j + 1;
                    testB[i][j] = i - j + 1;
                }
            }

            long t1 = System.nanoTime();
            int[][] r1 = classicalMultiply(testA, testB);
            long classicalTime = System.nanoTime() - t1;

            long t2 = System.nanoTime();
            int[][] r2 = strassenMultiply(testA, testB);
            long strassenTime = System.nanoTime() - t2;

            System.out.printf("n=%4d: Classical=%6.3f ms, Strassen=%6.3f ms, Speedup=%.2fx%n",
                              size, classicalTime / 1e6, strassenTime / 1e6,
                              (double) classicalTime / strassenTime);
        }
        System.out.println();

        // Part (c): Practical considerations
        System.out.println("--- Part (c): Practical Considerations ---");
        System.out.println("Current cutoff: " + STRASSEN_CUTOFF);
        System.out.println("Benefits:");
        System.out.println("  - Asymptotically faster: Θ(n^2.807) vs Θ(n³)");
        System.out.println("  - Significant for large matrices (n > 512)");
        System.out.println("Concerns:");
        System.out.println("  1. Numerical stability (more subtractions)");
        System.out.println("  2. Cache inefficiency (irregular memory access)");
        System.out.println("  3. Memory overhead (temporary matrices)");
        System.out.println("  4. Requires power-of-2 padding");
        System.out.println("  5. Larger constant factors for small n");
    }
}
