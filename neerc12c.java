import java.io.*;
import java.util.*;

public class neerc12c {
    static final int max = 100007;
    static final double eps = 1e-7;
    static int n;
    static Pair[] seg = new Pair[max];

    static class Pair {
        int first;
        int second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    static boolean can(double len) {
        double pos = 0;
        for (int i = 1; i <= n; i++) {
            pos = Math.max(pos, seg[i].first);
            if (pos + len > seg[i].second + eps)
                return false;
            pos += len;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            try {
                n = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println(" ");
                return;
            }

            for (int i = 1; i <= n; i++) {
                String[] parts = br.readLine().split(" ");
                seg[i] = new Pair(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            }
            Arrays.sort(seg, 1, n + 1, Comparator.comparingInt(a -> a.first));

            double l = 0, r = 1e6 + 5, ans = 0;
            for (int c = 0; c < 100; c++) {
                double mid = (l + r) / 2;
                if (can(mid)) {
                    l = mid;
                    ans = mid;
                } else {
                    r = mid;
                }
            }

            double min = Double.MAX_VALUE;
            int up = 0, down = 1;
            for (int i = 1; i <= n; i++) {
                int num = (int) Math.floor(ans * i);
                if (Math.abs((double) num / i - ans) < min) {
                    min = Math.abs((double) num / i - ans);
                    up = num;
                    down = i;
                }
                num = (int) Math.ceil(ans * i);
                if (Math.abs((double) num / i - ans) < min) {
                    min = Math.abs((double) num / i - ans);
                    up = num;
                    down = i;
                }
            }
            int g = gcd(up, down);
            up /= g;
            down /= g;
            System.out.printf("%d/%d\n", up, down);
        }
    }
}