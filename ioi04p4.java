import java.io.*;
import java.util.*;

public class ioi04p4 {
    private static final int wmax = 601;
    private static final int hmax = 601;
    private static int[][] dp = new int[wmax][hmax];
    private static boolean[][] plate = new boolean[wmax][hmax];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] input = br.readLine().split(" ");
        int W = Integer.parseInt(input[0]);
        int H = Integer.parseInt(input[1]);

        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            String[] psize = br.readLine().split(" ");
            int w = Integer.parseInt(psize[0]);
            int h = Integer.parseInt(psize[1]);
            plate[w][h] = true;
        }

        for (int[] i : dp) {
            Arrays.fill(i, Integer.MAX_VALUE);
        }
        dp[0][0] = 0; 
        int wmin = wmin(W, H);

        System.out.println(wmin);
    }

    private static int wmin(int w, int h) {
        if (plate[w][h]) {
            dp[w][h] = 0;
            return 0;
        }
        if (dp[w][h] != Integer.MAX_VALUE) {
            return dp[w][h];
        }

        for (int i = 1; i <= h / 2; i++) {
            dp[w][h] = Math.min(dp[w][h], wmin(w, i) + wmin(w, h - i));
        }
        for (int i = 1; i <= w / 2; i++) {
            dp[w][h] = Math.min(dp[w][h], wmin(i, h) + wmin(w - i, h));
        }

        if (dp[w][h] == Integer.MAX_VALUE) {
            dp[w][h] = w * h;
        }
        return dp[w][h];
    }
}