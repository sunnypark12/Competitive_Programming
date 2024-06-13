import java.util.*;

public class tree1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] adjMatrix = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adjMatrix[i][j] = sc.nextInt();
            }
        }
        String result = isTree(adjMatrix) ? "Yes" : "No";
        System.out.println(result);
        sc.close();
    }

    private static boolean isTree(int[][] matrix) {
        int numVertices = 4;
        int numEdges = 0;
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (matrix[i][j] == 1) {
                    numEdges++;
                }
            }
        }

        if (numEdges != numVertices - 1) {
            return false;
        }

        dfs(0, matrix, visited);

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                return false;
            }
        }

        return true;
    }

    private static void dfs(int vertex, int[][] matrix, boolean[] visited) {
        visited[vertex] = true;
        for (int i = 0; i < 4; i++) {
            if (matrix[vertex][i] == 1 && !visited[i]) {
                dfs(i, matrix, visited);
            }
        }
    }
}