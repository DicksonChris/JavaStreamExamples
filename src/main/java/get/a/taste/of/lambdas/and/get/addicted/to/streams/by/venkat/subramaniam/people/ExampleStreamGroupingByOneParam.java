package get.a.taste.of.lambdas.and.get.addicted.to.streams.by.venkat.subramaniam.people;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

public class ExampleStreamGroupingByOneParam {
    public static List<Person> createPeople() {
        return Arrays.asList(
                new Person("Sara", Gender.FEMALE, 20),
                new Person("Sara", Gender.FEMALE, 22),
                new Person("Bob", Gender.MALE, 20),
                new Person("Paula", Gender.FEMALE, 32),
                new Person("Paul", Gender.MALE, 32),
                new Person("Jack", Gender.MALE, 2),
                new Person("Jack", Gender.MALE, 72),
                new Person("Jill", Gender.FEMALE, 12)
        );
    }

    public static void main(String[] args) {
        List<Person> people = createPeople();

        // Given a list of people, create a map where
        // their name is the key and the value is all of the people with that name

        System.out.println(
                people.stream()
                      .collect(groupingBy(Person::getName)));
    }
}


