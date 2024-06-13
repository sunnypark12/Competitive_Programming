import java.util.*;

public class demographic {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] ages = new int[N];

        for (int i = 0; i < N; i++) {
            ages[i] = sc.nextInt();
        }
        Arrays.sort(ages);
        int[] uAge = Arrays.stream(ages).distinct().toArray();
        int[] pSum = new int[uAge.length];

        Map<Integer, Integer> ageToIndexMap = new HashMap<>();
        for (int i = 0, j = 0; i < N; i++) {
            if (i == 0 || ages[i] != ages[i - 1]) {
                ageToIndexMap.put(ages[i], j++);
            }
            pSum[ageToIndexMap.get(ages[i])]++;
        }

        for (int i = 1; i < uAge.length; i++) {
            pSum[i] += pSum[i - 1];
        }

        int Q = sc.nextInt();
        for (int q = 0; q < Q; q++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            int a_idx = Arrays.binarySearch(uAge, a);
            if (a_idx < 0) {
                a_idx = -(a_idx + 1);
            }
            int b_idx = Arrays.binarySearch(uAge, b);
            if (b_idx < 0) {
                b_idx = -(b_idx + 2);
            }

            int cA = (a_idx > 0) ? pSum[a_idx - 1] : 0;
            int cB = (b_idx >= 0) ? pSum[b_idx] : 0;
            System.out.println(cB - cA);
        }
        sc.close();
    }
}