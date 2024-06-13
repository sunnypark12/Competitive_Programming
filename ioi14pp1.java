import java.io.*;
import java.util.*;

public class ioi14pp1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] material = new int[n][n];
        int[][] dp = new int[n][n];
        int max = 0, c = 0;

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                material[i][j] = Integer.parseInt(st.nextToken());
                if (material[i][j] == 1) {
                    dp[i][j] = (i == 0 || j == 0) ? 1 : Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    if (dp[i][j] > max) {
                        max = dp[i][j];
                        c = 1;
                    } else if (dp[i][j] == max) {
                        c++;
                    }
                }
            }
        }

        int result = max * c;
        System.out.println(result);
    }
}