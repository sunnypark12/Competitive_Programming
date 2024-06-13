import java.util.Scanner;
import java.util.Arrays;

public class dpb {
    private static final int MAXN = 100010;
    private static final int INF = 2000000000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        int[] stoneHeights = new int[MAXN];
        int[] minCost = new int[MAXN];
        for (int i = 1; i <= N; i++) {
            stoneHeights[i] = sc.nextInt();
        }

        Arrays.fill(minCost, INF);
        minCost[1] = 0;
        for (int i = 2; i <= N; i++) {
            for (int j = Math.max(i - K, 1); j < i; j++) {
                minCost[i] = Math.min(minCost[i], minCost[j] + Math.abs(stoneHeights[i] - stoneHeights[j]));
            }
        }

        System.out.println(minCost[N]); 
        sc.close();
    }
}