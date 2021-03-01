# Get a Taste of Lambdas and Get Addicted to Streams by Venkat Subramaniam
https://www.youtube.com/watch?v=1OpAgZvYXLQ


# Lambdas

Lambdas can be used in place of any interface where a single abstract method. Those are functional interfaces, runnable, callable, and your own interfaces that take a single method.

## Under the hood
When you create a lambda expression, the call to the lambda expression simply becomes an invokedynamic (in byte code). 

The lambda expression itself will become one of three things:

* It might become a static method (depending on the context)
* It could become an instance method.
* It could become a routing of the invoke dynamic to an existing method in another class (depending on the context)

## External Iterators
Both normal for loops are examples of external iterators:
```java
List<Integer> numbers = Arrays.asList(1, 2, 3);
for (int i = 0; i < numbers.size(); i++) {
	System.out.println(numbers.get(i))
}
// or
for (int e : numbers) {
	System.out.println(e);
}
```

## Internal Iterators
Rather than passing the collection to ```for```, you are invoking the ```.forEach()``` on the collection. 

An advantage you get from this is that when you call a function on an object you can benefit from polymorphism (you can vary the implementation of the method based on the type of the object). We don't need to worry about the implementation. It could be sequential, parallel, lazy, etc. 
```java
numbers.forEach(new Consumer<Integer>() {
	public void accept(Integer value) {
		System.out.println(value);
	}
});
```
So what is ```Consumer```? It is from ```java.util.function.Consumer``` and is a available to accept a single input and return no result. It is expected to operate via side-effects.

This is a lot of noise so you can replace the *anonymous inner class* Consumer and replace it with a lambda:
```java
numbers.forEach((Integer value) -> System.out.println(value));
```
There is less code to write and we are not managing the iteration ourselves. 

Since `forEach` is a method operating on `numbers` which is a collection of `Integer`s  the type of the parameter for the anonymous function can be inferred.
```java
numbers.forEach(value -> System.out.println(value));
```
Additionally the parenthesis on parameters are optional but only for one parameter lambdas. If the lambda has no parameter, or multiple parameters, then you need a paranthesis.

---

## Method References
```java
List<Integer> numbers = Arrays.asList(1, 2, 3);

numbers.forEach(value -> System.out.println(value));
```
Since all this lambda does is pass the value to a function (without changing the value). You can replace the lambda with the *method reference syntax*.
```java
numbers.forEach(System.out::println);
```
All this means is "receive the value and pass it to the method reference as an argument without changing the value."

Now you can see that the code says "for each element in the collection 'numbers' print it."

### Keep it simple

Lambdas can make a mess of your code. The speaker Venkat Subramaniam suggest "Lambda expressions should be glue code. Two lines may be too many."

1. The code becomes hard to read.
2. It's noisy.
3. Leads to duplication.
4. Hard to test.

Example of bad/suspicious lambda. This is an **antipattern**.

```java
numbers.forEach(e -> {
	..;
	..;
	..;
	return... // return may be needed
});
```

### Static Method Reference
```System.out::println``` is a method reference to an instance method...
```String::valueOf``` is a reference to a static method.
In both cases your parameter has gone in as an argument.

```java
numbers.stream()
       .map(String::valueOf)
	   .forEach(System.out::println);
```


### Parameter as a target
In the previous method references the parameter passed in would go to the parenthesis. Consider toString in this lambda:
```java
numbers.stream()
	   .map(e -> e.toString(e.toString())
	   .forEach(System.out::println);
```
In this example the parameter is the target to the function `.toString`. In the previous cases the parameter became an argument to the function called.

So you have to ask what is the parameter really? In this case `e` is an `Integer` so it would be:
```java
numbers.stream()
	   .map(Integer::toString) 
	   ...
```
> error: reference to toString is ambiguous both method toString(int) in Integer and method toString() in Integer match

Unfortunately this example doesn't work. The compiler can't infer what to do because multiple `toString` methods match the syntax.

To use a different, less useful example:
```java
numbers.stream()
	   // maps the ints to Strings
	   .map(String::valueOf) // obj -> String.valueOf(obj)
	   // calls .toString on the Strings
	   .map(String::toString) // s -> s.toString()
	   .forEach(System.out::println);
```
Here `valueOf` is a static method whereas `toString` is an instance method.

