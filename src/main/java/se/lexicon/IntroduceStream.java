package se.lexicon;

import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntroduceStream {

    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Mehrdad", "Javan1", Gender.MALE, LocalDate.parse("1989-02-27"), false));
        persons.add(new Person("Mehrdad", "Javan2", Gender.MALE, LocalDate.parse("1989-02-27"), false));
        persons.add(new Person("Simon", "Elnbrink", Gender.MALE, LocalDate.parse("1992-01-01"), false));

        Predicate<Person> leapYear = person -> person.getBirthDate().isLeapYear();

        persons.stream() // source
                .filter(leapYear) // intermediate operation
                .forEach(person -> System.out.println(person)); // terminal operation
        //.forEach(System.out::println); // terminal operation - method references

        System.out.println("------------------------");
       List<Person> personsWithNameMehrdad =  persons.stream()
               .filter(person -> person.getFirstName().equals("Mehrdad"))
               .collect(Collectors.toList());
        personsWithNameMehrdad.forEach(System.out::println);
        System.out.println("------------------------");

        // Creating an empty stream
        Stream<String> empty = Stream.empty();
        // Creating a stream on array with single element
        Stream<Integer> singleElement = Stream.of(1);
        // Creating a stream on array with multiple elements
        Stream<Double> fromArray = Stream.of(1.1, 1.3, 55.11);

        String[] names = {"Mehrdad", "Ulf"};
        Stream<String> namesStream = Arrays.stream(names);

    }
}
