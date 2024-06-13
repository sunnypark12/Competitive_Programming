import java.util.Scanner;

public class dph {
    private static final int MAXN = 1010;
    private static final int MOD = 1000000007;

    private static char[][] grid = new char[MAXN][MAXN];
    private static int[][] dp = new int[MAXN][MAXN];
    private static boolean[][] visited = new boolean[MAXN][MAXN];
    private static int H, W;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        H = sc.nextInt();
        W = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < H; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < W; j++) {
                grid[i][j] = line.charAt(j);
            }
        }

        System.out.println(findPaths(0, 0));
        sc.close();
    }

    private static int findPaths(int x, int y) {
        if (x >= H || y >= W) return 0;
        if (grid[x][y] == '#') return 0;
        if (visited[x][y]) return dp[x][y];
        if (x == H - 1 && y == W - 1) return dp[x][y] = 1;

        visited[x][y] = true;
        dp[x][y] = (findPaths(x + 1, y) + findPaths(x, y + 1)) % MOD;
        return dp[x][y];
    }
}