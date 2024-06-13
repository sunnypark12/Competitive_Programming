import java.util.Scanner;

public class mccc1j2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int joyPerCactus = sc.nextInt(); 
        int joyPerMatroid = sc.nextInt(); 
        int maxItems = sc.nextInt(); 
        int maxJoy = Math.max(joyPerCactus, joyPerMatroid) * maxItems;

        System.out.println(maxJoy);
        sc.close();
    }
}