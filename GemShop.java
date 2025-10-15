public class GemShop {

    // Triangular sum: 1 + 2 + ... + x
    static long sumTo(long x) {
        // compute x*(x+1)/2 in 128-bit order to minimize overflow risk
        // (assuming K, N are within typical contest ranges this is fine)
        return (x & 1L) == 0 ? (x / 2) * (x + 1) : x * ((x + 1) / 2);
    }

    // Sum over a..b inclusive
    static long sumRange(long a, long b) {
        if (a > b) return 0L;
        return sumTo(b) - sumTo(a - 1);
    }

    // O(sqrt K) using quotient grouping:
    // S(K) = sum_{j=1}^{K-1} j * floor(K/j)
    static long fFast(long K) {
        if (K <= 1) return 0L;
        long ans = 0L;
        long j = 1L;
        long jMax = K - 1;

        while (j <= jMax) {
            long q = K / j;                 // current quotient
            long r = Math.min(jMax, K / q); // last j with same quotient
            // sum_{t=j}^{r} t = arithmetic series
            long blockSum = sumRange(j, r);
            ans += q * blockSum;
            j = r + 1;
        }
        return ans;
    }

    // Find max k such that fFast(k) <= N
    // Monotone in k; binary search with O(sqrt k) check
    static long maxK(long N) {
        long lo = 0, hi = Math.max(1L, N); // safe upper bound; you can also exponential-search
        while (lo < hi) {
            long mid = (lo + hi + 1) >>> 1;
            if (fFast(mid) <= N) lo = mid;
            else hi = mid - 1;
        }
        return lo;
    }

    // (Optional) sanity-only brute for very small N
    static long maxKBrute(long N) {
        long k = 0;
        while (true) {
            long next = fFast(k + 1);
            if (next <= N) k++;
            else break;
        }
        return k;
    }

    // --- Minimal demo + timings (optional) ---
    static long timeNanos(Runnable r) {
        long t0 = System.nanoTime();
        r.run();
        return System.nanoTime() - t0;
    }
    static double ms(long nanos) { return nanos / 1_000_000.0; }

    public static void main(String[] args) {
        long K = 100_000L;
        long N = 1_000_000_000L;

        long t1 = timeNanos(() -> {
            long val = fFast(K);
            System.out.println("fFast(" + K + ") = " + val);
        });
        System.out.printf("time fFast(K=%d): %.3f ms%n", K, ms(t1));

        long t2 = timeNanos(() -> {
            long k = maxK(N);
            System.out.println("maxK(N=" + N + ") = " + k);
        });
        System.out.printf("time maxK(N=%d): %.3f ms%n", N, ms(t2));
    }
}