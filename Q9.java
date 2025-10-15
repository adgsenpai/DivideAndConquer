import java.util.*;

public class Q9 {
    
    static class Point {
        double x, y;
        
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double distance(Point other) {
            double dx = this.x - other.x;
            double dy = this.y - other.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        @Override
        public String toString() {
            return String.format("(%.0f,%.0f)", x, y);
        }
    }

    static class PointPair {
        Point p1, p2;
        double distance;

        PointPair(Point p1, Point p2, double distance) {
            this.p1 = p1;
            this.p2 = p2;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return String.format("%s-%s: %.3f", p1, p2, distance);
        }
    }

    // Main closest pair function
    public static PointPair closestPair(Point[] points) {
        // Sort by x-coordinate
        Point[] sortedX = points.clone();
        Arrays.sort(sortedX, Comparator.comparingDouble(p -> p.x));

        // Sort by y-coordinate for strip checks
        Point[] sortedY = points.clone();
        Arrays.sort(sortedY, Comparator.comparingDouble(p -> p.y));

        return closestPairRecursive(sortedX, sortedY, 0, points.length - 1);
    }

    private static PointPair closestPairRecursive(Point[] px, Point[] py, int left, int right) {
        int n = right - left + 1;

        // Base case: brute force for small arrays
        if (n <= 3) {
            return bruteForce(px, left, right);
        }

        // Divide
        int mid = (left + right) / 2;
        Point midPoint = px[mid];

        // Split py into left and right based on x-coordinate
        List<Point> pyLeft = new ArrayList<>();
        List<Point> pyRight = new ArrayList<>();
        
        for (Point p : py) {
            if (p.x <= midPoint.x && pyLeft.size() < mid - left + 1) {
                pyLeft.add(p);
            } else {
                pyRight.add(p);
            }
        }

        // Conquer
        PointPair leftPair = closestPairRecursive(px, pyLeft.toArray(new Point[0]), left, mid);
        PointPair rightPair = closestPairRecursive(px, pyRight.toArray(new Point[0]), mid + 1, right);

        // Find minimum of two sides
        PointPair minPair = (leftPair.distance <= rightPair.distance) ? leftPair : rightPair;
        double delta = minPair.distance;

        // Check strip
        PointPair stripPair = checkStrip(py, midPoint.x, delta);

        // Return minimum
        if (stripPair != null && stripPair.distance < minPair.distance) {
            return stripPair;
        }
        return minPair;
    }

    // Brute force for small arrays
    private static PointPair bruteForce(Point[] points, int left, int right) {
        double minDist = Double.POSITIVE_INFINITY;
        Point p1 = null, p2 = null;

        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                double dist = points[i].distance(points[j]);
                if (dist < minDist) {
                    minDist = dist;
                    p1 = points[i];
                    p2 = points[j];
                }
            }
        }

        return new PointPair(p1, p2, minDist);
    }

    // Check strip for closer pairs
    private static PointPair checkStrip(Point[] py, double midX, double delta) {
        // Filter points in strip
        List<Point> strip = new ArrayList<>();
        for (Point p : py) {
            if (Math.abs(p.x - midX) < delta) {
                strip.add(p);
            }
        }

        double minDist = delta;
        Point p1 = null, p2 = null;

        // Check each point against next 7 points (packing lemma)
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && j < i + 8; j++) {
                // Early termination: if y-difference > delta, no need to check further
                if (strip.get(j).y - strip.get(i).y >= delta) {
                    break;
                }

                double dist = strip.get(i).distance(strip.get(j));
                if (dist < minDist) {
                    minDist = dist;
                    p1 = strip.get(i);
                    p2 = strip.get(j);
                }
            }
        }

        if (p1 != null && p2 != null) {
            return new PointPair(p1, p2, minDist);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("=== Q9: Closest Pair of Points ===\n");

        // Example from markdown: {(0,0), (1,2), (2,1), (3,3), (4,0), (5,2)}
        Point[] points = {
            new Point(0, 0),
            new Point(1, 2),
            new Point(2, 1),
            new Point(3, 3),
            new Point(4, 0),
            new Point(5, 2)
        };

        System.out.println("Points: " + Arrays.toString(points));
        System.out.println();

        PointPair result = closestPair(points);
        System.out.println("Closest pair: " + result);
        System.out.printf("Distance: %.3f (√2 ≈ 1.414)%n", result.distance);
        System.out.println();

        // Verify by brute force
        System.out.println("--- Verification (brute force) ---");
        PointPair bruteResult = bruteForce(points, 0, points.length - 1);
        System.out.println("Brute force result: " + bruteResult);
        System.out.println();

        // Test with more points
        System.out.println("--- Test with random points ---");
        Random rand = new Random(42);
        Point[] randomPoints = new Point[20];
        for (int i = 0; i < 20; i++) {
            randomPoints[i] = new Point(rand.nextInt(100), rand.nextInt(100));
        }

        PointPair randomResult = closestPair(randomPoints);
        System.out.println("Closest pair in 20 random points: " + randomResult);
        System.out.println();

        // Time complexity demonstration
        System.out.println("--- Time Complexity: O(n log n) ---");
        int[] sizes = {100, 1000, 10000};
        for (int size : sizes) {
            Point[] testPoints = new Point[size];
            for (int i = 0; i < size; i++) {
                testPoints[i] = new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
            }

            long start = System.nanoTime();
            closestPair(testPoints);
            long time = System.nanoTime() - start;

            System.out.printf("n=%5d: %.3f ms (expected: O(n log n))%n", size, time / 1e6);
        }
    }
}
