import java.util.*;

public class rgpc17p4 {
    static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        int M = input.nextInt();

        List<List<Edge>> G = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            G.add(new ArrayList<>());
        }

        int[] degree = new int[N + 1];
        for (int i = 0; i < M; i++) {
            int a = input.nextInt();
            int b = input.nextInt();
            int d = input.nextInt();
            G.get(a).add(new Edge(b, d));
            degree[b]++;
        }

        int[] dist = new int[N + 1];
        int[] num = new int[N + 1];
        Arrays.fill(dist, Integer.MIN_VALUE);
        dist[1] = 0;
        num[1] = 1;

        if (hasCycle(N, degree, G)) {
            System.out.println("-1");
        } else {
            topoSort(G, dist, num, N);
            if (dist[N] == Integer.MIN_VALUE) {
                System.out.println("-1");
            } else {
                System.out.println(dist[N] + " " + num[N]);
            }
        }
    }

    private static boolean hasCycle(int N, int[] d, List<List<Edge>> graph) {
        int visited = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (d[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            visited++;
            for (Edge e : graph.get(node)) {
                d[e.to]--;
                if (d[e.to] == 0) {
                    queue.add(e.to);
                }
            }
        }

        return visited != N;
    }

    private static void topoSort(List<List<Edge>> graph, int[] dist, int[] num, int N) {
        LinkedList<Integer> topo = new LinkedList<>();
        int[] degree = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            for (Edge e : graph.get(i)) {
                degree[e.to]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (degree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            topo.add(node);
            for (Edge e : graph.get(node)) {
                if (--degree[e.to] == 0) {
                    queue.add(e.to);
                }
            }
        }

        for (int n : topo) {
            for (Edge e : graph.get(n)) {
                if (dist[n] != Integer.MIN_VALUE) {
                    if (dist[e.to] < dist[n] + e.weight) {
                        dist[e.to] = dist[n] + e.weight;
                        num[e.to] = num[n] + 1;
                    } else if (dist[e.to] == dist[n] + e.weight) {
                        num[e.to] = Math.max(num[e.to], num[n] + 1);
                    }
                }
            }
        }
    }
}