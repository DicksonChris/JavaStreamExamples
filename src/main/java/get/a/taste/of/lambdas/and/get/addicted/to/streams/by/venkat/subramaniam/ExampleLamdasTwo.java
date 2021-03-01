package get.a.taste.of.lambdas.and.get.addicted.to.streams.by.venkat.subramaniam;

import com.google.common.base.Stopwatch;

import java.util.Arrays;
import java.util.List;

public class ExampleLamdasTwo {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Stopwatch timer = Stopwatch.createStarted();
        System.out.println(
                // numbers.stream()
                numbers.parallelStream()
                       .filter(e -> e % 2 == 0)
                       .mapToInt(ExampleLamdasTwo::compute)
                       .sum());
        System.out.println("Method took: " + timer.stop());
    }

    public static int compute(int number) {
        // assume this is a very time intensive function
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            //
        }
        return number * 2;
    }
}
