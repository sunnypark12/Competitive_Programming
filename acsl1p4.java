import java.util.*;

public class acsl1p4 {
    static List<List<Integer>> adj = new ArrayList<>();
    static boolean[] visited;
    static int[] parent;
    static boolean[] rec;
    static Set<Integer> players = new HashSet<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();

        for (int i = 0; i <= N; i++) {
            adj.add(new ArrayList<>());
        }
        visited = new boolean[N + 1];
        rec = new boolean[N + 1];
        parent = new int[N + 1];

        for (int i = 0; i < K; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int s_a = sc.nextInt();
            int s_b = sc.nextInt();
            if (s_a > s_b) {
                adj.get(a).add(b);
            } else if (s_b > s_a) {
                adj.get(b).add(a);
            }
        }

        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }

        System.out.println(players.size());
    }

    private static void dfs(int start) {
        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            int node = stack.peek();
            if (!visited[node]) {
                visited[node] = true;
                rec[node] = true;
            }
            boolean isLeaf = true;
            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    stack.push(neighbor);
                    parent[neighbor] = node;
                    isLeaf = false;
                    break;
                } else if (rec[neighbor]) {
                    add_participants(neighbor, node);
                }
            }

            if (isLeaf) {
                rec[node] = false;
                stack.pop();
            }
        }
    }

    private static void add_participants(int start, int curr) {
        Set<Integer> mem = new HashSet<>();
        mem.add(start);
        while (curr != start && curr != 0) {
            mem.add(curr);
            curr = parent[curr];
        }
        mem.add(curr);
        players.addAll(mem);
    }
}