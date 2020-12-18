package ru.tsedrik.lesson21;

import java.util.Random;

public class DataGenerationUtil {
    public static Person generatePerson(){
        Random random = new Random();

        int curAge = random.nextInt(101);
        String curName = generateName();
        Person curPerson = new Person(curAge, curName);

        return curPerson;
    }

    public static Pet generatePet(){
        Random random = new Random();
        String[] types = {"cat", "dog", "parrot", "turtle", "mouse", "snake"};
        String petType = types[random.nextInt(6)];
        String petName = generateName();
        Person petOwner = generatePerson();

        Pet newPet = new Pet.PetBuilder(petType, petName).setOwner(petOwner).build();
        return newPet;
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
}
