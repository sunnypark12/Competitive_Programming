import java.util.Scanner;

public class syssolve {

    private static final double EPS = 1e-5;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        double[][] A = new double[m][n];
        double[] b = new double[m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = sc.nextInt();
            }
            b[i] = sc.nextInt();
        }

        double[] sln = solve(A, b, m, n);

        if (sln == null) {
            System.out.println("NO UNIQUE SOLUTION");
        } else {
            for (double v : sln) {
                System.out.printf("%.5f%n", v);
            }
        }
        sc.close();
    }

    private static double[] solve(double[][] A, double[] b, int m, int n) {
        double[] x = new double[n];
        int rank = 0;

        for (int p = 0; p < Math.min(m, n); p++) {
            int max = p;
            for (int i = p + 1; i < m; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            double[] tempA = A[p];
            A[p] = A[max];
            A[max] = tempA;
            double t = b[p];
            b[p] = b[max];
            b[max] = t;
        
            if (Math.abs(A[p][p]) <= EPS) {
                boolean isZero = true;
                for (int j = p; j < n && isZero; j++) {
                    if (Math.abs(A[p][j]) > EPS) {
                        isZero = false;
                    }
                }

                if (!isZero && Math.abs(b[p]) > EPS) return null;
                else if (isZero && Math.abs(b[p]) <= EPS) { continue;}
            
            } else {
                rank++;
            }

            for (int i = p + 1; i < m; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        for (int i = Math.min(m, n) - 1; i >= 0; i--) {
            if (Math.abs(A[i][i]) <= EPS) {
                if (Math.abs(b[i]) > EPS) return null;
                x[i] = 0;
                continue;
            }
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }

        for (int i = 0; i < m; i++) {
            double sum = 0.0;
            for (int j = 0; j < n; j++) {
                sum += A[i][j] * x[j];
            }

            if (Math.abs(sum - b[i]) > EPS) return null;
        }
        
        if (rank < n) { 
            return null;
        }
        return x;
    }
}