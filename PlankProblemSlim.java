public class PlankProblemSlim {
    static long f(long x, long[] L) {
        if (x <= 0)
            return Long.MAX_VALUE; // guard
        long count = 0;
        for (long len : L)
            count += len / x; // floor division
        return count;
    }

    // Find the maximum x such that f(x, L) >= K
    static long maxLength(long[] L, long K) {
        long lo = 0, hi = 0;
        for (long len : L)
            hi = Math.max(hi, len); // max possible length
        while (lo < hi) {
            long mid = (lo + hi + 1) / 2; // upper mid
            if (f(mid, L) >= K)
                lo = mid; // feasible -> go higher
            else
                hi = mid - 1; // infeasible -> go lower
        }
        return lo; // == hi
    }

    // Convenience “process” for your example input
    static long process() {
        long[] L = { 10, 14, 15, 11 };
        long K = 6;
        return maxLength(L, K); // -> 7
    }

    public static void main(String[] args) {
        System.out.println(process()); // -> 7
    }
}