---

## Two argument lambdas
In this case we are summing the values of the list and printing out that value. 
```java
System.out.println(
    numbers.stream()
	       .reduce(0, (total, e) -> Integer.sum(total, e)));
```
The order of the parameters matters. If you see the values in an order then you pass them in that order. So ```Integer.sum(e, total)``` would be swapped, `total` would be `e` and `e` would be `total` (though for `sum` it wouldn't matter, other functions might not be as forgiving).

### Two argument lambda method reference

**Example where paremeters are both arguments**

Since the arguments must be passed in the same order this example:
```java
System.out.println(
    numbers.stream()
	       .reduce(0, (total, e) -> Integer.sum(total, e)));
```
Can be rewritten with a method reference instead:
```java
System.out.println(
    numbers.stream()
	       .reduce(0, Integer::sum));
```
Notice that both of the parameters became arguments for the static method sum.

**Example where parameters are an instance of an object and a parameter**
In this example `carry` is just an instance of `String` and `str` is the parameter. Since the parameters both parameters are used in order we can reduce this to a method reference.
```java
System.out.println(
	numbers.stream()
		   .map(String::valueOf)
		   // .reduce("", (carry, str) -> carry.concat(str)));
		   .reduce("", String::concat));
```

## Method reference limitations
1. You cannot use a method reference if you need to manipulate the data
2. You cannot use them if there is a conflict between an instance method and a static method (e.g. Integer.toString is an instance method and a static method)

---

## Function Composition
Think of a stream as a fancy iterator. Iterators give us the next element and the next element and the next element, etc.

```java
Sytem.out.println(
	 numbers.stream()
	        .filter(e -> e % 2 == 0)
	        .map(e -> e * 2)
		    .reduce(0, Integer::sum));
```
Using a for loop to do this simple operation requires a greater mental overhead:
```java
int sum = 0;
for (Integer e : numbers) {
	if (e % 2 == 0) {
		int i = e * 2;
		sum += i;
	}
}
System.out.println(sum);
```

The stream can even be rewritten as:
```java
System.out.println(
	 numbers.stream()
		    .filter(e -> e % 2 == 0)
			.mapToInt(e -> e * 2)
			.sum());
```
When writing a `stream` always newline each operation in order to make it easy to understand what your code is doing.

Do not do:
```java
System.out.println(numbers.stream().filter(e -> e % 2 == 0).mapToInt(e -> e * 2).sum());
```

# Streams

## Parallelization
```java
public class Sample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		
        System.out.println(
				 // numbers.stream()
                 numbers.parallelStream()
                        .filter(e -> e % 2 == 0)
                        .mapToInt(Sample::compute)
                        .sum());
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
```

Using the above we can make an operation that takes ~5 seconds when done sequentially and instead do it in ~1 second using parallelStreams.

Before using parallelStreams you should keep in mind that just because you can use it, doesn't mean you should. You have to be very careful where you use it.

Using *parallelStreams* is telling the program that you want to use a many threads and resources as you can in order to get the answer faster. This can be very wasteful/inefficient.

Use `parallelStream` when:
* *It makes sense to use it* - the problem at hand *is* parallizable.
* *Willing to spend a lot more resources to get the answer faster*.
* *The data size is big enough to get a performance benefit*.
* *The task computation is big enough that you'll get a performance benefit*.

Just because you parallized doesn't mean you'll always get better performance, you've got to make sure you actually do (you can use a stopwatch function around the code for example).

## Stream as absraction
A stream is not a physical object with data. A set is data, a list is data, a map is data. A stream is set of functions you evaluate.

A stream is a nonmutating pipeline. The pipeline (.stream > .filter > .map > etc. ) of transformations to the data which flows through it.

## Stream operations
**filter** - Given an element, only give me elements where a condition is true.
* filter: 0 <= number of elements in the output <= number of elements input
* parameter: `Stream<T>` filter takes `Predicate<T>`
  - lambdas are backed by functional interfaces, filter takes the `Predicate<T>` interface.

```java
numbers.stream()
	   .filter(e -> e % 2 == 0)
	   .map(e - e * 2.0) // The return stream is of type float
	   .reduce(0.0, (carry, e) -> carry + e));
```

**map** - A transforming function. It transforms values.
* map: numper of output == number of input
  - no guarantee on the type of the output with respect to the type of the input.
* parameter: `Stream<T>` map takes `Function<T, R>` to return `Stream<R>`
  - map takes in the oringinal stream and starts a return stream (which can have a different type).

**reduce** - Takes an intial value and then performs the given operation on each element of the stream sequentially. 

So given a stream of sequential ints where the initial value of the reduce is 1 and the operation performed is multiplication it would perform:

>1 * 1 = 1, then 1 * 2 = 2, then 2 * 3 = 6, then 6 * 4 = 24, then ...

* reduce: `Stream<T>` takes two parameters.
* paramters:
  - first parameter is of type `T` (the type of the stream give to reduce)
  - second parameter is of type `BiFunction<R, T, R>` to produce a result of R.

Looking at the previous example, if we simplify it:
```java
numbers.stream()
	   .filter(e -> e % 2 == 0)
	   .mapToDouble(e -> e * 2.0)
	   .sum())
```
You can see that sum is actually just a special case of reduce.

Reduce may transform a stream into a single value, or it can transform it into a non-stream...

## Shared mutable state
Shared mutability works as follows:
- If two or more parties can change the same data (variables, objects, etc.).
- And if their lifetimes overlap.
- Then there is a risk of one party’s modifications preventing other parties from working correctly.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 1, 2, 3, 4, 5);

