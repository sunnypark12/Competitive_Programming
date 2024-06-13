import java.util.Scanner;

public class dpd {
    private static final int MAX_CAPACITY = 100010;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        int capacity = input.nextInt();

        long[] values = new long[MAX_CAPACITY];

        for (int item = 1; item <= count; item++) {
            int weight = sc.nextInt();
            int value = sc.nextInt();
            for (int currW = capacity; currW >= weight; currW--) {
                values[currW] = Math.max(values[currW - weight] + value, values[currW]);
            }
        }

        long maxValue = findMax(values, capacity);
        System.out.println(maxValue);
        sc.close();
    }

    private static long findMax(long[] values, int capacity) {
        long best = 0;
        for (int i = 0; i <= capacity; i++) {
            best = Math.max(best, values[i]);
        }
        return best;
    }
}