import java.util.Scanner;

public class dmopc17c2p1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long totalCost = 0;

        for (int i = 0; i < n; i++) {
            int cost = sc.nextInt();
            int value = sc.nextInt();

            if (value > 0) {
                totalCost += cost;
            }
        }

        System.out.println(totalCost); 
        sc.close();
    }
}