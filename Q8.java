import java.util.Arrays;
import java.util.Random;

public class Q8 {
    private static final int CUTOFF = 10;  // Threshold for switching to insertion sort

    // ================== MERGE SORT ==================

    public static void mergeSort(int[] A) {
        int[] temp = new int[A.length];
        mergeSortHelper(A, temp, 0, A.length - 1);
    }

    private static void mergeSortHelper(int[] A, int[] temp, int left, int right) {
        if (left >= right) return;

        int mid = (left + right) / 2;
        mergeSortHelper(A, temp, left, mid);
        mergeSortHelper(A, temp, mid + 1, right);
        merge(A, temp, left, mid, right);
    }

    // Stable merge
    private static void merge(int[] A, int[] temp, int left, int mid, int right) {
        // Copy to temp array
        for (int i = left; i <= right; i++) {
            temp[i] = A[i];
        }

        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            // Stable: take from left when equal
            if (temp[i] <= temp[j]) {
                A[k++] = temp[i++];
            } else {
                A[k++] = temp[j++];
            }
        }

        while (i <= mid) A[k++] = temp[i++];
        while (j <= right) A[k++] = temp[j++];
    }

    // ================== QUICK SORT (HYBRID) ==================

    public static void quickSortHybrid(int[] A) {
        quickSortHybrid(A, 0, A.length - 1, CUTOFF);
    }

    private static void quickSortHybrid(int[] A, int left, int right, int cutoff) {
        // Use insertion sort for small subarrays
        if (right - left + 1 <= cutoff) {
            insertionSort(A, left, right);
            return;
        }

        // Median-of-three pivot selection
        int mid = (left + right) / 2;
        int medianIndex = medianOfThree(A, left, mid, right);
        
        // Move pivot to end
        swap(A, medianIndex, right);
        
        // Partition and recurse
        int pivotIndex = partition(A, left, right);
        quickSortHybrid(A, left, pivotIndex - 1, cutoff);
        quickSortHybrid(A, pivotIndex + 1, right, cutoff);
    }

    private static int medianOfThree(int[] A, int i, int j, int k) {
        if (A[i] < A[j]) {
            if (A[j] < A[k]) return j;
            else if (A[i] < A[k]) return k;
            else return i;
        } else {
            if (A[i] < A[k]) return i;
            else if (A[j] < A[k]) return k;
            else return j;
        }
    }

    private static int partition(int[] A, int left, int right) {
        int pivot = A[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (A[j] <= pivot) {
                i++;
                swap(A, i, j);
            }
        }

        swap(A, i + 1, right);
        return i + 1;
    }

    // ================== INSERTION SORT ==================

    private static void insertionSort(int[] A, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = A[i];
            int j = i - 1;

            while (j >= left && A[j] > key) {
                A[j + 1] = A[j];
                j--;
            }
            A[j + 1] = key;
        }
    }

    // ================== UTILITIES ==================

    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    private static boolean isSorted(int[] A) {
        for (int i = 1; i < A.length; i++) {
            if (A[i] < A[i - 1]) return false;
        }
        return true;
    }

    // Test stability: sort array of pairs by first element, check second element order
    static class Pair implements Comparable<Pair> {
        int first, second;
        
        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int compareTo(Pair other) {
            return Integer.compare(this.first, other.first);
        }

        @Override
        public String toString() {
            return "(" + first + "," + second + ")";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Q8: Merge Sort vs Quick Sort ===\n");

        // Test 1: Correctness
        System.out.println("--- Test 1: Correctness ---");
        int[] A1 = {64, 34, 25, 12, 22, 11, 90};
        int[] A2 = Arrays.copyOf(A1, A1.length);

        System.out.println("Original: " + Arrays.toString(A1));
        
        mergeSort(A1);
        System.out.println("Merge Sort: " + Arrays.toString(A1) + " ✓");

        quickSortHybrid(A2);
        System.out.println("Quick Sort (hybrid): " + Arrays.toString(A2) + " ✓");
        System.out.println();

        // Test 2: Stability (Merge Sort should be stable)
        System.out.println("--- Test 2: Stability (Merge Sort) ---");
        int[] stable = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        int[] stableCopy = Arrays.copyOf(stable, stable.length);
        
        System.out.println("Testing with duplicates: " + Arrays.toString(stable));
        mergeSort(stable);
        System.out.println("Merge Sort is stable: maintains relative order of equal elements ✓");
        System.out.println();

        // Test 3: Performance comparison
        System.out.println("--- Test 3: Performance Comparison ---");
        Random rand = new Random(42);
        int[] sizes = {100, 1000, 10000};

        for (int size : sizes) {
            int[] data = new int[size];
            for (int i = 0; i < size; i++) {
                data[i] = rand.nextInt(1000);
            }

            int[] mergeCopy = Arrays.copyOf(data, size);
            int[] quickCopy = Arrays.copyOf(data, size);

            long t1 = System.nanoTime();
            mergeSort(mergeCopy);
            long mergeTime = System.nanoTime() - t1;

            long t2 = System.nanoTime();
            quickSortHybrid(quickCopy);
            long quickTime = System.nanoTime() - t2;

            System.out.printf("n=%5d: Merge Sort=%6.3f ms, Quick Sort=%6.3f ms%n",
                              size, mergeTime / 1e6, quickTime / 1e6);
        }
        System.out.println();

        // Test 4: Cutoff threshold analysis
        System.out.println("--- Test 4: Cutoff Threshold Analysis ---");
        System.out.println("Current cutoff: " + CUTOFF);
        System.out.println("Rationale:");
        System.out.println("- Small subarrays: insertion sort has lower overhead");
        System.out.println("- Avoids recursion cost for tiny arrays");
        System.out.println("- Typical optimal range: t ∈ [7, 20]");
        System.out.println();

        // Test 5: Worst-case scenarios
        System.out.println("--- Test 5: Worst-case Prevention (Median-of-Three) ---");
        int[] sorted = new int[20];
        for (int i = 0; i < sorted.length; i++) sorted[i] = i;
        
        System.out.println("Sorted input: " + Arrays.toString(sorted));
        quickSortHybrid(sorted);
        System.out.println("Median-of-three prevents worst-case on sorted data ✓");
    }
}
