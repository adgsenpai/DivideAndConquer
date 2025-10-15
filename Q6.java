import java.util.*;

public class Q6 {

    // Generate powerset recursively (adding elements one at a time)
    public static List<Set<Character>> generatePowerset(char[] elements) {
        List<Set<Character>> result = new ArrayList<>();
        generatePowersetHelper(elements, 0, new HashSet<>(), result);
        return result;
    }

    private static void generatePowersetHelper(char[] elements, int index, 
                                                Set<Character> current, 
                                                List<Set<Character>> result) {
        if (index == elements.length) {
            result.add(new HashSet<>(current));
            return;
        }

        // Don't include current element
        generatePowersetHelper(elements, index + 1, current, result);

        // Include current element
        current.add(elements[index]);
        generatePowersetHelper(elements, index + 1, current, result);
        current.remove(elements[index]);
    }

    // Generate powerset in binary reflected Gray code order
    public static List<Set<Character>> generatePowersetGrayCode(char[] elements) {
        int n = elements.length;
        int size = 1 << n;  // 2^n
        List<Set<Character>> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            int gray = i ^ (i >> 1);  // Convert to Gray code
            Set<Character> subset = new HashSet<>();
            
            for (int j = 0; j < n; j++) {
                if ((gray & (1 << j)) != 0) {
                    subset.add(elements[j]);
                }
            }
            result.add(subset);
        }

        return result;
    }

    // Calculate Gray code for a number
    public static int toGrayCode(int n) {
        return n ^ (n >> 1);
    }

    // Verify that consecutive Gray codes differ by exactly one bit
    public static boolean verifyGrayCodeProperty(int n) {
        int size = 1 << n;
        for (int i = 0; i < size - 1; i++) {
            int g1 = toGrayCode(i);
            int g2 = toGrayCode(i + 1);
            int diff = g1 ^ g2;
            
            // Check if diff is a power of 2 (exactly one bit set)
            if (diff == 0 || (diff & (diff - 1)) != 0) {
                System.out.printf("Failed at i=%d: Gray(%d)=%s, Gray(%d)=%s%n",
                                  i, i, toBinary(g1, n), i + 1, toBinary(g2, n));
                return false;
            }
        }
        return true;
    }

    private static String toBinary(int num, int bits) {
        return String.format("%" + bits + "s", Integer.toBinaryString(num)).replace(' ', '0');
    }

    private static String setToString(Set<Character> set, char[] elements) {
        if (set.isEmpty()) return "{}";
        
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        // Maintain order based on elements array
        for (char c : elements) {
            if (set.contains(c)) {
                if (!first) sb.append(", ");
                sb.append(c);
                first = false;
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("=== Q6: Generating Powerset and Gray Code ===\n");

        char[] elements = {'a', 'b', 'c', 'd'};

        // Part (a): Generate powerset recursively
        System.out.println("--- Part (a): Powerset of {a, b, c, d} (first 10 subsets) ---");
        List<Set<Character>> powerset = generatePowerset(elements);
        
        for (int i = 0; i < Math.min(10, powerset.size()); i++) {
            System.out.printf("%2d. %s%n", i + 1, setToString(powerset.get(i), elements));
        }
        System.out.printf("...%nTotal subsets: %d%n%n", powerset.size());

        // Part (b): Generate powerset via Gray code
        System.out.println("--- Part (b): Powerset via Binary Reflected Gray Code ---");
        List<Set<Character>> grayPowerset = generatePowersetGrayCode(elements);
        
        System.out.println("Binary  Gray  Subset");
        System.out.println("------  ----  ------");
        
        for (int i = 0; i < grayPowerset.size(); i++) {
            int gray = toGrayCode(i);
            System.out.printf("%s  %s  %s%n", 
                              toBinary(i, 4), 
                              toBinary(gray, 4),
                              setToString(grayPowerset.get(i), elements));
        }

        // Verify one-bit flip property
        System.out.println("\n--- Verification: One-bit flip property ---");
        boolean valid = verifyGrayCodeProperty(4);
        System.out.printf("Gray code property verified for n=4: %s%n", valid ? "✓" : "✗");

        // Show bit differences
        System.out.println("\nConsecutive differences:");
        for (int i = 0; i < grayPowerset.size() - 1; i++) {
            int g1 = toGrayCode(i);
            int g2 = toGrayCode(i + 1);
            int diff = g1 ^ g2;
            int bitPos = Integer.numberOfTrailingZeros(diff);
            
            Set<Character> s1 = grayPowerset.get(i);
            Set<Character> s2 = grayPowerset.get(i + 1);
            
            System.out.printf("%s → %s (flip bit %d, element '%c')%n",
                              setToString(s1, elements),
                              setToString(s2, elements),
                              bitPos,
                              elements[bitPos]);
        }
    }
}
