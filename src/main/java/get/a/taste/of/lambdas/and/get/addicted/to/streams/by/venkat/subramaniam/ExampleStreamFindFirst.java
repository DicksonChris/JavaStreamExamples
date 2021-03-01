package get.a.taste.of.lambdas.and.get.addicted.to.streams.by.venkat.subramaniam;

import java.util.Arrays;
import java.util.List;

public class ExampleStreamFindFirst {
    // 4 & 5 intentionally swapped
    private static List<Integer> numbers = Arrays.asList(1, 2, 3, 5, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

    public static void main(String[] args) {
        // Given an ordered list find the double of the first even number greater than 3

//        imperativeStyle();

        declarativeStyle();

//        declarativeStyleBadDebugging();

    }

    private static void declarativeStyle() {
        System.out.println(
                numbers.stream()
                       .filter(e -> e > 3)
                       .filter(e -> e % 2 == 0)
                       .map(e -> e * 2)
                       .findFirst());
    }

    private static void imperativeStyle() {
        int result = 0;
        for (int e : numbers) {
            if (e > 3 && e % 2 == 0) {
                result = e * 2;
                break;
            }
        }
        System.out.println(result);
    }

    // Don't do, lazy evaluation should not have side effects (such as the use of println).
    private static boolean isGT3(int number) {
        System.out.println("isGT3 " + number);
        return number > 3;
    }
    private static boolean isEven(int number) {
        System.out.println("isEven " + number);
        return number % 2 == 0;
    }
    private static int doubleIt(int number) {
        System.out.println("doubleIt " + number);
        return number * 2;
    }
    private static void declarativeStyleBadDebugging() {
        System.out.println(
                numbers.stream()
                       .filter(ExampleStreamFindFirst::isGT3)
                       .filter(ExampleStreamFindFirst::isEven)
                       .map(ExampleStreamFindFirst::doubleIt)
                       .findFirst());
    }
}