// double the even values and put that into a list.

// wrong way to do this.
List<Integer> doubleOfEven = new ArrayList<>();

numbers.stream()
	   .filter(e -> e % 2 == 0)
	   .map(e -> e * 2)
	   .forEach(e -> doubleOfEven.add(e)); //doubleOfEven is a shared variable
System.out.println(doubleOfEven); // bad
```
You are potentially going to be running this in a parallelStream and multiple threads might try to change the variable at the same time. You may lose some data doing this. You have concurrency problems and race conditions.

## Collect

There is a better way to do what we tried above while avoiding shared mutable state. 

>Note: Use `collect`, do not use `forEach` 'add to list.'

**collect** - `collect` is a `reduce` operation. It takes care of thread safety automatically.

**Example of collecting to a list**

**toList** is a function which will add every element from the stream it receives to a list and that will be returned by the stream. 

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 1, 2, 3, 4, 5);

// double the even values and put that into a List.

List<Integer> doubleOfEven = 
	numbers.stream()
		   .filter(e -> e % 2 == 0)
		   .map(e -> e * 2)
		   .collect(toList());
System.out.println(doubleOfEven); // [4, 8, 4, 8]
```
**Example of collecting to a Set**

**toSet** is an easy to understand function, returns a set instead of a list (reminder: a set has no duplicated elements).

```java
           // see previous example...
		   .collect(toSet());
System.out.println(doubleOfEven); // [4, 8]
```

**Example of collecting to a Map**

**toMap** will take two parameters. The first is for the Key of the map, the second is for the 

The **toMap()** method is a static method of the Collectors class which returns a Collector that accumulates elements into a Map whose keys and values are the result of applying the provided *mapping functions* to the input elements. 

>Note: Keys are unique and if in any case the keys are duplicated then an IllegalStateException is thrown when the collection operation is performed.

`toMap(Function keyMapper, Function valueMapper)`

The first parameter is the function used on the given element to determine the key.

The second parameter is the function used on the given element to determine the value.


```java
public class Sample {
    public static List<Person> createPeople() {
        return Arrays.asList(
            new Person("Sara", Gender.FEMALE, 20),
            new Person("Sara", Gender.FEMELE, 22),
            new Person("Bob", Gender.MALE, 20),
            new Person("Paula", Gender.FEMALE, 32),
            new Person("Paul", Gender.MALE, 32),
            new Person("Jack", Gender.MALE, 2),
            new Person("Jack", Gender.MALE, 72)
        );
    }

    publlic static void main(String[] args) {
        List<Person> people = createPeople();

        // create a Map with name and age as key, and the person as value
        System.out.println(
            people.stream()
                .collect(toMap(
                    // first parameter is a lambda that transforms the object to key-value pairs.
                    person -> person.getName() + "-" + person.getAge(),
                    // given a person, give back the person
                    person -> person)));
    }
}
```
In the example of collecting to a map the format "name-age" are used as unique keys. The value is a person object. 

