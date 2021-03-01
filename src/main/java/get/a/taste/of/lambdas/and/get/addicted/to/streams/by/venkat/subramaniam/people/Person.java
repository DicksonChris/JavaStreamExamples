package get.a.taste.of.lambdas.and.get.addicted.to.streams.by.venkat.subramaniam.people;

public class Person {

    private final String name;
    private final Gender gender;
    private final int age;

    public Person(String name, Gender gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    @Override
    public String toString() {
        return "{" + name + ", " + gender + ", " + age + "}";
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

}