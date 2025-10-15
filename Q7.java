public class Q7 {
    
    static class DirectedElement {
        int value;
        boolean pointsLeft;  // true = left (←), false = right (→)

        DirectedElement(int value, boolean pointsLeft) {
            this.value = value;
            this.pointsLeft = pointsLeft;
        }

        @Override
        public String toString() {
            return value + (pointsLeft ? "←" : "→");
        }
    }

    // Generate all permutations using Johnson-Trotter algorithm
    public static void generateJohnsonTrotter(int n, int limit) {
        DirectedElement[] perm = new DirectedElement[n];
        
        // Initialize: all elements point left
        for (int i = 0; i < n; i++) {
            perm[i] = new DirectedElement(i + 1, true);
        }

        int count = 0;
        System.out.printf("Perm %2d: %s%n", ++count, permToString(perm));

        while (count < limit) {
            // Find largest mobile element
            int mobileIndex = findLargestMobile(perm);
            
            if (mobileIndex == -1) {
                break;  // No mobile elements, done
            }

            int mobileValue = perm[mobileIndex].value;
            
            // Swap mobile element with adjacent element in direction of arrow
            int swapIndex = perm[mobileIndex].pointsLeft ? mobileIndex - 1 : mobileIndex + 1;
            swap(perm, mobileIndex, swapIndex);

            // Reverse direction of all elements larger than mobile element
            for (int i = 0; i < n; i++) {
                if (perm[i].value > mobileValue) {
                    perm[i].pointsLeft = !perm[i].pointsLeft;
                }
            }

            System.out.printf("Perm %2d: %s (moved %d from pos %d to %d)%n", 
                              ++count, permToString(perm), mobileValue, swapIndex, mobileIndex);
            
            if (count >= limit) break;
        }

        if (count < factorial(n)) {
            System.out.printf("... (%d more permutations)%n", factorial(n) - count);
        }
    }

    // Find index of largest mobile element, or -1 if none exists
    private static int findLargestMobile(DirectedElement[] perm) {
        int largestMobile = -1;
        int largestValue = -1;

        for (int i = 0; i < perm.length; i++) {
            if (isMobile(perm, i)) {
                if (perm[i].value > largestValue) {
                    largestValue = perm[i].value;
                    largestMobile = i;
                }
            }
        }

        return largestMobile;
    }

    // Check if element at index i is mobile
    private static boolean isMobile(DirectedElement[] perm, int i) {
        if (perm[i].pointsLeft) {
            // Points left: needs left neighbor that is smaller
            return i > 0 && perm[i - 1].value < perm[i].value;
        } else {
            // Points right: needs right neighbor that is smaller
            return i < perm.length - 1 && perm[i + 1].value < perm[i].value;
        }
    }

    private static void swap(DirectedElement[] perm, int i, int j) {
        DirectedElement temp = perm[i];
        perm[i] = perm[j];
        perm[j] = temp;
    }

    private static String permToString(DirectedElement[] perm) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < perm.length; i++) {
            if (i > 0) sb.append(" ");
            sb.append(perm[i]);
        }
        return sb.toString();
    }

    private static int factorial(int n) {
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Count total permutations generated (verify n!)
    public static int countPermutations(int n) {
        DirectedElement[] perm = new DirectedElement[n];
        for (int i = 0; i < n; i++) {
            perm[i] = new DirectedElement(i + 1, true);
        }

        int count = 1;  // Count initial permutation

        while (true) {
            int mobileIndex = findLargestMobile(perm);
            if (mobileIndex == -1) break;

            int mobileValue = perm[mobileIndex].value;
            int swapIndex = perm[mobileIndex].pointsLeft ? mobileIndex - 1 : mobileIndex + 1;
            swap(perm, mobileIndex, swapIndex);

            for (int i = 0; i < n; i++) {
                if (perm[i].value > mobileValue) {
                    perm[i].pointsLeft = !perm[i].pointsLeft;
                }
            }

            count++;
        }

        return count;
    }

    public static void main(String[] args) {
        System.out.println("=== Q7: Johnson-Trotter Permutations ===\n");

        // Part (a): First six permutations of {1, 2, 3, 4}
        System.out.println("--- Part (a): First 6 permutations of {1, 2, 3, 4} ---");
        generateJohnsonTrotter(4, 6);
        System.out.println();

        // Show all permutations of {1, 2, 3}
        System.out.println("--- All permutations of {1, 2, 3} ---");
        generateJohnsonTrotter(3, Integer.MAX_VALUE);
        System.out.println();

        // Part (b): Verify count and complexity
        System.out.println("--- Part (b): Time Complexity Analysis ---");
        for (int n = 3; n <= 7; n++) {
            int count = countPermutations(n);
            int expected = factorial(n);
            System.out.printf("n=%d: generated %d permutations (expected %d!) %s%n", 
                              n, count, n, count == expected ? "✓" : "✗");
        }

        System.out.println("\nTime complexity: Θ(n! × n)");
        System.out.println("- Must generate n! permutations");
        System.out.println("- Each transition takes O(n) time");
        System.out.println("- Optimal w.r.t. output size");
    }
}
