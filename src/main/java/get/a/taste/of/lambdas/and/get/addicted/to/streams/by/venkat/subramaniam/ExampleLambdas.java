package get.a.taste.of.lambdas.and.get.addicted.to.streams.by.venkat.subramaniam;

import java.util.Arrays;
import java.util.List;

public class ExampleLambdas {
    public static void main(String[] args) {
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 1, 2, 3, 4, 5);
//        printListOfIntA(numbers);
//        System.out.println("");
//        printListOfIntB(numbers);
//        twoArgLambdaAMethodReference(numbers);
//        twoArgLambdaBMethodReference(numbers);
        //
    }

    private static void printListOfIntA(List<Integer> numbers) {
        numbers.stream()
               .map(String::valueOf)
               .forEach(System.out::print);
    }

    private static void printListOfIntB(List<Integer> numbers) {
        numbers.stream()
               // obj -> String.valueOf(obj)
               .map(String::valueOf)
               // s -> s.toString()
               .map(String::toString)
               .forEach(System.out::print);
    }

    private static void twoArgLambdaAMethodReference(List<Integer> numbers) {
        System.out.println(
                numbers.stream()
                       // .reduce(0, (total, e) -> Integer.sum(e, total)));
                       .reduce(0, Integer::sum));
    }

    private static void twoArgLambdaBMethodReference(List<Integer> numbers) {
        System.out.println(
                numbers.stream()
                       .map(String::valueOf)
                       // .reduce("", (carry, str) -> carry.concat(str)));
                       .reduce("", String::concat));
    }

    // given the values, double the even numbers and total
    private static void doubleEvensAndSumLambda(List<Integer> numbers) {
        System.out.println(
                numbers.stream()
                       .filter(e -> e % 2 == 0)
                       .mapToInt(e -> e * 2)
                       .sum());
    }
}