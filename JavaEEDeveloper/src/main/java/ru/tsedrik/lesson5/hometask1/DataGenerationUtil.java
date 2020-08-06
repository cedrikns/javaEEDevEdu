package ru.tsedrik.lesson5.hometask1;

import java.util.Random;

public class DataGenerationUtil {
    public static Person generatePerson(){
        Random random = new Random();

        Sex curSex = Sex.values()[random.nextInt(2)];
        int curAge = random.nextInt(101);
        String curName = generateName();
        Person curPerson = new Person(curAge, curSex, curName);

        return curPerson;
    }

    public static Pet generatePet(){
        Random random = new Random();
        String petName = generateName();
        Person petOwner = generatePerson();
        double petWeight = random.nextDouble()*10 + 0.5;

        Pet newPet = new Pet(petName, petOwner, petWeight);
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
