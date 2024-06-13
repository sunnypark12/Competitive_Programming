import java.util.Scanner;

public class ioi94p1 {
    private static final int max = 200;
    private static int[][] V = new int[max][max];
    private static Integer[][] dp = new Integer[max][max];
    private static int N;

    private static int findSum(int r, int c) {
        if (r == N - 1) {
            return V[r][c];
        }
        if (dp[r][c] != null) {
            return dp[r][c];
        }
        int left = findSum(r + 1, c);
        int right = findSum(r + 1, c + 1);
        dp[r][c] = V[r][c] + Math.max(left, right);
        return dp[r][c];
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        N = input.nextInt();
        for (int r = 0; r < N; r++) {
            for (int c = 0; c <= r; c++) {
                V[r][c] = input.nextInt();
            }
        }
        System.out.println(findSum(0, 0));
        input.close();
    }
}