package ru.tsedrik.lesson15.hometask1;

import java.time.LocalDate;

public class WorkWithPetsCardFile {
    public static void main(String[] args) {

        PetsCardFile petsCardFile = PetsCardFile.getInstance();

        for (int i = 0; i < 10; i++){
            petsCardFile.addPet(DataGenerationUtil.generatePet());
        }

        Pet newPet = new Pet.PetBuilder("cat", "Мурзик")
                .setBirthday(LocalDate.of(2018, 12, 04))
                .setBreed("Русская голубая")
                .setCountryOfBirth("Россия")
                .build();

        System.out.println(newPet);
        petsCardFile.addPet(newPet);
    }
}
