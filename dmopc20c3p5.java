import java.util.Scanner;
import java.util.Stack;

public class dmopc20c3p5 {
    public static long Euler(long n) {
        long a = n;
        for (long p = 2; p <= Math.sqrt(n); p++) {
            if (n % p == 0) {
                while (n % p == 0) {
                    n /= p;
                }
                a -= a / p;
            }
        }
        if (n > 1) {
            a -= a / n;
        }
        return a;
    }
    public static long mod(long a, long b, long m) {
        a *= b;
        if (a >= m) return (a % m) + m;
        return a;
    }
    public static long exp(long n, long p, long MOD) {
        if (p == 0) return 1;
        if ((p & 1) == 1) return mod(exp(n, p - 1, MOD), n, MOD);
        long a = exp(n, p / 2, MOD);
        return mod(a, a, MOD);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        long M = input.nextLong();
        long x, y = M;
        Stack<Long> s = new Stack<>();
        Stack<Long> t = new Stack<>();

        for (int i = 0; i < N; i++) {
            x = input.nextLong();
            s.push(x);
        }
        t.push(M);
        for (int i = 2; i < N; i++) {
            y = Euler(y);
            t.push(y);
        }
        long output = s.pop();
        while (!s.isEmpty()) {
            output = exp(s.pop(), output, t.pop());
        }
        System.out.println(output % M);
    }
}