import java.util.stream.IntStream;
import java.util.List;

class PrimeCalculator {
    int nth(int nth) {
        try {
            return primeNumberList(nth + 1).get(nth - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private List<Integer> primeNumberList(int number) {
        return IntStream.concat(
                IntStream.of(2),
                IntStream.iterate(3, i -> i + 2)
                        .filter(PrimeCalculator::isPrime)
                        .limit(number))
                .boxed()
                .toList();
    }

    private static boolean isPrime(int n) {
        return IntStream.iterate(3, i -> i <= Math.sqrt(n), i -> i + 2)
                .noneMatch(i -> n % i == 0);
    }
}
