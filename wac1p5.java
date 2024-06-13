import java.util.*;
import java.io.*;

public class wac1p5 {
    static final int MAX = 25001;
    static int N, M, K;
    static int[] ord = new int[MAX*2], low = new int[MAX*2], comp = new int[MAX*2];
    static int ccomp = 0, cord = 0;
    static boolean[] instk = new boolean[MAX*2];
    static ArrayList<Integer>[] g = new ArrayList[MAX*2], g2 = new ArrayList[MAX*2];
    static ArrayList<Integer> stk = new ArrayList<>();

    static BitSet[] dp = new BitSet[MAX*2];

    static void edge(int a, int b) {
        if (a < 0) a = N - a;
        if (b < 0) b = N - b;
        g[a].add(b);
    }

    static void rem(int c) {
        ord[c] = low[c] = ++cord;
        instk[c] = true;
        stk.add(c);
        for (int to : g[c]) {
            if (ord[to] == 0) {
                rem(to);
                low[c] = Math.min(low[c], low[to]);
            } else if (instk[to])
                low[c] = Math.min(low[c], ord[to]);
        }

        if (low[c] == ord[c]) {
            int u;
            int cno = ++ccomp;
            do {
                u = stk.remove(stk.size() - 1);
                instk[u] = false;
                comp[u] = cno;
            } while (u != c);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        for (int i = 0; i < MAX*2; i++) {
            g[i] = new ArrayList<>();
            g2[i] = new ArrayList<>();
            dp[i] = new BitSet(MAX*2);
        }

        for (int i = 0; i < K; i++) {
            int x = Integer.parseInt(br.readLine());
            edge(-x, x);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            String t = st.nextToken();
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (t.equals("FRIENDS")) {
                edge(a, b);
                edge(b, a);
                edge(-a, -b);
                edge(-b, -a);
            } else if (t.equals("ENEMIES")) {
                edge(a, -b);
                edge(b, -a);
            } else if (t.equals("PARTNERS")) {
                edge(-a, b);
                edge(-b, a);
                edge(a, -b);
                edge(b, -a);
            } else if (t.equals("GROUP")) {
                edge(-a, b);
                edge(-b, a);
            }
        }

        for (int i = 1; i <= 2 * N; i++)
            if (ord[i] == 0)
                rem(i);

        for (int i = 1; i <= N; i++) {
            if (comp[i] == comp[i + N]) {
                out.println("NO");
                out.close();
                return;
            }
        }

        HashMap<Integer, ArrayList<Integer>> comps = new HashMap<>();
        for (int i = 1; i <= 2 * N; i++) {
            comps.computeIfAbsent(comp[i], k -> new ArrayList<>()).add(i);
            for (int to : g[i]) {
                if (comp[i] != comp[to]) {
                    g2[comp[i]].add(comp[to]);
                }
            }
        }

        for (int i = 1; i <= ccomp; i++) {
            Collections.sort(g2[i]);
            g2[i] = new ArrayList<>(new LinkedHashSet<>(g2[i]));
        }

        for (int i = 1; i <= ccomp; i++) {
            for (int x : comps.get(i))
                dp[i].set(x);
            for (int to : g2[i])
                dp[i].or(dp[to]);
        }

        out.println("YES");
        for (int i = 1; i <= N; i++) {
            boolean ok1 = !dp[comp[i]].get(i + N), ok0 = !dp[comp[i + N]].get(i);

            if (ok1 && ok0) out.print("?");
            else if (ok1) out.print("1");
            else out.print("0");
        }
        out.println();
        out.close();
    }
}