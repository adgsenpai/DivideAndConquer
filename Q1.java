public class Q1 {

    public static void insertionSort(int[] A) {
        int n = A.length;
        for (int i = 1; i < n; i++) {
            int key = A[i];
            int j = i - 1;

            // Move elements of A[0..i-1], that are greater than key,
            // to one position ahead of their current position
            while (j >= 0 && A[j] > key) {
                A[j + 1] = A[j];
                j--;
            }
            A[j + 1] = key;
        }
    }

    private static void printArray(int[] A) {
        for (int num : A) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] A = {6, 2, 9, 1, 5, 3};
        insertionSort(A);
        printArray(A); // -> 1 2 3 5 6 9
    }
}
