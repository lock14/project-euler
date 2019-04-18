import java.util.BitSet;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * ProjectEuler
 */
public class ProjectEuler {
    public static void main(String[] args) {
        // time(ProjectEuler::summationOfPrimes);
        time(() -> System.out.println(primes().parallel().takeWhile(p -> p < 2000000L).sum()));
        time(() -> System.out.println(sieve(2000000).sum()));
    }

    /**
     * Problem 1
     */
    public static void multiplesOfThreeAndFive() {
        System.out.println(IntStream.range(3, 1000)
                                    .filter(n -> (n % 3 == 0) || (n % 5 == 0))
                                    .sum());
    }

    /**
     * Problem 2
     */
    public static void evenFibonacciNumbers() {
        int nMinusTwo = 0;
        int nMinusOne = 1;
        int n = 1;
        int sum = 0;
        while (n < 4000000) {
            nMinusTwo = nMinusOne;
            nMinusOne = n;
            n = nMinusOne + nMinusTwo;
            if (n % 2 == 0) {
                sum += n;
            }
        }
        System.out.println(sum);
    }

    /**
     * Problem 3
     */
    public static void largestPrimeFactor() {
        System.out.println(primes().takeWhile(p -> p < (long)(Math.sqrt(600851475143L) + 1))
                                   .filter(p -> 600851475143L % p == 0)
                                   .max()
                                   .getAsLong());
    }

    /**
     * Problem 4
     */
    public static void largestPalindromeProduct() {
        int r = 0;
        int maxPalindrome = 0;
        for (int p = 990; p > 99; p -= 11) {
            if (maxPalindrome < p * 999) {
                for (int q = 999; q > 99; q--) {
                    r = p * q;
                    if (r > maxPalindrome && isPalindrome(r)) {
                        maxPalindrome = r;
                        break;
                    }
                }
            }
        }
        System.out.println(maxPalindrome);
    }

    /**
     * Problem 5
     */
    public static void smallestMultiple() {
        System.out.println(primes().takeWhile(p -> p < 20L)
                                   .reduce(1L, (p1, p2) -> p1 * p2) * 2 * 2 * 2 * 3);
    }

    /**
     * Problem 6
     */
    public static void sumSquareDifference() {
        int squareOfSum = (100 * 100 * 101 * 101) / 4;
        int sumOfSquares = IntStream.rangeClosed(1, 100)
                                    .map(n -> n * n)
                                    .sum();
        System.out.println(squareOfSum - sumOfSquares);
    }

    /**
     * Problem 7
     */
    public static void tenThousandAndFirstPrime() {
        System.out.println(primes().skip(10000).findFirst().getAsLong());
    }

    /**
     * Problem 10
     */
    public static void summationOfPrimes() {
        System.out.println(primes().parallel()
                                   .takeWhile(p -> p < 2000000L)
                                   .sum());
    }
    
    public static IntStream sieve(int limit) {
        BitSet sieve = new BitSet(limit+1);
        return IntStream.rangeClosed(2, limit)
                  .filter(x -> !sieve.get(x))
                  .peek(x -> {
                      if (x*x < limit)
                        for(int i = x; i <= limit; i+=x)
                           sieve.set(i);
                   });
    }

    public static LongStream primes() {
        return LongStream.iterate(2L, n -> n + 1L)
                         .filter(ProjectEuler::isPrime);
    }

    public static boolean isPrime(long n) {
        // Even numbers
        if ((n & 1L) == 0L) {
            return n == 2L;
        }

        // Odd numbers
        long limit = (long) Math.sqrt(n);
        for (long m = 3L; m <= limit; m += 2L) {
            if (n % m == 0L) {
                return false;
            }
        }
        return true;
    }

    public static long[] digits(long n) {
        return String.valueOf(n).chars()
                                .map(c -> c - '0')
                                .mapToLong(Long::valueOf)
                                .toArray();
    }

    public static boolean isPalindrome(long n) {
        long x = n;
        long m = 0;
        while (x > 0) {
            m = (m * 10) + x % 10;
            x /= 10;
        }
        return n == m;
    }

    public static void time(Procedure p) {
        // run the algorithm and time it
        long start = System.currentTimeMillis();
        p.invoke();
        double elapsed = (System.currentTimeMillis() - start) / 1000.0;
        System.out.println(String.format("procedure took %.2f seconds", elapsed));
    }

    @FunctionalInterface
    public interface Procedure {
        void invoke();
    }
}
