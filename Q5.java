import java.util.Arrays;
import java.util.Random;

public class Q5 {
    private static final Random rand = new Random();
    private static boolean TRACE = true;

    // Main quickselect function to find k-th smallest element (0-indexed)
    public static int quickselect(int[] A, int k) {
        if (k < 0 || k >= A.length) {
            throw new IllegalArgumentException("k out of bounds");
        }
        return quickselectHelper(A, 0, A.length - 1, k, 0);
    }

    private static int quickselectHelper(int[] A, int left, int right, int k, int depth) {
        String indent = "  ".repeat(depth);
        
        if (TRACE) {
            System.out.printf("%sQuickselect([%d..%d], k=%d): %s%n", 
                              indent, left, right, k, arrayToString(A, left, right));
        }

        // Base case: single element
        if (left == right) {
            if (TRACE) {
                System.out.printf("%sBase case: return A[%d] = %d%n", indent, left, A[left]);
            }
            return A[left];
        }

        // Choose random pivot
        int pivotIndex = left + rand.nextInt(right - left + 1);
        if (TRACE) {
            System.out.printf("%sChosen pivot: A[%d] = %d%n", indent, pivotIndex, A[pivotIndex]);
        }

        // Partition around pivot
        pivotIndex = partition(A, left, right, pivotIndex, indent);
        
        if (TRACE) {
            System.out.printf("%sAfter partition: %s (pivot at %d)%n", 
                              indent, arrayToString(A, left, right), pivotIndex);
        }

        // Check where k falls
        if (k == pivotIndex) {
            if (TRACE) {
                System.out.printf("%sFound! k == pivotIndex, return A[%d] = %d%n", 
                                  indent, k, A[k]);
            }
            return A[k];
        } else if (k < pivotIndex) {
            if (TRACE) {
                System.out.printf("%sk < pivotIndex, recurse left%n", indent);
            }
            return quickselectHelper(A, left, pivotIndex - 1, k, depth + 1);
        } else {
            if (TRACE) {
                System.out.printf("%sk > pivotIndex, recurse right%n", indent);
            }
            return quickselectHelper(A, pivotIndex + 1, right, k, depth + 1);
        }
    }

    // Partition array around pivot, return final pivot position
    private static int partition(int[] A, int left, int right, int pivotIndex, String indent) {
        int pivotValue = A[pivotIndex];
        
        // Move pivot to end
        swap(A, pivotIndex, right);
        
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (A[i] < pivotValue) {
                swap(A, i, storeIndex);
                storeIndex++;
            }
        }
        
        // Move pivot to final position
        swap(A, storeIndex, right);
        return storeIndex;
    }

    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    private static String arrayToString(int[] A, int left, int right) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = left; i <= right; i++) {
            if (i > left) sb.append(", ");
            sb.append(A[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    // Find median
    public static int findMedian(int[] A) {
        int[] copy = Arrays.copyOf(A, A.length);
        int k = copy.length / 2;  // 0-indexed middle element
        return quickselect(copy, k);
    }

    public static void main(String[] args) {
        System.out.println("=== Q5: Quickselect (k-th Order Statistic) ===\n");

        // Example from the markdown: find median of [12, 7, 3, 9, 14, 1, 10]
        int[] A = {12, 7, 3, 9, 14, 1, 10};
        System.out.println("--- Example: Find median of " + Arrays.toString(A) + " ---");
        System.out.println("Array length: " + A.length + ", median index k = " + (A.length / 2));
        System.out.println();

        // Set seed for reproducible trace
        rand.setSeed(42);
        int median = findMedian(A);
        System.out.printf("%nMedian = %d%n%n", median);

        // Verify by sorting
        int[] sorted = Arrays.copyOf(A, A.length);
        Arrays.sort(sorted);
        System.out.println("Verification (sorted array): " + Arrays.toString(sorted));
        System.out.println("Median at index " + (sorted.length / 2) + " = " + sorted[sorted.length / 2]);
        System.out.println();

        // Additional test: find minimum and maximum
        TRACE = false;  // Disable trace for brevity
        
        int[] B = {12, 7, 3, 9, 14, 1, 10};
        System.out.println("--- Finding min, median, max in " + Arrays.toString(B) + " ---");
        
        int min = quickselect(Arrays.copyOf(B, B.length), 0);
        int med = quickselect(Arrays.copyOf(B, B.length), B.length / 2);
        int max = quickselect(Arrays.copyOf(B, B.length), B.length - 1);
        
        System.out.printf("Min (k=0): %d%n", min);
        System.out.printf("Median (k=%d): %d%n", B.length / 2, med);
        System.out.printf("Max (k=%d): %d%n", B.length - 1, max);
    }
}
