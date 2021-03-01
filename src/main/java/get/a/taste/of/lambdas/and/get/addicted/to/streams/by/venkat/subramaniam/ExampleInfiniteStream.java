package get.a.taste.of.lambdas.and.get.addicted.to.streams.by.venkat.subramaniam;

import java.util.stream.Stream;

public class ExampleInfiniteStream {

    public static void main(String[] args) {
        // Start with 100, create a series
        // 100, 101, 102, 103, ...
//        System.out.println(Stream.iterate(100, e -> e + 1));

        // Given a number k, and a count n, find the total of double of n
        // even numbers starting with k, where the sqrt of each number is > 20
        int k = 1;
        int n = 3;

//        System.out.println(computeImperative(k, n));

        System.out.println(computeDeclarative(k, n));
    }

    private static int computeDeclarative(int k, int n) {
        return Stream.iterate(k, e -> e + 1)// unbounded, lazy
                     .filter(e -> e % 2 == 0) // unbounded, lazy
                     .filter(e -> Math.sqrt(e) > 20) // unbounded, lazy
                     .mapToInt(e -> e * 2) // unbounded, lazy
                     .limit(n) // sized, lazy
                     .sum();
    }

    public static int computeImperative(int k, int n) {
        int result = 0;

        int index = k;
        int count = 0;
        while(count < n) {
            if (index % 2 == 0 && Math.sqrt(index) > 20) {
                result += index * 2;
                count++;
            }
            index++;
        }
        return result;
    }
}
