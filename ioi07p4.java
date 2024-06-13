import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ioi07p4 {
    private static final int MAX_LENGTH = 100010;
    private static char[] sequence = new char[MAX_LENGTH];
    private static int sequenceLength;
    private static Map<StatePair, Integer> dp = new HashMap<>();
    private static Map<StatePair, Integer> updatedDP;

    private static class SimplePair {
        char first;
        char second;

        SimplePair(char first, char second) {
            this.first = first;
            this.second = second;
        }

        public int compareTo(SimplePair other) {
            if (this.first != other.first) {
                return this.first - other.first;
            }
            return this.second - other.second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SimplePair)) return false;
            SimplePair pair = (SimplePair) o;
            return first == pair.first && second == pair.second;
        }

        @Override
        public int hashCode() {
            return 31 * first + second;
        }
    }

    private static class StatePair {
        SimplePair subPair1;
        SimplePair subPair2;

        StatePair(SimplePair subPair1, SimplePair subPair2) {
            if (subPair1.compareTo(subPair2) < 0) {
                this.subPair1 = subPair1;
                this.subPair2 = subPair2;
            } else {
                this.subPair1 = subPair2;
                this.subPair2 = subPair1;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StatePair)) return false;
            StatePair pair = (StatePair) o;
            return subPair1.equals(pair.subPair1) && subPair2.equals(pair.subPair2);
        }

        @Override
        public int hashCode() {
            return 31 * subPair1.hashCode() + subPair2.hashCode();
        }
    }

    private static int pointValue(SimplePair pair, char nextChar) {
        char a = pair.first;
        char b = pair.second;
        if (a == 'N') {
            if (b == 'N' || b == nextChar) {
                return 1;
            }
            return 2;
        }
        if (a == b && a == nextChar) {
            return 1;
        }
        if (a != b && a != nextChar && b != nextChar) {
            return 3;
        }
        return 2;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        sequenceLength = scanner.nextInt();
        sequence = scanner.next().toCharArray();
        dp.put(new StatePair(new SimplePair('N', 'N'), new SimplePair('N', 'N')), 0);

        for (int pos = 0; pos < sequenceLength; pos++) {
            updatedDP = new HashMap<>();
            for (Map.Entry<StatePair, Integer> entry : dp.entrySet()) {
                StatePair state = entry.getKey();
                int bestScore = entry.getValue();

                int score1 = pointValue(state.subPair1, sequence[pos]) + bestScore;
                StatePair newPair1 = new StatePair(
                    new SimplePair(state.subPair1.second, sequence[pos]),
                    new SimplePair(state.subPair2.first, state.subPair2.second)
                );

                int score2 = pointValue(state.subPair2, sequence[pos]) + bestScore;
                StatePair newPair2 = new StatePair(
                    new SimplePair(state.subPair1.first, state.subPair1.second),
                    new SimplePair(state.subPair2.second, sequence[pos])
                );

                updatedDP.merge(newPair1, score1, Math::max);
                updatedDP.merge(newPair2, score2, Math::max);
            }
            dp = updatedDP;
        }

        int bestResult = 0;
        for (int score : dp.values()) {
            bestResult = Math.max(bestResult, score);
        }
        System.out.println(bestResult);
    }
}