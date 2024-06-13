import java.util.Scanner;

public class dwite07c5p2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            int number = sc.nextInt();
            int totalFactors = countPrimeFactors(number);
            System.out.println(totalFactors);
        }
        sc.close();
    }

    private static int countPrimeFactors(int number) {
        if (isPrime(number)) {
            return 0;
        }

        int count = 0;
        while (number % 2 == 0) {
            count++;
            number /= 2;
        }

        for (int i = 3; i <= number; i += 2) {
            while (number % i == 0) {
                count++;
                number /= i;
            }
        }

        if (count == 0) {
            count = 0;
        }
        return count;
    }

    private static boolean isPrime(int n) {
        if (n <= 1) { return false;}
        if (n <= 3) { return true;}
        if (n % 2 == 0 || n % 3 == 0) { return false;}
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) { return false;}
        }
        return true;
    }
}