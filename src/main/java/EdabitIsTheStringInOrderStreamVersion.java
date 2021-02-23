import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.util.Arrays;

public class EdabitIsTheStringInOrderStreamVersion {
    public static void main(String[] args) {
        System.out.println(isInOrder("edabit"));
    }

    public static boolean isInOrder(String str){
        // Submitted by user Jonas Tad on edabit
        return str.equals(Stream.of(str.split(""))
                .sorted()
                .collect(Collectors.joining("")));
    }
}