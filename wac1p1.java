import java.util.Scanner;

public class wac1p1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for (int i = 0; i < T; i++) {
            long M = sc.nextLong();
            long N = minVertex(M);
            System.out.println(N);
        }
        sc.close();
    }

    private static long minVertex(long M) {
        long left = 0, right = (long) Math.ceil(Math.sqrt(2 * M));
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (mid * (mid - 1) / 2 < M) {
                left = mid + 1;
            } else if (mid * (mid - 1) / 2 > M) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return left;
    }
}