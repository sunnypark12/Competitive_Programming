import java.util.*;

public class pacnw16f {
    static List<List<Integer>> G, GR;
    static boolean[] visited;
    static int[] component;
    static int[] scc;
    static Stack<Integer> stack;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int r = input.nextInt();
        int l = input.nextInt();

        int[][] lamps = new int[l][2];
        for (int i = 0; i < l; i++) {
            lamps[i][0] = input.nextInt();
            lamps[i][1] = input.nextInt();
        }

        G = new ArrayList<>(2 * l);
        GR = new ArrayList<>(2 * l);
        for (int i = 0; i < 2 * l; i++) {
            G.add(new ArrayList<>());
            GR.add(new ArrayList<>());
        }

        Map<Integer, List<Integer>> rMap = new HashMap<>();
        Map<Integer, List<Integer>> cMap = new HashMap<>();

        for (int i = 0; i < l; i++) {
            rMap.computeIfAbsent(lamps[i][0], k -> new ArrayList<>()).add(i);
            cMap.computeIfAbsent(lamps[i][1], k -> new ArrayList<>()).add(i);
        }

        for (List<Integer> row : rMap.values()) {
            if (row.size() > 1) {
                for (int i = 0; i < row.size(); i++) {
                    for (int j = i + 1; j < row.size(); j++) {
                        int l1 = row.get(i);
                        int l2 = row.get(j);
                        if (Math.abs(lamps[l1][1] - lamps[l2][1]) <= 2 * r) {
                            implication(l1, false, l2, false); 
                        }
                    }
                }
            }
        }

        for (List<Integer> col : cMap.values()) {
            if (col.size() > 1) {
                for (int i = 0; i < col.size(); i++) {
                    for (int j = i + 1; j < col.size(); j++) {
                        int l1 = col.get(i);
                        int l2 = col.get(j);
                        if (Math.abs(lamps[l1][0] - lamps[l2][0]) <= 2 * r) {
                            implication(l1, true, l2, true);
                        }
                    }
                }
            }
        }

        visited = new boolean[2 * l];
        component = new int[2 * l];
        scc = new int[2 * l];
        stack = new Stack<>();

        for (int i = 0; i < 2 * l; i++) {
            if (!visited[i]) {
                dfs1(i);
            }
        }

        Arrays.fill(visited, false);
        int id = 0;

        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (!visited[u]) {
                dfs2(u, id++);
            }
        }

        boolean satisfiable = true;
        for (int i = 0; i < l; i++) {
            if (scc[2 * i] == scc[2 * i + 1]) {
                satisfiable = false;
                break;
            }
        }

        System.out.println(satisfiable ? "YES" : "NO");
    }

    static void implication(int x, boolean xVal, int y, boolean yVal) {
        int notX = 2 * x + (xVal ? 0 : 1);
        int notY = 2 * y + (yVal ? 0 : 1);
        int X = 2 * x + (xVal ? 1 : 0);
        int Y = 2 * y + (yVal ? 1 : 0);
        G.get(notX).add(Y);
        G.get(notY).add(X);
        GR.get(Y).add(notX);
        GR.get(X).add(notY);
    }

    static void dfs1(int u) {
        visited[u] = true;
        for (int v : G.get(u)) {
            if (!visited[v]) {
                dfs1(v);
            }
        }
        stack.push(u);
    }

    static void dfs2(int u, int id) {
        visited[u] = true;
        scc[u] = id;
        component[u] = id;
        for (int v : GR.get(u)) {
            if (!visited[v]) {
                dfs2(v, id);
            }
        }
    }
}