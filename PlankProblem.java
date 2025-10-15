import java.io.*;
import java.util.*;

public class PlankProblem {
    // Returns the maximum integer length M such that cutting all planks in L into
    // pieces of length M yields at least K pieces. If impossible, returns 0.
    static long maxLength(long[] L, long K) {
        long hi = 0;
        for (long x : L) hi = Math.max(hi, x);  // search upper bound = max plank length
        long lo = 0;                             // allow 0 to represent "impossible"

        while (lo < hi) {
            long mid = (lo + hi + 1) / 2;       // upper mid to avoid infinite loop
            if (piecesAtLeast(L, mid) >= K) {
                lo = mid;                       // feasible -> try longer
            } else {
                hi = mid - 1;                   // infeasible -> go shorter
            }
        }
        return lo; // == hi
    }

    // Count how many pieces of length m we can make from all planks.
    static long piecesAtLeast(long[] L, long m) {
        if (m <= 0) return Long.MAX_VALUE; // guard; shouldn’t happen in our search
        long count = 0;
        for (long x : L) {
            count += x / m;                // floor division
            // Optional early exit to avoid overflow (not required with given bounds)
            if (count < 0) return Long.MAX_VALUE;
        }
        return count;
    }

    // ---- I/O as specified: each number on its own line ----
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Read all non-empty lines as longs
        ArrayList<Long> vals = new ArrayList<>();
        for (String s; (s = br.readLine()) != null; ) {
            s = s.trim();
            if (!s.isEmpty()) vals.add(Long.parseLong(s));
        }
        if (vals.isEmpty()) return;

        int idx = 0;
        int N = vals.get(idx++).intValue();
        long[] L = new long[N];
        for (int i = 0; i < N; i++) L[i] = vals.get(idx++);
        long K = vals.get(idx++);

        long ans = maxLength(L, K);
        System.out.println(ans);
    }
}
