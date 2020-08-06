package ru.tsedrik.lesson6.hometask1;

import java.util.Comparator;

public class WorkWithPetsCardFile {
    public static void main(String[] args) {
        PetsCardFile petsCardFile = new PetsCardFile();

        for (int i = 0; i < 10; i++){
            petsCardFile.addPet(DataGenerationUtil.generatePet());
        }

        System.out.println(petsCardFile.pets);

        Comparator<Pet> comparator = Comparator.comparing(Pet::getOwner).thenComparing(Pet::getName).thenComparing(Pet::getWeight);

        petsCardFile.printPets(comparator);
    }
}
