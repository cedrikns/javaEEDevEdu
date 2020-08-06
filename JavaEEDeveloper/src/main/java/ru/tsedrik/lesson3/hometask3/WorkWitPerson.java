package ru.tsedrik.lesson3.hometask3;

import ru.tsedrik.lesson3.hometask3.exceptions.DublicatePersonException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class WorkWitPerson {
    public static Person[] generatePersonArray(int count){
        Person[] people = new Person[count];
        HashSet<Person> set = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < count; i++){
            Sex curSex = Sex.values()[random.nextInt(2)];
            int curAge = random.nextInt(101);
            String curName = generateName();
            Person curPerson = new Person(curAge, curSex, curName);
            if (set.contains(curPerson)) {
                throw new DublicatePersonException();
            }
            set.add(curPerson);
            people[i] = curPerson;
        }

        return people;
    }

    private static String generateName(){
        Random random = new Random();
        int nameLength = random.nextInt(11) + 5;
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nameLength; i++){
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Person[] people = generatePersonArray(1000);
//        Sorter sorter = new MergeSorter();
        Sorter sorter = new BubbleSorter();
        long startTime = System.currentTimeMillis();
        sorter.sort(people);
        long endTime = System.currentTimeMillis();
        System.out.println("Sorted array:");
        System.out.println(Arrays.toString(people));
        System.out.println("Time of sorting the array: " + (endTime - startTime) + " milliseconds");
     }
}
