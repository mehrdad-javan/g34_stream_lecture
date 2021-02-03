package se.lexicon;

import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamOperationExample {

    public static void main(String[] args) {
        ex16();
    }

    // Terminal Operation
    // count(): determines the amount of elements in a stream
    public static void ex1() {
        // Create a Stream
        Stream<String> skills = Stream.of("Java", "C#", "Python", "C++");
        long count = skills.count();
        System.out.println("Count: " + count);
    }

    // TO
    // max() and min(): are reduction operation and used for determine min and max - return optional container
    public static void ex2() {
        List<Integer> numbers = Arrays.asList(2, 100, 12, 1000, 20000);
        /*
        Comparator<Integer> comparatorBasic = (o1, o2) -> {
            if (o1 > o2) {
                return 1;
            } else if (o1 < o2) {
                return -1;
            } else {
                return 0;
            }
        };*/

        Comparator<Integer> comparatorLambda = (o1, o2) -> o1 - o2;

        // optional is a container object that we can use it for checking not-null objects
        Optional<Integer> minResult = numbers.stream().min(comparatorLambda);
        if (minResult.isPresent()) {
            System.out.println("min result: " + minResult.get());
        }

        Optional<Integer> maxResult = numbers.stream().max(comparatorLambda);
        if (maxResult.isPresent()) {
            // we cannot get null pointer exception
            System.out.println("Max Result: " + maxResult.get());
        }

    }

    // TO
    // findFirst() and findAny(): are used to find first element
    public static void ex3() {
        List<String> names = Arrays.asList("Niclas", "Erik", "Ulf", "Kent", "Fredrik");
        Optional<String> findFirstElement = names.stream().findFirst();
        System.out.println("findFirstElement = " + findFirstElement.orElse("Not found"));
        System.out.println("firstElement using findAny(): " + names.stream().findAny().orElse("Empty"));

        //boolean result = findFirstElement.isPresent();
        //System.out.println("result = " + result);
    }

    // TO
    // allMatch(), anyMatch(), noneMatch():
    public static void ex4() {
        List<String> names = Arrays.asList("Niclas", "Erik", "Ulf", "Kent", "Fredrik");
        Predicate<String> myCondition = s -> s.length() > 5;

        // means all elements should match or have the same length
        boolean allMatchResult = names.stream().allMatch(myCondition);
        System.out.println("allMatchResult = " + allMatchResult); // false
        // at least one element should be matched
        boolean anyMatchResult = names.stream().anyMatch(myCondition);
        System.out.println("anyMatchResult = " + anyMatchResult); // true

        // no element in the stream should be matched
        boolean nonMatchResult = names.stream().noneMatch(myCondition);
        System.out.println("nonMatchResult = " + nonMatchResult); // false

    }

    //TO
    // foreEach()
    public static void ex5() {
        List<String> names = Arrays.asList("Niclas", "Erik", "Ulf", "Kent", "Fredrik");
        //Consumer<String> printString = s -> System.out.println(s);
        //Consumer<String> printString = System.out::println;

        //names.stream().forEach(s -> System.out.println(s));
        //names.stream().forEach(printString);
        names.stream().forEach(System.out::println);
    }

    // TP
    // reduce() is used to combine a stream into a single object
    public static void ex6() {
        List<String> words = Arrays.asList("I", "love", "programming");

        String sentence = words.stream().reduce("", (s1, s2) -> s1 + " " + s2); // I love programming
        System.out.println(sentence);

        List<Integer> numbers = Arrays.asList(2, 3, 4, 5);
        int result = numbers.stream().reduce(1, (n1, n2) -> n1 * n2);
        System.out.println(result);
    }

    // TP
    // collect() advance:
    public static void ex7() {
        List<String> words = Arrays.asList("Collect", "Is", "Useful");

        //Supplier<StringBuilder> supplier = () -> new StringBuilder();
        //BiConsumer<StringBuilder, String> accumulator = (stringBuilder, s) -> stringBuilder.append(" ").append(s);
        //BiConsumer<StringBuilder, StringBuilder> combiner = (stringBuilder1, stringBuilder2) -> stringBuilder1.append(stringBuilder2);
        //StringBuilder result = words.stream().collect(supplier, accumulator, combiner);

        StringBuilder result = words.stream().collect(StringBuilder::new, (stringBuilder, s) -> stringBuilder.append(" ").append(s), StringBuilder::append);

        System.out.println("result = " + result); // Collect Is Useful

    }

    // TP
    // collect() simple:
    public static void ex8() {
        List<String> names = Arrays.asList("Niclas", "Erik", "Ulf", "Kent", "Fredrik", "Fredrik");
        Set<String> set = names.stream().collect(Collectors.toSet());
        set.forEach(System.out::println);
        System.out.println("--------------------");
        List<String> list = names.stream().collect(Collectors.toList());
        list.forEach(s -> System.out.println(s));
    }


    // Intermediate Operation
    // filter()
    public static void ex9() {
        List<Integer> numbers = Arrays.asList(1, -7, 10, 26, -123, 32, 11, 7, 19);
        Predicate<Integer> even = n -> n % 2 == 0;
        Stream<Integer> newStream = numbers.stream().filter(even);
        newStream.forEach(System.out::println);
        System.out.println("------------------");
        numbers.stream().filter(i -> i > 0).forEach(System.out::println);
    }

    // filter
    // *******************
    public static void ex10() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Mehrdad", "Javan1", Gender.MALE, LocalDate.parse("1989-02-27"), false));
        persons.add(new Person("Mehrdad", "Javan2", Gender.MALE, LocalDate.parse("1989-02-27"), false));
        persons.add(new Person("Simon", "Elnbrink", Gender.MALE, LocalDate.parse("1992-01-01"), false));
        persons.add(new Person("Test", "Test", Gender.FEMALE, LocalDate.parse("1992-01-01"), false));
        persons.add(new Person("Test2", "Test2", Gender.FEMALE, LocalDate.parse("1992-01-01"), false));

        Predicate<Person> maleCondition = person -> person.getGender() == Gender.MALE;
        Predicate<Person> personNameStartMCondition = person -> person.getFirstName().startsWith("M");
        System.out.println("---------------------");
        persons.stream().
                filter(maleCondition). // it returns new stream
                filter(personNameStartMCondition).
                forEach(System.out::println);
        System.out.println("---------------------");
        List<Person> femalePersons = persons.stream().
                filter(person -> person.getGender() == Gender.FEMALE). // intermediate operation
                collect(Collectors.toList()); // terminal operation
        femalePersons.forEach(System.out::println);
    }

    // Intermediate Operation
    // limit and skip
    public static void ex11() {
        List<String> names = Arrays.asList("Erik", "Ulf", "Niclas", "Fredrik", "Marcus", "Kent");
        names.stream().
                skip(3).
                limit(2).
                forEach(System.out::println);
    }

    // Intermediate Operation
    // limit and skip
    public static void ex12() {
        int max = 10;
        int min = 5;
        int length = 10;
        Stream<Integer> integers = Stream.generate(() -> new Random().nextInt((max - min) + 1) + min);

        List<Integer> numbersBetween5To10 = integers.limit(length).collect(Collectors.toList());
        numbersBetween5To10.forEach(System.out::println);
    }

    // Intermediate Operation
    // limit and skip
    public static void ex13() {
        Stream<Integer> integerStream = Stream.iterate(1, i -> i + 1);
        integerStream.limit(20).forEach(System.out::println);
    }

    // map Intermediate Operation
    //******************
    public static void ex14() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Mehrdad", "Javan1", Gender.MALE, LocalDate.parse("1989-02-27"), false));
        persons.add(new Person("Mehrdad", "Javan2", Gender.MALE, LocalDate.parse("1989-02-27"), false));
        persons.add(new Person("Simon", "Elnbrink", Gender.MALE, LocalDate.parse("1992-01-01"), false));
        persons.add(new Person("Test", "Test", Gender.FEMALE, LocalDate.parse("1992-01-01"), false));
        persons.add(new Person("Test2", "Test2", Gender.FEMALE, LocalDate.parse("1992-01-01"), false));

        Function<Person, LocalDate> birthDateFunction = person -> person.getBirthDate();

        List<LocalDate> birthDateList = persons.stream()
                .map(birthDateFunction) // map Person Object to LocalDate
                .collect(Collectors.toList());
        birthDateList.forEach(System.out::println);
        System.out.println("------------------------------------");
        Function<Person, String> fullName = person -> person.getFirstName() + " " + person.getLastName();
        List<String> fullNames = persons.stream() // source
                .map(fullName) // intermediate operation
                .collect(Collectors.toList()); // terminal operation
        fullNames.forEach(System.out::println);
        System.out.println("--------------------------------------");
        List<String> femaleLastNames = persons.stream().
                filter(person -> person.getGender() == Gender.FEMALE)
                .map(person -> person.getLastName())
                .collect(Collectors.toList());
        //for (String lastname : femaleLastNames) {
        //    System.out.println("lastname = " + lastname);
        //}
        femaleLastNames.forEach(System.out::println);


    }


    // sorted() operation
    public static void ex15() {
        List<Person> persons = Arrays.asList(
                new Person("Erik", "Svensson", Gender.MALE, LocalDate.parse("1976-09-11"), false),
                new Person("Emil", "Svensson", Gender.MALE, LocalDate.parse("2002-10-23"), false),
                new Person("Elin", "Karlsson", Gender.MALE, LocalDate.parse("1987-03-11"), false),
                new Person("Jonas", "Andersson", Gender.MALE, LocalDate.parse("1973-02-20"), false)
        );

        persons.stream()
                .sorted((o1, o2) -> o1.getBirthDate().compareTo(o2.getBirthDate()))
                .forEach(System.out::println);
        System.out.println("----------------------------------");

        Comparator<Person> lastName = (o1, o2) -> o1.getLastName().compareTo(o2.getLastName());
        Comparator<Person> firstName = (o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName());

        persons.stream()
                .sorted(lastName.thenComparing(firstName))
                .forEach(System.out::println);
    }


    public static void ex16(){
        Stream<String> words = Stream.of("Ulf", "Niclas", "Erik", "Kent", "Marcus", "Fredrik");
       List<String> wordsContainsI = words.filter( s -> s.contains("i"))
                //.peek(System.out::println) // show output
               .collect(Collectors.toList());
        System.out.println("--------------------");
        wordsContainsI.forEach(System.out::println);
    }

}
