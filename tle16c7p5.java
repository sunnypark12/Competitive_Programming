import java.io.*;
import java.util.*;

public class tle16c7p5 {
    static class Edge {
        int to;
        long initialDurability;

        Edge(int to, long initialDurability) {
            this.to = to;
            this.initialDurability = initialDurability;
        }
    }

    static class State implements Comparable<State> {
        int node;
        long distance;

        State(int node, long distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(State other) {
            return Long.compare(this.distance, other.distance);
        }
    }

    static class Query implements Comparable<Query> {
        int index;
        long f;

        Query(int index, long f) {
            this.index = index;
            this.f = f;
        }

        @Override
        public int compareTo(Query other) {
            return Long.compare(this.f, other.f);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            long d = Long.parseLong(st.nextToken());
            graph.get(u).add(new Edge(v, d));
        }

        int Q = Integer.parseInt(br.readLine());
        Query[] queries = new Query[Q];
        for (int i = 0; i < Q; i++) {
            long f = Long.parseLong(br.readLine());
            queries[i] = new Query(i, f);
        }

        Arrays.sort(queries); 

        String[] answers = new String[Q];
        Arrays.fill(answers, "Fail");

        for (Query query : queries) {
            PriorityQueue<State> pq = new PriorityQueue<>();
            pq.add(new State(1, 0));
            long[] distances = new long[N + 1];
            Arrays.fill(distances, Long.MAX_VALUE);
            distances[1] = 0;
            int[] prev = new int[N + 1];
            Arrays.fill(prev, -1);

            while (!pq.isEmpty()) {
                State state = pq.poll();
                int current = state.node;
                long curDist = state.distance;

                if (curDist > distances[current]) continue;

                for (Edge edge : graph.get(current)) {
                    if (edge.initialDurability > 0) {
                        int next = edge.to;
                        long nextDist = curDist + 1;
                        if (nextDist < distances[next]) {
                            distances[next] = nextDist;
                            prev[next] = current;
                            pq.add(new State(next, nextDist));
                        }
                    }
                }
            }

            if (distances[N] < Long.MAX_VALUE) {
                List<Integer> path = new ArrayList<>();
                for (int at = N; at != -1; at = prev[at]) {
                    path.add(at);
                }
                Collections.reverse(path);

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < path.size(); i++) {
                    if (i > 0) sb.append(" ");
                    sb.append(path.get(i));
                }

                answers[query.index] = sb.toString();
                for (int i = 0; i < path.size() - 1; i++) {
                    int u = path.get(i);
                    int v = path.get(i + 1);
                    for (Edge edge : graph.get(u)) {
                        if (edge.to == v) {
                            edge.initialDurability--;
                            break;
                        }
                    }
                }
            }
        }

        for (String answer : answers) {
            out.println(answer);
        }

        out.flush();
        out.close();
    }
}
