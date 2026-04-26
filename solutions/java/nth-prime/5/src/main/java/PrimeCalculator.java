import java.util.stream.IntStream;
import java.util.List;
import java.util.stream.Collectors;

class PrimeCalculator {

    int nth(int nth) {
        try {
            return primeNumberList(nth + 1).get(nth - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e);
        }

    }

    private List<Integer> primeNumberList(int number) {
        return IntStream.iterate(2, i -> i + 1)
                .filter(PrimeCalculator::isPrime)
                .limit(number)
                .boxed()
                .collect(Collectors.toList());
    }

    private static boolean isPrime(int n) {
        for (int i = 2; i < n; i++)
            if (n % i == 0)
                return false;

        return true;
    }

}
