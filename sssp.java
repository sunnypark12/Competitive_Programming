import java.util.Arrays;
import java.util.Scanner;

public class sssp {
    private static final int inf = 100000000;
    private static final int max = 1001;
    private static int[][] adjMatrix = new int[max][max];
    private static int[] min_distances = new int[max];
    private static boolean[] processed = new boolean[max];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nodes, edges;
        nodes = sc.nextInt();
        edges = sc.nextInt();

        for (int i = 1; i <= nodes; i++) {
            Arrays.fill(adjMatrix[i], inf);
            adjMatrix[i][i] = 0;
        }

        // Read the edges
        while (edges-- > 0) {
            int fromNode = sc.nextInt();
            int toNode = sc.nextInt();
            int weight = sc.nextInt();
            adjMatrix[fromNode][toNode] = Math.min(adjMatrix[fromNode][toNode], weight);
            adjMatrix[toNode][fromNode] = Math.min(adjMatrix[toNode][fromNode], weight);
        }

        System.arraycopy(adjMatrix[1], 1, min_distances, 1, nodes);

        while (true) {
            int curr = -1;
            int min_dist = inf;
            for (int i = 1; i <= nodes; i++) {
                if (!processed[i] && min_distances[i] < min_dist) {
                    curr = i;
                    min_dist = min_distances[i];
                }
            }
            if (curr == -1) break;
            processed[curr] = true;
            for (int i = 1; i <= nodes; i++) {
                if (min_dist + adjMatrix[curr][i] < min_distances[i]) {
                    min_distances[i] = min_dist + adjMatrix[curr][i];
                }
            }
        }

        for (int i = 1; i <= nodes; i++) {
            if (min_distances[i] == inf)
                min_distances[i] = -1;
            System.out.println(min_distances[i]);
        }
    }
}
