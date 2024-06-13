import java.util.Scanner;
import java.util.ArrayList;

public class floydwarshall {
    static void floydWarshallPatch1(int[][] dist, int n, int k) {
        for (int i = 0; i < k; i++) {
            dist[i][i] = 0;
        }
        for (int kth = 0; kth < n; kth++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][kth] + dist[kth][j]);
                }
            }
        }
    }

    static void floydWarshallPatch2(int[][] dist, int n) {
        for (int i = 0; i < n; i++) {
            dist[i][i] = 0;
        }
        for (int kth = 0; kth < n; kth++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][kth] + dist[kth][j]);
                }
            }
        }
    }

    static ArrayList<int[]> createGraph(int N, int M, int K) {
        if (K >= N || K == N - 1 || M < N - 1) {
            return null;
        }

        ArrayList<int[]> edges = new ArrayList<>();
        for (int i = 1; i < N; i++) {
            edges.add(new int[]{i, i + 1, 1});
        }

        if (M > N - 1) {
            edges.add(new int[]{N, 1, 10});
        }

        int additionalEdges = M - edges.size();
        while (additionalEdges > 0) {
            int fromNode = additionalEdges % N + 1;
            int toNode = (additionalEdges * 2) % N + 1;
            if (fromNode != toNode) {
                edges.add(new int[]{fromNode, toNode, additionalEdges + 5}); 
            }
            additionalEdges--;
        }

        return edges;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        sc.close();

        ArrayList<int[]> edges = createGraph(N, M, K);
        if (edges == null) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
            for (int[] e : edges) {
                System.out.println(e[0] + " " + e[1] + " " + e[2]);
            }
        }
    }
}