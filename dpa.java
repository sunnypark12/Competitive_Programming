import java.util.Scanner;

public class dpa {
    private static final int INF = 2 * (int)1e9;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int[] stoneHeights = new int[N + 1]; 
        int[] dp = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            stoneHeights[i] = scanner.nextInt();
        }

        for (int i = 2; i <= N; i++) {
            dp[i] = INF;
        }
        dp[1] = 0;
        for (int i = 2; i <= N; i++) {
            for (int j = i - 1; j >= Math.max(i - 2, 1); j--) {
                int cost = Math.abs(stoneHeights[i] - stoneHeights[j]);
                dp[i] = Math.min(dp[i], dp[j] + cost);
            }
        }

        System.out.println(dp[N]);
        scanner.close();
    }
}