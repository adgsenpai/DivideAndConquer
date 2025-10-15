public class Q3 {

    // Simulate finding a fake (lighter) coin using 2-pile method
    public static int findFakeCoin2Pile(int n, int fakeIndex) {
        return findFakeCoin2PileHelper(0, n - 1, fakeIndex, 0);
    }

    private static int findFakeCoin2PileHelper(int low, int high, int fakeIndex, int weighings) {
        if (low == high) {
            System.out.printf("Found fake coin at index %d with %d weighings (2-pile)%n", low, weighings);
            return weighings;
        }

        int mid = (low + high) / 2;
        System.out.printf("Weighing %d: Compare coins [%d-%d] vs [%d-%d]%n", 
                          weighings + 1, low, mid, mid + 1, high);

        // The pile containing the fake coin is lighter
        if (fakeIndex <= mid) {
            System.out.printf("  Left pile is lighter%n");
            return findFakeCoin2PileHelper(low, mid, fakeIndex, weighings + 1);
        } else {
            System.out.printf("  Right pile is lighter%n");
            return findFakeCoin2PileHelper(mid + 1, high, fakeIndex, weighings + 1);
        }
    }

    // Simulate finding a fake (lighter) coin using 3-pile method
    public static int findFakeCoin3Pile(int n, int fakeIndex) {
        return findFakeCoin3PileHelper(0, n - 1, fakeIndex, 0);
    }

    private static int findFakeCoin3PileHelper(int low, int high, int fakeIndex, int weighings) {
        if (low == high) {
            System.out.printf("Found fake coin at index %d with %d weighings (3-pile)%n", low, weighings);
            return weighings;
        }

        int count = high - low + 1;
        int pileSize = count / 3;
        int pile1End = low + pileSize - 1;
        int pile2End = pile1End + pileSize;

        System.out.printf("Weighing %d: Compare coins [%d-%d] vs [%d-%d] (third pile: [%d-%d])%n",
                          weighings + 1, low, pile1End, pile1End + 1, pile2End, pile2End + 1, high);

        if (fakeIndex <= pile1End) {
            System.out.printf("  First pile is lighter%n");
            return findFakeCoin3PileHelper(low, pile1End, fakeIndex, weighings + 1);
        } else if (fakeIndex <= pile2End) {
            System.out.printf("  Second pile is lighter%n");
            return findFakeCoin3PileHelper(pile1End + 1, pile2End, fakeIndex, weighings + 1);
        } else {
            System.out.printf("  Piles balanced, fake in third pile%n");
            return findFakeCoin3PileHelper(pile2End + 1, high, fakeIndex, weighings + 1);
        }
    }

    // Calculate theoretical number of weighings needed
    public static int theoreticalWeighings2Pile(int n) {
        return (int) Math.ceil(Math.log(n) / Math.log(2));
    }

    public static int theoreticalWeighings3Pile(int n) {
        return (int) Math.ceil(Math.log(n) / Math.log(3));
    }

    public static void main(String[] args) {
        System.out.println("=== Q3: Fake Coin Problem ===\n");

        // Example 1: n = 26 coins, fake at index 20
        int n = 26;
        int fakeIndex = 20;

        System.out.printf("Finding fake coin among %d coins (fake is at index %d)%n%n", n, fakeIndex);

        System.out.println("--- 2-Pile Method ---");
        int weighings2 = findFakeCoin2Pile(n, fakeIndex);
        int theoretical2 = theoreticalWeighings2Pile(n);
        System.out.printf("Theoretical weighings for 2-pile: ⌈log₂ %d⌉ = %d%n%n", n, theoretical2);

        System.out.println("--- 3-Pile Method ---");
        int weighings3 = findFakeCoin3Pile(n, fakeIndex);
        int theoretical3 = theoreticalWeighings3Pile(n);
        System.out.printf("Theoretical weighings for 3-pile: ⌈log₃ %d⌉ = %d%n%n", n, theoretical3);

        System.out.println("--- Comparison ---");
        System.out.printf("2-pile method: %d weighings%n", weighings2);
        System.out.printf("3-pile method: %d weighings%n", weighings3);
        System.out.printf("Improvement: %.1f%%%n", 100.0 * (weighings2 - weighings3) / weighings2);
    }
}
