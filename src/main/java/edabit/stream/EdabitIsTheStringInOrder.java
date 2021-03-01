package edabit.stream;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EdabitIsTheStringInOrder {
    public static void main(String[] args) {
        System.out.println(isInOrder("edabit"));
    }

    public static boolean isInOrder(String str){
        char[] charArray = (new String(str)).toCharArray();
        Arrays.sort(charArray);
        System.out.println(charArray);
        String str2 = String.valueOf(charArray);
        System.out.println(str + " " + str2);
        return str2.equals(str);
    }
}