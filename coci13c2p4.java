import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class coci13c2p4 {
    private static final int MAX = 1501;
    private static int n;
    private static int[][] adj = new int[MAX][MAX];
    private static int[][] dp = new int[MAX][MAX];

    private static int seq(int i, int j) {
        if (j == n || i == n) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int next = Math.max(i, j) + 1;
        int a1 = seq(next, j) + adj[next][i];
        int a2 = seq(i, next) + adj[j][next];
        dp[i][j] = Math.min(a1, a2);
        return dp[i][j];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine().trim());

        for (int i = 0; i < n; i++) {
            String[] input = br.readLine().trim().split(" ");
            for (int j = 0; j < n; j++) {
                adj[i][j] = Integer.parseInt(input[j]);
            }
        }
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }
        System.out.println(seq(0, 0));
    }
}