public class Q2 {

    // Toggle this to turn tracing on/off without changing code elsewhere
    private static final boolean TRACE = true;

    public static int binarySearch(int[] A, int target) {
        int left = 0, right = A.length - 1;
        int probes = 0;

        if (TRACE) {
            System.out.println("=== Binary Search Trace ===");
            System.out.printf("Target=%d, Array=%s%n", target, java.util.Arrays.toString(A));
        }

        while (left <= right) {
            int mid = left + (right - left) / 2;
            probes++;

            if (TRACE) {
                System.out.printf("[Binary] left=%d right=%d mid=%d A[mid]=%d%n",
                        left, right, mid, A[mid]);
            }

            if (A[mid] == target) {
                if (TRACE) {
                    System.out.printf("[Binary] FOUND target at index %d (probes=%d)%n", mid, probes);
                }
                return mid;
            } else if (A[mid] < target) {
                left = mid + 1; // go right
            } else {
                right = mid - 1; // go left
            }
        }

        if (TRACE) {
            System.out.printf("[Binary] NOT FOUND (probes=%d)%n", probes);
        }
        return -1;
    }

    public static int interpolationSearch(int[] A, int target) {
        int low = 0, high = A.length - 1;
        int probes = 0;

        if (TRACE) {
            System.out.println("\n=== Interpolation Search Trace ===");
            System.out.printf("Target=%d, Array=%s%n", target, java.util.Arrays.toString(A));
        }

        // Keep searching while target is within the current value range
        while (low <= high && target >= A[low] && target <= A[high]) {
            // Single element range
            if (low == high) {
                probes++;
                if (TRACE) {
                    System.out.printf("[Interp] low==high==%d A[low]=%d%n", low, A[low]);
                }
                if (A[low] == target) {
                    if (TRACE) {
                        System.out.printf("[Interp] FOUND target at index %d (probes=%d)%n", low, probes);
                    }
                    return low;
                }
                if (TRACE) System.out.printf("[Interp] NOT FOUND (probes=%d)%n", probes);
                return -1;
            }

            // Avoid division by zero when all values in range are equal
            int denom = A[high] - A[low];
            if (denom == 0) {
                // All values in A[low..high] are equal
                probes++;
                if (TRACE) {
                    System.out.printf("[Interp] A[low]==A[high]==%d; checking equality%n", A[low]);
                }
                if (A[low] == target) {
                    if (TRACE) {
                        System.out.printf("[Interp] FOUND target at index %d (probes=%d)%n", low, probes);
                    }
                    return low; // any index in [low..high] is valid; choose low
                }
                if (TRACE) System.out.printf("[Interp] NOT FOUND (probes=%d)%n", probes);
                return -1;
            }

            // Estimate position (use long in numerator to avoid rare overflow)
            int pos = low + (int) (((long) (target - A[low]) * (high - low)) / denom);
            probes++;

            if (TRACE) {
                System.out.printf("[Interp] low=%d high=%d A[low]=%d A[high]=%d -> pos=%d A[pos]=%d%n",
                        low, high, A[low], A[high], pos, A[pos]);
            }

            if (A[pos] == target) {
                if (TRACE) {
                    System.out.printf("[Interp] FOUND target at index %d (probes=%d)%n", pos, probes);
                }
                return pos;
            } else if (A[pos] < target) {
                low = pos + 1; // search right subarray
            } else {
                high = pos - 1; // search left subarray
            }
        }

        if (TRACE) {
            System.out.printf("[Interp] NOT FOUND within value bounds (probes=%d)%n", probes);
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] A = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        // Demo 1: target present
        int target1 = 5;
        int b1 = binarySearch(A, target1);
        int i1 = interpolationSearch(A, target1);
        System.out.printf("%nBinary Search Result: %d%n", b1);
        System.out.printf("Interpolation Search Result: %d%n", i1);

        // Demo 2: target absent
        int target2 = 13;
        int b2 = binarySearch(A, target2);
        int i2 = interpolationSearch(A, target2);
        System.out.printf("%nBinary Search Result (absent): %d%n", b2);
        System.out.printf("Interpolation Search Result (absent): %d%n", i2);
    }
}
