public class GemShop {
    // Exact sum: S(K) = sum_{j=1}^{K-1} j * floor(K/j)
    // Time: Θ(K)
    static long fLinear(long K) {
        if (K <= 1) return 0L;
        long ans = 0;
        for (long j = 1; j < K; j++) {
            ans += j * (K / j);
        }
        return ans;
    }

    // Find max k such that fLinear(k) <= N
    // Binary search over k in [0, N]; each check costs Θ(k)
    static long maxK(long N) {
        long low = 0, high = N;
        while (low < high) {
            long mid = (low + high + 1) / 2; // upper mid
            if (fLinear(mid) <= N) {
                low = mid;          // mid is feasible
            } else {
                high = mid - 1;     // mid too large
            }
        }
        return low; // == high
    }

    // Brute force: increment k until it breaks the budget N
    // Time: potentially large; only for small N/testing
    static long maxKBrute(long N) {
        long k = 0;
        while (true) {
            long next = fLinear(k + 1);
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
            long val = fLinear(K);
            System.out.println("fLinear(" + K + ") = " + val);
        });
        System.out.printf("time fLinear(K=%d): %.3f ms%n", K, ms(t1));

        long t2 = timeNanos(() -> {
            long k = maxK(N);
            System.out.println("maxK(N=" + N + ") = " + k);
        });
        System.out.printf("time maxK(N=%d): %.3f ms%n", N, ms(t2));

        long smallN = 100_000L; // keep small for brute
        long t3 = timeNanos(() -> {
            long kb = maxKBrute(smallN);
            System.out.println("maxKBrute(N=" + smallN + ") = " + kb);
        });
        System.out.printf("time maxKBrute(N=%d): %.3f ms%n", smallN, ms(t3));
    }
}
