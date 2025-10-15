public class Q4 {

    // Euclid's GCD algorithm with trace
    public static int gcd(int m, int n) {
        System.out.printf("gcd(%d, %d)%n", m, n);
        return gcdHelper(m, n, 0);
    }

    private static int gcdHelper(int m, int n, int depth) {
        if (n == 0) {
            System.out.printf("%sBase case: gcd(%d, 0) = %d%n", indent(depth), m, m);
            return m;
        }
        
        int remainder = m % n;
        int quotient = m / n;
        System.out.printf("%s%d = %d × %d + %d%n", indent(depth), m, quotient, n, remainder);
        System.out.printf("%sgcd(%d, %d) = gcd(%d, %d)%n", indent(depth), m, n, n, remainder);
        
        return gcdHelper(n, remainder, depth + 1);
    }

    // Iterative version for comparison
    public static int gcdIterative(int m, int n) {
        int steps = 0;
        System.out.printf("Iterative GCD(%d, %d):%n", m, n);
        
        while (n != 0) {
            steps++;
            int remainder = m % n;
            System.out.printf("Step %d: %d = %d × %d + %d%n", steps, m, m / n, n, remainder);
            m = n;
            n = remainder;
        }
        
        System.out.printf("Result: %d (in %d steps)%n", m, steps);
        return m;
    }

    // Count steps to verify O(log min{m,n}) complexity
    public static int gcdSteps(int m, int n) {
        int steps = 0;
        while (n != 0) {
            int temp = n;
            n = m % n;
            m = temp;
            steps++;
        }
        return steps;
    }

    // Fibonacci number generator (for verification)
    public static int fibonacci(int n) {
        if (n <= 1) return 1;
        int a = 1, b = 1;
        for (int i = 2; i <= n; i++) {
            int temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }

    // Verify Lamé's theorem: if GCD takes k steps, then n >= F_{k+1}
    public static void verifyLamesTheorem(int m, int n) {
        int steps = gcdSteps(m, n);
        int fib = fibonacci(steps + 1);
        int minVal = Math.min(m, n);
        
        System.out.printf("Steps: %d, F_%d = %d, min{%d,%d} = %d%n", 
                          steps, steps + 1, fib, m, n, minVal);
        System.out.printf("Lamé's theorem: %d >= %d ? %s%n", 
                          minVal, fib, minVal >= fib ? "✓" : "✗");
    }

    public static void main(String[] args) {
        System.out.println("=== Q4: Euclid's GCD Algorithm ===\n");

        // Example 1: From the markdown
        System.out.println("--- Example 1: gcd(252, 105) ---");
        int result1 = gcd(252, 105);
        System.out.printf("Final result: %d%n%n", result1);

        // Example 2: Iterative version
        System.out.println("--- Example 2: gcd(1071, 462) (iterative) ---");
        int result2 = gcdIterative(1071, 462);
        System.out.println();

        // Example 3: Verify Lamé's theorem with Fibonacci worst case
        System.out.println("--- Example 3: Fibonacci worst case ---");
        int f6 = fibonacci(6);  // F_6 = 8
        int f7 = fibonacci(7);  // F_7 = 13
        System.out.printf("F_6 = %d, F_7 = %d%n", f6, f7);
        System.out.printf("Testing gcd(F_7, F_6) = gcd(%d, %d):%n", f7, f6);
        verifyLamesTheorem(f7, f6);
        System.out.println();

        // Example 4: Analysis of several cases
        System.out.println("--- Example 4: Complexity Analysis ---");
        int[][] testCases = {
            {100, 35},
            {1000, 600},
            {10000, 3000},
            {100000, 40000}
        };

        for (int[] testCase : testCases) {
            int m = testCase[0];
            int n = testCase[1];
            int steps = gcdSteps(m, n);
            double logMin = Math.log(Math.min(m, n)) / Math.log(2);
            System.out.printf("gcd(%d, %d): %d steps, log₂(min) = %.2f%n", 
                              m, n, steps, logMin);
        }
    }

    private static String indent(int depth) {
        return "  ".repeat(depth);
    }
}