**Example of using groupingBy**

The `groupingBy()` method of Collectors class in Java are used for grouping objects by some property and storing results in a Map instance. 

In order to use it, we always need to specify a property by which the grouping would be performed. *This method provides similar functionality to SQL’s GROUP BY clause.*

`groupingBy(Function classifier)` 

**Function** - It is the property which is to be applied to the input elements.
**Classifier** - It is used to map input elements into the destination map.
**Return value** - It returns a collector as a map.

```java
// Given a list of people, create a map where
// their name is the key and the value is all of the people with that name

System.out.println(
	people.stream()
	      .collect(groupingBy(Person::getName))); 
```


```java
import static java.util.stream.Collectors.*; 
// ...
// Given a list of people, create a map where
        // their name is the key and the value is the ages of all the people with that name

System.out.println(
	people.stream()
		  .collect(groupingBy(Person::getName,
		      mapping(Person::getAge, toList()))));
```

## Lazy Evaluation
One of the most important characteristics of Java streams is that they allow for significant optimizations through *lazy evaluations*.

**Lazy evaluation** is a strategy which delays the evaluation of an expression until its value is needed.

For streams, computation on the source data is only performed when the terminal operation is initiated, and source elements are consumed only as needed.

>Note: All intermediate operations are lazy, so they’re not executed until a result of a processing is actually needed.

Take for example:
```java
private static List<Integer> numbers = Arrays.asList(1, 2, 3, 5, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

public static void main(String[] args) {

	// Given an ordered list find the double of the first even number greater than 3
	System.out.println(
			numbers.stream()
					.filter(e -> e > 3)
					.filter(e -> e % 2 == 0)
					.map(e -> e * 2)
					.findFirst());
    }
```
The stream will evaluate as follows:
* The first element `1` is compared and is not greater than `3`
* The second element `2` is also not greater than `3`.
* The third element `3` is not greater than `3`.
* The fourth element `5` *is* greater than `3`.
  - The fourth element `5` is not even.
* The fifth element `4` *is* greater than `3`.
  - The fifth element `4` *is* even.
    - The fifth element `4` is then mapped to double its value and is now `8`.
	  - findFirst returns `8`.

The stream knows that the terminal operation is `findFirst()` so it doesn't waste time filtering and mapping every item in the stream. 

*Lazy evaluation* is implemented by evaluating the stream based on the terminal operation. 

If you never triggered the terminal operation 
it would never evaluate the *stream pipeline*.

Lazy evaluation is possible *only* if the functions don't have side effects. For example do not println inside of functions/lambdas executed in the *stream pipeline*.

## Characteristics of a stream
Characteristics:
* sized
  - sized - Has a predetermined size
  - unbounded - Stream is boundless 
* ordered
  - ordered - Has an order: first element, second element...
  - unordered - No order at all
* distinct
  - distinct - All elements distinct
  - non-distinct
* sorted
  - sorted
  - unsorted

`sorted()` - Sorts the elements of the stream
`distinct()` - Filters nondistinct stream elements

```java
numbers.stream()
	   .filter(e -> e % 2 == 0)
	   .distinct()
	   .sorted()
	   .forEach(System.out::println);
```

### Infinite Streams

```java
Stream.iterate(k, e -> e + 1)// unbounded, lazy
	  .filter(e -> e % 2 == 0) // unbounded, lazy
	  .filter(e -> Math.sqrt(e) > 20) // unbounded, lazy
	  .mapToInt(e -> e * 2) // unbounded, lazy
	  .limit(n) // sized, lazy
	  .sum();
```
Note: Any function that returns a stream from a stream then it is **lazy**. 

In the exampe above .sum() does **eager evaluation**.

You can gain efficiency by postponing evaluations until you can no longer postpone them (due to the way lazy evaluation works).

