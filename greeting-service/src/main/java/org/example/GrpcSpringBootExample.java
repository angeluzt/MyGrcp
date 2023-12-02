package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootApplication
public class GrpcSpringBootExample {
    public static void main(String[] args) {
        //SpringApplication.run(GrpcSpringBootExample.class);

        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 8, 9, 10, 1, 2, 3);

        // find max number
        String response = data.stream()
                .max(Comparator.naturalOrder())
                .toString();
        System.out.println(response);

        // remove duplicates
        List<Integer> responseData = data.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("No duplicates: " + responseData);

        // remove duplicates
        response = data.stream()
                .min((a, b) -> {
                    System.out.println("a: " + a);
                    System.out.println("b: " + b);
                    return b;
                })
                .toString();
        System.out.println("min: " + response);

        response = data.stream()
                .reduce((a, b)-> a+b)
                .toString();
        System.out.println("reduce: " + response);


        new GrpcSpringBootExample().manageUsers();
    }

    public void manageUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Donovana", "a@something.com", 13, "USA"));
        users.add(new User("Donovana", "a@something.com", 13, "USA"));
        users.add(new User("Donovanb", "b@something.com", 13, "USA"));
        users.add(new User("Donovanc", "c@something.com", 13, "USA"));
        users.add(new User("Donovane", "d@something.com", 13, "USA"));
        users.add(new User("Marthaa", "aa@something.com", 13, "Italy"));
        users.add(new User("Marthab", "ab@something.com", 13, "Italy"));
        users.add(new User("Marthac", "ac@something.com", 13, "Italy"));
        users.add(new User("Rubinaa", "ad@something.com", 13, "Poland"));
        users.add(new User("Rubinab", "ae@something.com", 13, "Poland"));
        users.add(new User("Rubinac", "af@something.com", 13, "Poland"));
        users.add(new User("Rubinad", "ag@something.com", 13, "Poland"));
        users.add(new User("Rubinae", "ah@something.com", 13, "Poland"));
        users.add(new User("Rubinaf", "ai@something.com", 13, "Poland"));

        // considering duplicated users
        users.stream()
                .collect(Collectors.groupingBy(user -> user.country))
                .forEach((key, value) -> {
                    System.out.println(key + ": " + value.size());
                });

        // removing duplicated users
        users.stream()
                .distinct()
                .collect(Collectors.groupingBy(user -> user.country, Collectors.counting()))
                .forEach((key, value) -> {
                    System.out.println(key + ": " + value);
                });

        /* Exercise 1
            Use Java Streams to create a new list containing the lengths of each word.
            Data:
            List<String> words = Arrays.asList("apple", "banana", "orange", "pear", "grape");
         */
        List<String> words = Arrays.asList("apple", "banana", "orange", "pear", "grape");
        List<Integer> wordsResponse= words.stream()
                .map(word -> word.length())
                .collect(Collectors.toList());
        System.out.println("Words length: " + wordsResponse);

        /*
        Exercise 2:
            Find the sum of all even numbers in the list.
            Data:
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
         */

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        long response =  numbers.stream()
                        .reduce((a, b) -> a + b)
                                .get();
        System.out.println("Count response: " + response);

        /*
        Exercise 3:
            Create a new list containing only the names starting with the letter 'B'.
            Data:
            List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eva");
        */
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eva");
        names = names.stream()
                .filter(name -> name.startsWith("B"))
                .collect(Collectors.toList());
        System.out.println("Names with B: " + names);

        /*
        Exercise 4:
            Find the square of each number and collect them into a new list.
        Data:
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        */
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        integers = integers.stream()
                .map(number -> number*number)
                .collect(Collectors.toList());
        System.out.println("Numbers square: " + integers);

        /*
        Exercise 5:
            Check if all the fruits have a length greater than 3.
            Data:
            List<String> fruits = Arrays.asList("apple", "banana", "orange", "pear", "grape");
         */
        List<String> fruits = Arrays.asList("apple", "banana", "orange", "pearl", "grape");
        boolean fruitResponse = fruits.stream()
                .allMatch(fruit -> fruit.length() > 3);
        System.out.println("Are all fruits grater that length 3: " + fruitResponse);

        /*
        Exercise 6:
            Create a new list containing the first three letters of each word.
        Data:
        List<String> words = Arrays.asList("hello", "world", "java", "stream", "example");
        */
        List<String> wordsb = Arrays.asList("hello", "world", "java", "stream", "example");
        wordsb = wordsb.stream()
                .map(word -> word.substring(0, 3))
                .collect(Collectors.toList());
        System.out.println("Are all fruits grater that length 3: " + fruitResponse);

    }

    public void myCollector() {

    }
    class User {
        public String name;
        public String email;
        public int age;
        public String country;

        public User(String name, String email, int age, String country) {
            this.name = name;
            this.email = email;
            this.age = age;
            this.country = country;
        }

        public String getCountry() {
            return this.country;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return age == user.age && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(country, user.country);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, email, age, country);
        }
    }

}