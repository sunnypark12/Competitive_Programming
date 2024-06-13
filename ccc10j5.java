import java.io.*;
import java.util.*;

public class ccc10j5 {
    private static final int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2};
    private static final int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};
    private static final int SIZE = 8;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] start = br.readLine().split(" ");
        String[] end = br.readLine().split(" ");

        int startX = Integer.parseInt(start[0]) - 1;
        int startY = Integer.parseInt(start[1]) - 1;
        int endX = Integer.parseInt(end[0]) - 1;
        int endY = Integer.parseInt(end[1]) - 1;

        System.out.println(min_move(startX, startY, endX, endY));
    }

    private static int min_move(int startX, int startY, int endX, int endY) {
        boolean[][] visited = new boolean[SIZE][SIZE];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY, 0});

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int x = cell[0];
            int y = cell[1];
            int dist = cell[2];
            if (x == endX && y == endY) {
                return dist;
            }

            for (int i = 0; i < 8; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx >= 0 && ny >= 0 && nx < SIZE && ny < SIZE && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny, dist + 1});
                }
            }
        }
        return -1;
    }
}