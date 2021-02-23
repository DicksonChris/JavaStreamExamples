import java.util.Arrays;

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
        if (str2.equals(str)) {
            return true;
        } else {
            return false;
        }
    }
}