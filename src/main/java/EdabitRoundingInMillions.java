import java.util.stream.IntStream;

public class EdabitRoundingInMillions {
    public static void main(String[] args) {
        Object[] arr = new Object[]{
                new Object[]{"Nice", 942208},
                new Object[]{"Abu Dhabi", 1482816},
                new Object[]{"Naples", 2186853},
                new Object[]{"Vatican City", 572}
        };
        System.out.println((int) ((Object[]) (millionsRounding(arr)[0]))[1]);
    }

    public static Object[] millionsRounding(Object[] cities) {
        for (int i = 0; i < cities.length; i++) {
            Object[] city = (Object[]) cities[i];
            city[1] = ((int) city[1] < 5e5) ? 0 : roundToNearestMillion(city[1]);
            cities[i] = new Object[]{city[0], city[1]};
        }
        return cities;
    }

    private static int roundToNearestMillion(Object pop) {
        return (int) (Math.round((int) pop / 1e6) * 1e6);
    }
}
